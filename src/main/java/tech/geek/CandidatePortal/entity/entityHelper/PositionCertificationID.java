package tech.geek.CandidatePortal.entity.entityHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PositionCertificationID implements Serializable {

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "certification_id")
    private Long certificationId;

    public PositionCertificationID() {
    }

    public PositionCertificationID(Long positionId, Long certificationId) {
        this.positionId = positionId;
        this.certificationId = certificationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((certificationId == null) ? 0 : certificationId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PositionCertificationID other = (PositionCertificationID) obj;
        if (certificationId == null) {
            if (other.certificationId != null)
                return false;
        } else if (!certificationId.equals(other.certificationId))
            return false;
        if (positionId == null) {
            if (other.positionId != null)
                return false;
        } else if (!positionId.equals(other.positionId))
            return false;
        return true;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Long certificationId) {
        this.certificationId = certificationId;
    }
}
