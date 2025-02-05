package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class WeeklyRankingBoard {
    @Id
    @GeneratedValue
    private Long idx;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private int readCount = 0;

    @Column(columnDefinition = "DATE")
    private LocalDate readTime;

    public static WeeklyRankingBoard create(Member member,int readCount,LocalDate readTime){
        WeeklyRankingBoard weeklyRankingBoard = new WeeklyRankingBoard();
        weeklyRankingBoard.member = member;
        weeklyRankingBoard.readCount = readCount;
        weeklyRankingBoard.readTime = readTime;
        return weeklyRankingBoard;
    }
}
