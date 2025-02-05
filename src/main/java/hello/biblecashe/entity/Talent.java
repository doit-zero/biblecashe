package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Entity
@Getter
@NoArgsConstructor
public class Talent {

    @Id
    @GeneratedValue
    private Long idx;

    @ManyToOne
    private Member member;

    @Column(columnDefinition = "int default 0")
    private int talent;

    @Column(columnDefinition = "DATE")
    private LocalDate updatedAt;

    public Talent(Member member, int standTalent,LocalDate clickedDate) {
        this.member = member;
        this.talent = standTalent;
        this.updatedAt = clickedDate;
    }


    public void updateTalent(LocalDate clickedDate,int talent){
        log.info("기존 달란트 금액 : {}",this.talent);
        this.talent = this.talent + talent;
        log.info("합쳐진 달란트 금액 : {}",this.talent);
    }
}