package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tech.geek.CandidatePortal.entity.entityHelper.ApplicationCertificationID;

import javax.persistence.*;

@Entity
public class ApplicationCertification {

    @EmbeddedId
    private ApplicationCertificationID id;

    @ManyToOne
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    @JsonBackReference
    private Application application;

    @ManyToOne
    @MapsId("certificationId")
    @JoinColumn(name = "certification_id")
    private Certification certification;

    public ApplicationCertification() {
    }

    public ApplicationCertification(ApplicationCertificationID id, Application application, Certification certification) {
        this.id = id;
        this.application = application;
        this.certification = certification;
    }

    public ApplicationCertificationID getId() {
        return id;
    }

    public void setId(ApplicationCertificationID id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }
}
