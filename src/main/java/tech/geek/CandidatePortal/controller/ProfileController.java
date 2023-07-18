package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserService userService;


    @GetMapping
    private String viewProfile(@RequestParam(value="code") Optional<Integer> code, Model model){
        String status = "";
        String style = "good";
        if (code.isPresent()) {
            System.out.println(code.get());
            if (code.get().equals(0)) {
                status = "Successfully Updated Information!";
            }
            else if (code.get().equals(1)) {
                style = "error";
                status = "Password is Incorrect!";
            }
            else if (code.get().equals(2)) {
                style = "error";
                status = "Password Does Not Match!";
            }
            else if (code.get().equals(3)) {
                style = "error";
                status = "Password Does Not Meet Criteria!";
            }
            else if (code.get().equals(7)){
                style = "good";
                status = "Successfully Added Resume!";
            }
        }
        model.addAttribute("style", style);
        model.addAttribute("status",status);
        model.addAttribute("user",userService.currentUser());
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(@RequestParam("code") Optional<Integer> code, Model model){
        String status = "";
        String style = "good";
        if (code.isPresent()) {
            System.out.println(code.get());
            if (code.get().equals(0)){
                return "redirect:/profile?code=0";
            }
            else if (code.get().equals(4)) {
                style = "error";
                status = "Username Already Exists!";
            }
            else if (code.get().equals(5)) {
                style = "error";
                status = "Invalid Email Address!";
            }
            else if (code.get().equals(6)) {
                style = "error";
                status = "Email Address Already Exists!";
            }

        }
        model.addAttribute("style", style);
        model.addAttribute("status",status);
        model.addAttribute("user",userService.currentUser());
        return "profile-edit";
    }

}
