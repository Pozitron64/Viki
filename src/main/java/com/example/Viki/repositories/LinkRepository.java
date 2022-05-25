package com.example.Viki.repositories;

import com.example.Viki.models.Link;
import com.example.Viki.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByIdentificationWord(String identificationWord);
}
