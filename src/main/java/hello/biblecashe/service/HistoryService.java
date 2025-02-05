package hello.biblecashe.service;

import hello.biblecashe.entity.*;
import hello.biblecashe.exception.HandleRuntimeException;
import hello.biblecashe.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.biblecashe.entity.ReadingStatus.READ;

@Slf4j
@Service
@Transactional
@HandleRuntimeException
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final WeeklyRankingBoardRepository weeklyRankingBoardRepository;
    public final static int STANDARD_READ_COUNT = 5;

    public void saveHistory(Member member) {
        // member readCount 업데이트 회원가입 할 때부터 기본적으로 생성되어있음
        memberRepository.incrementReadCount(member.getIdx(), STANDARD_READ_COUNT);


        // history에 기록 저장
        History history = History.create(member, STANDARD_READ_COUNT, LocalDate.now());
        historyRepository.save(history);


        // WeeklyRankingBoard 기록 저장
        Optional<WeeklyRankingBoard> memberOptional = weeklyRankingBoardRepository.findByMemberIdx(member.getIdx());
        if (memberOptional.isEmpty()) {
            WeeklyRankingBoard weeklyRankingBoard = WeeklyRankingBoard.create(member, STANDARD_READ_COUNT, LocalDate.now());
            weeklyRankingBoardRepository.save(weeklyRankingBoard);
        } else {
            weeklyRankingBoardRepository.updateReadCountAndTime(member.getIdx(), STANDARD_READ_COUNT, LocalDate.now());
        }

    }

    public void saveAdd(Member member, int additionalVerse) {
        // member readCount 업데이트
        memberRepository.incrementReadCount(member.getIdx(), additionalVerse);

        // history에 기록 저장
        History history = History.create(member, additionalVerse, LocalDate.now());
        historyRepository.save(history);

        // WeeklyRankingBoard 기록 저장
        Optional<WeeklyRankingBoard> memberOptional = weeklyRankingBoardRepository.findByMemberIdx(member.getIdx());
        if (memberOptional.isEmpty()) {
            WeeklyRankingBoard weeklyRankingBoard = WeeklyRankingBoard.create(member, additionalVerse, LocalDate.now());
            weeklyRankingBoardRepository.save(weeklyRankingBoard);
        } else {
            weeklyRankingBoardRepository.updateReadCountAndTime(member.getIdx(), additionalVerse, LocalDate.now());

        }
    }
}

//    public void saveHistory(Member member,LocalDate clickedDate){
//        // 코드 수정 - 읽은 날짜 데이터를 받아야함, 읽은 날짜로 rangeRepository find해야함
//        // updatedAt은 history에 update 된 날짜를 의미
//        LocalDate now = LocalDate.now();
//        Optional<Range> optionalRange = rangeRepository.findByUpdatedAt(clickedDate);
//        Range range = optionalRange.orElseThrow(() -> new IllegalStateException("해당 Range는 존재하지 않습니다."));
//
//        History history = new History(now, READ, member,range);
//        History savedHistory = historyRepository.save(history);
//
//        // 오늘 날짜와 클릭된 날짜를 비교하여 지급될 달란트 설정
//        int plusTalent;
//        if(now.isEqual(clickedDate)){
//            plusTalent = STAND_TALENT;
//        } else {
//            plusTalent = LOW_TALENT;
//        }
//        log.info("지급될 달란트 : {}",plusTalent);
//
//        // talent에 member의 Idx로 찾아지는 member가 있는지 확인한다.
//        /**
//         * memberIdx로 talentRepository에 클릭된 날자에 달란트 지급 이력이 존재하는지 확인
//         * -> 지급 이력이 존재하면 해당 달란트에 값을 날짜에 따라서 값을 추가해주면 됨
//         * -> talent가 존재하지 않으면 새롭게 만든다.
//         * */
//        Optional<Talent> findTalent = talentRepository.findByMemberIdx(member.getIdx());
//
//        if(findTalent.isPresent()){
//            // 달란트 지급내역에서 클릭된 날짜와 같은 날짜로 지급 이력이 있는지 확인해야함
//            // 달란트 지급내역에 클릭된 날짜와 같은 날짜에 지급 이력이 있다면 update 되면 안됨
//            List<TalentHistory> talentHistoryList = talentHistoryRepository.findAllById(findTalent.get().getIdx());
//            for (TalentHistory talentHistory : talentHistoryList) {
//                if(talentHistory.getUpdatedAt().isEqual(clickedDate)){
//                    return;
//                }
//            }
//            findTalent.get().updateTalent(clickedDate,plusTalent);
//            Talent savedTalent = talentRepository.save(findTalent.get());
//            TalentHistory talentHistory = new TalentHistory(savedTalent,now,plusTalent);
//            talentHistoryRepository.save(talentHistory);
//        } else {
//            // 새로운 달란트 작성
//            Talent talent = new Talent(member,STAND_TALENT,clickedDate);
//            Talent savedTalent = talentRepository.save(talent);
//            TalentHistory talentHistory = new TalentHistory(talent, now,STAND_TALENT);
//            TalentHistory savedTalentHistory = talentHistoryRepository.save(talentHistory);
//
//            log.info("기존에 작성된 달란트가 없어서 새로 저장된 savedTalent : {}", savedTalent);
//            log.info("새롭게 작성된 달란트 내역 : {}", savedTalentHistory);
//        }
//
//    }
