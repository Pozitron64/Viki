package com.example.Viki.controllers;

import com.example.Viki.models.Article;
import com.example.Viki.models.Link;
import com.example.Viki.repositories.ArticleRepository;
import com.example.Viki.services.ArticleService;
import com.example.Viki.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    @GetMapping("/article")
    public String articles(Model model, Principal principal) {
        model.addAttribute("articles", articleService.list());
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "article";
    }

    @GetMapping("/article-search")
    public String articlesSearch(String search, Model model, Principal principal) {
        model.addAttribute("articles", articleService.listSearch(search));
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "article";
    }

    @GetMapping("/article-add")
    public String articlesAdd(Model model, Principal principal) {
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "article-add";
    }

    @GetMapping("/article-add/getLink")
    public String articlesAddLink(Model model, Principal principal) {
        userService.setNumberLink(articleService.getUserByPrincipal(principal));
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "article-add";
    }

    @GetMapping("/article-info/edit/{id}")
    public String articlesEdit(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        userService.setNumberLink(articleService.getUserByPrincipal(principal));
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        model.addAttribute("article", articleService.getArticleById(id));
        model.addAttribute("id", id);
        return "article-add";
    }

    @PostMapping("/article-add")
    public String articlesPostAdd(Article article, Principal principal) throws IOException {
        article.setSize(new Long(article.getText().length()));
        article.setNumberLikes(new Long(0));
        article.setNumberViews(new Long(0));
        if (article.getText().length() < 20){
            article.setTiny_text(article.getText().substring(0,article.getText().length()-1));
        }
        article.setTiny_text(article.getText().substring(0,19));
        article.setLinks(articleService.parse(article.getTextLinks()));
        articleService.saveArticle(principal, article);
        return "redirect:/article";
    }

    @PostMapping("/article-add/{id}")
    public String articlesPostEdit(@PathVariable(value = "id") Long id, Article article, Principal principal) throws IOException {
        article.setId(id);
        article.setSize(new Long(article.getText().length()));
        article.setNumberLikes(new Long(0));
        article.setNumberViews(new Long(0));
        if (article.getText().length() < 20){
            article.setTiny_text(article.getText().substring(0,article.getText().length()-1));
        }
        article.setTiny_text(article.getText().substring(0,19));
        article.setLinks(articleService.parse(article.getTextLinks()));
        articleService.saveArticle(principal, article);
        return "redirect:/article";
    }
    @GetMapping("/article/{id}")
    public String getArticle(@PathVariable(value = "id") Long id, Model model) {
        List<String> textNoLinks = new ArrayList<>();
        Map<Integer, Link> map = new TreeMap<>(Comparator.comparingInt(o->o));
        for (Link link : articleService.getArticleById(id).getLinks()){
            map.put(articleService.getArticleById(id).getText().indexOf(link.getIdentificationWord()),link);
        }
        Integer begin = 0;
        for(Link link : map.values()){
            textNoLinks.add(articleService.getArticleById(id).getText().substring(begin,
                    articleService.getArticleById(id).getText().indexOf(link.getIdentificationWord()) - 1));
            begin = articleService.getArticleById(id).getText().indexOf(link.getIdentificationWord()) + link.getIdentificationWord().length();
        }
        textNoLinks.add(articleService.getArticleById(id).getText().substring(begin,
                        articleService.getArticleById(id).getText().length()));
        model.addAttribute("article", articleService.getArticleById(id));
        model.addAttribute("textNoLinks", textNoLinks);
        model.addAttribute("links", map.values());
        model.addAttribute("sizeTextNoLinks",textNoLinks.size());
        model.addAttribute("sizeLinks",articleService.getArticleById(id).getLinks().size());

        return "article-info";
    }
}