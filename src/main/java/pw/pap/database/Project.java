package pw.pap.database;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner_id;

    @ManyToMany
    @JoinTable(name = "project_user",
            joinColumns = { @JoinColumn(name = "project_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<User> members = new HashSet<>();

    public Project() {
    }

    public Project(String name, User owner_id) {
        this.name = name;
        this.owner_id = owner_id;
    }

    public void addMember(User member) {
        this.members.add(member);
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

    public User getOwner_id() {
        return owner_id;
    }
    public void setOwner_id(User owner_id) {
        this.owner_id = owner_id;
    }

    public Set<User> getMembers() {
        return members;
    }
    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
