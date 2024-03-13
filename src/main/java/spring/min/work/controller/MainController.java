package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.min.work.domain.Message;
import spring.min.work.repository.MessageRepository;
import spring.min.work.repository.UserRepository;

import java.util.List;

@Controller
//@RequestMapping("/new")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping({"", "/"})
    public String first(Model model) {
//        model.addAttribute("name", );
        return "first";
    }

    @GetMapping("/main")
    public String main(String message, Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

//    @GetMapping("/main/users")
//    public String getAll(Model model) {
//        model.addAttribute("users", userRepository.findAll());
//        return "listUsers";
//    }
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

    @PostMapping("/main")
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
//                             @RequestParam String filter,
                             Model model){
        Message message = new Message(text, tag);
        System.out.println("one: " + message.getText());
        messageRepository.save(message);
        List<Message> messages = messageRepository.findAll();
        messages.stream().forEach(System.out::println);
        model.addAttribute("messages", messages);
        return "main";
    }
    @PostMapping("/main/deleteAll")
    public String deleteAll(Model model){
        messageRepository.deleteAll();

        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/main";
    }
    @PostMapping("/main/filter")
    public String filter(@RequestParam String filter, Model model){
        String path;
        List<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
            path = "main";
        } else {
            messages = messageRepository.findAll();
            path = "redirect:/main";

        }
        model.addAttribute("messages", messages);
        System.out.println(messages);
        return path;
    }
}
