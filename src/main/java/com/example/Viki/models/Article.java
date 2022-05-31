package com.example.Viki.models;


import com.example.Viki.models.enums.TypeArticle;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Column(name = "textLinks", columnDefinition = "text")
    private String textLinks;

    @Column(name = "tiny_text")
    private String tiny_text;

    @Column(name = "size")
    private Long size;

    @Column(name = "numberViews")
    private Long numberViews;

    @Column(name = "numberLikes")
    private Long numberLikes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ElementCollection(targetClass = TypeArticle.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "article_type",
            joinColumns = @JoinColumn(name = "article_id"))
    @Enumerated(EnumType.STRING)
    private Set<TypeArticle> typeArticles = new HashSet<>();

    @OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private List<Link> links = new ArrayList<>();

    private String linkFoto;

    private Long previewImageId;

}
