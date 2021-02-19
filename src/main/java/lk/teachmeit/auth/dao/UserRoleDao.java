package lk.teachmeit.auth.dao;

import lk.teachmeit.auth.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends CrudRepository<Role, Long> {
}
