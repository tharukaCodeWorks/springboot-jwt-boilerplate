package lk.teachmeit.auth.dao;

import lk.teachmeit.auth.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
    @Modifying
    @Query("update User ear set ear.websocketToken = :token where ear.id = :id")
    int setWebsocketTokenFor(@Param("token") String token, @Param("id") Long id);
}
