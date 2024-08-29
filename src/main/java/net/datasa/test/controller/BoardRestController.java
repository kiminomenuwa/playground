package net.datasa.test.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;
import net.datasa.test.service.ReplyService;

/**
 * 거래 게시판 Ajax 요청 처리 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardRestController {
	

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("listAll")
    public List<BoardDTO> listAll() {
        List<BoardDTO> list = boardService.getList();
        return list;
    }

    @GetMapping("searchByCategory")
    public List<BoardDTO> searchByCategory(@RequestParam("searchCategory") String searchCategory) {
        List<BoardDTO> list = boardService.searchList(searchCategory);
        return list;
    }

    // 추가된 부분: 키워드 검색 기능
    @GetMapping("searchByKeyword")
    public List<BoardDTO> searchByKeyword(@RequestParam("searchCategory") String searchCategory,
                                          @RequestParam("searchWord") String searchWord) {
        List<BoardDTO> list = boardService.searchListByKeyword(searchCategory, searchWord);
        return list;
    }
    
    @GetMapping("getReplyList")
    public List<ReplyDTO> getReplyList(@RequestParam("boardNum") Integer boardNum) {
        List<ReplyDTO> list = replyService.getListByBoardNum(boardNum);
        return list;
    }
    
    @PostMapping("replyWrite")
    public List<ReplyDTO> replyWrite(@ModelAttribute ReplyDTO replyDTO
            , @AuthenticationPrincipal AuthenticatedUser user) {
        replyDTO.setMemberId(user.getUsername());
        boardService.replyWrite(replyDTO);
        
        List<ReplyDTO> list = replyService.getListByBoardNum(replyDTO.getBoardNum());
        		
        return list;
    }
    
    @GetMapping("buy")
    public void buyUpdate(
            @RequestParam("boardNum") Integer boardNum,
            @AuthenticationPrincipal AuthenticatedUser user) {
        boardService.buyUpdate(boardNum, user.getId());
    }
}
