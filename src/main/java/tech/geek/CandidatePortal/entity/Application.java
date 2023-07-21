package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long application_id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name= "position_id")
    @JsonIgnore
    private Position position;
    @OneToMany(mappedBy = "application")
    @JsonIgnore
    private Set<ApplicationCertification> application_certifications = new HashSet<>();

    @Column(length = 20)
    private String first_name;

    @Column(length = 20)
    private String last_name;

    @Column(length = 50)
    private String education;

    @Column(columnDefinition = "TINYINT")
    private int experience;

    @Column(length = 255)
    private String resume_path;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate initial_contact_date;

    @Column(name = "availability", columnDefinition = "json")
    @JsonRawValue
    private String availability;

    @Column(columnDefinition = "TINYINT")
    private int current_interview;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate meet_and_greet_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate technical_interview_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate offer_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate filled_date;

    private boolean archived;

    @Column(columnDefinition = "TINYINT")
    private int score;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate score_run_date;

    public Application() {
    }

    public Application(long application_id, User user, Position position, Set<ApplicationCertification> application_certifications, String first_name, String last_name, String education, int experience, String resume_path, String notes, LocalDate initial_contact_date, String availability, int current_interview, LocalDate meet_and_greet_date, LocalDate technical_interview_date, LocalDate offer_date, LocalDate filled_date, boolean archived, int score, LocalDate score_run_date) {
        this.application_id = application_id;
        this.user = user;
        this.position = position;
        this.application_certifications = application_certifications;
        this.first_name = first_name;
        this.last_name = last_name;
        this.education = education;
        this.experience = experience;
        this.resume_path = resume_path;
        this.notes = notes;
        this.initial_contact_date = initial_contact_date;
        this.availability = availability;
        this.current_interview = current_interview;
        this.meet_and_greet_date = meet_and_greet_date;
        this.technical_interview_date = technical_interview_date;
        this.offer_date = offer_date;
        this.filled_date = filled_date;
        this.archived = archived;
        this.score = score;
        this.score_run_date = score_run_date;
    }

    public long getApplication_id() {
        return application_id;
    }

    public void setApplication_id(long application_id) {
        this.application_id = application_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<ApplicationCertification> getApplication_certifications() {
        return application_certifications;
    }

    public void setApplication_certifications(Set<ApplicationCertification> application_certifications) {
        this.application_certifications = application_certifications;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getResume_path() {
        return resume_path;
    }

    public void setResume_path(String resume_path) {
        this.resume_path = resume_path;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getInitial_contact_date() {
        return initial_contact_date;
    }

    public void setInitial_contact_date(LocalDate initial_contact_date) {
        this.initial_contact_date = initial_contact_date;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getCurrent_interview() {
        return current_interview;
    }

    public void setCurrent_interview(int current_interview) {
        this.current_interview = current_interview;
    }

    public LocalDate getMeet_and_greet_date() {
        return meet_and_greet_date;
    }

    public void setMeet_and_greet_date(LocalDate meet_and_greet_date) {
        this.meet_and_greet_date = meet_and_greet_date;
    }

    public LocalDate getTechnical_interview_date() {
        return technical_interview_date;
    }

    public void setTechnical_interview_date(LocalDate technical_interview_date) {
        this.technical_interview_date = technical_interview_date;
    }

    public LocalDate getOffer_date() {
        return offer_date;
    }

    public void setOffer_date(LocalDate offer_date) {
        this.offer_date = offer_date;
    }

    public LocalDate getFilled_date() {
        return filled_date;
    }

    public void setFilled_date(LocalDate filled_date) {
        this.filled_date = filled_date;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getScore_run_date() {
        return score_run_date;
    }

    public void setScore_run_date(LocalDate score_run_date) {
        this.score_run_date = score_run_date;
    }

    @Override
    public String toString() {
        return "Application{" +
                "application_id=" + application_id +
                ", user=" + user +
                ", position=" + position +
                ", application_certifications=" + application_certifications +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", education='" + education + '\'' +
                ", experience=" + experience +
                ", resume_path='" + resume_path + '\'' +
                ", notes='" + notes + '\'' +
                ", initial_contact_date=" + initial_contact_date +
                ", availability='" + availability + '\'' +
                ", current_interview=" + current_interview +
                ", meet_and_greet_date=" + meet_and_greet_date +
                ", technical_interview_date=" + technical_interview_date +
                ", offer_date=" + offer_date +
                ", filled_date=" + filled_date +
                ", archived=" + archived +
                ", score=" + score +
                ", score_run_date=" + score_run_date +
                '}';
    }
}