package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@Table(name = "`range`") // 테이블명에 백틱 사용
@NoArgsConstructor
public class Range {
    @Id
    @GeneratedValue
    private Long idx;

    private int book;

    private int endChapter;

    @Column(columnDefinition = "DATE")
    private LocalDate updatedAt;

    public static Range create(int book,int endChapter,LocalDate localDate){
        Range range = new Range();
        range.book = book;
        range.endChapter = endChapter;
        range.updatedAt = localDate;
        return range;
    }
}