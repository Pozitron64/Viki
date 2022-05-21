package com.example.Viki.services;

import com.example.Viki.models.Article;
import com.example.Viki.models.User;
import com.example.Viki.repositories.ArticleRepository;
import com.example.Viki.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<Article> list() {return articleRepository.findAll();}

    public void saveArticle(Principal principal, Article article){
        article.setUser(getUserByPrincipal(principal));
        articleRepository.save(article);
    }

    public void deleteArticle(Long id){
        articleRepository.deleteById(id);
    }

    public User getUserByPrincipal(Principal principal){
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public Article getArticleById(Long id){
        articleRepository.findById(id).orElse(null).setNumberViews(articleRepository.findById(id).orElse(null).getNumberViews() + 1);
        return articleRepository.findById(id).orElse(null);
    }

}
