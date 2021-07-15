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

    private String login;

    @GetMapping(value = "")
    public String getWelcomePage(ModelMap model) {
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping(value = "/")
    public String index(ModelMap model) {
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/admin/profile")
    public String getAdminPage(Principal principal, ModelMap modelMap, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        model.addAttribute("account", userService.selectForAutorize(login));
        modelMap.addAttribute("accounts", userService.selectAll(User.class));
        model.addAttribute("phone", new Phone());
        model.addAttribute("user", new User());
        return "admin/profile";
    }

    @GetMapping(value = "/user/profile")
    public String getUserPage(Principal principal, Model model) {
        this.login = principal.getName();
        model.addAttribute("login", login);
        model.addAttribute("account", userService.selectForAutorize(login));
        return "user/profile";
    }

    @RequestMapping(value = "/admin/registry", method = {RequestMethod.POST})
    public String registry(@ModelAttribute User user,
                             @ModelAttribute Phone phone, Model model) throws Exception {
        model.addAttribute("user", new User());
        model.addAttribute("phone", new Phone());
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
        return "redirect:/admin/profile";
    }

    @RequestMapping(value = "/admin/edit/{id}")
    public String user(@PathVariable("id") long id, ModelMap modelMap, Model model) {
        model.addAttribute("login", login);
        model.addAttribute("account", userService.selectForAutorize(login));
        User u = userService.selectById(User.class, id);
        modelMap.addAttribute("user", u);
        modelMap.addAttribute("newPhone", new Phone());
        modelMap.addAttribute("newUser", new User());
        return "admin/edit";
    }

    @RequestMapping(value = "editUser", method = {RequestMethod.POST})
    public String editUser(@RequestParam("id") long id, @ModelAttribute User user) {
        userService.edit(id, user);
        return "redirect:/admin/edit/" + id;
    }

    @RequestMapping(value = "editPhone", method = {RequestMethod.POST})
    public String addPhone(@RequestParam("id") long id, @ModelAttribute Phone phone) {
        User u = userService.selectById(User.class, id);
        for (Phone p : u.getPhones()) {
            if (!phone.getPhone().equals("")) {
                phoneService.edit(p.getId(), phone);
            }
        }
        return "redirect:/admin/edit/" + id;
//        u.setPhones(userService.set((Phone) phoneService.selectByData(Phone.class, phone.getPhone())));
//        userService.bind(u);
    }

    @RequestMapping(value = "editRole", method = {RequestMethod.POST})
    public String edit(@RequestParam("id") long id, @RequestParam("role") String role) {
        User u = userService.selectById(User.class, id);
        if (role.equals("admin")) {
            u.setRoles(userService.set(roleService.selectById(Role.class, 1L)));
            userService.bind(u);
        } else if (role.equals("user")) {
            u.setRoles(userService.set(roleService.selectById(Role.class, 2L)));
            userService.bind(u);
        }
        return "redirect:/admin/edit/" + id;
    }

    @RequestMapping(value = "accountDelete", method = {RequestMethod.POST})
    public String delete(@RequestParam("id") long id, @ModelAttribute User user) {
        userService.delete(User.class, id);
        return "redirect:/admin/profile";
    }

    @RequestMapping(value = "phoneDelete", method = {RequestMethod.POST})
    public String phoneDelete(@ModelAttribute Phone phone) {
        phoneService.delete(Phone.class, phone.getId());
        return "redirect:/admin/profile";
    }
}