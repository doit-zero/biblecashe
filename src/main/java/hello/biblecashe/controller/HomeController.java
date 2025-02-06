package hello.biblecashe.controller;

import hello.biblecashe.dto.GroupReadCountDto;
import hello.biblecashe.dto.RankDto;
import hello.biblecashe.entity.Group;
import hello.biblecashe.entity.History;
import hello.biblecashe.entity.Member;
import hello.biblecashe.entity.Talent;
import hello.biblecashe.repository.HistoryRepository;
import hello.biblecashe.repository.MemberRepository;
import hello.biblecashe.repository.TalentRepository;
import hello.biblecashe.repository.WeeklyRankingBoardRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final WeeklyRankingBoardRepository weeklyRankingBoardRepository;

    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        // 오늘까지 내가 읽은 양
        Member member =(Member) session.getAttribute("member");
        int readCount = member.getReadCount();
        // model.addAttribute("MyReadCount",readCount);

        // 이번주 가장 많이 읽은 순위
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        log.info("startOfWeek : {} ,endOfWeek : {} ",startOfWeek,endOfWeek);
        List<Object[]> weeklyRanking = weeklyRankingBoardRepository.findTop5ByReadCountWithRanking(startOfWeek, endOfWeek);
        List<RankDto> weeklyRankinList = weeklyRanking.stream()
                .map(row -> new RankDto(
                        (String) row[0], // name
                        Group.valueOf(String.valueOf(row[1])), // ✅ Character → String 변환 후 Enum 변환
                        ((Number) row[2]).intValue(), // readCount
                        ((Number) row[3]).intValue() // ranking
                )).toList();

        log.info("weeklyRankinList : {}",weeklyRankinList);
        Map<Integer, List<RankDto>> weeklyRankinMap = weeklyRankinList.stream()
                .collect(Collectors.groupingBy(RankDto::getRanking));
        log.info("weeklyRankinMap : {}",weeklyRankinMap);
        model.addAttribute("weeklyRankinMap",weeklyRankinMap);


        // 현재까지 읽은 양이 제일 많은 순위 데이터 가져오기
        List<Object[]> results = memberRepository.findTop5ByReadCountWithRanking();
        List<RankDto> rankDtoList = results.stream()
                .map(row -> new RankDto(
                        (String) row[0],
                        Group.valueOf(String.valueOf(row[1])),  // ✅ 수정: Character → String 변환
                        ((Number) row[2]).intValue(),
                        ((Number) row[3]).intValue()
                ))
                .collect(Collectors.toList());

        Map<Integer, List<RankDto>> groupedRanking = rankDtoList.stream()
                .collect(Collectors.groupingBy(RankDto::getRanking, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("groupedRanking", groupedRanking);


        //
        List<Object[]> countSumByGroup = memberRepository.getReadCountSumByGroup();
        List<GroupReadCountDto> groupReadCountDtos = countSumByGroup.stream()
                .map(row -> new GroupReadCountDto(
                        Group.valueOf(String.valueOf(row[0])), // ✅ Character → String 변환 후 Enum 변환
                        ((Number) row[1]).intValue()           // readCount 합계 변환
                ))
                .collect(Collectors.toList());
        model.addAttribute("groupReadCountDtos", groupReadCountDtos);
        return "home";
    }
}