package pw.pap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pw.pap.service.UserService;
import pw.pap.database.User;
import pw.pap.service.ProjectService;
import pw.pap.database.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class ProjectPlannerApplication {
	//throws IOException is temporary and to be deleted!!!
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(ProjectPlannerApplication.class, args);
		ProjectService projectService = configurableApplicationContext.getBean(ProjectService.class);
		UserService userService = configurableApplicationContext.getBean(UserService.class);

		User bob = userService.createUser(new User("Bob"));
		Project rollerCoaster = projectService.createProject(new Project("Roller Coaster", bob));

		User robert = userService.createUser(new User("Robert"));
		User ronald = userService.createUser(new User("Ronald"));

		projectService.assignUserToProject(rollerCoaster.getId(), robert.getId());
		projectService.assignUserToProject(rollerCoaster.getId(), ronald.getId());

		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		String name = reader.readLine();

		projectService.deleteProject(rollerCoaster.getId());

		name = reader.readLine();

		userService.deleteUser(bob.getId());

		name = reader.readLine();

		userService.deleteUser(ronald.getId());
		userService.deleteUser(robert.getId());
	}
}


