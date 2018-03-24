package practice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import practice.UnAuthenticationException;
import practice.dto.MemberDto;
import practice.model.Member;
import practice.repository.MemberRepository;

@Service
public class MemberService {
	
	@Resource(name ="memberRepository")
	private MemberRepository memberRepository;
	
	public void create(Member user) {
		memberRepository.save(user);
	}
	
	public Member login(MemberDto user) throws UnAuthenticationException{
		Member loginedMember = memberRepository.findByMemberId(user.getMemberId());
		if(!loginedMember.login(user.toMember())) {
			throw new UnAuthenticationException("아이디 혹은 비밀번호를 잘못 치셨습니다.");
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
