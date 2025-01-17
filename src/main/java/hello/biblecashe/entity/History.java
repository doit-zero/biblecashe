package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class History {
    @Id
    @GeneratedValue
    private Long idx;

    @Column(columnDefinition = "DATE")
    private LocalDate readTime;

    private ReadingStatus readingStatus;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Range range;

    public History(LocalDate readTime, ReadingStatus readingStatus, Member member,Range range) {
        this.readTime = readTime;
        this.readingStatus = readingStatus;
        this.member = member;
        this.range = range;
    }
}