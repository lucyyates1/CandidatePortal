package tech.geek.CandidatePortal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.services.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    UserService userService;
    @Autowired
    ApplicationService applicationService;


    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam(value="id") Long positionID, Model model, HttpServletResponse response){

        if (userService.currentUser().getRole().getRole_id() != 4){;
            Position position = positionService.findById(positionID);
            List<Application> listCandidates = applicationService.getApplicationsByPosition(position);
            // TODO: Candidate archival isn't possible yet
            //listCandidates.removeIf(Candidate::isArchived);

            // TODO: Sort listPositionCandidates based on listCandidates order of candidate_id
            // May not be necessary

            model.addAttribute("position", position);
            model.addAttribute("listCandidates", listCandidates);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            model.addAttribute("formatter", formatter);

            return "needl-candidatesposition";
        }
        Position position = positionService.getPositionById(positionID);
        List<Skill> skillList = new ArrayList<>();
        List<Certification> certificationList = new ArrayList<>();
        for (PositionSkill p: position.getPosition_skills()
             ) {
            skillList.add(p.getSkill());
        }
        for (PositionCertification c: position.getPosition_certification()
        ) {
            certificationList.add(c.getCertification());
        }
        model.addAttribute("position",position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.addAttribute("formatter",formatter);
        model.addAttribute("skills", skillList);
        model.addAttribute("certifications", certificationList);
        model.addAttribute("type", StringUtils.substringBetween(position.getDescription(),"Job Type: ","\n"));
        model.addAttribute("virtual", StringUtils.substringBetween(position.getDescription(),"Virtual: ","\n"));
        //Creates a cookie with the position Id and timestamp
        Cookie cookie = new Cookie(Long.toString(position.getPosition_id()),Long.toString(System.currentTimeMillis()));
        cookie.setPath("/");
        cookie.setMaxAge(600);
        response.addCookie(cookie);
        /*
        RecentViewed recentViewed = new RecentViewed();
        User currentUser = userService.currentUser();
        recentViewed.setRecentViewedID(new RecentViewedID(position.getPosition_id(), currentUser.getUser_id()));
        recentViewed.setPosition(position);
        recentViewed.setUser(currentUser);
        recentViewed.setLast_viewed(new Timestamp(System.currentTimeMillis()));
        recentViewedService.saveRecentlyViewed(recentViewed);
        //If the id already exists replace it at the beginning
        if (PositionService.prevViewed.contains(positionId)){
            PositionService.prevViewed.remove(positionId);
        }
        //If queue is full, push the oldest element out of the queue
        if ((PositionService.prevViewed.size() == 3)){
            PositionService.prevViewed.remove(2);
        }
        PositionService.prevViewed.add(0,id);
        */

        return "candidateposition";
    }

    @GetMapping("/availability")
    public String getAvailability(@RequestParam("id") Long applicationID, Model model){
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(applicationID);
        Application currentApp = applicationService.getApplicationById(applicationID);
        try{
            Map<String, String []> availability = mapper.readValue(currentApp.getAvailability(),Map.class);
            System.out.println(availability);
            model.addAttribute("available", availability);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "test-availability";
    }

    //Candidate Side
    @GetMapping("/positions")
    public String getPositions(Model m){
        if (userService.currentUser().getRole().getRole_id() != 4){
            return "redirect:/recruiting";
        }
        List<Position> positions = positionService.getAllPositions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("listPositions", positions);
        m.addAttribute("formatter",formatter);
        return "positions";
    }

    //Recruiting side
    @GetMapping("/recruiting")
    public String recruiting(Model model)
    {
        if (userService.currentUser().getRole().getRole_id() == 4){
            return "redirect:/positions";
        }
        // Creating list of positions to send to front end
        List<Position> listPositions = positionService.getAllPositions();
        listPositions.removeIf(Position::isArchived);
        listPositions.removeIf(Position::isTemplate);
        model.addAttribute("listPositions", listPositions);

        // Creating a list of dates to send to front end
        //  - Necessary because we need to format the dates
        List<LocalDate> listFilDates = new ArrayList<>();

        for (Position pos : listPositions) {
            LocalDate tempFill = null;
            for(Application application: pos.getApplications()) {
                if(application.getFilled_date() != null)
                    tempFill = application.getFilled_date();
            }
            listFilDates.add(tempFill);
        }


        model.addAttribute("listFilDates", listFilDates);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.addAttribute("formatter", formatter);

        return "recruiting";
    }

    @GetMapping("/applied")
    public String applied(Model model){
        //Declaring Variables
        User account = userService.currentUser();
        List<Application> applications = account.getApplications().stream().toList();
        List<Position> positions = new ArrayList<>();
        for (Application application : applications){
            for (Application app : applications){
                positions.add(app.getPosition());
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.addAttribute("listCandidates", applications);
        model.addAttribute("listPositions",positions);
        model.addAttribute("formatter",formatter);
        return "active-application";
    }
}
