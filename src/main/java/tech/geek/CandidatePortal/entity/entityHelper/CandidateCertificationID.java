package tech.geek.CandidatePortal.entity.entityHelper;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CandidateCertificationID implements Serializable {

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "certification_id")
    private Long certificationId;

    public CandidateCertificationID() {
    }

    public CandidateCertificationID(Long candidateId, Long certificationId) {
        this.candidateId = candidateId;
        this.certificationId = certificationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
        result = prime * result + ((certificationId == null) ? 0 : certificationId.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CandidateCertificationID other = (CandidateCertificationID) obj;
        if (certificationId == null) {
            if (other.certificationId != null)
                return false;
        } else if (!certificationId.equals(other.certificationId))
            return false;
        if (candidateId == null) {
            if (other.candidateId != null)
                return false;
        } else if (!candidateId.equals(other.candidateId))
            return false;
        return true;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }
}