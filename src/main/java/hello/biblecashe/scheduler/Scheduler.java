package hello.biblecashe.scheduler;

import hello.biblecashe.entity.Range;
import hello.biblecashe.repository.ContentRepository;
import hello.biblecashe.repository.RangeRepository;
import hello.biblecashe.repository.WeeklyRankingBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Scheduler {

    private WeeklyRankingBoardRepository weeklyRankingBoardRepository;
    private final RangeRepository rangeRepository;
    private final ContentRepository contentRepository;
    private final int EVERY_READ_CHAPTER = 5;

    @Scheduled(cron = "0 0 0 * * MON-FRI") // 평일(월~금) 자정에 실행됨
    //@Scheduled(fixedRate = 500000) //
    public void updateFiveChapter(){

        // 가장 최근 등록된 range를 가져온다
        Range recentRage = rangeRepository.findMaxIdxRange();

        // 등록된 범위가 없을 경우 바로 최초 등록
        if(recentRage == null){
            Range range = Range.create( 1, 0, LocalDate.now());
            rangeRepository.save(range);
        } else {
            // 최근 등록된
            int recentBook = recentRage.getBook();
            int recentEndChapter = recentRage.getEndChapter();

            List<Map<String, Integer>> results = contentRepository.findNextChapters(recentBook, recentEndChapter);

            Map<String,Integer> lastResult = results.get(results.size() - 1);
            int book = lastResult.get("book");
            int chapter = lastResult.get("chapter");
            log.info("book: {},chapter:{}",book,chapter);
            Range range = Range.create(book, chapter, LocalDate.now());
            rangeRepository.save(range);
        }
    }

    /**
     * 매주 주일 00시 마다 readCount 초기화
     * */
    @Scheduled(cron = "0 0 0 * * SUN")// 매주 주일 00:00 실행
    public void resetWeeklyRankingBoard(){
        weeklyRankingBoardRepository.resetWeeklyRankingBoard();
    }

}