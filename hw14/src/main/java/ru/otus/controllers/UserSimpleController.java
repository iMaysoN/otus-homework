package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.UserSimple;
import ru.otus.repostory.UserRepository;

import java.util.List;

@Controller
public class UserSimpleController {

    private final UserRepository repository;

    public UserSimpleController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping({"/", "/user/simple/list"})
    public String userList(Model model) {
        List<UserSimple> users = repository.findAll();
        model.addAttribute("users", users);
        return "userSimpleList.html";
    }

    @GetMapping("/user/simple/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new UserSimple());
        return "userSimpleCreate.html";
    }

    @PostMapping("/user/simple/save")
    public RedirectView userSave(@ModelAttribute UserSimple user) {
        repository.create(user.getName());
        return new RedirectView("/user/simple/list", true);
    }

}
