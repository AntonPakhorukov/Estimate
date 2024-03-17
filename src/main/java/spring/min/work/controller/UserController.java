package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.min.work.domain.Role;
import spring.min.work.domain.User;
import spring.min.work.repository.UserRepository;

import java.security.Principal;

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
            @RequestParam("userId") User user, @RequestParam String username) {
        return "redirect:/user";
    }

    @PostMapping("/user") // button save
    public String backToFirstPage(Principal principal, @RequestParam String email,
                                  @RequestParam String address, @RequestParam String phone){
        User user = userRepository.findByUsername(principal.getName());
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);
        return "redirect:/";
    }
}
