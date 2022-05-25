package com.example.Viki.models;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private List<Link> links = new ArrayList<>();

    private String linkFoto;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Image> images = new ArrayList<>();

    private Long previewImageId;


//    public void addImageToArticle(Image image) {
//        image.setArticle(this);
//        images.add(image);
//    }

}
