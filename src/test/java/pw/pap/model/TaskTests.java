package pw.pap.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pw.pap.repository.ProjectRepository;
import pw.pap.repository.TaskRepository;
import pw.pap.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskTests {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime date = LocalDateTime.parse("2023-10-11 13:37:42", formatter);
    private final LocalDateTime deadlineDate = LocalDateTime.parse("2023-10-21 13:37:42", formatter);

    @Test
    public void testEmptyConstructor() {
        Task task = new Task();
        assertThrows(DataIntegrityViolationException.class, () -> taskRepository.save(task));
    }

    @Test
    public void testNameConstructor() {
        User user = new User("Rob", date);
        Project project = new Project("Shop", user,  date);
        Task task = new Task("Entrance", date, user, project);
        assertEquals("Entrance", task.getTitle());
        assertEquals(date, task.getTaskCreationDate());
        assertEquals(user, task.getCreator());
        assertEquals(project, task.getProject());
    }

    @Test
    public void testFullConstructor() {
        User user = new User("Rob", date);
        Project project = new Project("Shop", user,  date);
        List<User> assignees = new ArrayList<>(Arrays.asList(user));
        Task task = new Task("Entrance", "Get new doors", date, deadlineDate, assignees, user, project);
        assertEquals("Entrance", task.getTitle());
        assertEquals("Get new doors", task.getDescription());
        assertEquals(date, task.getTaskCreationDate());
        assertEquals(deadlineDate, task.getTaskDeadline());
        assertEquals(assignees, task.getAssignees());
        assertEquals(user, task.getCreator());
        assertEquals(project, task.getProject());

    }

    @Test
    @Transactional
    @Rollback
    public void testSaveTaskWithoutSavedProject() {
        User user = new User("Bob", "abcd", "dcba", date);
        Project project = new Project("Shop", user,  date);
        List<User> assignees = new ArrayList<>(Arrays.asList(user));
        Task task = new Task("Entrance", "Get new doors", date, deadlineDate, assignees, user, project);

        userRepository.save(user);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> taskRepository.save(task));
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveRetrieveDeleteProject() {
        User user = new User("Bob", "abcd", "dcba", date);
        Project project = new Project("Shop", user,  date);
        List<User> assignees = new ArrayList<>(Arrays.asList(user));
        Task task = new Task("Entrance", "Get new doors", date, deadlineDate, assignees, user, project);
        User savedUser = userRepository.save(user);
        Project savedProject = projectRepository.save(project);
        Task savedTask = taskRepository.save(task);
        Task retrievedTask = taskRepository
                .findById(savedTask.getId())
                .orElse(null);

        assertNotNull(retrievedTask);
        assertEquals("Entrance", retrievedTask.getTitle());
        assertEquals("Get new doors", retrievedTask.getDescription());
        assertEquals(date, retrievedTask.getTaskCreationDate());
        assertEquals(deadlineDate, retrievedTask.getTaskDeadline());
        assertEquals(assignees, retrievedTask.getAssignees());
        assertEquals(user, retrievedTask.getCreator());
        assertEquals(project, retrievedTask.getProject());

        taskRepository.deleteById(retrievedTask.getId());
        retrievedTask = taskRepository
                .findById(savedTask.getId())
                .orElse(null);
        assertNull(retrievedTask);

        Project retrievedProject = projectRepository
                .findById(savedProject.getId())
                .orElse(null);
        assertNotNull(retrievedProject);
        projectRepository.deleteById(retrievedProject.getId());

        User retrievedUser = userRepository
                .findById(savedUser.getId())
                .orElse(null);
        assertNotNull(retrievedUser);
        userRepository.deleteById(retrievedUser.getId());
    }
}

