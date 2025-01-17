package hello.biblecashe.service;

import hello.biblecashe.entity.History;
import hello.biblecashe.entity.Member;
import hello.biblecashe.entity.Range;
import hello.biblecashe.entity.Talent;
import hello.biblecashe.exception.HandleRuntimeException;
import hello.biblecashe.repository.HistoryRepository;
import hello.biblecashe.repository.RangeRepository;
import hello.biblecashe.repository.TalentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static hello.biblecashe.entity.ReadingStatus.READ;

@Slf4j
@Service
@Transactional
@HandleRuntimeException
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final RangeRepository rangeRepository;
    private final TalentRepository talentRepository;

    public void saveHistory(Member member){
        LocalDate readTime = LocalDate.now();
        Optional<Range> optionalRange = rangeRepository.findByUpdatedAt(readTime);
        Range range = optionalRange.orElseThrow(() -> new IllegalStateException("해당 Range는 존재하지 않습니다."));

        History history = new History(readTime, READ, member, range);
        History savedHistory = historyRepository.save(history);
        log.info("저장된 기록 : {}",savedHistory);

        Talent talent = new Talent(member, readTime);
        Talent savedTalent = talentRepository.save(talent);
        log.info("저장된 달란트 : {}",savedTalent);
    }
}