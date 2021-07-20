package lk.teachmeit.boilerplate.dao;

import lk.teachmeit.boilerplate.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends CrudRepository<Role, Long> {
}
