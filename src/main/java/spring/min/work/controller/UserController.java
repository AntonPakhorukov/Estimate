package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.min.work.domain.Role;
import spring.min.work.domain.User;
import spring.min.work.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }
    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable(name = "user") User user, Model model){
        model.addAttribute("user", user.getId());
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping("/user/{user}")
    public String userSave(
            @RequestParam("userId") User user,
            @RequestParam String username,
            @RequestParam String password
    ) {

        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user")
    public String backToFirstPage(){
        return "redirect:/";
    }
}
