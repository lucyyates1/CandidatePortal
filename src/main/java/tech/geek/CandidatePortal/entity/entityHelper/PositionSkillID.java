package tech.geek.CandidatePortal.entity.entityHelper;

import tech.geek.CandidatePortal.entity.PositionSkill;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PositionSkillID implements Serializable {

    @Column(name="position_id")
    private Long positionId;

    @Column(name="skill_id")
    private Long skillId;

    public PositionSkillID() {
    }

    public PositionSkillID(long positionId, long skillId) {
        this.positionId = positionId;
        this.skillId = skillId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
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
        PositionSkillID other = (PositionSkillID) obj;
        if (skillId == null) {
            if (other.skillId != null)
                return false;
        } else if (!skillId.equals(other.skillId))
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

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
