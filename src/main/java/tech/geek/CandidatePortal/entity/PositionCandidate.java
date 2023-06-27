package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;
import tech.geek.CandidatePortal.entity.entityHelper.PositionDataID;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name ="position_candidate")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PositionCandidate {
    @EmbeddedId
    private PositionDataID id;

    @ManyToOne
    @MapsId("positionId")
    @JoinColumn(name="position_id")
    private Position position;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name="candidate_id")
    private Application application;

    @Column(columnDefinition = "TINYINT")
    private int current_interview;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate technical_interview_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate offer_date;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate filled_date;

    @Column(columnDefinition = "TINYINT")
    private int score;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate score_run_date;

    public PositionCandidate() {
    }

    public PositionCandidate(PositionDataID id, Position position, Application application, int current_interview, LocalDate technical_interview_date, LocalDate offer_date, LocalDate filled_date, int score, LocalDate score_run_date) {
        this.id = id;
        this.position = position;
        this.application = application;
        this.current_interview = current_interview;
        this.technical_interview_date = technical_interview_date;
        this.offer_date = offer_date;
        this.filled_date = filled_date;
        this.score = score;
        this.score_run_date = score_run_date;
    }

    public PositionDataID getId() {
        return id;
    }

    public void setId(PositionDataID id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Application getCandidate() {
        return application;
    }

    public void setCandidate(Application application) {
        this.application = application;
    }

    public int getCurrent_interview() {
        return current_interview;
    }

    public void setCurrent_interview(int current_interview) {
        this.current_interview = current_interview;
    }

    public LocalDate getTechnical_interview_date() {
        return technical_interview_date;
    }

    public void setTechnical_interview_date(LocalDate technical_interview_date) {
        this.technical_interview_date = technical_interview_date;
    }

    public LocalDate getOffer_date() {
        return offer_date;
    }

    public void setOffer_date(LocalDate offer_date) {
        this.offer_date = offer_date;
    }

    public LocalDate getFilled_date() {
        return filled_date;
    }

    public void setFilled_date(LocalDate filled_date) {
        this.filled_date = filled_date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getScore_run_date() {
        return score_run_date;
    }

    public void setScore_run_date(LocalDate score_run_date) {
        this.score_run_date = score_run_date;
    }
}