package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.Repository.RolePermissionRepository;
import com.hiep.democnw.Dao.RequestObject.RolePermissionRequest;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
import com.hiep.democnw.Entities.real.RolePermissionEntity;
import com.hiep.democnw.Entities.real.UserRoleEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public Boolean createNewRolePerMission(RolePermissionRequest rolePermissionRequest) {
        for (RolePermissionEntity rolePermission : getAllRolePermission()) {
            if (rolePermission.getIdRole() == rolePermissionRequest.getRoleId() && rolePermission.getIdPermission() == rolePermissionRequest.getPermissionId()) {
                return false;
            }
        }

        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setCode(rolePermissionRequest.getCode());
        rolePermissionEntity.setIdRole(rolePermissionRequest.getRoleId());
        rolePermissionEntity.setIdPermission(rolePermissionRequest.getPermissionId());
        rolePermissionRepository.save(rolePermissionEntity);
        return true;
    }

    public List<RolePermissionEntity> getAllRolePermission() {
        return rolePermissionRepository.findAll();
    }

    public RolePermissionEntity getUserById(Integer id) {
        Optional<RolePermissionEntity> byId = rolePermissionRepository.findById(id);
        if (byId.isPresent() == false) {
            throw new UserUnknowException(String.format("User have id : '%s' not found" + id));
        }

        return byId.get();
    }
    public RolePermissionEntity findByCode(Integer code)
    {
        List<RolePermissionEntity> rolePermissionEntities = getAllRolePermission();
        for(RolePermissionEntity rolePermissionEntity : rolePermissionEntities)
        {
            if(rolePermissionEntity.getCode() == code)
            {
                return rolePermissionEntity;
            }
        }
        return null;
    }

    public Boolean updateByCode(Integer code, RolePermissionRequest rolePermissionRequest) {
        RolePermissionEntity byCode = findByCode(code);
        if (byCode != null) {
            RolePermissionEntity rolePermissionEntity = byCode;
            rolePermissionEntity.setIdPermission(rolePermissionRequest.getPermissionId());
            rolePermissionRepository.save(rolePermissionEntity);
            return true;
        }
        return false;

    }

    public Boolean updateByID(Integer id, RolePermissionRequest rolePermissionRequest) {
        Optional<RolePermissionEntity> byId = rolePermissionRepository.findById(id);
        if (rolePermissionRepository.existsById(id)) {
            RolePermissionEntity rolePermissionEntity = byId.get();
            rolePermissionEntity.setCode(rolePermissionRequest.getCode());
            rolePermissionEntity.setIdRole(rolePermissionRequest.getRoleId());
            rolePermissionEntity.setIdRolePermission(rolePermissionRequest.getPermissionId());
            rolePermissionRepository.save(rolePermissionEntity);
            return true;
        }
        return false;

    }

    public boolean Delete(int code) {
        RolePermissionEntity byId = findByCode(code);
        if (byId != null) {
            rolePermissionRepository.delete(byId);
            return true;
        }
        return false;
    }
}
