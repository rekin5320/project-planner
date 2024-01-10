package pw.pap.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pw.pap.model.Project;
import pw.pap.model.Task;
import pw.pap.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime date = LocalDateTime.parse("2024-10-11 13:37:42", formatter);

    @Test
    @Transactional
    @Rollback
    public void testCreateTask() {
        String username = "testBob";
        String password = "reallySecurePassword";
        User user = userService.register(username, password);
        Project project = projectService.createProject("roller coaster", user);

        try {
            Task task = taskService.createTask("Stairs", user.getId(), project.getId());
            Task taskInDatabase = taskService.getTaskById(task.getId()).orElse(null);
            assertNotNull(taskInDatabase, "Task not in database");

            assertEquals(task.getId(), taskInDatabase.getId());
        } catch (Exception e) {
            fail("Exception occurred during login: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateTask() {
        String username = "testBob";
        String password = "reallySecurePassword";
        User user = userService.register(username, password);
        Project project = projectService.createProject("roller coaster", user);
        Task task = taskService.createTask("Stairs", user.getId(), project.getId());

        task.setTitle("Chair");
        task.setDescription("Make chair");
        task.setTaskDeadline(date);
        taskService.updateTask(task.getId(), task);

        Task taskInDatabase = taskService.getTaskById(task.getId()).orElse(null);
        assertNotNull(taskInDatabase);
        assertEquals(taskInDatabase.getTitle(), "Chair");
        assertEquals(taskInDatabase.getDescription(), "Make chair");
        assertEquals(taskInDatabase.getTaskDeadline(), date);
    }
}
