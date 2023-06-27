package tech.geek.CandidatePortal.entity.entityHelper;

import tech.geek.CandidatePortal.entity.RecentViewed;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RecentViewedID implements Serializable {
    @Column(name="position_id")
    private Long positionId;

    @Column(name="candidate_user_id")
    private Long candidateUserId;

    public RecentViewedID() {
    }

    public RecentViewedID(Long positionId, Long candidateUserId) {
        this.positionId = positionId;
        this.candidateUserId = candidateUserId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((candidateUserId == null) ? 0 : candidateUserId.hashCode());
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
        RecentViewedID other = (RecentViewedID) obj;
        if (candidateUserId == null) {
            if (other.candidateUserId != null)
                return false;
        } else if (!candidateUserId.equals(other.candidateUserId))
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

    public Long getCandidateUserId() {
        return candidateUserId;
    }

    public void setCandidateUserId(Long candidateUserId) {
        this.candidateUserId = candidateUserId;
    }

    @Override
    public String toString() {
        return "RecentViewedID{" +
                "positionId=" + positionId +
                ", candidateUserId=" + candidateUserId +
                '}';
    }
}
