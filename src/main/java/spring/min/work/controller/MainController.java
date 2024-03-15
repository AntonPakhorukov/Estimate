package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "startPage";
    }

    @GetMapping("/main")
    public String main(String message, Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
        return "mainPage";
    }

    @PostMapping("/mainPage")
    public String checkAccount(@RequestParam("button") String buttonValue){
        if("Logout".equals(buttonValue)){
            return "login";
        } else if ("Create Estimate".equals(buttonValue)){
            return "redirect:/createEstimate";
        } else {
            return "redirect:/details";
        }
    }
    @GetMapping("/details")
    public String details(){
        return "details";
    }
    @GetMapping("/createEstimate")
    public String createEstimate(){
        return "createEstimate";
    }
    @PostMapping("/createEstimate")
    public String backToMainPageFromCreateEstimate(){
        return "redirect:/mainPage";
    }

    @PostMapping("/details")
    public String backToMainPageFromDetails(){
        return "redirect:/mainPage";
    }
    @PostMapping("/main")
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
                             Model model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/main/deleteAll")
    public String deleteAll(Model model) {
        messageRepository.deleteAll();

        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/main";
    }

    @PostMapping("/main/filter")
    public String filter(@RequestParam String filter, Model model) {
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
