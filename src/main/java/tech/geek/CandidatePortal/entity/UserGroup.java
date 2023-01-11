package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="user_group")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "user_group_id"
)
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_group_id;

    // @OneToMany(mappedBy = "userGroup")
    // private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "userGroup")
    private Set<User> users = new HashSet<>();

    @Column(length = 30)
    private String name;

    @Column(length = 40)
    private String email;

    @Column(length = 10)
    private String phone;

    @Column(length = 20)
    private String street;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String state;

    @Column(length = 10)
    private String zip;

    public UserGroup() {}

    public UserGroup(int user_group_id, String name, String email, String phone, String street, String city, String state, String zip) {
        this.user_group_id = user_group_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public long getUser_group_id() { return user_group_id; }

    public void setUser_group_id(long user_group_id) { this.user_group_id = user_group_id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }

    public void setZip(String zip) { this.zip = zip; }

    /* public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }
    */
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "user_group{" +
                "user_group_id=" + user_group_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
