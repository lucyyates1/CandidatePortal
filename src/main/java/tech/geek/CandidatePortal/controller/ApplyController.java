package tech.geek.CandidatePortal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.entity.*;
import tech.geek.CandidatePortal.services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        return "apply-position";
    }

    @PostMapping("/applyposition")
    public String confirmPosition(@RequestParam("resume") MultipartFile resume,
                                  @RequestParam("cover-letter") MultipartFile coverLetter,
                                  @RequestParam("position-id") Long positionId,
                                  @RequestParam("first-name") String firstName,
                                  @RequestParam("last-name") String lastName,
                                  @RequestParam("text") String notes,
                                  RedirectAttributes attributes,
                                  Model m) throws Exception {
        //Declaring Variables
        String firstnameError = "";
        String lastnameError = "";
        String resumeError = "";
        boolean firstnameEntered = (firstName.length() > 0);
        boolean lastnameEntered = (lastName.length() > 0);
        boolean resumeEntered = (resume.getOriginalFilename() != "");
        Application newApplication = new Application();
        User account = userService.currentUser();
        Position position = positionService.getPositionById(positionId);
        newApplication.setFirst_name(firstName);
        newApplication.setLast_name(lastName);

        //Checks For Valid Fields
        if (!firstnameEntered || !lastnameEntered || !resumeEntered ) {
            if (!firstnameEntered)
                firstnameError = "Field Required";
            if (!lastnameEntered)
                lastnameError = "Field Required";
            if (!resumeEntered)
                resumeError = "Please Upload A Resume (.doc,.docx, .pdf)";
            attributes.addFlashAttribute("firstnameError", firstnameError);
            attributes.addFlashAttribute("lastnameError", lastnameError);
            attributes.addFlashAttribute("resumeError", resumeError);
            return "redirect:/applyposition?id=" + positionId;
        }
        newApplication.setUser(account);
        newApplication.setPosition(position);
        newApplication.setInitial_contact_date(LocalDate.now());

        //Saves the file to the Resume folder, and returns the file path
        newApplication.setResume_path(fileService.saveResume(resume));
        if (coverLetter != null){
            //Set the cover letter path to the cover letter (NOT IMPLEMENTED YET)
        }
        newApplication.setNotes(notes);
        //Saving the Candidate in the database
        newApplication = applicationService.saveApplication(newApplication);

        System.out.println(mailService.sendConfirmation(account,position.getName(),position.getUserGroup().getName()));
        m.addAttribute("applyText","Thank You For Applying!");
        return "apply-success";
    }
}
