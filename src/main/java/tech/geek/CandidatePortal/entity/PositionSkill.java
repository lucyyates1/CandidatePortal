package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import tech.geek.CandidatePortal.entity.entityHelper.PositionDataID;
import tech.geek.CandidatePortal.entity.entityHelper.PositionSkillID;

import javax.persistence.*;

@Entity
@Table(name = "position_skill")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PositionSkill {

    @EmbeddedId
    PositionSkillID id;

    @ManyToOne
    @MapsId("positionId")
    @JoinColumn(name = "position_id")
    @JsonBackReference
    private Position position;


    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    @JsonBackReference
    private Skill skill;

    @Column(columnDefinition = "TINYINT")
    private int priority;

    @Column(columnDefinition = "TINYINT")
    private int experience;

    public PositionSkill() {
    }

    public PositionSkill(PositionSkillID id, Position position, Skill skill, int priority, int experience) {
        this.id = id;
        this.position = position;
        this.skill = skill;
        this.priority = priority;
        this.experience = experience;
    }

    public PositionSkillID getId() {
        return id;
    }

    public void setId(PositionSkillID id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
