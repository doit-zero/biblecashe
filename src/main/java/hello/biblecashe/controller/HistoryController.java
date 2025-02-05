package hello.biblecashe.controller;

import hello.biblecashe.entity.Member;
import hello.biblecashe.service.HistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/history")
    public String saveHistory(HttpSession session){
        Member member = (Member) session.getAttribute("member");
        log.info("member: {}",member.getUserId());
        historyService.saveHistory(member);
        return "redirect:/home";
    }

    @PostMapping("/add")
    public String addAdditionalVerse(@RequestParam("additionalVerse") int additionalVerse, HttpSession session) {
        log.info("추가 읽은 구절 수: {}", additionalVerse);
        Member member = (Member)session.getAttribute("member");
        log.info("member : {}",member.getName());
        historyService.saveAdd(member,additionalVerse);
        return "redirect:/home"; // 홈 화면으로 리디렉트
    }
}