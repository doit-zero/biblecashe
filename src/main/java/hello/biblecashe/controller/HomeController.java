package hello.biblecashe.controller;

import hello.biblecashe.entity.History;
import hello.biblecashe.entity.Member;
import hello.biblecashe.repository.HistoryRepository;
import hello.biblecashe.repository.TalentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HistoryRepository historyRepository;
    private final TalentRepository talentRepository;

    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        // 읽음 이력 가져오기
        Member member =(Member) session.getAttribute("member");
        List<History> historyList = historyRepository.findByMemberIdx(member.getIdx());
        // member 총합 talent 가져오기

        return "home";
    }
}