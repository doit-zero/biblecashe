package hello.biblecashe.repository;

import hello.biblecashe.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<Talent, Long> {
}
