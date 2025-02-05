package hello.biblecashe.dto;

import hello.biblecashe.entity.Content;
import lombok.Data;

@Data
public class ContentDto {
    private String longLabel;
    private Integer chapter;
    private Integer paragraph;
    private String sentence;

    public ContentDto(Content content){
        this.longLabel = content.getLongLabel();
        this.chapter = content.getChapter();
        this.paragraph = content.getParagraph();
        this.sentence = content.getSentence();
    }

    public ContentDto(String longLabel, Integer chapter, Integer paragraph, String sentence) {
        this.longLabel = longLabel;
        this.chapter = chapter;
        this.paragraph = paragraph;
        this.sentence = sentence;
    }

}
