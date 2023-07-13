package tech.geek.CandidatePortal.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.geek.CandidatePortal.services.FileService;
import tech.geek.CandidatePortal.services.UserService;

@RestController
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    @PostMapping("/addResume")
    private ResponseEntity<String> addResume(@RequestParam("resume") MultipartFile resume) throws Exception {
        System.out.println("MADE IT HERE");
        System.out.println(resume.getName());
        String filePath = fileService.saveResume(resume, userService.currentUser());
        return new ResponseEntity<>(filePath, HttpStatus.CREATED);
    }
}
