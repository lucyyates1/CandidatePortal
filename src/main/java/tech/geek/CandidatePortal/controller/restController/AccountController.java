package tech.geek.CandidatePortal.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AccountController {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest servletRequest;

    @PostMapping("/changeusername")
    private ResponseEntity<String> changeUser(@RequestParam("username") String newUsername) throws ServletException {
        if (usernameAlreadyExists(newUsername)){
            return new ResponseEntity("user_exists", HttpStatus.CREATED);
        }
        User user = userService.currentUser();
        user.setUsername(newUsername);
        user = userService.saveUser(user);
        servletRequest.logout();
        return new ResponseEntity("changed", HttpStatus.CREATED);
    }

    @PostMapping("/changepassword")
    private ResponseEntity<String> changePassword(@RequestParam("originalPassword") String originalPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword) throws ServletException {
        User user = userService.currentUser();
        if (!(passwordEncoder.matches(originalPassword,user.getPassword()))){
            return new ResponseEntity<>("Wrong Password", HttpStatus.CREATED);
        }
        if (!(newPassword.equals(confirmPassword))){
            return new ResponseEntity<>("Passwords Don't Match", HttpStatus.CREATED);
        }
        if (!(meetsPasswordCriteria(newPassword))){
            return new ResponseEntity<>("Doesn't Meet Password Criteria", HttpStatus.CREATED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        servletRequest.logout();
        return new ResponseEntity<>("Changed Password", HttpStatus.CREATED);
    }

    @PostMapping("/changefirstname")
    private ResponseEntity<String> changeFirstName(@RequestParam("firstname") String newFirstName){
        System.out.println("---------------------------------");
        System.out.println(newFirstName);
        User user = userService.currentUser();
        user.setFirst_name(newFirstName);
        userService.saveUser(user);
        return new ResponseEntity<>("Changed First Name", HttpStatus.CREATED);
    }

    @PostMapping("/changelastname")
    private ResponseEntity<String> changeLastName(@RequestParam("lastname") String newLastName){
        System.out.println(newLastName);
        User user = userService.currentUser();
        user.setLast_name(newLastName);
        userService.saveUser(user);
        return new ResponseEntity<>("Changed Last Name", HttpStatus.CREATED);
    }

    @PostMapping("/changeEmail")
    private ResponseEntity<String> changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("confirmEmail") String confirmEmail){
        if (!(newEmail.equals(confirmEmail))){
            return new ResponseEntity<>("Email Not Match", HttpStatus.CREATED);
        }
        if (emailAlreadyExists(newEmail)){
            return new ResponseEntity<>("Email In Use", HttpStatus.CREATED);
        }
        User user = userService.currentUser();
        user.setEmail(newEmail);
        userService.saveUser(user);
        return new ResponseEntity<>("Email Changed", HttpStatus.CREATED);
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
