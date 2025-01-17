package hello.biblecashe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Range {
    @Id
    @GeneratedValue
    private Long idx;

    private int startPoint;

    private int endPoint;

    @Column(columnDefinition = "DATE")
    private LocalDate updatedAt;

    public Range(int startPoint, int endPoint, LocalDate updatedAt) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.updatedAt = updatedAt;
    }
}