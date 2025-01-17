package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
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

    public Talent(Member member,LocalDate rangeUpdatedAt){
        this.member = member;
        this.updatedAt = updatedAt;
        if(rangeUpdatedAt.isEqual(LocalDate.now())){
            this.talent = 10;
        } else {
            this.talent = 2;
        }

    }
}