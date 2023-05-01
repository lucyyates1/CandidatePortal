package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="Candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long application_id;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<PositionCandidate> position_candidates = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<CandidateCertification> candidate_certifications = new HashSet<>();

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

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate initial_contact_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate meet_and_greet_date;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private boolean archived;

    public Candidate() {
    }

    public Candidate(long candidate_id, Set<PositionCandidate> position_candidates, Set<CandidateCertification> candidate_certifications, String first_name, String last_name, String education, int experience, String resume_path, LocalDate initial_contact_date, LocalDate meet_and_greet_date, String notes, boolean archived) {
        this.candidate_id = candidate_id;
        this.position_candidates = position_candidates;
        this.candidate_certifications = candidate_certifications;
        this.first_name = first_name;
        this.last_name = last_name;
        this.education = education;
        this.experience = experience;
        this.resume_path = resume_path;
        this.initial_contact_date = initial_contact_date;
        this.meet_and_greet_date = meet_and_greet_date;
        this.notes = notes;
        this.archived = archived;
    }

    public long getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(long candidate_id) {
        this.candidate_id = candidate_id;
    }

    public Set<PositionCandidate> getPosition_candidates() {
        return position_candidates;
    }

    public void setPosition_candidates(Set<PositionCandidate> position_candidates) {
        this.position_candidates = position_candidates;
    }

    public Set<CandidateCertification> getCandidate_certifications() {
        return candidate_certifications;
    }

    public void setCandidate_certifications(Set<CandidateCertification> candidate_certifications) {
        this.candidate_certifications = candidate_certifications;
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

    public LocalDate getInitial_contact_date() {
        return initial_contact_date;
    }

    public void setInitial_contact_date(LocalDate initial_contact_date) {
        this.initial_contact_date = initial_contact_date;
    }

    public LocalDate getMeet_and_greet_date() {
        return meet_and_greet_date;
    }

    public void setMeet_and_greet_date(LocalDate meet_and_greet_date) {
        this.meet_and_greet_date = meet_and_greet_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "candidate{" +
                "candidate_id=" + candidate_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", education='" + education + '\'' +
                ", experience='" + experience + '\'' +
                ", resume_path='" + resume_path + '\'' +
                ", initial_contact_date=" + initial_contact_date +
                ", meet_and_greet_date=" + meet_and_greet_date +
                ", notes='" + notes + '\'' +
                ", archived=" + archived +
                '}';
    }
}