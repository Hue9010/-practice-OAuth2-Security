package practice.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import practice.model.Member;
import practice.model.SecurityMember;
import practice.repository.MemberRepository;

@Service("securityMemberService")
public class SecurityMemberService implements UserDetailsService {
	@Resource(name="memberRepository")
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByMemberId(username);
		return new SecurityMember(member);
	}

}
