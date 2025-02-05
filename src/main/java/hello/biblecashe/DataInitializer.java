package hello.biblecashe;

import hello.biblecashe.dto.SignUpForm;
import hello.biblecashe.entity.Group;
import hello.biblecashe.entity.Member;
import hello.biblecashe.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    public DataInitializer(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 초기화 데이터 생성
        SignUpForm signUpForm1 = new SignUpForm("test", "유재준", Group.A);

        // Member 객체 생성 및 저장
        Member member1 = new Member(signUpForm1);

        memberRepository.save(member1);

    }
}

