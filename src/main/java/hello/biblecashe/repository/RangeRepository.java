package hello.biblecashe.repository;

import hello.biblecashe.entity.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RangeRepository extends JpaRepository<Range,Long> {

    Range findTopByOrderByUpdatedAtDesc();

    //Optional<Range> findByUpdatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<Range> findByUpdatedAt(LocalDate dateTime);
}