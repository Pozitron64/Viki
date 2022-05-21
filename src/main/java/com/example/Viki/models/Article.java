package com.example.Viki.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Column(name = "tiny_text")
    private String tiny_text;

    @Column(name = "size")
    private Long size;
    //    private List<Link> links;

    @Column(name = "numberViews")
    private Long numberViews;

    @Column(name = "numberLikes")
    private Long numberLikes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

}
