package hello.biblecashe.controller;

import hello.biblecashe.entity.Member;
import hello.biblecashe.service.HistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/history")
    public String saveHistory(HttpSession session){
        Member member = (Member)session.getAttribute("member");
        historyService.saveHistory(member);
        return "home";
    }
}