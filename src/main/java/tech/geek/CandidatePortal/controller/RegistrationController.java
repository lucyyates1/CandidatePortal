package tech.geek.CandidatePortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geek.CandidatePortal.services.RoleService;
import tech.geek.CandidatePortal.services.UserGroupService;
import tech.geek.CandidatePortal.services.UserService;
import tech.geek.CandidatePortal.entity.Role;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.entity.UserGroup;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Loads registration page
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handles the form of the registration page
    @PostMapping("/register")
    public String register_confirm(@ModelAttribute User user, @RequestParam String passwordConfirm, RedirectAttributes attributes) {
        // flags for whether something was even entered
        boolean firstnameEntered = (user.getFirst_name() != "");
        boolean lastnameEntered = (user.getLast_name() != "");
        boolean usernameEntered = (user.getUsername() != "");
        boolean emailEntered = (user.getEmail() != "");
        boolean passwordEntered = (user.getPassword() != "");
        boolean passwordConfirmEntered = (passwordConfirm != "");
        // flags for all other validation
        boolean usernameAlreadyExistsFlag = false;
        boolean emailAlreadyExistsFlag = false;
        boolean emailCriteriaFlag = false;
        boolean passwordCriteriaFlag = false;
        boolean passwordMatchFlag = false;
        // strings to store errors
        String firstnameError = "";
        String lastnameError = "";
        String usernameError = "";
        String passwordError = "";
        String emailError = "";
        String passwordConfirmError = "";

        if (!firstnameEntered)
            firstnameError += "First Name required. ";
        if (!firstnameEntered)
            lastnameError += "Last Name required. ";
        if (!usernameEntered)
            usernameError += "Username required. ";
        if (!emailEntered)
            emailError += "Email required. ";
        if (!passwordEntered)
            passwordError += "Password required. ";
        if (!passwordConfirmEntered)
            passwordConfirmError += "Please confirm your password. ";

        if (usernameAlreadyExists(user.getUsername())) {
            usernameAlreadyExistsFlag = true;
            usernameError += "Username already exists.";
        }
        if (emailAlreadyExists(user.getEmail())) {
            emailAlreadyExistsFlag = true;
            emailError += "Email already exists.";
        }
        if (!meetsEmailCriteria(user.getEmail())) {
            emailCriteriaFlag = true;
            emailError += "Not a valid email.";
        }

        if (!meetsPasswordCriteria(user.getPassword()) && passwordEntered) {
            passwordCriteriaFlag = true;
            passwordError += "Password does not match criteria listed in the tooltip.";
        }
        if (!user.getPassword().equals(passwordConfirm)) {
            passwordMatchFlag = true;
            passwordConfirmError += "Passwords didn't match.";
        }


        if (!firstnameEntered || !lastnameEntered || !usernameEntered || !emailEntered || !passwordEntered || !passwordConfirmEntered
                || usernameAlreadyExistsFlag || emailAlreadyExistsFlag || passwordCriteriaFlag || passwordMatchFlag || emailCriteriaFlag) {
            attributes.addFlashAttribute("firstnameError", firstnameError);
            attributes.addFlashAttribute("lastnameError", lastnameError);
            attributes.addFlashAttribute("usernameError", usernameError);
            attributes.addFlashAttribute("emailError", emailError);
            attributes.addFlashAttribute("passwordError", passwordError);
            attributes.addFlashAttribute("passwordConfirmError", passwordConfirmError);
            return "redirect:/register";
        }

        List<Role> roles = roleService.getAllRoles();
        for (Role role : roles) {
            if (Objects.equals(role.getRole(), "ROLE_UNAUTHORIZEDUSER")) {
                user.setRole(role);
                break;
            }
        }
        // THIS WILL BE GEEKSI BY DEFAULT BUT WE NEED TO FIGURE OUT HOW WE'RE DETERMINING USER GROUPS
        List<UserGroup> userGroups = userGroupService.getAllUserGroup();
        for (UserGroup userGroup : userGroups) {
            if (Objects.equals(userGroup.getName(), "GeekSI")) {
                user.setUserGroup(userGroup);
                break;
            }
        }

        // Encoding password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Setting registration success redirect attribute
        String registrationSuccess = "Successfully Registered!";
        attributes.addFlashAttribute("registrationSuccess", registrationSuccess);

        userService.saveUser(user);
        return "redirect:/register";
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