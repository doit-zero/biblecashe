package hello.biblecashe.repository;

import hello.biblecashe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUserId(String userId);
}