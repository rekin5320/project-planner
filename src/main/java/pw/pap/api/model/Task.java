package pw.pap.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_creation_date", nullable = false, updatable = false)
    private LocalDateTime taskCreationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_deadline")
    private LocalDateTime taskDeadline;

    @ManyToMany
    @JoinTable(
        name = "task_user",
        joinColumns = {@JoinColumn(name = "task_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> assignees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Task() { }

    public Task(String title, String description, User creator, Project project, LocalDateTime taskCreationDate, LocalDateTime taskDeadline) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.taskCreationDate = taskCreationDate;
        this.taskDeadline = taskDeadline;
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

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
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

    public LocalDateTime getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(LocalDateTime taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public LocalDateTime getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDateTime taskDeadline) {
        this.taskDeadline = taskDeadline;
    }
}
