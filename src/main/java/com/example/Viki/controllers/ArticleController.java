package com.example.Viki.controllers;

import com.example.Viki.models.Article;
import com.example.Viki.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/article")
    public String articles(Model model, Principal principal) {
        model.addAttribute("articles", articleService.list());
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "article";
    }

    @GetMapping("/article-add")
    public String articlesAdd() {
        return "article-add";
    }

    @PostMapping("/article-add")
    public String articlesPostAdd(Article article, Principal principal) {
        article.setSize(new Long(article.getText().length()));
        article.setNumberLikes(new Long(0));
        article.setNumberViews(new Long(0));
        article.setTiny_text(article.getText().substring(0,19));
        articleService.saveArticle(principal, article);
        return "redirect:/article";
    }
    @GetMapping("/article/{id}")
    public String getArticle(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("article", articleService.getArticleById(id));
        return "article-info";
    }
}