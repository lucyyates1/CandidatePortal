package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.entity.entityHelper.PositionDataID;
import tech.geek.CandidatePortal.services.CandidateService;
import tech.geek.CandidatePortal.services.FileService;
import tech.geek.CandidatePortal.services.PositionCandidateService;
import tech.geek.CandidatePortal.services.PositionService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CandidatePositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    CandidateService candidateService;

    @Autowired
    PositionCandidateService positionCandidateService;
    @Autowired
    FileService fileService;
    @GetMapping("/candidatesposition")
    public String getPosition(@RequestParam int id, Model m){
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
        PositionCandidate positionCandidate = new PositionCandidate();
        positionCandidate.setCandidate(candidate);
        positionCandidate.setPosition(positionService.getPositionById(id));
        m.addAttribute("positions", positionList);
        m.addAttribute("selectedPositionID", id);
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
        Candidate newCandidate = new Candidate();
        PositionCandidate positionCandidate = new PositionCandidate();
        Position position = positionService.getPositionById(positionId);
        newCandidate.setFirst_name(firstName);
        newCandidate.setLast_name(lastName);
        //Saves the file to the Resume folder, and returns the file path
        newCandidate.setResume_path(fileService.saveResume(resume));
        if (coverLetter != null){
            //Set the cover letter path to the cover letter (NOT IMPLEMENTED YET)
        }
        newCandidate.setNotes(notes);
        System.out.println(newCandidate.getResume_path());
        //Saving the Candidate in the database
        newCandidate = candidateService.saveCandidate(newCandidate);

        //Setting up the candidatePosition Object
        positionCandidate.setPosition(position);
        positionCandidate.setCandidate(newCandidate);

        //Setting the proper ids to the object.
        positionCandidate.setId(new PositionDataID(positionId, newCandidate.getCandidate_id()));
        positionCandidate = positionCandidateService.savePositionCandidate(positionCandidate);
        return "apply-success";
    }
}
