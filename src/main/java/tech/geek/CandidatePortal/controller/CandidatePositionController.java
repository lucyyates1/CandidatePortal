package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.entity.entityHelper.PositionDataID;
import tech.geek.CandidatePortal.services.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    CandidateService candidateService;

    @Autowired
    MailService mailService;
    @Autowired
    PositionCandidateService positionCandidateService;
    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;


    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam long id, Model m){
        //This is so the list uses the correct method remove()
        Long positionId = id;

        Position position = positionService.getPositionById(id);
        List<PositionSkill> skills = position.getPosition_skills().stream().toList();
        for (PositionSkill p: position.getPosition_skills()
             ) {
            System.out.println(p.getSkill().getName());
        }
        m.addAttribute("position",position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("formatter",formatter);
        m.addAttribute("skills", skills);
        //If the id already exists replace it at the beginning
        if (PositionService.prevViewed.contains(positionId)){
            PositionService.prevViewed.remove(positionId);
        }
        //If queue is full, push the oldest element out of the queue
        if ((PositionService.prevViewed.size() == 3)){
            PositionService.prevViewed.remove(2);
        }
        PositionService.prevViewed.add(0,id);
        return "candidateposition";
    }

    @GetMapping("/positions")
    public String getPositions(Model m){
        List<Position> positions = positionService.getRecentPositions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        m.addAttribute("listPositions", positions);
        m.addAttribute("formatter",formatter);
        return "positions";
    }

    @GetMapping("/applyposition")
    public String applyPosition(@RequestParam int id, Model m){
        List<Position> positionList = positionService.getAllPositions();
        Candidate candidate = new Candidate();
        PositionCandidate positionCandidate = new PositionCandidate();
        positionCandidate.setCandidate(candidate);
        positionCandidate.setPosition(positionService.getPositionById(id));
        m.addAttribute("positions", positionList);
        m.addAttribute("selectedPosition", positionService.getPositionById(id));
        m.addAttribute("positionCandidate", positionCandidate);
        return "apply-position";
    }

    @PostMapping("/applyposition")
    public String confirmPosition(@RequestParam("resume") MultipartFile resume,
                                  @RequestParam("cover-letter") MultipartFile coverLetter,
                                  @RequestParam("position-id") Long positionId,
                                  @RequestParam("first-name") String firstName,
                                  @RequestParam("last-name") String lastName,
                                  @RequestParam("text") String notes,
                                  Model m) throws Exception {
        //Declaring Variables
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Candidate newCandidate = new Candidate();
        User account = new User();
        PositionCandidate positionCandidate = new PositionCandidate();
        Position position = positionService.getPositionById(positionId);
        newCandidate.setFirst_name(firstName);
        newCandidate.setLast_name(lastName);
        for (User user : userService.getAllUsers()){
            if (username.contains(user.getUsername())){
                account = user;
            }
        }
        //Saves the file to the Resume folder, and returns the file path
        newCandidate.setResume_path(fileService.saveResume(resume));
        if (coverLetter != null){
            //Set the cover letter path to the cover letter (NOT IMPLEMENTED YET)
        }
        newCandidate.setNotes(notes);
        //Saving the Candidate in the database
        newCandidate = candidateService.saveCandidate(newCandidate);

        //Setting up the candidatePosition Object
        positionCandidate.setPosition(position);
        positionCandidate.setCandidate(newCandidate);

        //Setting the proper ids to the object.
        positionCandidate.setId(new PositionDataID(positionId, newCandidate.getCandidate_id()));
        positionCandidate = positionCandidateService.savePositionCandidate(positionCandidate);
        System.out.println(mailService.sendConfirmation(account,position.getName(),position.getUserGroup().getName()));
        return "apply-success";
    }
}
