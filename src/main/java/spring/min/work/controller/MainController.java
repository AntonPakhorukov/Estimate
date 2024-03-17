package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.min.work.service.EstimateService;
import spring.min.work.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class MainController {
    private final EstimateService estimateService;
    private final UserServiceImpl userService;

    @Autowired
    public MainController(EstimateService estimateService, UserServiceImpl userService) {
        this.estimateService = estimateService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String first() {
        return "startPage";
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model, Principal principal) {
        model.addAttribute("estimates", estimateService
                .getTotal(userService.getUserByName(principal.getName()).getEstimates()));
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
}