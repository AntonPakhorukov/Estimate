package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.min.work.domain.Estimate;
import spring.min.work.repository.EstimateRepository;
import spring.min.work.repository.UserRepository;
import spring.min.work.service.EstimateService;

import java.security.Principal;
import java.util.Comparator;

@Controller
public class MainController {
    private final UserRepository userRepository;
    private final EstimateService estimateService;
    private final EstimateRepository estimateRepository;

    @Autowired
    public MainController(UserRepository userRepository, EstimateService estimateService, EstimateRepository estimateRepository) {
        this.userRepository = userRepository;
        this.estimateService = estimateService;
        this.estimateRepository = estimateRepository;
    }

    @GetMapping({"", "/"})
    public String first() {
        return "startPage";
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model, Principal principal) {
        model.addAttribute("estimates", estimateService
                .getTotal(userRepository.findByUsername(principal.getName()).getEstimates()));
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
        model.addAttribute("estimates", userRepository
                .findByUsername(principal.getName()).getEstimates()
                .stream().sorted(Comparator.comparing(Estimate::getRoom)));
        return "details";
    }

    @GetMapping("/createEstimate")
    public String createEstimate(Model model, Principal principal) {
        model.addAttribute("estimates", userRepository
                .findByUsername(principal.getName()).getEstimates());
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
            userRepository.findByUsername(principal.getName()).getEstimates().add(estimate);
            userRepository.save(userRepository.findByUsername(principal.getName()));
            model.addAttribute("estimates", userRepository
                    .findByUsername(principal.getName()).getEstimates());
            return "createEstimate";
        } else {
            model.addAttribute("estimates", userRepository
                    .findByUsername(principal.getName()).getEstimates());
            return "redirect:/mainPage";
        }
    }

    @PostMapping("/createEstimate/deleteById")
    public String deleteById(@RequestParam String delId, Model model, Principal principal) {
        if (delId != null && !delId.equals("")) {
            estimateService.deleteEstimateById(Integer.parseInt(delId));
            model.addAttribute("estimates", userRepository
                    .findByUsername(principal.getName()).getEstimates());
            return "createEstimate";
        }
        model.addAttribute("estimates", userRepository
                .findByUsername(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/createEstimate/deleteAll")
    public String deleteAllEstimate(Model model, Principal principal) {
        estimateRepository.deleteAll();
        model.addAttribute("estimates", userRepository
                .findByUsername(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/details")
    public String backToMainPageFromDetails(Model model) {
        model.addAttribute("estimates", estimateService.getAll());
        return "redirect:/mainPage";
    }
}