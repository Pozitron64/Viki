package com.example.Viki.models;

import com.example.Viki.models.primarykey.RankingId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {

    @EmbeddedId
    private RankingId rankingId;
    private Integer rating = null;

}
