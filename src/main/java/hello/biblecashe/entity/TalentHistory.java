package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class TalentHistory {
    @Id
    @GeneratedValue
    private Long idx;
    @ManyToOne
    private Talent talent;
    @Column(columnDefinition = "DATE")
    private LocalDate updatedAt;
    private int updatedTalent;

    public TalentHistory(Talent talent, LocalDate updatedAt, int updatedTalent) {
        this.talent = talent;
        this.updatedAt = updatedAt;
        this.updatedTalent = updatedTalent;
    }
}
