package tech.geek.CandidatePortal.entity.entityHelper;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ApplicationCertificationID implements Serializable {

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "certification_id")
    private Long certificationId;

    public ApplicationCertificationID() {
    }

    public ApplicationCertificationID(Long applicationId, Long certificationId) {
        this.applicationId = applicationId;
        this.certificationId = certificationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((applicationId == null) ? 0 : applicationId.hashCode());
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
        ApplicationCertificationID other = (ApplicationCertificationID) obj;
        if (certificationId == null) {
            if (other.certificationId != null)
                return false;
        } else if (!certificationId.equals(other.certificationId))
            return false;
        if (applicationId == null) {
            if (other.applicationId != null)
                return false;
        } else if (!applicationId.equals(other.applicationId))
            return false;
        return true;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }
}