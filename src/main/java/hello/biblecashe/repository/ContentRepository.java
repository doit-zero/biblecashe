package hello.biblecashe.repository;

import hello.biblecashe.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    List<Content> findByIdxBetween(int startIdx, int endIdx);
}