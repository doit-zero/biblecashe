package hello.biblecashe.service.scheduler;

import hello.biblecashe.entity.Range;
import hello.biblecashe.repository.RangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RangeScheduler {

    private final RangeRepository rangeRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행됨
    //@Scheduled(fixedRate = 30000) // 30초마다 실행
    public void updateRange(){
        Range currentRange = rangeRepository.findTopByOrderByUpdatedAtDesc();

        if (currentRange != null) {
            int newStart;
            int newEnd;

            // end 값이 31100인 경우
            if (currentRange.getEndPoint() == 31100) {
                newStart = currentRange.getEndPoint() + 1;
                newEnd = currentRange.getEndPoint() + 38;
            } else {
                // 그 외의 경우
                newStart = currentRange.getEndPoint() + 1;
                newEnd = newStart + 99;
            }

            // 계산된 값으로 Range 객체 생성 및 저장
            Range range = new Range(newStart, newEnd, LocalDate.now());
            rangeRepository.save(range);
            log.info("업데이트 된 range: start = {} end = {}", newStart, newEnd);
        } else {
            // 초기 데이터 생성
            Range initRange = new Range(1, 100, LocalDate.now());
            rangeRepository.save(initRange);
            log.info("초기 데이터 생성: start = 1, end = 100");
        }

    }
}