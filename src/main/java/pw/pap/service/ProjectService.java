package pw.pap.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.pap.model.Project;
import pw.pap.model.User;
import pw.pap.model.Task;
import pw.pap.repository.TaskRepository;
import pw.pap.repository.UserRepository;
import pw.pap.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Project createProject(String name, User owner) {
        LocalDateTime currentDate = LocalDateTime.now();
        Project project = new Project(name, owner, currentDate);
        return projectRepository.save(project);
    }

    public List<Task> getProjectTasks(Long projectId){
        List<Task> projectTasks = new ArrayList<>();
        Iterable<Task> tasks = taskRepository.findAll();

        for (Task task : tasks){
            if(task.getProject().getId().equals(projectId)){
                projectTasks.add(task);
            }
        }
        return projectTasks;
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

        if(!updatedProject.getId().equals(existingProject.getId())){
            throw new IllegalArgumentException("Cannot change project id");
        }

        if(updatedProject.getName().isEmpty()){
            throw new IllegalArgumentException("Project name cannot be empty");
        }

        if(!updatedProject.getProjectCreationDate().equals(existingProject.getProjectCreationDate())){
            throw new IllegalArgumentException("Cannot change project creation date");
        }

        if(!updatedProject.getProjectDeadline().equals(existingProject.getProjectDeadline())){
            LocalDateTime currentDate = LocalDateTime.now();
            if (updatedProject.getProjectDeadline().isBefore(currentDate)) {
                throw new IllegalArgumentException("New deadline must be after current time");
            }
        }

        if(!updatedProject.getOwner().getId().equals(existingProject.getOwner().getId())){
            userRepository.findById(updatedProject.getOwner().getId())
                    .orElseThrow(() -> new EntityNotFoundException("New owner not in database"));
        }

        if(!updatedProject.getMembers().equals(existingProject.getMembers())){
            for (User member : updatedProject.getMembers()) {
                userRepository.findById(member.getId())
                        .orElseThrow(() -> new EntityNotFoundException("One of new members not in database"));
            }
        }

        projectRepository.deleteById(projectId);
        updatedProject.setId(projectId);
        return projectRepository.save(updatedProject);
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Iterable<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if(task.getProject().getId().equals(projectId)){
                taskRepository.deleteById(task.getId());
            }
        }

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

        Iterable<Task> tasks = taskRepository.findAll();
         for (Task task : tasks) {
             if (task.getProject().getId().equals(projectId)) {
                 task.getAssignees().remove(user);
             }
         }

        project.getMembers().remove(user);
        projectRepository.save(project);
    }

    @Transactional
    public void removeTaskFromProject(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        taskRepository.deleteById(taskId);
        projectRepository.save(project);
    }
}
