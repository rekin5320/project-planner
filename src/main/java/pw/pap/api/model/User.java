package pw.pap.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private Set<Project> ownedProjects = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> memberOfProjects = new HashSet<>();

    @ManyToMany(mappedBy = "assignees")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    private Set<Task> createdTasks = new HashSet<>();

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

    public Set<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(Set<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public Set<Project> getMemberOfProjects() {
        return memberOfProjects;
    }

    public void setMemberOfProjects(Set<Project> memberOfProjects) {
        this.memberOfProjects = memberOfProjects;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public Set<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(Set<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }
}
