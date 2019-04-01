package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.User;
import ru.otus.services.DBService;

import java.util.List;

@Controller
public class UserController {
    private DBService dbService;

    public UserController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = dbService.getAll(User.class);
        model.addAttribute("users", users);
        return "userFullList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new User());
        return "userFullCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        dbService.save(user);
        return new RedirectView("/user/list", true);
    }
}
