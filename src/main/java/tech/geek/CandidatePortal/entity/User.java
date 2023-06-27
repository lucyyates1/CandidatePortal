package tech.geek.CandidatePortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();

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

    public User(int user_id, UserGroup userGroup, Set<Application> applications, Role role, String first_name, String last_name, String username, String password, String email) {
        this.user_id = user_id;
        this.userGroup = userGroup;
        this.applications = applications;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
