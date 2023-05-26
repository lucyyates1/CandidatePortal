package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.geek.CandidatePortal.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public boolean sendConfirmation(User reciever, String position, String userGroup){
        try{
            //Setting up the message to be sent.
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(reciever.getEmail());
            mailMessage.setSubject("Confirmation on Application to " + position);

            //This can always be changed but will be the body of the email.
            mailMessage.setText("Thank you " + reciever.getFirst_name() + " " + reciever.getLast_name() + "for apply for the position " + position + " at " + userGroup + "\n"
                    +"Please Do Not Respond To This Email!");

            //Sends the email and returns true if it succeeds
            javaMailSender.send(mailMessage);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
