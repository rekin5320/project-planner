package pw.pap.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=100)
    private String name;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_creation_date", nullable = false, updatable = false)
    private Date accountCreationDate;

    @OneToMany(mappedBy = "owner")
    private List<Project> ownedProjects = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private List<Project> memberOfProjects = new ArrayList<>();

    @ManyToMany(mappedBy = "assignees")
    private List<Task> assignedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Task> createdTasks = new ArrayList<>();

    public User() {
        this.accountCreationDate = new Date();
    }

    public User(String name, String passwordHash, String salt) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.accountCreationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public List<Project> getMemberOfProjects() {
        return memberOfProjects;
    }

    public void setMemberOfProjects(List<Project> memberOfProjects) {
        this.memberOfProjects = memberOfProjects;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(List<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }
}
