package tech.geek.CandidatePortal.controller.restController;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.entity.Application;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.*;

import javax.servlet.ServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ApplicationController {

    @Autowired
    UserService userService;
    @Autowired
    PositionService positionService;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    MailService mailService;
    @Autowired
    FileService fileService;

    //Function for Candidates applying through the candidate portal
    @PostMapping("/applyposition")
    public ResponseEntity<String> confirmPosition(@RequestParam("position-id") Long positionId,
                                                  @RequestParam("resume-upload") Optional<MultipartFile> resumeFile,
                                                  @RequestParam("resume") Optional<String> resume,
                                                  @RequestParam("cover-letter") MultipartFile coverLetter,
                                                  @RequestParam("first-name") String firstName,
                                                  @RequestParam("last-name") String lastName,
                                                  @RequestParam("notes") String notes,
                                                  @RequestParam("timeZone") Integer timeZone,
                                                  ServletRequest request) throws Exception {

        //Declaring Variables
        Application newApplication = new Application();
        User account = userService.currentUser();
        Map<String, String> resumePaths = fileService.pullUserResumes(account);
        Position position = positionService.getPositionById(positionId);
        JSONObject availability = new JSONObject();
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDate = currentDate.plusDays((7 + (6-currentDate.getDayOfWeek().getValue())));
        List<String> nextTimes = new ArrayList<>();
        List<String> times = new ArrayList<>();
        while (!(currentDate.equals(lastDate))){
            currentDate = currentDate.plusDays(1);
            if (request.getParameterValues(currentDate.toString()) != null) {
                for (String time : nextTimes){
                    times.add(time);
                }
                nextTimes.clear();
                for (String time: request.getParameterValues(currentDate.toString())) {
                    String [] hourMin = time.split(":");
                    Integer hour = Integer.parseInt(hourMin[0]);
                    hour = hour + timeZone;
                    if (hour >= 24){
                        hour = hour%24;
                        nextTimes.add(hour + ":" + hourMin[1]);
                    }
                    else{
                        times.add(hour + ":" + hourMin[1]);
                    }
                }
                if (times.size() > 0)
                    availability.put(currentDate.toString(), times);
                times.clear();
            }
            else if (nextTimes.size() > 0){
                availability.put(currentDate.toString(), nextTimes);
                nextTimes.clear();
            }
        }
        newApplication.setFirst_name(firstName);
        newApplication.setLast_name(lastName);
        //Checks For Valid Fields
        newApplication.setUser(account);
        newApplication.setPosition(position);
        newApplication.setInitial_contact_date(LocalDate.now());
        newApplication.setAvailability(availability.toString());

        //If the user is using a previously uploaded resume
        if (resume.isPresent()){
            newApplication.setResume_path(resumePaths.get(resume.get()));
        }

        //If the user is uploading a resume for the first time.
        else if (resumeFile.isPresent()){
            //Saves the file to the Resume folder, and returns the file path
            newApplication.setResume_path(fileService.saveResume(resumeFile.get(), userService.currentUser()));
        }
        if (coverLetter != null){
            //Set the cover letter path to the cover letter (NOT IMPLEMENTED YET)
        }
        newApplication.setNotes(notes);
        //Saving the Candidate in the database
        newApplication = applicationService.saveApplication(newApplication);
        position.setTotal_candidates(position.getTotal_candidates() + 1);
        position = positionService.savePosition(position);
        //Sends an Email Confirmation
        //System.out.println(mailService.sendConfirmation(account,position.getName(),position.getUserGroup().getName()));
        System.out.println(availability);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }

    //Functions for when an Employer is creating an application internally
    @PostMapping("/positions/{positionID}/candidates")
    public ResponseEntity<Application> createAndAddCandidate(@PathVariable(value="positionID") Long positionID,
                                                           @RequestBody Application application) throws Exception{
        // get position
        Position position = new Position();
        try{
            position = positionService.findById(positionID);
        } catch (Exception e) {
            throw new Exception("No position with id = " + positionID, e);
        }
        // Save candidate
        application.setPosition(position);

        //Simply saves the application in the database, Need to change later
        Application _candidate = applicationService.saveApplication(application);

        //FUNCTION CALLS API NEED TO IMPLEMENT IT LATER
        //Application _candidate = applicationService.CreateCandidate(application.getPosition(), application);

        // Update position's total_candidates field
        position.setTotal_candidates(position.getTotal_candidates() + 1);
        positionService.savePosition(position);

        return new ResponseEntity<>(_candidate, HttpStatus.CREATED);
    }
}
