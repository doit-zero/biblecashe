package hello.biblecashe.repository;

import hello.biblecashe.dto.ContentDto;
import hello.biblecashe.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    List<Content> findByIdxBetween(int startIdx, int endIdx);

    @Query(value = "SELECT DISTINCT book, chapter " +
            "FROM content " +
            "WHERE (book > :recentBook) OR (book = :recentBook AND chapter > :recentEndChapter) " +
            "ORDER BY book, chapter " +
            "LIMIT 5", nativeQuery = true)
    List<Map<String,Integer>> findNextChapters(@Param("recentBook") int recentBook,
                                               @Param("recentEndChapter") int recentEndChapter);

    @Query(
            value = "SELECT c.book, c.chapter, c.long_label, c.paragraph, c.sentence " +
                    "FROM (" +
                    "   SELECT DISTINCT book, chapter " +
                    "   FROM content " +
                    "   WHERE (book > :recentBook) OR (book = :recentBook AND chapter > :recentEndChapter) " +
                    "   ORDER BY book, chapter " +
                    "   LIMIT 5" +
                    ") t " +
                    "LEFT JOIN content c ON c.chapter = t.chapter AND c.book = t.book " +
                    "ORDER BY c.chapter asc , c.paragraph asc",
            nativeQuery = true
    )
    List<Object[]> findBookDetails(@Param("recentBook") int recentBook, @Param("recentEndChapter") int recentEndChapter);

}