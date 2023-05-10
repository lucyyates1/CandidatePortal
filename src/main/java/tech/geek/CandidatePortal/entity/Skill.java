package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skill_id;

    @OneToMany(mappedBy = "skill")
    @JsonBackReference
    private Set<PositionSkill> position_skills = new HashSet<>();

    @Column(length = 30)
    private String name;

    public Skill() {
    }

    public Skill(long skill_id, Set<PositionSkill> position_skills, String name) {
        this.skill_id = skill_id;
        this.position_skills = position_skills;
        this.name = name;
    }

    public long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(long skill_id) {
        this.skill_id = skill_id;
    }

    public Set<PositionSkill> getPosition_skills() {
        return position_skills;
    }

    public void setPosition_skills(Set<PositionSkill> position_skills) {
        this.position_skills = position_skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
