package lk.teachmeit.boilerplate.service.impl;

import lk.teachmeit.boilerplate.dao.PermissionDao;
import lk.teachmeit.boilerplate.dao.UserDao;
import lk.teachmeit.boilerplate.dao.UserRoleDao;
import lk.teachmeit.boilerplate.dto.RoleDto;
import lk.teachmeit.boilerplate.model.Permission;
import lk.teachmeit.boilerplate.model.User;
import lk.teachmeit.boilerplate.model.Role;
import lk.teachmeit.boilerplate.service.interfaces.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements ICrudService<RoleDto, Role> {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private UserDao userDao;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Role create(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        return userRoleDao.save(role);
    }

    @Override
    public Role update(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        return userRoleDao.save(role);
    }

    @Override
    public boolean delete(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        userRoleDao.delete(role);
        return true;
    }

    @Override
    public boolean delete(long id) {
        userRoleDao.deleteById(id);
        return true;
    }

    @Override
    public Role getById(long id) {
        return userRoleDao.findById(id).get();
    }

    @Override
    public List<Role> getAll() {
        return (List<Role>) userRoleDao.findAll();
    }

    public Role addPermissions(long roleId, List<Long> permissions) {
        Role userRole = userRoleDao.findById(roleId).get();
        List<User> userList = userDao.findByRoleId(roleId);
        userRole.getPermissions().clear();
        for(long permission: permissions){
            Permission role = permissionDao.findById(permission).get();
            userRole.getPermissions().add(role);
        }
        userRole = userRoleDao.save(userRole);
        Set<Permission> roles = new HashSet<>(userRole.getPermissions());
        for(User user:userList){
            user.setPermissions(roles);
            userDao.save(user);
        }

        return userRole;
    }

    public Role addPermissionsByName(long roleId, List<String> name){
        List<Permission> permission = new ArrayList<>();
        for(String perm:name){
            permission.add(permissionDao.findRoleByName(perm));
        }
        Role role =userRoleDao.findById(roleId).get();
        role.setPermissions(permission);
        role = userRoleDao.save(role);
        return role;
    }

    @Override
    public List<Role> getPaginate(long page, long offset) {
        return null;
    }
}
