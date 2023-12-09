package pw.pap.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.pap.api.model.Project;
import pw.pap.api.model.User;
import pw.pap.repository.ProjectRepository;
import pw.pap.repository.UserRepository;


@Service
public class UserService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public UserService(ProjectRepository projectRepository, UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            return userRepository.save(existingUser);
        }
        else {
            return null;
        }
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
