package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import practice.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByMemberId(String memberId);
}
