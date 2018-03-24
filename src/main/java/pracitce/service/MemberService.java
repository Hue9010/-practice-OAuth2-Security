package pracitce.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pracitce.UnAuthenticationException;
import pracitce.dto.MemberDto;
import pracitce.model.Member;
import pracitce.repository.MemberRepository;

@Service
public class MemberService {
	
	@Resource(name ="memberRepository")
	private MemberRepository memberRepository;
	
	public void create(Member user) {
		memberRepository.save(user);
	}
	
	public Member login(MemberDto user) {
		Member loginedMember = memberRepository.findByMemberId(user.getMemberId());
		if(!loginedMember.login(user.toMember())) {
			throw new UnAuthenticationException();
		}
		return loginedMember;
	}

	public List<MemberDto> allList() {
		return toMemberDtoList(memberRepository.findAll());
	}

	private List<MemberDto> toMemberDtoList(List<Member> members) {
		List<MemberDto> memberDtos = new ArrayList();
		for (Member member : members) {
			memberDtos.add(member.toMemberDto());
		}
		return memberDtos;
	}
}
