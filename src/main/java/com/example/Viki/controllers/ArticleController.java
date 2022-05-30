package com.example.Viki.controllers;

import com.example.Viki.models.Article;
import com.example.Viki.models.Link;
import com.example.Viki.models.Ranking;
import com.example.Viki.models.primarykey.RankingId;
import com.example.Viki.repositories.ArticleRepository;
import com.example.Viki.repositories.RankingRepository;
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
    private final RankingRepository rankingRepository;

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
    public String getArticle(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        List<String> textNoLinks = new ArrayList<>();
        Map<Integer, Link> map = new TreeMap<>(Comparator.comparingInt(o->o));
        Double middleOption = new Double(0);
        Double numberArticle = new Double(0);
        Double numberUser = new Double(0);
        for(Ranking ranking : rankingRepository.findAll()){
            if (ranking.getRankingId().getArticleId() == id){
                middleOption += ranking.getRating();
                numberArticle++;
            }
            if (ranking.getRankingId().getUserId() == articleService.getUserByPrincipal(principal).getId()){
                numberUser++;
            }
        }
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
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        if (numberArticle != 0){
            model.addAttribute("middleOption",middleOption / numberArticle);
            if (numberUser != 0){
                model.addAttribute("optionUser",rankingRepository.findById(new RankingId(articleService.getUserByPrincipal(principal).getId(),id)).get().getRating());
            }
        }



        return "article-info";
    }

    @GetMapping("/rate/{id}")
    public String rateArticle(@PathVariable(value = "id") Long id, Principal principal, Model model, String option) throws IOException {
        Integer opt = 10;
        switch (option){
            case "1": opt = 1;break;
            case "2": opt = 2;break;
            case "3": opt = 3;break;
            case "4": opt = 4;break;
            case "5": opt = 5;break;
            default:break;
        }
        if(opt != 10){
            rankingRepository.save(new Ranking(new RankingId(articleService.getUserByPrincipal(principal).getId(),id),opt));
        }
        return getArticle(id, model, principal);
    }
}