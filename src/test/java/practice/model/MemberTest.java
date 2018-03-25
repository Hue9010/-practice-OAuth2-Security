package practice.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import practice.model.Member;

public class MemberTest {
	Member member;

	@Before
	public void setup() {
		member = new Member("memberId", "password", "email");
	}

	@Test
	public void 로그인_성공() {
		Member loginedMember = new Member("memberId", "password", "email");
		assertTrue(member.login(loginedMember));
	}
	
	@Test
	public void 로그인_Id_틀림() {
		Member loginedMember = new Member("틀린_아이디", "password", "email");
		assertFalse(member.login(loginedMember));
	}
	
	@Test
	public void 로그인_비밀번호_틀림() {
		Member loginedMember = new Member("memberId", "패스워드으으", "email");
		assertFalse(member.login(loginedMember));
	}

}
