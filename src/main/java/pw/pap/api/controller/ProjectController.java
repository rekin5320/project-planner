package pw.pap.api.controller;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pw.pap.api.model.Project;
import pw.pap.service.ProjectService;


@CrossOrigin()
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return (List<Project>) projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public Optional<Project> getProject(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/add")
    public Project addProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/update/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @RequestBody Project updatedProject) {
        return projectService.updateProject(projectId, updatedProject);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    @Transactional
    @PostMapping("/assignUser/{projectId}/{userId}")
    public void assignUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.assignUserToProject(projectId, userId);
    }

    @Transactional
    @PostMapping("/removeUser/{projectId}/{userId}")
    public void removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.removeUserFromProject(projectId, userId);
    }
}
