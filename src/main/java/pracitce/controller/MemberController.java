package pracitce.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pracitce.dto.MemberDto;
import pracitce.repository.MemberRepository;
import pracitce.service.MemberService;

@Controller
public class MemberController {

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@GetMapping("/users/login")
	public String loginform() {
		return "/users/login";
	}
	
	@PostMapping("/users/login")
	public String login(MemberDto user, HttpSession session) {
		session.setAttribute("loginedUser", memberService.login(user));
		return "redirect:/";
	}
	
	@GetMapping("/users/create")
	public String createForm() {
		return "/users/create";
	}
	
	@PostMapping("/users/create")
	public String create(MemberDto user) {
		memberService.create(user.toMember());
		return "redirect:/";
	}
	
	@GetMapping("/users/list")
	public String list(HttpSession session, Model model) {
		model.addAttribute("users", memberService.allList());
		return "/users/list";
	}
	
}
