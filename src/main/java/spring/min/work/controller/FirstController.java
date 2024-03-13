package spring.min.work.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.min.work.domain.Message;
import spring.min.work.domain.User;
import spring.min.work.repository.MessageRepository;
import spring.min.work.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
//@RequestMapping("/new")
public class FirstController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/first")
    public String first(@RequestParam(name = "name", required = false, defaultValue = "world") String name, Model model) {
        model.addAttribute("name", name);
        return "first";
    }

    @GetMapping({"", "/"})
    public String main(String message, Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "listUsers";
    }
//    @PostMapping
//    public String add(@RequestParam String name,
//                      @RequestParam String email,
//                      @RequestParam String password,
////                      @RequestParam Boolean archive,
//                      @RequestParam String phone,
//                      @RequestParam String address, Model model){
//        User user = new User(name, email, password, phone,address);
//        userRepository.save(user);
//        List<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
//        return "main";
//    }

    @PostMapping
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
//                             @RequestParam String filter,
                             Model model){
        Message message = new Message(text, tag);
        messageRepository.save(message);
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }
    @PostMapping("/deleteAll")
    public String deleteAll(Model model){
        messageRepository.deleteAll();

        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/";
    }
    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model){
        String path;
        List<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
            path = "main";
        } else {
            messages = messageRepository.findAll();
            path = "redirect:/";

        }
        model.addAttribute("messages", messages);
        System.out.println(messages);
        return path;
    }
}
