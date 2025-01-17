package hello.biblecashe.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Integer cate;

    @Column(nullable = false)
    private Integer book;

    @Column(nullable = false)
    private Integer chapter;

    @Column(nullable = false)
    private Integer paragraph;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentence;

    @Column(nullable = false, length = 10)
    private String testament;

    @Column(nullable = false, length = 30)
    private String longLabel;

    @Column(nullable = false, length = 10)
    private String shortLabel;
}