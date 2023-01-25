package tech.geek.CandidatePortal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import tech.geek.CandidatePortal.entity.entityHelper.PositionDataID;

@Entity
@Table(name="position_data")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PositionData {
    @EmbeddedId
    PositionDataID id;

    @ManyToOne
    @MapsId("positionId")
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @DateTimeFormat(pattern = "dd/MM/yyyy") //MySQL format: YYYY-MM-DD
    private LocalDate offer_date;



    public PositionData() {
    }

    public PositionData(Position position, Client client, LocalDate offer_date) {
        this.position = position;
        this.client = client;
        this.offer_date = offer_date;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getOffer_date() {
        return offer_date;
    }

    public void setOffer_date(LocalDate offer_date) {
        this.offer_date = offer_date;
    }

    @Override
    public String toString() {
        return "position_candidate{" +
                "Position=" + position.getName() +
                ", Client=" + client.getName() +
                ", offer_date=" + offer_date +
                '}';
    }
}