package practice.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import practice.dto.MemberDto;
import practice.service.MemberService;

@Controller
public class MemberController {
	private final static Logger log = LoggerFactory.getLogger(MemberController.class);

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@GetMapping("/users/login")
	public String loginform() {
		return "/users/login";
	}
	
	@GetMapping("/users/create")
	public String createForm() {
		return "/users/create";
	}
	
	@PostMapping("/users/create")
	public String create(MemberDto user) {
		log.debug("memberDto : {}", user);
		memberService.create(user.toMember());
		return "redirect:/";
	}
	
	@GetMapping("/users/list")
	public String list(HttpSession session, Model model) {
		model.addAttribute("users", memberService.allList());
		return "/users/list";
	}
	
}
