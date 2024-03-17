package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.min.work.domain.User;
import spring.min.work.repository.UserRepository;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("button") String buttonValue,
                          User user, Model model) {
        if ("Registering".equals(buttonValue)) {
            User userFromDb = userRepository.findByUsername(user.getUsername());
            if (userFromDb != null) {
                model.addAttribute("message", "User exists!");
                return "registration";
            }
            user.setActive(true);
            userRepository.save(user);
            return "redirect:/login";
        } else {
            return "login";
        }
    }
}
