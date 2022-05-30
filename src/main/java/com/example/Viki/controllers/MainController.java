package com.example.Viki.controllers;

import com.example.Viki.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ArticleService articleService;
    @GetMapping("/")
    public String main(Model model, Principal principal){
        model.addAttribute("user", articleService.getUserByPrincipal(principal));

        return "maintitle";
    }
}
