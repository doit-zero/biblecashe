package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class History {
    @Id
    @GeneratedValue
    private Long idx;

    @ManyToOne
    private Member member;

    @Column(columnDefinition = "DATE")
    private LocalDate readTime;

    @Column(nullable = false)
    private int readCount = 0;

    public static History create(Member member, int readCount, LocalDate readTime) {
        History history = new History();
        history.member = member;
        history.readCount = readCount;
        history.readTime = readTime;
        return history;
    }
}