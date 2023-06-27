package tech.geek.CandidatePortal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.entity.entityHelper.RecentViewedID;
import tech.geek.CandidatePortal.services.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    UserService userService;
    @Autowired
    RecentViewedService recentViewedService;


    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam long id, Model m, HttpServletResponse response){

        Position position = positionService.getPositionById(id);
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
        m.addAttribute("position",position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("formatter",formatter);
        m.addAttribute("skills", skillList);
        m.addAttribute("certifications", certificationList);
        m.addAttribute("type", StringUtils.substringBetween(position.getDescription(),"Job Type: ","\n"));
        m.addAttribute("virtual", StringUtils.substringBetween(position.getDescription(),"Virtual: ","\n"));
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

    @GetMapping("/positions")
    public String getPositions(Model m){
        List<Position> positions = positionService.getAllPositions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("listPositions", positions);
        m.addAttribute("formatter",formatter);
        return "positions";
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
