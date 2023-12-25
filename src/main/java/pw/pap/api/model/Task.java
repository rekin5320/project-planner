package pw.pap.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=100)
    private String title;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_creation_date", nullable = false, updatable = false)
    private Date taskCreationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_deadline")
    private Date taskDeadline;

    @ManyToMany
    @JoinTable(name = "task_user",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> assignees = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
        this.taskCreationDate = new Date();
    }

    public Task(String title, String description, User creator, Project project, Date taskDeadline) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.taskDeadline = taskDeadline;
        this.taskCreationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<User> assignees) {
        this.assignees = assignees;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public Date getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.taskDeadline = taskDeadline;
    }
}
