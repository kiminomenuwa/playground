package net.datasa.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.MemberDTO;
import net.datasa.test.service.MemberService;

/**
 * 회원 관련 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("member")
public class MemberController {
	
	private final MemberService memberService;
   
	@GetMapping("loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@GetMapping("join")
	public String join() {
		return "member/joinForm";
	}
	
	@PostMapping("join")
	public String join(@ModelAttribute MemberDTO member) {
		log.debug("전달된 가입정보 : {}", member);
		
		memberService.join(member);
		return "redirect:/";
	}
}
