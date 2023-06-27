package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.UserService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    private String loadProfile(Model model){
        model.addAttribute("user",userService.currentUser());
        return "profile";
    }

    @PostMapping("/changeusername")
    private String changeUser(@RequestParam("username") String newUsername, RedirectAttributes attributes){
        String usernameError = "";
        if (usernameAlreadyExists(newUsername)){
            usernameError += "Username already exists!";
            attributes.addFlashAttribute("usernameError",usernameError);
            return "redirect:/profile";
        }
        User user = userService.currentUser();
        user.setUsername(newUsername);
        user = userService.saveUser(user);
        attributes.addFlashAttribute("user", user);
        return "redirect:/logout";
    }

    @PostMapping("/changepassword")
    private String changePassword(@RequestParam("originalPassword") String originalPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  RedirectAttributes attributes){
        User user = userService.currentUser();
        String passwordError = "";
        //Needs to be fixed (Password is encrypted)
        if (!(passwordEncoder.matches(originalPassword,user.getPassword()))){
            passwordError = "Please Enter Original Password";
            attributes.addFlashAttribute("passwordError", passwordError);
            return "redirect:/profile";
        }
        if (!(newPassword.equals(confirmPassword))){
            passwordError = "Passwords do not match";
            attributes.addFlashAttribute("passwordError", passwordError);
            return "redirect:/profile";
        }
        if (!(meetsPasswordCriteria(newPassword))){
            passwordError = "Password does not meet criteria";
            attributes.addFlashAttribute("passwordError", passwordError);
            return "redirect:/profile";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        attributes.addFlashAttribute("user", userService.saveUser(user));
        return "redirect:/logout";
    }

    @PostMapping("/changefirstname")
    private String changeFirstName(@RequestParam("firstname") String newFirstName, RedirectAttributes attributes){
        User user = userService.currentUser();
        user.setFirst_name(newFirstName);
        attributes.addFlashAttribute("user", userService.saveUser(user));
        return "redirect:/profile";
    }

    @PostMapping("/changelastname")
    private String changeLastName(@RequestParam("lastname") String newLastName, RedirectAttributes attributes){
        User user = userService.currentUser();
        user.setLast_name(newLastName);
        attributes.addFlashAttribute("user", userService.saveUser(user));
        return "redirect:/profile";
    }

    @PostMapping("/changeEmail")
    private String changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("confirmEmail") String confirmEmail, RedirectAttributes attributes){
        String emailError = "";
        if (!(newEmail.equals(confirmEmail))){
            emailError += "Email Addresses must match!";
            attributes.addFlashAttribute("emailError", emailError);
            return "redirect:/profile";
        }
        if (emailAlreadyExists(newEmail)){
            emailError += "Email Address is already in use!";
            attributes.addFlashAttribute("emailError", emailError);
            return "redirect:/profile";
        }
        User candidateUser = userService.currentUser();
        candidateUser.setEmail(newEmail);
        attributes.addFlashAttribute("user", userService.saveUser(candidateUser));
        return "redirect:/profile";
    }

    public boolean meetsPasswordCriteria(String password) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean specialCharFlag = false;
        boolean lengthFlag = password.length() > 6;

        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            } else if (!Character.isDigit(ch) && !Character.isAlphabetic(ch) && !Character.isWhitespace(ch)) {
                specialCharFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag && specialCharFlag && lengthFlag)
                return true;
        }
        return false;
    }

    public boolean usernameAlreadyExists(String username) {
        List<User> listUsers = userService.getAllUsers();

        for (User user : listUsers) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean emailAlreadyExists(String email) {
        List<User> listUsers = userService.getAllUsers();
        email = email.toLowerCase();

        for (User user : listUsers) {
            if (email.equals(user.getEmail().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // regex pattern was found here: https://www.baeldung.com/java-email-validation-regex
    public boolean meetsEmailCriteria(String email) {
        Pattern regexPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(email);
        return matcher.find();
    }
}
