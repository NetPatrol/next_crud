package org.course.controller;

import org.course.model.User;
import org.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class DefaultController {

    @Qualifier("UserServiceImpl")
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    private String login;

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("login", login);
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Principal principal, ModelMap modelMap, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        modelMap.addAttribute("users", service.selectAll());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        model.addAttribute("user", service.selectByLogin(login));
        return "user";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user) {
        service.save(user);
        return "redirect:admin";
    }

    @GetMapping("/update/{id}")
    public String getPasswordUpdateForm(@PathVariable long id, Model model) {
        model.addAttribute("login", login);
        model.addAttribute("user", service.selectById(id));
        model.addAttribute("newUser", new User());
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("id") long id, @ModelAttribute User user, Model model) {
        if (user.getConfirmPassword().equals(user.getPassword())) {
            service.update(id, user);
            model.addAttribute("user", service.selectById(id));
            return "redirect:login";
        } else {
            return "update";
        }

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(long id) {
        service.delete(id);
        return "redirect:admin";
    }
}
