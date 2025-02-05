package hello.biblecashe.repository;

import hello.biblecashe.entity.Talent;
import hello.biblecashe.entity.TalentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalentHistoryRepository extends JpaRepository<TalentHistory,Long> {
    //List<TalentHistory> findAllById(Optional<Talent> findTalent);

    @Query("SELECT t FROM TalentHistory t WHERE t.talent.idx = :talentIdx")
    List<TalentHistory> findAllById(Long talentIdx);
}
