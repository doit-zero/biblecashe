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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@HandleRuntimeException
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final RangeRepository rangeRepository;

    public List<ContentDto> getContent(LocalDate localDate) {
        //  외부 날짜를 LocalDateTime로 변환함.

        //LocalDateTime localDateTime = LocalDateTime.of(year,month,date,0,0);

        //LocalDateTime startOfDay = localDate.atStartOfDay();
        //LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999);

        // 해당 날짜에 해당하는 Range를 가져온다.
        //Optional<Range> range = rangeRepository.findByUpdatedAtBetween(startOfDay,endOfDay);
        Optional<Range> range = rangeRepository.findByUpdatedAt(localDate);
        log.info("rage: {}",range);


        // Range에 해당하는 Content들을 가져온 후 ContentDto로 변환
        List<Content> contentList = contentRepository.findByIdxBetween(range.get().getStartPoint(), range.get().getEndPoint());

        List<ContentDto> contentDtoList = contentList.stream()
                .map(ContentDto::new)
                .sorted(Comparator.comparing(ContentDto::getIdx))
                .collect(Collectors.toList());
        return contentDtoList;
    }

}