package lk.teachmeit.auth.service.impl;

import lk.teachmeit.auth.dao.PermissionDao;
import lk.teachmeit.auth.dao.UserDao;
import lk.teachmeit.auth.model.Permission;
import lk.teachmeit.auth.model.Role;
import lk.teachmeit.auth.model.User;
import lk.teachmeit.auth.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getPermissions().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public List<User> getByPermission(String permission){
		return userDao.findByPermissionsName(permission);
	}

	@Override
	public boolean isUserExists(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public void delete(long id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).get();
	}

	@Override
    public User save(User user) {
		user = userDao.save(user);
        return user;
    }

    @Override
    public User setUserRole(long userRoleId, User user){
		Role role = roleService.getById(userRoleId);
		Set<Permission> roles = new HashSet<>(role.getPermissions());
		user.setPermissions(roles);
		user.setUserRole(role);
		user = userDao.save(user);
		return  user;
	}
}
