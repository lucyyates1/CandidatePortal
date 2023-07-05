package tech.geek.CandidatePortal.entity;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long position_id;


    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private Set<PositionSkill> position_skills = new HashSet<>();

    @OneToMany(mappedBy = "certification")
    @JsonIgnore
    private Set<PositionCertification> position_certification = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    @JsonIgnore
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;


    @Column(length=40)
    private String name;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length=50)
    private String education;

    private boolean education_required;

    @Column(columnDefinition = "TINYINT")
    private int experience_required;

    @Column(length=40)
    private String place_of_performance;

    private boolean template;

    private boolean archived;

    private int scored_candidates;

    private int total_candidates;

    public Position() {}

    public Position(long position_id, Set<Application> applications, Set<PositionSkill> position_skills, Set<PositionCertification> position_certification, UserGroup userGroup, Client client, String name, LocalDate date, String description, String education, boolean education_required, int experience_required, String place_of_performance, boolean template, boolean archived, int scored_candidates, int total_candidates) {
        this.position_id = position_id;
        this.applications = applications;
        this.position_skills = position_skills;
        this.position_certification = position_certification;
        this.userGroup = userGroup;
        this.client = client;
        this.name = name;
        this.date = date;
        this.description = description;
        this.education = education;
        this.education_required = education_required;
        this.experience_required = experience_required;
        this.place_of_performance = place_of_performance;
        this.template = template;
        this.archived = archived;
        this.scored_candidates = scored_candidates;
        this.total_candidates = total_candidates;
    }

    public long getPosition_id() {
        return position_id;
    }

    public void setPosition_id(long position_id) {
        this.position_id = position_id;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Set<PositionSkill> getPosition_skills() {
        return position_skills;
    }

    public void setPosition_skills(Set<PositionSkill> position_skills) {
        this.position_skills = position_skills;
    }

    public Set<PositionCertification> getPosition_certification() {
        return position_certification;
    }

    public void setPosition_certification(Set<PositionCertification> position_certification) {
        this.position_certification = position_certification;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isEducation_required() {
        return education_required;
    }

    public void setEducation_required(boolean education_required) {
        this.education_required = education_required;
    }

    public int getExperience_required() {
        return experience_required;
    }

    public void setExperience_required(int experience_required) {
        this.experience_required = experience_required;
    }

    public String getPlace_of_performance() {
        return place_of_performance;
    }

    public void setPlace_of_performance(String place_of_performance) {
        this.place_of_performance = place_of_performance;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getScored_candidates() {
        return scored_candidates;
    }

    public void setScored_candidates(int scored_candidates) {
        this.scored_candidates = scored_candidates;
    }

    public int getTotal_candidates() {
        return total_candidates;
    }

    public void setTotal_candidates(int total_candidates) {
        this.total_candidates = total_candidates;
    }

    @Override
    public String toString() {
        return "Position{" +
                "position_id=" + position_id +
                ", applications=" + applications +
                ", position_skills=" + position_skills +
                ", position_certification=" + position_certification +
                ", userGroup=" + userGroup +
                ", client=" + client +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", education='" + education + '\'' +
                ", education_required=" + education_required +
                ", experience_required=" + experience_required +
                ", place_of_performance='" + place_of_performance + '\'' +
                ", template=" + template +
                ", archived=" + archived +
                ", scored_candidates=" + scored_candidates +
                ", total_candidates=" + total_candidates +
                '}';
    }
}
