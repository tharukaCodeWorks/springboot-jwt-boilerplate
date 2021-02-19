package lk.teachmeit.auth.dao;

import lk.teachmeit.auth.model.Permission;
import org.springframework.data.repository.CrudRepository;

/*
 * Author: Tharuka Lakshan Dissanayake
 * Date: 2020/12/04
 */

public interface PermissionDao extends CrudRepository<Permission, Long> {
    Permission findRoleByName(String name);
}
