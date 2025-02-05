package hello.biblecashe.service;

import hello.biblecashe.dto.ContentDto;
import hello.biblecashe.entity.Content;
import hello.biblecashe.entity.Range;
import hello.biblecashe.exception.HandleRuntimeException;
import hello.biblecashe.repository.ContentRepository;
import hello.biblecashe.repository.RangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
//@HandleRuntimeException
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final RangeRepository rangeRepository;

    public List<ContentDto> getContent(LocalDate localDate) {
        //  외부 날짜를 LocalDateTime로 변환함.

        // 해당 날짜에 해당하는 Range를 가져온다.
        //Optional<Range> range = rangeRepository.findByUpdatedAtBetween(startOfDay,endOfDay);
        Optional<Range> range = rangeRepository.findByUpdatedAt(localDate);
        log.info("rage: {}",range);
        log.info("range.get().getBook() : {}",range.get().getBook());
        log.info("range.get().getEndChapter() : {}",range.get().getEndChapter());


        // Range에 해당하는 Content들을 가져온 후 ContentDto로 변환
        List<Object[]> results = contentRepository.findBookDetails(range.get().getBook(), range.get().getEndChapter());

        log.info("contentList : {}",results.toString());
        List<ContentDto> contentDtoList = new ArrayList<>();

        for (Object[] row : results) {
            Integer chapter = (Integer) row[1];
            String longLabel = (String) row[2];
            Integer paragraph = (Integer) row[3];
            String sentence = (String) row[4];

            // ContentDto로 변환하여 리스트에 추가
            contentDtoList.add(new ContentDto(longLabel,chapter, paragraph, sentence));
        }

        return contentDtoList;
    }

}