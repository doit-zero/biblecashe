package hello.biblecashe.dto;

import hello.biblecashe.entity.Content;
import lombok.Data;

@Data
public class ContentDto {
    private Long idx;
    private String longLabel;
    private Integer chapter;
    private Integer paragraph;
    private String sentence;

    public ContentDto(Content content){
        this.idx = content.getIdx();
        this.longLabel = content.getLongLabel();
        this.chapter = content.getChapter();
        this.paragraph = content.getParagraph();
        this.sentence = content.getSentence();
    }
}
