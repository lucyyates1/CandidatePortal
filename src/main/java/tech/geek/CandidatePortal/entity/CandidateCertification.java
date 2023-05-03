package tech.geek.CandidatePortal.entity;

import tech.geek.CandidatePortal.entity.entityHelper.CandidateCertificationID;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.security.cert.Certificate;

@Entity
public class CandidateCertification {

    @EmbeddedId
    private CandidateCertificationID id;

    @ManyToOne
    @MapsId("candidateId")
    private Candidate candidate;

    @ManyToOne
    @MapsId("certificationId")
    private Certification certification;

    public CandidateCertification() {
    }

    public CandidateCertification(CandidateCertificationID id, Candidate candidate, Certification certification) {
        this.id = id;
        this.candidate = candidate;
        this.certification = certification;
    }

    public CandidateCertificationID getId() {
        return id;
    }

    public void setId(CandidateCertificationID id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }
}
