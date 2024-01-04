package pw.pap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pw.pap.model.Project;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
