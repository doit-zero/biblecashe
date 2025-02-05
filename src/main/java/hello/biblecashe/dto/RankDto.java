package hello.biblecashe.dto;

import hello.biblecashe.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {
    private String name;
    private Group memberGroup;
    private int readCount;
    private int ranking;
}