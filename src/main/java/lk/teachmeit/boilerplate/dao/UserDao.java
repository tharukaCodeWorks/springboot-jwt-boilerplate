package lk.teachmeit.boilerplate.dao;

import lk.teachmeit.boilerplate.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Author: Tharuka Lakshan Dissanayake
 * Date: 2020/12/04
 */

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByPermissionsName(String permissionName);
    List<User> findByRoleId(long roleId);
    boolean existsByEmail(String email);
}
