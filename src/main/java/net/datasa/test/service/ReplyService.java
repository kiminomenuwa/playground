package net.datasa.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.domain.entity.ReplyEntity;
import net.datasa.test.repository.ReplyRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	
	public ReplyDTO entityToDTO(ReplyEntity entity) {
		return ReplyDTO.builder()
				.replyNum(entity.getReplyNum())
				.boardNum(entity.getBoard().getBoardNum())
				.memberId(entity.getMember().getMemberId())
				.replyText(entity.getReplyText())
				.build();
	}
	
	public List<ReplyDTO> getListByBoardNum(Integer boardNum) {
	    List<ReplyEntity> entityList = replyRepository.findByBoard_BoardNum(boardNum);
	    
	    List<ReplyDTO> dtoList = new ArrayList<>();
	    for (ReplyEntity entity : entityList) {
	        ReplyDTO dto = entityToDTO(entity);
	        dtoList.add(dto);
	    }
	    return dtoList;
	}

}
