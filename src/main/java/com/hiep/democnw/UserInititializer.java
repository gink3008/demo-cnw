package com.hiep.democnw;

import com.hiep.democnw.Dao.Repository.UserRespository;
import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import com.hiep.democnw.Dao.RequestObject.UserRequest;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
import com.hiep.democnw.Dao.Services.RoleService;
import com.hiep.democnw.Dao.Services.UserRoleService;
import com.hiep.democnw.Dao.Services.UserService;
import com.hiep.democnw.Entities.real.RolesEntity;
import com.hiep.democnw.Entities.real.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInititializer implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    //   @Autowired
    //   private RolesRepository roleRepository;

//    @Autowired
//    private com.hiep.democnw.Dao.Repository.RoleRepository roleRepository2;
//    @Autowired
//    private UserRoleRepository userRoleRepository;


    @Override
    public void run(String... args) throws Exception {


//        RoleRequest roleRequest = new RoleRequest();
//        roleRequest.setName("hiep");
//        roleService.createNewUser(1,roleRequest );
//        RolesEntity rolesEntity = new RolesEntity();
//        rolesEntity.setRoleId(1);
//        List<RolesEntity> rolesEntities = roleRepository.findAll();
//        if(rolesEntities != null)
//        {
//            System.out.println(1);
//        }
//        List<RolesEntity> rolesEntities = roleRepository.findAll();
//        int dem = 0;
//        int dem2 =0;
//        for(RolesEntity role : rolesEntities)
//        {
//            if (role.getRoleName().equals("ROLE_ADMIN")) {
//                dem ++;
//            }
//            if(role.getRoleName().equals("ROLE_MEMBER"))
//            {
//                dem2 ++;
//            }
//        }
//        if (dem == 0) {
//            roleRepository.save(new RolesEntity("ROLE_ADMIN"));
//        }

        if(roleService.findByRoleName("ROLE_ADMIN") == null)
        {
            RoleRequest roleRequest = new RoleRequest("ROLE_ADMIN");
            roleService.createNewRole(roleRequest);

        }
        if (roleService.findByRoleName("ROLE_CLIENT") == null) {
            RoleRequest roleRequest = new RoleRequest("ROLE_CLIENT");
            roleService.createNewRole(roleRequest);
        }
//
        UsersEntity user = new UsersEntity();

//        List<RolesEntity> rolesEntities1 = roleRepository.findAll();
        RolesEntity role = roleService.findByRoleName("ROLE_MEMBER");
//         Admin account
        if (userService.findByUsername("admin") == null) {
            UserRequest admin = new UserRequest();
            admin.setUsername("admin");
            admin.setPassword(("123456"));
            userService.createNewUser(admin);

            System.out.print(admin.getUsername());
//            Set<RoleEntity> roles = new HashSet<>();
//            roles.add(roleRepository.findByName("ROLE_ADMIN"));
//            roles.add(roleRepository.findByName("ROLE_MEMBER"));
//            admin.setListUserRoleEnitiys(roles);
//            userRespository.save(admin);
//            log.info("user insert :" + admin);
            UsersEntity usersEntity = userService.findByUsername("admin");
            RolesEntity roleEntity = roleService.findByRoleName("ROLE_ADMIN");
            UserRoleRequest userRoleRequest = new UserRoleRequest();
            userRoleRequest.setUserId(usersEntity.getIdUser());
            userRoleRequest.setRoleId(roleEntity.getIdRole());
            userRoleService.createNewUserRole(userRoleRequest);

        }

//        for(UserRoleEntity userRoleEntity : userService.findByUsername("admin").getUserRolesByIdUser())
//        {
//            System.out.print(userRoleEntity.getRolesByIdRole().getName() + "\n");
//        }

//         Member account
//        if (userService.findByUsername("member") == null) {
//            Users user2 = new Users();
//            user2.setUsername("member");
//            user2.setPassword(("123456"));
//            HashSet<Roles> roles = new HashSet<>();
//            roles.add(roleRepository.findByName("ROLE_MEMBER"));
//            user2.setRoles(roles);
//            userRespository.save(user2);
//        }
    }

}
