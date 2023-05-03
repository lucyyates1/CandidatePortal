package tech.geek.CandidatePortal.entity.entityHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PositionDataID implements Serializable {

    @Column(name = "position_id")
    private Long positionId;

    @Column(name="candidate_id")
    private Long candidateId;

    public PositionDataID() {
    }

    public PositionDataID(Long positionId, Long candidateId) {
        this.positionId = positionId;
        this.candidateId = candidateId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
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
        PositionDataID other = (PositionDataID) obj;
        if (candidateId == null) {
            if (other.candidateId != null)
                return false;
        } else if (!candidateId.equals(other.candidateId))
            return false;
        if (positionId == null) {
            if (other.positionId != null)
                return false;
        } else if (!positionId.equals(other.positionId))
            return false;
        return true;
    }

    public Long getPositionId() { return positionId; }

    public void setPositionId(Long positionId) { this.positionId = positionId; }

    public Long getCandidateId() { return candidateId; }

    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
}
