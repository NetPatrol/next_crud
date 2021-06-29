package org.course.controller;

import org.course.model.User;
import org.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger("User");

	@Qualifier("UserServiceImpl")
	private UserService service;

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("users", service.selectAll());
		return "index";
	}

	@GetMapping("/create")
	public String viewCreate(Model model) {
		model.addAttribute("user", new User());
		return "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("user") User user, Model model) {
		try {
			service.save(user);
			String message = "Успешно!";
			model.addAttribute("message", message);
			return "redirect:/index";
		} catch (Exception e) {
			System.out.println("что-то пошло не так");
			return "redirect:/index";
		}
	}

	@GetMapping("/update/{id}")
	public String viewCreate(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", service.selectById(id));
		model.addAttribute("newUser", new User());
		return "update";
	}

	@RequestMapping(value = "update", method = {RequestMethod.POST})
	public String show(@RequestParam("id") long id, @ModelAttribute User user, Model model) {
		service.update(id, user);
		model.addAttribute("users", service.selectAll());
		return "redirect:/index";
	}

	@RequestMapping(value = "delete", method = {RequestMethod.POST})
	public String delete(@RequestParam("id") long id, @ModelAttribute User user, Model model) {
		service.delete(id);
		model.addAttribute("users", service.selectAll());
		return "redirect:/index";
	}
}

