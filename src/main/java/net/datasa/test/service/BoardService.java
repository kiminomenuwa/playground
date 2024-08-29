package net.datasa.test.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.domain.entity.BoardEntity;
import net.datasa.test.domain.entity.MemberEntity;
import net.datasa.test.domain.entity.ReplyEntity;
import net.datasa.test.repository.BoardRepository;
import net.datasa.test.repository.MemberRepository;
import net.datasa.test.repository.ReplyRepository;

/**
 * 게시판 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	public void write(BoardDTO dto) {
		MemberEntity memberEntity = memberRepository.findById(dto.getMemberId())
				.orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
		
		BoardEntity entity = BoardEntity.builder()
				.member(memberEntity)
				.category(dto.getCategory())
				.title(dto.getTitle())
				.contents(dto.getContents())
				.price(dto.getPrice())
				.soldout(false)
				.buyer(null)
				.build();
		
		log.debug("저장되는 글 : {}", entity);
		boardRepository.save(entity);
	}
	
	public BoardDTO entityToDTO(BoardEntity entity) {
		return BoardDTO.builder()
				.boardNum(entity.getBoardNum())
				.memberId(entity.getMember().getMemberId())
				.category(entity.getCategory())
				.title(entity.getTitle())
				.contents(entity.getContents())
				.price(entity.getPrice())
				.soldout(entity.getSoldout())
				.buyer(entity.getBuyer() != null ? entity.getBuyer().getMemberId() : "")
				.inputDate(entity.getInputDate())
				.build();
	}
	
	public List<BoardDTO> getList() {
		Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
		List<BoardEntity> entityList = boardRepository.findAll(sort);
		
		List<BoardDTO> dtoList = new ArrayList<>();
		
		for (BoardEntity entity : entityList) {
			
			BoardDTO dto = entityToDTO(entity);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	public List<BoardDTO> searchList(String category) {
		Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
	    List<BoardDTO> dtoList = new ArrayList<>();
	    
	    if (category.equals("전체")) {
	        dtoList = getList();
	    } else {
	        List<BoardEntity> entityList = boardRepository.findByCategory(category, sort);
	        
	        for (BoardEntity entity : entityList) {
	            BoardDTO dto = entityToDTO(entity);
	            dtoList.add(dto);
	        }
	    }
	    
	    return dtoList;
	}

    public List<BoardDTO> searchListByKeyword(String category, String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
        List<BoardDTO> dtoList = new ArrayList<>();

        if (category.equals("전체")) {
            List<BoardEntity> entityList = boardRepository.findByTitleContaining(keyword, sort);
            for (BoardEntity entity : entityList) {
                BoardDTO dto = entityToDTO(entity);
                dtoList.add(dto);
            }
        } else {
            List<BoardEntity> entityList = boardRepository.findByCategoryAndTitleContaining(category, keyword, sort);
            for (BoardEntity entity : entityList) {
                BoardDTO dto = entityToDTO(entity);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

	public BoardDTO getBoard(Integer boardNum) {
		
		BoardEntity entity = boardRepository.findById(boardNum)
				.orElseThrow(() ->  new EntityNotFoundException("해당 번호의 글이 없습니다"));
		
		BoardDTO dto = entityToDTO(entity);
		
		return dto;
	}

	public void delete(Integer boardNum, String username) {
		BoardEntity boardEntity = boardRepository.findById(boardNum)
				.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
		
		if (!boardEntity.getMember().getMemberId().equals(username)) {
			throw new RuntimeException("삭제 권한이 없습니다");
		}
		
		
		boardRepository.delete(boardEntity);
	}

	public void replyWrite(ReplyDTO replyDTO) {
        MemberEntity memberEntity = memberRepository.findById(replyDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 아이디가 없습니다."));

        BoardEntity boardEntity = boardRepository.findById(replyDTO.getBoardNum())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
        
        ReplyEntity entity = ReplyEntity.builder()
                .board(boardEntity)
                .member(memberEntity)
                .replyText(replyDTO.getReplyText())
                .build();

        replyRepository.save(entity);
        
	}

	public void buyUpdate(Integer boardNum, String id) {
	    BoardEntity boardEntity = boardRepository.findById(boardNum)
	            .orElseThrow(() -> new EntityNotFoundException("해당 글이 없습니다."));
	    
	    MemberEntity buyer = memberRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));

	    boardEntity.setSoldout(true);
	    boardEntity.setBuyer(buyer);
	}
	
}
