package pw.pap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pw.pap.repository.UserRepository;
import pw.pap.database.User;
import pw.pap.repository.ProjectRepository;
import pw.pap.database.Project;

@SpringBootApplication
public class ProjectPlannerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(ProjectPlannerApplication.class, args);
		ProjectRepository projectRepository = configurableApplicationContext.getBean(ProjectRepository.class);
		UserRepository userRepository = configurableApplicationContext.getBean(UserRepository.class);

		User bob = new User("Bob");
		userRepository.save(bob);
		Project roller_coaster = new Project("roller coaster", bob);

		User robert = new User("Robert");
		userRepository.save(robert);
		User ronald = new User("Ronald");
		userRepository.save(ronald);

		roller_coaster.addMember(robert);
		roller_coaster.addMember(ronald);

		projectRepository.save(roller_coaster);
	}
}


