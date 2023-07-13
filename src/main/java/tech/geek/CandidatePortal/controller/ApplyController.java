package tech.geek.CandidatePortal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApplyController {

    @Autowired
    PositionService positionService;
    @Autowired
    ApplicationService applicationService;


    //@Autowired
    //PositionCandidateService positionCandidateService;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    MailService mailService;

    @GetMapping("/applyposition")
    public String applyPosition(@RequestParam int id, Model m){
        User user = userService.currentUser();
        Position selectedPosition = positionService.getPositionById(id);
        List<Skill> skillList = new ArrayList<>();
        List<Certification> certificationList = new ArrayList<>();
        Map<String, String> savedResumes = new HashMap<>();
        List<String> fileNames = new ArrayList<>();
        for (Application application : user.getApplications()
             ) {
            if (application.getPosition().getPosition_id() == id){
                m.addAttribute("applyText", "Application Aleady Recieved!");
                return "apply-success";
            }
        }
        List<Position> positionList = positionService.getAllPositions();
        m.addAttribute("positions", positionList);
        for (PositionSkill p: selectedPosition.getPosition_skills()
        ) {
            /*skills += "\u2022 " + p.getSkill().getName() + ": " + p.getExperience() + " Year";
            if (p.getExperience() != 1){
                skills += "s";
            }
            skills += "\n";*/
            skillList.add(p.getSkill());
        }
        for (PositionCertification c: selectedPosition.getPosition_certification()
        ) {
            certificationList.add(c.getCertification());
        }
        m.addAttribute("selectedPosition", selectedPosition);
        m.addAttribute("skills",skillList);
        m.addAttribute("certifications", certificationList);
        m.addAttribute("type", StringUtils.substringBetween(selectedPosition.getDescription(),"Job Type: ","\n"));
        m.addAttribute("virtual", StringUtils.substringBetween(selectedPosition.getDescription(),"Virtual: ","\n"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("formatter",formatter);
        m.addAttribute("user", user);
        try {
            savedResumes = fileService.pullUserResumes(user);
            for (String key:
                    savedResumes.keySet()) {
                System.out.println(key);
                System.out.println(savedResumes.get(key));
                fileNames.add(key);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        m.addAttribute("fileNames", fileNames);
        return "apply-position";
    }

    @GetMapping("/applySuccess")
    public String applySuccess(Model model){
        model.addAttribute("applyText", "Thank You For Applying!");
        return "apply-success";
    }


}
