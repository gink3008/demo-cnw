package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.Repository.PermissionRepository;
import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Dao.RequestObject.PermissionRequest;
import com.hiep.democnw.Entities.real.PermissionEntity;
import com.hiep.democnw.Entities.real.RolePermissionEntity;
import com.hiep.democnw.Entities.real.RolesEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Boolean createNewPermisison(PermissionRequest permissionRequest) {
        for (PermissionEntity permissionEntity : getAllPermision()) {
            if (permissionEntity.getNamePermission().equals(permissionRequest.getName())) {
                return false;
            }
        }
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode(permissionRequest.getCode());
        permissionEntity.setNamePermission(permissionRequest.getName());
        permissionEntity.setUrl(permissionRequest.getUrl());
        permissionRepository.save(permissionEntity);
        return true;
    }

    public List<PermissionEntity> getAllPermision() {
        return permissionRepository.findAll();
    }

    public PermissionEntity getPermissionById(Integer id) {
        Optional<PermissionEntity> byId = permissionRepository.findById(id);
        if (byId.isPresent() == false) {
            throw new UserUnknowException(String.format("Permission have id : '%s' not found" + id));
        }

        return byId.get();
    }

    public Boolean updatePermissionByID(Integer id, PermissionRequest permissionRequest) {
        Optional<PermissionEntity> byId = permissionRepository.findById(id);
        if (permissionRepository.existsById(id)) {
            PermissionEntity permissionEntity = byId.get();
            permissionEntity.setCode(permissionRequest.getCode());
            permissionEntity.setNamePermission(permissionRequest.getName());
            permissionEntity.setUrl(permissionRequest.getUrl());
            permissionRepository.save(permissionEntity);
            return true;
        }
        return false;

    }

    public PermissionEntity findByRolename(String name) {
        List<PermissionEntity> permissionEntities  = getAllPermision();
        if(permissionEntities != null)
        {
            for (PermissionEntity permission : permissionEntities) {
                if (permission.getNamePermission().equals(name)) {
                    return permission;
                }
            }
        }
        return null;
    }

    public List<ControllerRequest> getControllerRequestByPermission(RolesEntity roles) {
        List<ControllerRequest> controllerRequests = new ArrayList<>();
        Set<RolePermissionEntity> rolePermissions = roles.getRolePermissionsByIdRole();
        if(rolePermissions != null){
            for(RolePermissionEntity rolePermission : rolePermissions)
            {
                ControllerRequest controllerRequest = new ControllerRequest();
                controllerRequest.setName(rolePermission.getPermissionByIdPermission().getNamePermission());
                controllerRequests.add(controllerRequest);
            }
        }
        return controllerRequests;

    }
}
