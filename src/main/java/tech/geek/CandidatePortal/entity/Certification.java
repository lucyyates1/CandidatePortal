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

    @OneToMany(mappedBy = "certification")
    @JsonBackReference
    private Set<ApplicationCertification> application_certifications = new HashSet<>();

    @OneToMany(mappedBy = "certification")
    @JsonBackReference
    private Set<PositionCertification> position_certifications = new HashSet<>();

    @Column(length = 100)
    private String name;

    public Certification() {
    }

    public Certification(long certification_id, Set<ApplicationCertification> application_certifications, Set<PositionCertification> position_certifications, String name) {
        this.certification_id = certification_id;
        this.application_certifications = application_certifications;
        this.position_certifications = position_certifications;
        this.name = name;
    }

    public long getCertification_id() {
        return certification_id;
    }

    public void setCertification_id(long certification_id) {
        this.certification_id = certification_id;
    }

    public Set<ApplicationCertification> getApplication_certifications() {
        return application_certifications;
    }

    public void setApplication_certifications(Set<ApplicationCertification> application_certifications) {
        this.application_certifications = application_certifications;
    }

    public Set<PositionCertification> getPosition_certifications() {
        return position_certifications;
    }

    public void setPosition_certifications(Set<PositionCertification> position_certifications) {
        this.position_certifications = position_certifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
