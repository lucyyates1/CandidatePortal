package tech.geek.CandidatePortal.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();

    @Column(length = 20)
    private String role;       // This is a very confusing name, should be changed to "name"

    private boolean read;

    private boolean write;

    private boolean execute;

    public Role() {}

    public Role(int role_id, String role, boolean read, boolean write, boolean execute) {
        this.role_id = role_id;
        this.role = role;
        this.read = read;
        this.write = write;
        this.execute = execute;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "role{" +
                "role_id=" + role_id +
                ", role='" + role + '\'' +
                ", read=" + read +
                ", write=" + write +
                ", execute=" + execute +
                '}';
    }

}
