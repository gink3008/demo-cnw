package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.Repository.UserRoleRepository;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
import com.hiep.democnw.Entities.real.UserRoleEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public Boolean createNewUserRole(UserRoleRequest userRoleRequest) {
        List<UserRoleEntity> userRoleEntities = getAllUserRole();
        if(userRoleEntities != null)
        {
            for (UserRoleEntity userRole : userRoleEntities) {
                if (userRole.getIdRole() == userRoleRequest.getRoleId() && userRole.getIdUser() == userRoleRequest.getUserId()) {
                    return false;
                }
            }
        }
        UserRoleEntity userRoleEnitiy = new UserRoleEntity();
        userRoleEnitiy.setCode(userRoleRequest.getCode());
        userRoleEnitiy.setIdRole(userRoleRequest.getRoleId());
        userRoleEnitiy.setIdUser(userRoleRequest.getUserId());
        userRoleRepository.save(userRoleEnitiy);
        return true;
    }

    public List<UserRoleEntity> getAllUserRole() {
        return userRoleRepository.findAll();
    }

    public UserRoleEntity getUserById(Integer id) {
        Optional<UserRoleEntity> byId = userRoleRepository.findById(id);
        if (byId.isPresent() == false) {
            throw new UserUnknowException(String.format("User have id : '%s' not found" + id));
        }

        return byId.get();
    }

    public Boolean updateByID(Integer id, UserRoleRequest userRoleRequest) {
        Optional<UserRoleEntity> byId = userRoleRepository.findById(id);
        if (userRoleRepository.existsById(id)) {
            UserRoleEntity users = byId.get();
            users.setCode(userRoleRequest.getCode());
            users.setIdRole(userRoleRequest.getRoleId());
            users.setIdUser(userRoleRequest.getUserId());
            userRoleRepository.save(users);
            return true;
        }
        return false;

    }
}
