package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.Repository.RolesRepository;
import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Dao.RequestObject.PermissionRequest;
import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import com.hiep.democnw.Entities.real.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RolesRepository roleRepository;

    public Boolean createNewRole(RoleRequest roleRequest) {
        List<RolesEntity> rolesEntities = getAllRoles();
        if(rolesEntities != null){
            for (RolesEntity role : rolesEntities) {
                if (role.getName().equals(roleRequest.getName())) {
                    return false;
                }
            }
        }

        RolesEntity roles = new RolesEntity();
        roles.setCode(roleRequest.getCode());
        roles.setName(roleRequest.getName());
        roleRepository.save(roles);
        return true;
    }

    List<RolesEntity> rolesEntities = null;

    public List<RolesEntity> getAllRoles() {
        rolesEntities = roleRepository.findAll();
        if (rolesEntities.size() != 0) {
            return rolesEntities;
        }
        return null;
    }

    public RolesEntity getRoleById(Integer id) {
        Optional<RolesEntity> byId = roleRepository.findById(id);
        if (byId.isPresent() == false) {
            return null;
        }

        return byId.get();
    }

    public Boolean updateRoleByID(Integer id, RoleRequest roleRequest) {
        Optional<RolesEntity> byId = roleRepository.findById(id);
        if (roleRepository.existsById(id)) {
            RolesEntity role = byId.get();
            role.setCode(roleRequest.getCode());
            role.setName(roleRequest.getName());
            roleRepository.save(role);
            return true;
        }
        return false;

    }

    public RolesEntity findByRoleName(String name) {
        List<RolesEntity> lstRole = getAllRoles();
        if(lstRole != null)
        {
            for (RolesEntity role : lstRole) {
                if (role.getName().equals(name)) {
                    return role;
                }
            }
        }
        return null;
    }


    public List<PermissionRequest> getPermissionRequestByRole(RolesEntity roles) {
        List<PermissionRequest> permissionRequests = new ArrayList<>();
        Set<RolePermissionEntity> rolePermissions = roles.getRolePermissionsByIdRole();
        if(rolePermissions != null){
            for(RolePermissionEntity rolePermission : rolePermissions)
            {
                PermissionRequest permissionRequest = new PermissionRequest();
                permissionRequest.setCode(rolePermission.getPermissionByIdPermission().getCode());
                permissionRequest.setName(rolePermission.getPermissionByIdPermission().getNamePermission());
                permissionRequest.setUrl(rolePermission.getPermissionByIdPermission().getUrl());
                permissionRequests.add(permissionRequest);
            }
        }
        return permissionRequests;

    }

    public List<ControllerRequest> getControllerRequestByRole(RolesEntity roles) {
        List<ControllerRequest> controllerRequests = new ArrayList<>();
        Set<RoleControllerEntity> roleController = roles.getRoleControllersByIdRole();
        if(roleController != null){
            for(RoleControllerEntity roleControllerEntity : roleController)
            {
                ControllerRequest controllerRequest = new ControllerRequest();
                controllerRequest.setCode(roleControllerEntity.getControllerByIdController().getCode());
                controllerRequest.setName(roleControllerEntity.getControllerByIdController().getNameController());
                controllerRequest.setPermissionId(roleControllerEntity.getControllerByIdController().getIdPermission());
                controllerRequests.add(controllerRequest);
            }
        }
        return controllerRequests;

    }

    public List<PermissionEntity> getPermissionByRole(RolesEntity roles) {
        List<PermissionEntity> permissionEntities = new ArrayList<>();
        Set<RolePermissionEntity> rolePermissions = roles.getRolePermissionsByIdRole();
        if(rolePermissions != null){
            for(RolePermissionEntity rolePermission : rolePermissions)
            {
                permissionEntities.add(rolePermission.getPermissionByIdPermission());
            }
        }
        return permissionEntities;

    }
    public List<ControllerEntity> getControllerByRole(RolesEntity roles) {
        List<ControllerEntity> controllerEntities = new ArrayList<>();
        Set<RoleControllerEntity> rolePermissions = roles.getRoleControllersByIdRole();
        if(rolePermissions != null){
            for(RoleControllerEntity roleController : rolePermissions)
            {
                controllerEntities.add(roleController.getControllerByIdController());
            }
        }
        return controllerEntities;

    }
}