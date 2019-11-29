package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import com.hiep.democnw.Entities.real.UserRoleEntity;
import com.hiep.democnw.Entities.real.UsersEntity;
import com.hiep.democnw.Dao.RequestObject.UserRequest;
import com.hiep.democnw.Dao.Repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    public Boolean createNewUser(UserRequest userRequest) {
        List<UsersEntity> usersEntities =  getAllUsers();
        if(usersEntities != null)
        {
            for (UsersEntity user : getAllUsers()) {
                if (user.getUsername().equals(userRequest.getUsername()) && user.getPassword().equals(userRequest.getPassword())) {
                    return false;
                }
            }
        }

        UsersEntity users = new UsersEntity();
        users.setUsername(userRequest.getUsername());
        users.setPassword(userRequest.getPassword());
        userRespository.save(users);
        return true;
    }

    public List<UsersEntity> getAllUsers() {
        return userRespository.findAll();
    }

    public UsersEntity getUserById(int id) {
        Optional<UsersEntity> byId = userRespository.findById(id);
        if (byId.isPresent() == false) {
            return null;
        }

        return byId.get();
    }

    public Boolean updateByID(int id, String username, String password) {
        Optional<UsersEntity> byId = userRespository.findById(id);
        if (userRespository.existsById(id)) {
            UsersEntity users = byId.get();
            users.setUsername(username);
            users.setPassword(password);
            userRespository.save(users);
            return true;
        }
        return false;

    }

    public UsersEntity findByUsername(String username) {
        for (UsersEntity user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Boolean checkLogin(UserRequest userRequest) {
        List<UsersEntity> usersList = getAllUsers();

        for (UsersEntity user : usersList) {
            System.out.println(user);
            if (user.getUsername().equals(userRequest.getUsername()) && user.getPassword().equals(userRequest.getPassword())) {
                return true;
            }
        }
        return false;
    }
    public List<RoleRequest> getRoleRequestByUser(UsersEntity usersEntity){
        List<RoleRequest> roleRequests = new ArrayList<>();
        Set<UserRoleEntity> userRoles = usersEntity.getUserRolesByIdUser();
        if(userRoles != null)
        {
            for(UserRoleEntity userRoleEntity : userRoles)
            {
                RoleRequest roleRequest = new RoleRequest();
                roleRequest.setName(userRoleEntity.getRolesByIdRole().getName());
                roleRequest.setControllerList(roleService.getControllerRequestByRole(userRoleEntity.getRolesByIdRole()));
                roleRequest.setPermissionList(roleService.getPermissionRequestByRole(userRoleEntity.getRolesByIdRole()));
                roleRequests.add(roleRequest);
            }
        }
        return roleRequests;
    }


}
