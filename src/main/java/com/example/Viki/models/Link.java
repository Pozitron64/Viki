package com.example.Viki.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "identificationWord")
    private String identificationWord;

    @Column(name = "textLink")
    private String textLink;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private Article article;

    public Link(String identificationWord, String textLink) {
        this.identificationWord = identificationWord;
        this.textLink = textLink;
    }
}
