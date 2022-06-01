package com.example.Viki.services;

import com.example.Viki.models.Article;
import com.example.Viki.models.Link;
import com.example.Viki.models.User;
import com.example.Viki.repositories.ArticleRepository;
import com.example.Viki.repositories.LinkRepository;
import com.example.Viki.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private static final String URL = "jdbc:mysql://localhost:3306/library_article";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Pozitron1@";

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;

    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Article> list() {return articleRepository.findAll();}

    public List<Article> listSearch(String frazeSearch) {
        List<Article> searchedArticle = new ArrayList<>();
        List<Long> listId = new ArrayList<>();
        Statement statement = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE name LIKE ?"
            );
            preparedStatement.setString(1,  "%" + frazeSearch + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                listId.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Long id : listId){
            searchedArticle.add(articleRepository.findById(id).get());
        }
        return searchedArticle;
    }

    public void saveArticle(Principal principal, Article article) throws IOException {

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

    public List<Link> parse(String textLinks){
        List<Link> links = new ArrayList<>();
        if(textLinks.length() == 0){
            return null;
        }
        textLinks.replaceAll("\\r", "");
        for(String line : textLinks.split("\n")){
            Pattern p = Pattern.compile(".+--.+");
            Matcher m = p.matcher(line);
            if (!m.lookingAt()){
                continue;
            }
            String[] lineDou = line.split("--");
            Boolean islinkRepository = false;
            if(linkRepository.findByIdentificationWord(lineDou[0].trim()) == null){
                linkRepository.save(new Link(lineDou[0].trim(),lineDou[1].trim()));
            }
            links.add(linkRepository.findByIdentificationWord(lineDou[0].trim()));
        }
        return links;
    }
}
