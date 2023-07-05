package tech.geek.CandidatePortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.geek.CandidatePortal.entity.Application;

@Controller
public class AddApplicationController {
    @GetMapping("/addCandidate")
    public String addCandidate(Model model, @RequestParam(value="id") Long positionID) {

        Application application = new Application();
        model.addAttribute("newcandidate", application);

        return "add_candidate";
    }
}
