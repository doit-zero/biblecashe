package hello.biblecashe.controller;

import hello.biblecashe.dto.LoginForm;
import hello.biblecashe.dto.SignUpForm;
import hello.biblecashe.entity.Member;
import hello.biblecashe.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignUpForm signUpForm, Model model, HttpSession session){
        Member member = new Member(signUpForm);
        Member savedMember = memberRepository.save(member);

        // 세션에 멤버 저장 서버에서만 확인 가능 세션은 클라이언트마다 각각임.
        session.setAttribute("member",savedMember);
        model.addAttribute("id",signUpForm.getName());
        return "home";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, Model model, HttpSession session){
        log.info("loginForm : {}",loginForm);
        Member findMember = memberRepository.findByUserId(loginForm.getUserId());
        session.setAttribute("member",findMember);
        model.addAttribute("id",findMember.getUserId());
        return "home";
    }
}
