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


    public Position() {}

    public Position(int position_id, UserGroup userGroup, Client client, String name, LocalDate date, String description, String education, boolean education_required, int experience_required, String place_of_performance, boolean template) {
        this.position_id = position_id;
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
    }

    public long getPosition_id() { return position_id; }

    public void setPosition_id(long position_id) {
        this.position_id = position_id;
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

    public void setDescription(String description) { this.description = description; }

    public String getEducation() { return education; }

    public void setEducation(String education) { this.education = education; }

    public boolean isEducation_required() { return education_required; }

    public void setEducation_required(boolean education_required) { this.education_required = education_required; }

    public int getExperience_required() { return experience_required; }

    public void setExperience_required(int experience_required) { this.experience_required = experience_required; }

    public String getPlace_of_performance() {
        return place_of_performance;
    }

    public void setPlace_of_performance(String place_of_performance) { this.place_of_performance = place_of_performance; }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }



    @Override
    public String toString() {
        return "position{" +
                "position_id=" + position_id +
                ", userGroup=" + userGroup.getName() +
                ", Client=" + client.getName() +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", education='" + education + '\'' +
                ", education_required='" + education_required + '\'' +
                ", experience_required='" + experience_required + '\'' +
                ", place_of_performance='" + place_of_performance + '\''+
                ", template=" + template +
                '}';

    }
}
