package controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import controllers.jsf.dlg.DlgCreateDep;
import controllers.jsf.model.UserManager;

@Controller
@RequestMapping("/test")
public class UsersController {
    @Inject
    private UserManager userManager;
    @Inject
    private DlgCreateDep userDialogues;

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("userManager", userManager);
        model.addAttribute("userDlg", userDialogues);
        return "/admin/admin.xhtml";
    }
}
