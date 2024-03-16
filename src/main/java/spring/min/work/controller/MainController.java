package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.min.work.domain.Estimate;
import spring.min.work.domain.Message;
import spring.min.work.repository.EstimateRepository;
import spring.min.work.repository.MessageRepository;
import spring.min.work.repository.UserRepository;
import spring.min.work.service.EstimateService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private EstimateService estimateService;
    @Autowired
    private EstimateRepository estimateRepository;

    @GetMapping({"", "/"})
    public String first() {
        return "startPage";
    }

    @GetMapping("/main")
    public String main(String message, Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }


    @GetMapping("/mainPage")
    public String mainPage(Model model, Principal principal) {
        model.addAttribute("estimates",
                userRepository.findByUsername(principal.getName()).getEstimates());
        return "mainPage";
    }

    @PostMapping("/mainPage")
    public String checkAccount(@RequestParam("button") String buttonValue) {
        if ("Logout".equals(buttonValue)) {
            return "login";
        } else if ("Create Estimate".equals(buttonValue)) {
            return "redirect:/createEstimate";
        } else {
            return "redirect:/details";
        }
    }

    @GetMapping("/details")
    public String details(Model model, Principal principal) {
        model.addAttribute("estimates",
                userRepository.findByUsername(principal.getName()).getEstimates());
        return "details";
    }

    @GetMapping("/createEstimate")
    public String createEstimate(Model model, Principal principal) {
        model.addAttribute("estimates",
                userRepository.findByUsername(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/createEstimate")
    public String addEstimate(@RequestParam String room, @RequestParam String category,
                              @RequestParam String description, @RequestParam String manufacturer,
                              @RequestParam String product, @RequestParam String quantity,
                              @RequestParam String price, @RequestParam("button") String buttonValues,
                              Model model, Principal principal) {
        if ("Enter".equals(buttonValues)) {
            if (quantity.equals("") && !price.equals("")) {
                quantity = "1";
            } else if (quantity.equals("") && price.equals("")) {
                quantity = "1";
                price = "1";
                manufacturer = "test";
                product = "test";
            }
            Estimate estimate = new Estimate(room, category, description,
                    manufacturer, product, quantity, price);
            estimateService.createEstimate(estimate);
            System.err.println("введены: " + estimate);
            if (userRepository.findByUsername(principal.getName()).getEstimates() == null) {
                userRepository.findByUsername(principal.getName()).setEstimates(new ArrayList<>());
            }
            userRepository.findByUsername(principal.getName()).getEstimates().add(estimate);
            userRepository.save(userRepository.findByUsername(principal.getName()));
            model.addAttribute("estimates",
                    userRepository.findByUsername(principal.getName()).getEstimates());
            return "createEstimate";
        } else {
            model.addAttribute("estimates",
                    userRepository.findByUsername(principal.getName()).getEstimates());
            return "redirect:/mainPage";
        }
    }

    @PostMapping("/createEstimate/deleteById")
    public String deleteById(@RequestParam String delId, Model model, Principal principal) {
        if (delId != null && !delId.equals("")) {
            estimateService.deleteEstimateById(Integer.parseInt(delId));
            model.addAttribute("estimates",
                    userRepository.findByUsername(principal.getName()).getEstimates());
            return "createEstimate";
        }
        model.addAttribute("estimates",
                userRepository.findByUsername(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/createEstimate/deleteAll")
    public String deleteAllEstimate(Model model, Principal principal) {
        estimateRepository.deleteAll();
        model.addAttribute("estimates",
                userRepository.findByUsername(principal.getName()).getEstimates());
        System.err.println("auth: " + principal.getName());
        System.err.println(userRepository.findByUsername(principal.getName())
                .getEstimates().stream().map(estimate -> estimate.getManufacturer()).collect(Collectors.toList()));
        System.err.println(userRepository.findByUsername(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/details")
    public String backToMainPageFromDetails(Model model) {
        model.addAttribute("estimates", estimateService.getAll());
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
        return path;
    }
}