package tech.geek.CandidatePortal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(length = 20)
    private String first_name;

    @Column(length = 20)
    private String last_name;

    @Column(length = 20)
    private String username;

    //@Convert(converter = AttributeEncryptor.class)
    @Column(length = 32)
    private String password;

    @Column(length = 40)
    private String email;


    public User() {}

    public User(int user_id, UserGroup userGroup, Role role, String firstName, String lastName, String username, String password, String email) {
        this.user_id = user_id;
        this.userGroup = userGroup;
        this.role = role;
        this.first_name = firstName;
        this.last_name = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return user_id;
    }

    public void setId(int userId) {
        this.user_id = userId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Role getRole() {
        return role;
    }       // Consider changing to set by String instead of Role Object

    public void setRole(Role role) {            // Consider changing to set by String instead of Role Object
        this.role = role;
    }

    public String getFirstName() { return first_name; }

    public void setFirstName(String firstName) { this.first_name = firstName; }

    public String getLastName() { return last_name; }

    public void setLastName(String lastName) { this.last_name = lastName; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "user{" +
                "user_id=" + user_id +
                ", userGroup=" + userGroup +
                ", Role=" + role +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
