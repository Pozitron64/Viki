package com.example.Viki.controllers;

import com.example.Viki.models.Article;
import com.example.Viki.models.User;
import com.example.Viki.repositories.UserRepository;
import com.example.Viki.services.ArticleService;
import com.example.Viki.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model, Principal principal) {
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable("id")Long id, Model model, Principal principal) {
        Set<Article> articles = new HashSet<>(userRepository.findById(id).get().getArticles());
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("userByPrincipal", articleService.getUserByPrincipal(principal));
        model.addAttribute("articles", articles);
        return "user_info";
    }

}
