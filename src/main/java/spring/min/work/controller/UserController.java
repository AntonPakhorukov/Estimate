package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.min.work.domain.User;
import spring.min.work.repository.UserRepository;

import java.security.Principal;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable(name = "user") User user, Model model) {
        model.addAttribute("user", user.getId());
        return "userEdit";
    }

    @PostMapping("/user/{user}")
    public String userSave(@RequestParam("userId") User user, @RequestParam String username,
                           @RequestParam String email, @RequestParam String address,
                           @RequestParam String phone, Principal principal, Model model) {
        userRepository.findByUsername(principal.getName()).setAddress(address);
        userRepository.findByUsername(principal.getName()).setPhone(phone);
        userRepository.findByUsername(principal.getName()).setEmail(email);
        userRepository.save(userRepository.findByUsername(principal.getName()));
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user";
    }

    @PostMapping("/user")
    public String backToFirstPage(Principal principal, @RequestParam String email, Model model,
                                  @RequestParam String address, @RequestParam String phone) {
        userRepository.findByUsername(principal.getName()).setAddress(address);
        userRepository.findByUsername(principal.getName()).setPhone(phone);
        userRepository.findByUsername(principal.getName()).setEmail(email);
        userRepository.save(userRepository.findByUsername(principal.getName()));
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/";
    }
}
