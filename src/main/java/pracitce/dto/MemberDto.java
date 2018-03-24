package pracitce.dto;

import lombok.Setter;
import pracitce.model.Member;
import lombok.Getter;

@Getter
@Setter
public class MemberDto {
	private String memberId;
	private String password;
	private String email;

	public MemberDto(String memberId, String password, String email) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
	}

	public Member toMember() {
		return new Member(memberId, password, email);
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
