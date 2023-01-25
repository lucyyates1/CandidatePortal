package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tech.geek.CandidatePortal.services.PositionService;
import tech.geek.CandidatePortal.entity.Position;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public String home(Model model) {

        List<Position> listPositions = positionService.getAllPositions();
        listPositions.removeIf(Position::isTemplate);
        List<Position> listRecentPositions = positionService.getRecentPositions();
        // Creating a list of dates to send to front end
        //  - Necessary because we need to format the dates
        List<LocalDate> listFilDates = new ArrayList<>();

        // Grabbing Position Dates & Filled Dates
        for (Position pos : listRecentPositions) {
            LocalDate tempFill = null;
            for(Position position: pos.getPosition_candidates()) {
                if(positionCandidate.getFilled_date() != null)
                    tempFill = positionCandidate.getFilled_date();
            }
            listFilDates.add(tempFill);
        } // Done Grabbing Dates
        List<PositionCandidate> filledPositionCandidates = positionCandidateService.getAllFilledPositionCandidates();
        List<Candidate> candidates = candidateService.getAllCandidates();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("activePositions", listPositions.size());
        model.addAttribute("listFilDates", listFilDates);
        model.addAttribute("listPositions", listRecentPositions);
        model.addAttribute("filledPositions", filledPositionCandidates.size());
        model.addAttribute("numCandidates", candidates.size());
        model.addAttribute("username", username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.addAttribute("formatter", formatter);

        return "index";
    }
}
