package practice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import practice.dto.MemberDto;
import practice.model.Member;
import practice.model.MemberRole;
import practice.repository.MemberRepository;

@Service
public class MemberService {
	
	@Resource(name ="memberRepository")
	private MemberRepository memberRepository;
	
	public void create(Member member) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setRoles(Lists.newArrayList(new MemberRole(MemberRole.USER)));
		memberRepository.save(member);
	}

	public List<MemberDto> allList() {
		return toMemberDtoList(memberRepository.findAll());
	}

	private List<MemberDto> toMemberDtoList(List<Member> members) {
		List<MemberDto> memberDtos = new ArrayList<>();
		for (Member member : members) {
			memberDtos.add(member.toMemberDto());
		}
		return memberDtos;
	}
}
