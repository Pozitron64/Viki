package com.example.Viki.controllers;

import com.example.Viki.models.User;
import com.example.Viki.models.enums.Role;
import com.example.Viki.models.enums.TypeArticle;
import com.example.Viki.repositories.UserRepository;
import com.example.Viki.services.ArticleService;
import com.example.Viki.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String usersEdit(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("userEdit", userRepository.findById(id).get());
        model.addAttribute("user", articleService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        model.addAttribute("typeArticles", TypeArticle.values());
        return "user-edit";
    }
}
