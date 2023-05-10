package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.geek.CandidatePortal.entity.entityHelper.PositionCertificationID;

import javax.persistence.*;

@Entity
@Table(name="position_certification")
public class PositionCertification {

    @EmbeddedId
    PositionCertificationID id;

    @ManyToOne
    @MapsId("positionId")
    @JoinColumn(name = "position_id")
    @JsonBackReference
    private Position position;

    @ManyToOne
    @MapsId("certificationId")
    @JoinColumn(name = "certification_id")
    private Certification certification;

    @Column(columnDefinition = "TINYINT")
    private int priority;

    public PositionCertification() {}

    public PositionCertification(Position position, Certification certification, int priority) {
        this.position = position;
        this.certification = certification;
        this.priority = priority;
    }

    public PositionCertificationID getId() { return id; }

    public void setId(PositionCertificationID id) { this.id = id; }

    public Position getPosition() { return position; }

    public void setPosition(Position position) { this.position = position; }

    public Certification getCertification() { return certification; }

    public void setCertification(Certification certification) { this.certification = certification; }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "PositionCertification{" +
                "id=" + id +
                ", position=" + position +
                ", certification=" + certification +
                ", priority=" + priority +
                '}';
    }
}
