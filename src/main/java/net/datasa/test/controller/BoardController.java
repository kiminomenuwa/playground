package net.datasa.test.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;

/**
 * 거래 게시판 관련 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("list")
	public String list() {
		return "/board/list";
	}
	
	@GetMapping("write")
	public String write() {
		return "/board/writeForm";
	}
	
	@PostMapping("write")
	public String write(
			@ModelAttribute BoardDTO boardDTO
			, @AuthenticationPrincipal AuthenticatedUser user) {
		boardDTO.setMemberId(user.getId());
		
		boardService.write(boardDTO);
		return "/board/list";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name="boardNum") Integer boardNum
			, Model model) {
		
		BoardDTO dto = boardService.getBoard(boardNum);
		
		model.addAttribute("board", dto);
		return "/board/read";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam("boardNum") Integer boardNum
			, @AuthenticationPrincipal AuthenticatedUser user) {
		
		boardService.delete(boardNum, user.getUsername());
		
		return "/board/list";
	}
	
}
