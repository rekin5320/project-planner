package pw.pap.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.pap.api.model.Project;
import pw.pap.api.model.User;
import pw.pap.api.model.Task;
import pw.pap.repository.ProjectRepository;
import pw.pap.repository.UserRepository;
import pw.pap.repository.TaskRepository;


@Service
public class UserService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    @Autowired
    public UserService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.deleteById(userId);
        updatedUser.setId(userId);
        return userRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Remove the user from the members set of all projects
        Iterable<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            project.getMembers().remove(user);
            if (userId.equals(project.getOwner().getId())) {
                projectRepository.deleteById(project.getId());
            }
        }
        userRepository.deleteById(userId);
    }
}
