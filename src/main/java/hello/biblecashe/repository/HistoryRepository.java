package hello.biblecashe.repository;

import hello.biblecashe.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Long> {
    List<History> findByMemberIdx(Long memberIdx);
}
