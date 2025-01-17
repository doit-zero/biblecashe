package hello.biblecashe.controller;

import hello.biblecashe.dto.ContentDto;
import hello.biblecashe.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/contents")
    public String getContents(@RequestParam("year") int year,
                              @RequestParam("month") int month,
                              @RequestParam("date") int date,
                              Model model){
        log.info("Date: {}-{}-{}", year, month, date);
        /**
         * 1. 해당 날짜에 맞는 content를 가져온다.
         * 2. ReadingHistory에서 readingStatus를 가져온다.
         * 3. model에 담아서 전송한다.
         * */
        LocalDate newDate = LocalDate.of(year, month, date);
        List<ContentDto> contentDtoList = contentService.getContent(newDate);
        //log.info("contentList: {}",contentDtoList);
        model.addAttribute("contentDtoList",contentDtoList);
        model.addAttribute("newDate",newDate);
        //contentService.getReadingStatus();
        return "content";
    }
}