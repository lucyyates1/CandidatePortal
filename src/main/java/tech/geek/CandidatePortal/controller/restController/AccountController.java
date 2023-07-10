package tech.geek.CandidatePortal.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collection;
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
    private ResponseEntity<Integer> changeUser(@RequestParam("username") String newUsername) throws ServletException {
        if (usernameAlreadyExists(newUsername)){
            return new ResponseEntity(1, HttpStatus.CREATED);
        }
        User user = userService.currentUser();
        user.setUsername(newUsername);
        user = userService.saveUser(user);

        //Handles the session context after the username has been changed
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return new ResponseEntity(0, HttpStatus.CREATED);
    }

    @PostMapping("/changepassword")
    private ResponseEntity<Integer> changePassword(@RequestParam("originalPassword") String originalPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword) throws ServletException {
        User user = userService.currentUser();
        if (!(passwordEncoder.matches(originalPassword,user.getPassword()))){
            return new ResponseEntity<>(2, HttpStatus.CREATED);
        }
        if (!(newPassword.equals(confirmPassword))){
            return new ResponseEntity<>(3, HttpStatus.CREATED);
        }
        if (!(meetsPasswordCriteria(newPassword))){
            return new ResponseEntity<>(4, HttpStatus.CREATED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);

        //Handles the session context after the username has been changed
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return new ResponseEntity<>(0, HttpStatus.CREATED);
    }

    @PostMapping("/changefirstname")
    private ResponseEntity<Integer> changeFirstName(@RequestParam("firstname") String newFirstName){
        System.out.println("---------------------------------");
        System.out.println(newFirstName);
        User user = userService.currentUser();
        user.setFirst_name(newFirstName);
        userService.saveUser(user);
        return new ResponseEntity<>(0, HttpStatus.CREATED);
    }

    @PostMapping("/changelastname")
    private ResponseEntity<Integer> changeLastName(@RequestParam("lastname") String newLastName){
        System.out.println(newLastName);
        User user = userService.currentUser();
        user.setLast_name(newLastName);
        userService.saveUser(user);
        return new ResponseEntity<>(0, HttpStatus.CREATED);
    }

    @PostMapping("/changeEmail")
    private ResponseEntity<Integer> changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("confirmEmail") String confirmEmail){
        if (!(newEmail.equals(confirmEmail))){
            return new ResponseEntity<>(5, HttpStatus.CREATED);
        }
        if (emailAlreadyExists(newEmail)){
            return new ResponseEntity<>(6, HttpStatus.CREATED);
        }
        User user = userService.currentUser();
        user.setEmail(newEmail);
        userService.saveUser(user);
        return new ResponseEntity<>(0, HttpStatus.CREATED);
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
