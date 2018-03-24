package pracitce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pracitce.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByMemberId(String memberId);
}
