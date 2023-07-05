package tech.geek.CandidatePortal.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.services.MailService;
import tech.geek.CandidatePortal.services.UserService;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping("/admin")
    public String admin(Model model)
    {
        if (userService.currentUser().getRole().getRole_id() == 4){
            return "redirect:/";
        }
        //model.addAttribute("userGroup", new UserGroup());     // WILL EVENTUALLY NEED TO ADD THIS
        // Creating list of users to send to front end
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);

        //model.addAttribute("user", new User());       // will be used later for user invitation
        return "admin";
    }

    // Function for archiving positions
    @PutMapping("/admin")
    @ResponseBody
    public void removeUser(@RequestParam("selectedID") long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(id);
    }

    @GetMapping("/admin_inviteUser")
    public String invite(Model model)
    {
        return "admin-invite";
    }

    @PostMapping("/admin_inviteUserConfirm")
    public String invite_confirm(@ModelAttribute User user) {

        // TODO: send email
        // get confirmation of email receipt
        if (mailService.sendInvite(user))
            System.out.println("User was successfully invited?");
        return "admin-confirm";
    }

    @GetMapping("/admin_resetPassword")
    public String reset_password(Model model, @ModelAttribute User user)
    {
        System.out.println(user.getUsername());
        return "admin-reset-password";
    }

    @GetMapping("/admin_removeUser")
    public String remove_user(Model model, @ModelAttribute User user)
    {
        System.out.println(user.getUsername());
        return "admin-remove-user";
    }
}
