package controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.managers.UserManager;

@Controller
@RequestMapping("/test")
public class UsersController {
    @Inject
    private UserManager userManager;

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("userManager", userManager);
        return "/admin/admin.xhtml";
    }
}
