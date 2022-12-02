package test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.User;

@Controller
@RequestMapping("/design")
public class UsersController {
	
	@GetMapping
	public String showDesignForm(Model model) {

		model.addAttribute("design", new User());
		return "/admin/admin.xhtml";
	}
}
