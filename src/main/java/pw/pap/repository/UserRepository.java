package pw.pap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pw.pap.api.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
