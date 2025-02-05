package hello.biblecashe.repository;

import hello.biblecashe.entity.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RangeRepository extends JpaRepository<Range,Long> {

    Optional<Range> findByUpdatedAt(LocalDate dateTime);

    @Query(value = "SELECT * FROM range ORDER BY idx DESC LIMIT 1", nativeQuery = true)
    Range findMaxIdxRange();
}