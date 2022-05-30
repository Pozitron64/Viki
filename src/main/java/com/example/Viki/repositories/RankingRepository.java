package com.example.Viki.repositories;

import com.example.Viki.models.Ranking;
import com.example.Viki.models.primarykey.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, RankingId> {
}
