package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long certification_id;

    @OneToMany
    private Set<CandidateCertification> candidate_certifications = new HashSet<>();

    @Column(length = 100)
    private String name;

    public Certification() {
    }

    public Certification(long certification_id, Set<CandidateCertification> candidate_certifications, String name) {
        this.certification_id = certification_id;
        this.candidate_certifications = candidate_certifications;
        this.name = name;
    }

    public long getCertification_id() {
        return certification_id;
    }

    public void setCertification_id(long certification_id) {
        this.certification_id = certification_id;
    }

    public Set<CandidateCertification> getCandidate_certifications() {
        return candidate_certifications;
    }

    public void setCandidate_certifications(Set<CandidateCertification> candidate_certifications) {
        this.candidate_certifications = candidate_certifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
