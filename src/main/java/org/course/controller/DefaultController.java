package org.course.controller;

import org.course.dao.dao.descendants.RoleDao;
import org.course.model.permission.Role;
import org.course.model.phone.Phone;
import org.course.model.user.User;
import org.course.service.phone.PhoneService;
import org.course.service.user.UserService;
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

    private final RoleDao roleService;
    @Autowired
    public DefaultController(@Qualifier("roleMySqlDaoImpl") RoleDao roleService) {
        this.roleService = roleService;
    }

    private UserService userService;
    @Autowired
    public void setUserService(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    private PhoneService phoneService;
    @Autowired
    public void setService(@Qualifier("phoneServiceImpl") PhoneService phone) {
        this.phoneService = phone;
    }

    String login;

    @GetMapping(value = "/")
    public String getWelcomePage(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("Spring MVC-SECURITY application");
        model.addAttribute("messages", messages);
        model.addAttribute("login", login);
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(Principal principal, ModelMap modelMap, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        model.addAttribute("account", userService.selectForAutorize(login));
        modelMap.addAttribute("accounts", userService.selectAll(User.class));
        model.addAttribute("user", new User());
        model.addAttribute("phone", new Phone());
        return "admin";
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal principal, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        model.addAttribute("account", userService.selectForAutorize(login));
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());

        return "user";
    }

    @GetMapping(value = "/registry")
    public String getRegistryPage(Model model) {
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        return "modal";
    }

    @RequestMapping(value = "registry", method = RequestMethod.POST)
    public String registration(@ModelAttribute User user,
                             @ModelAttribute Phone phone, Model model) throws Exception {
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        try {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                userService.save(user);
                phoneService.save(phone);
            }
            User u = (User) userService.selectByData(User.class, user.getUsername());
            u.setPhones(userService.set((Phone) phoneService.selectByData(Phone.class, phone.getPhone())));
            userService.bind(u);
        } catch (Exception e) {
            throw new Exception("Ошибка создания профиля");
        }
        return "#";
    }


    @GetMapping(value = "/update/{id}")
    public String getPasswordUpdateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.selectById(User.class, id));
        model.addAttribute("create", new User());
        return "update";
    }

    @RequestMapping(value = "add-phone", method = RequestMethod.POST)
    public void add(@RequestParam("id") long id, @ModelAttribute User user, @ModelAttribute Phone phone) {
        User u = userService.selectById(User.class, user.getId());
        u.setPhones(userService.set((Phone) phoneService.selectByData(Phone.class, phone.getPhone())));
        userService.bind(u);
    }

    @RequestMapping(value = "edit-password", method = RequestMethod.POST)
    public void edit(@RequestParam("id") long id, @ModelAttribute User user) {
        User u = userService.selectById(User.class, id);
        if (user.getConfirmPassword().equals(user.getPassword())) {
            u.setPassword(userService.passwordEncoder(user.getPassword()));
            userService.edit(u);
        }
    }

    @RequestMapping(value = "edit-role", method = RequestMethod.POST)
    public void edit(@RequestParam("id") long id, @RequestParam("role") String role) {
        User u = userService.selectById(User.class, id);
        if (role.equals("admin")) {
            u.setRoles(userService.set(roleService.selectById(Role.class, 1L)));
            userService.bind(u); // admin
        } else if (role.equals("user")) {
            u.setRoles(userService.set(roleService.selectById(Role.class, 2L)));
            userService.bind(u); // user
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(long id) {
        userService.delete(User.class, id);
    }
}