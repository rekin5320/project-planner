package pw.pap.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.pap.api.model.Project;
import pw.pap.api.model.User;
import pw.pap.repository.UserRepository;
import pw.pap.repository.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(Project project){
        User owner = project.getOwner();
        return projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    public Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(Long projectId, Project updatedProject) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        projectRepository.deleteById(projectId);
        updatedProject.setId(projectId);
        return projectRepository.save(updatedProject);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public void assignUserToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.getMembers().add(user);
        projectRepository.save(project);
    }

    @Transactional
    public void removeUserFromProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.getMembers().remove(user);
        projectRepository.save(project);
    }
}
