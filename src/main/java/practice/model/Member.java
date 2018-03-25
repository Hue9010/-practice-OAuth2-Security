package practice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import practice.dto.MemberDto;

@Entity
public class Member {

	@Id
	@GeneratedValue
	private long id;

	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20)
	private String memberId;

	@Column(nullable = false)
	private String password;

	@Size(min = 6, max = 50)
	@Column(unique = true, nullable = false, length = 50)
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="member")
	private List<MemberRole> roles = new ArrayList<>();

	public Member() {
	}
	
	public Member(String memberId, String password, String email) {
		this(0L, memberId, password, email);
	}

	public Member(long id, String memberId, String password, String email) {
		this.id = id;
		this.memberId = memberId;
		this.password = password;
		this.email = email;
	}

	public boolean login(Member loginedMember) {
		if (!loginedMember.matchMemberId(memberId)) {
			return false;
		}
		return loginedMember.matchPassword(password);
	}

	private boolean matchPassword(String loginedPassword) {
		return password.equals(loginedPassword);
	}

	private boolean matchMemberId(String loginedId) {
		return memberId.equals(loginedId);
	}

	public MemberDto toMemberDto() {
		return new MemberDto(memberId, password, email);
	}

	public String getMemberId() {
		return memberId;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<MemberRole> getRoles() {
		return roles;
	}

	public void setRoles(List<MemberRole> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
