package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Controller.RoleController;
import com.hiep.democnw.Dao.Repository.RoleControllerRepository;
import com.hiep.democnw.Dao.RequestObject.RoleControllerRequest;
import com.hiep.democnw.Dao.RequestObject.RolePermissionRequest;
import com.hiep.democnw.Entities.real.RoleControllerEntity;
import com.hiep.democnw.Entities.real.RolePermissionEntity;
import com.hiep.democnw.Entities.real.UserRoleEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleControllerService {
    @Autowired
    private RoleControllerRepository roleControllerRepository;

    public Boolean createNewRoleController(RoleControllerRequest roleControllerRequest) {
        for (RoleControllerEntity roleControllerEntity : getAllRoleController()) {
            if (roleControllerEntity.getIdRole() == roleControllerRequest.getRoleId() && roleControllerEntity.getIdRoleController() == roleControllerRequest.getControllerId()) {
                return false;
            }
        }

        RoleControllerEntity roleControllerEntity = new RoleControllerEntity();
        roleControllerEntity.setCode(roleControllerRequest.getCode());
        roleControllerEntity.setIdRole(roleControllerRequest.getRoleId());
        roleControllerEntity.setIdController(roleControllerRequest.getControllerId());
        roleControllerRepository.save(roleControllerEntity);
        return true;
    }

    public List<RoleControllerEntity> getAllRoleController() {
        return roleControllerRepository.findAll();
    }

    public RoleControllerEntity getUserById(Integer id) {
        Optional<RoleControllerEntity> byId = roleControllerRepository.findById(id);
        if (byId.isPresent() == false) {
            throw new UserUnknowException(String.format("RoleController have id : '%s' not found" + id));
        }

        return byId.get();
    }

    public Boolean updateByID(Integer id, RoleControllerRequest roleControllerRequest) {
        Optional<RoleControllerEntity> byId = roleControllerRepository.findById(id);
        if (roleControllerRepository.existsById(id)) {
            RoleControllerEntity roleControllerEntity = byId.get();
            roleControllerEntity.setCode(roleControllerRequest.getCode());
            roleControllerEntity.setIdRole(roleControllerRequest.getRoleId());
            roleControllerEntity.setIdController(roleControllerRequest.getControllerId());
            roleControllerRepository.save(roleControllerEntity);
            return true;
        }
        return false;

    }

    public RoleControllerEntity findByCode(Integer code)
    {
        List<RoleControllerEntity> rolePermissionEntities = getAllRoleController();
        for(RoleControllerEntity roleControllerEntity : rolePermissionEntities)
        {
            if(roleControllerEntity.getCode() == code)
            {
                return roleControllerEntity;
            }
        }
        return null;
    }

    public Boolean updateByCode(Integer code, RoleControllerRequest roleControllerRequest) {
        RoleControllerEntity byCode = findByCode(code);
        if (byCode != null) {
            RoleControllerEntity roleControllerEntity = byCode;
            roleControllerEntity.setIdController(roleControllerRequest.getControllerId());
            roleControllerRepository.save(roleControllerEntity);
            return true;
        }
        return false;

    }

    public boolean Delete(int code) {
        RoleControllerEntity byId = findByCode(code);
        if (byId != null) {
            roleControllerRepository.delete(byId);
            return true;
        }
        return false;
    }
}
