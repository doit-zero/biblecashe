package hello.biblecashe.repository;

import hello.biblecashe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUserId(String userId);

    @Query(value = """
    WITH Ranked AS (
        SELECT name, member_group, read_count,
               DENSE_RANK() OVER (ORDER BY read_count DESC) AS ranking
        FROM member
    )
    SELECT name, member_group, read_count ,ranking 
    FROM Ranked 
    WHERE ranking <= 5 
    ORDER BY ranking
""", nativeQuery = true)
    List<Object[]> findTop5ByReadCountWithRanking();

    @Query(value = """
    SELECT member_group, SUM(read_count) AS total_read_count
    FROM member
    GROUP BY member_group
    ORDER BY total_read_count DESC
""", nativeQuery = true)
    List<Object[]> getReadCountSumByGroup();

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.readCount = COALESCE(m.readCount, 0) + :increment WHERE m.idx = :idx")
    void incrementReadCount( Long idx,  int increment);
}