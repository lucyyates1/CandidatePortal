package tech.geek.CandidatePortal.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.services.PositionService;

import java.time.format.DateTimeFormatter;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam int id, Model m){
        Position position = positionService.getPositionById(id);
        m.addAttribute("position",position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("formatter",formatter);
        return "candidateposition";
    }

    @GetMapping("/applyposition")
    public String applyPosition(@RequestParam int id, Model m){
        return "apply-position";
    }
}
