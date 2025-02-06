package hello.biblecashe.entity;

import hello.biblecashe.dto.SignUpForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long idx;

    @Column(unique = true)
    private String userId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_group")
    private Group group;

    @Column(nullable = false)
    private int readCount = 0;

    @OneToMany(mappedBy = "member")
    private List<History> historyList = new ArrayList<>();

    public Member(String userId, String name, Group group) {
        this.userId = userId;
        this.name = name;
        this.group = group;
    }

    public Member(SignUpForm signUpForm){
        this.userId = signUpForm.getUserId();
        this.name = signUpForm.getName();
        this.group = signUpForm.getGroup();
    }

}
