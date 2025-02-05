package hello.biblecashe.repository;

import hello.biblecashe.entity.Member;
import hello.biblecashe.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TalentRepository extends JpaRepository<Talent, Long> {
    @Query("SELECT t FROM Talent t WHERE t.member.idx = :memberIdx")
    Optional<Talent> findByMemberIdx(@Param("memberIdx") Long memberIdx);
}
