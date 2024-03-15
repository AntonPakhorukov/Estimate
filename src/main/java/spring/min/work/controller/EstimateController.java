package spring.min.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import spring.min.work.repository.UserRepository;
import spring.min.work.service.EstimateService;

@Controller
public class EstimateController {
    private final UserRepository userRepository;
    private final EstimateService estimateService;
    @Autowired
    public EstimateController(UserRepository userRepository, EstimateService estimateService) {
        this.userRepository = userRepository;
        this.estimateService = estimateService;
    }


}
