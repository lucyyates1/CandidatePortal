package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.services.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    PositionService positionService;

    @Autowired
    ApplicationService applicationService;

    //@Autowired
    //PositionCandidateService positionCandidateService;

    @Autowired
    UserService userService;
    //@Autowired
    //RecentViewedService recentViewedService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        //Fills the array with the recently viewed positions
        List<Position> listPositions = positionService.getAllPositions();
        listPositions.removeIf(Position::isTemplate);
        List<Position> listRecent = new ArrayList<>();
        Cookie [] cookies = request.getCookies();
        SortedMap<Long, Long> positionHistory = new TreeMap<>(Collections.reverseOrder());
        if (cookies != null){
            for (Cookie c: cookies
                 ) {
                try {
                    //We flip the value and name to order the list by timestamp in descending order
                    positionHistory.put(Long.parseLong(c.getValue()), Long.parseLong(c.getName()));
                } catch (Exception e) {
                }
            }
            for (Long timestamp : positionHistory.keySet()){
                //Converts the Map to a List to pass into the Model
                if (listRecent.size() < 5)
                    listRecent.add(positionService.getPositionById(positionHistory.get(timestamp)));
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Creating a list of dates to send to front end
        //  - Necessary because we need to format the dates
        List<LocalDate> listFilDates = new ArrayList<>();

        // Grabbing Position Dates & Filled Dates
        // Done Grabbing Dates
        List<Application> applications = applicationService.getAllApplications();



        //model.addAttribute("activePositions", listPositions.size());
        model.addAttribute("listFilDates", listFilDates);
        model.addAttribute("listPositions", listRecent);
        model.addAttribute("numCandidates", applications.size());
        model.addAttribute("username", username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.addAttribute("formatter", formatter);
        return "index";
    }
}
