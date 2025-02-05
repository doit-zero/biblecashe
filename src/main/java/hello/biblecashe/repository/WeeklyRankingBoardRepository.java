package hello.biblecashe.repository;

import hello.biblecashe.entity.WeeklyRankingBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyRankingBoardRepository extends JpaRepository<WeeklyRankingBoard,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE WeeklyRankingBoard w SET w.readCount = w.readCount + :readCount, w.readTime = :readTime WHERE w.member.idx = :memberIdx")
    int updateReadCountAndTime(Long memberIdx, int readCount, LocalDate readTime);

    Optional<WeeklyRankingBoard> findByMemberIdx(Long idx);

    @Transactional
    @Modifying
    @Query("UPDATE WeeklyRankingBoard w SET w.readCount = 0")
    void resetWeeklyRankingBoard();

    @Query(value = """
    WITH Ranked AS (
        SELECT m.name, m.member_group, w.read_count,
               DENSE_RANK() OVER (ORDER BY w.read_count DESC) AS ranking
        FROM weekly_ranking_board w
        JOIN member m ON w.member_idx = m.idx
        WHERE w.read_time BETWEEN :startOfWeek AND :endOfWeek
    )
       SELECT name, member_group,read_count,ranking
    FROM Ranked
    WHERE ranking <= 5
    ORDER BY ranking
""", nativeQuery = true)
    List<Object[]> findTop5ByReadCountWithRanking(
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );

}