package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.services.CandidateService;
import tech.geek.CandidatePortal.services.PositionService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    CandidateService candidateService;
    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam int id, Model m){
        Position position = positionService.getPositionById(id);
        List<PositionSkill> skills = position.getPosition_skill().stream().toList();
        for (PositionSkill p: position.getPosition_skill()
             ) {
            System.out.println(p.getSkill().getName());
        }
        m.addAttribute("position",position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("formatter",formatter);
        m.addAttribute("skills", skills);
        return "candidateposition";
    }

    @GetMapping("/positions")
    public String getPositions(Model m){
        List<Position> positions = positionService.getAllPositions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("listPositions", positions);
        m.addAttribute("formatter",formatter);
        return "positions";
    }

    @GetMapping("/applyposition")
    public String applyPosition(@RequestParam int id, Model m){
        List<Position> positionList = positionService.getAllPositions();
        Candidate candidate = new Candidate();
        m.addAttribute("positions", positionList);
        m.addAttribute("selectedPositionID", id);
        m.addAttribute("newCandidate", candidate);
        m.addAttribute("positionCandidate", new PositionCandidate());
        return "apply-position";
    }
}
