package lk.teachmeit.boilerplate.service.interfaces;

import lk.teachmeit.boilerplate.model.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);

    User setUserRole(long userRoleId, User user);

    List<User> getByPermission(String permission);

    boolean isUserExists(String email);
}
