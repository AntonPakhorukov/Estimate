package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.min.work.domain.Role;
import spring.min.work.domain.User;
import spring.min.work.repository.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        System.out.println("1 " + user.getUsername() + " " + user.getPassword());
        System.out.println(userFromDb);

        if(userFromDb != null) {
            System.out.println(userFromDb);
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.CLIENT));
        userRepository.save(user);
        System.out.println("3 " + user.getUsername() + " " + user.getPassword());
        System.out.println("3DB " + userFromDb.getUsername() + " " + userFromDb.getPassword());
        return "redirect:/login";
    }
}
