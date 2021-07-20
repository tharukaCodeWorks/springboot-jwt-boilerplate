package lk.teachmeit.boilerplate.events;

import lk.teachmeit.boilerplate.dao.*;
import lk.teachmeit.boilerplate.model.*;
import lk.teachmeit.boilerplate.service.impl.RoleServiceImpl;
import lk.teachmeit.boilerplate.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SeedEventListner {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        try{
            boolean roles =  permissionDao.findById(1L).isPresent();
            if(!roles){
                seedRoles();
                seedUsers();
            }
        }catch (Exception ignored){
            System.out.println("this is the error");
            System.out.println(ignored.getMessage());
        }

    }

    private void seedRoles(){

        try{
            List<Permission> userPermissions = Arrays.asList(
                    new Permission("ADMIN_PRIVILEGES", "Admin privileges")
            );

            List<Permission> userRoles = new ArrayList<>();
            for(Permission item:userPermissions){
                userRoles.add(permissionDao.save(item));
            }

            Role userRole = new Role();
            userRole.setDescription("Admin");
            userRole.setName("ADMIN");
            userRole.setPermissions(userPermissions);
            userRoleDao.save(userRole);

        }catch (Exception ignored){

        }
    }

    private void seedUsers(){
        User admin;

        try{
            admin = new User();
            Role userRole = userRoleDao.findById((long) 1).get();
            admin.setFirstName("Default");
            admin.setLastName("Admin");
            admin.setEmail("admin@sample.com");
            admin.setIsEmailVerified("TRUE");
            admin.setPassword(bcryptEncoder.encode("12345678"));
            admin.setUserRole(userRole);
            admin = userRepository.save(admin);
            userService.setUserRole(1, admin);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
