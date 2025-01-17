package hello.biblecashe.service;

import hello.biblecashe.dto.SignUpForm;
import hello.biblecashe.entity.Member;
import hello.biblecashe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(SignUpForm signUpForm){
        Member member = new Member(signUpForm);
        memberRepository.save(member);
    }

}