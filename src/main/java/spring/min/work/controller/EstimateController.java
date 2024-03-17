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
import spring.min.work.service.UserServiceImpl;

import java.security.Principal;
import java.util.Comparator;

@Controller
public class EstimateController {
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final EstimateService estimateService;
    private final EstimateRepository estimateRepository;

    @Autowired
    public EstimateController(UserRepository userRepository, UserServiceImpl userService,
                              EstimateService estimateService, EstimateRepository estimateRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.estimateService = estimateService;
        this.estimateRepository = estimateRepository;
    }

    @GetMapping("/details")
    public String details(Model model, Principal principal) {
        model.addAttribute("estimates", userService
                .getUserByName(principal.getName()).getEstimates().stream()
                .sorted(Comparator.comparing(Estimate::getRoom)));
        return "details";
    }

    @GetMapping("/createEstimate")
    public String createEstimate(Model model, Principal principal) {
        model.addAttribute("estimates", userService
                .getUserByName(principal.getName()).getEstimates());
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
            Estimate estimate = new Estimate(room, category, description, manufacturer, product, quantity, price);
            estimate.setUser(userService.getUserByName(principal.getName()));
            estimateService.createEstimate(estimate);
            userService.getUserByName(principal.getName()).getEstimates().add(estimate);
            userRepository.save(userService.getUserByName(principal.getName()));

            model.addAttribute("estimates", userService
                    .getUserByName(principal.getName()).getEstimates());
            return "createEstimate";
        } else {
            model.addAttribute("estimates", userService
                    .getUserByName(principal.getName()).getEstimates());
            return "redirect:/mainPage";
        }
    }

    @PostMapping("/createEstimate/deleteById")
    public String deleteById(@RequestParam String delId, Model model, Principal principal) {
        if (delId != null && !delId.equals("")) {
            estimateService.deleteEstimateById(Integer.parseInt(delId));
            model.addAttribute("estimates", userService
                    .getUserByName(principal.getName()).getEstimates());
            return "createEstimate";
        }
        model.addAttribute("estimates", userService
                .getUserByName(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/createEstimate/deleteAll")
    public String deleteAllEstimate(Model model, Principal principal) {
        estimateService.deleteAll();
        model.addAttribute("estimates", userService
                .getUserByName(principal.getName()).getEstimates());
        return "createEstimate";
    }

    @PostMapping("/details")
    public String backToMainPageFromDetails(Model model) {
        model.addAttribute("estimates", estimateService.getAll());
        return "redirect:/mainPage";
    }
}
