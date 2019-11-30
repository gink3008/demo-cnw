package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.PermissionRequest;
import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import com.hiep.democnw.Dao.Services.PermissionService;
import com.hiep.democnw.Dao.Services.RolePermissionService;
import com.hiep.democnw.Dao.Services.RoleService;
import com.hiep.democnw.Entities.real.PermissionEntity;
import com.hiep.democnw.Entities.real.RolesEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/getlist_permission")
    public ResponseEntity<List<PermissionRequest>> getListRole() {
        List<PermissionEntity> permissionEntities = permissionService.getAllPermision();
        List<PermissionRequest> permissionRequests = new ArrayList<>();
        if(permissionEntities != null)
        {
            for (PermissionEntity permissions : permissionEntities) {
                PermissionRequest permissionRequest = new PermissionRequest();
                permissionRequest.setCode(permissions.getCode());
                permissionRequest.setName(permissions.getNamePermission());
                permissionRequest.setUrl(permissions.getUrl());
                permissionRequests.add(permissionRequest);
            }
        }

        return ResponseEntity.ok(permissionRequests);
    }

    @GetMapping("/getlist_permission/{id}")
    public ResponseEntity<Object> getUserByID(Session session, @PathVariable("id") int id) {
        PermissionEntity permissionEntity = permissionService.getPermissionById(id);
        if (permissionEntity != null) {
            PermissionRequest permissionRequest = new PermissionRequest();
            permissionRequest.setCode(permissionEntity.getCode());
            permissionRequest.setName(permissionEntity.getNamePermission());
            permissionRequest.setUrl(permissionEntity.getUrl());
            return ResponseEntity.ok(permissionRequest);
        }

        return new ResponseEntity<Object>("Not Found User", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/update_permission")
    public ResponseEntity<Void> updatePermission(@Valid @RequestBody PermissionRequest permissionRequest, UriComponentsBuilder uriComponentsBuilder) {
        PermissionEntity permissionEntity = permissionService.findByRolename(permissionRequest.getName());
        int id = permissionEntity.getIdPermission();
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/id : {id}").buildAndExpand(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        if (permissionEntity != null) {
//            System.out.print(roleRequest.getName());
            permissionService.updatePermissionByID(id,permissionRequest);
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post_permission")
    public ResponseEntity<Void> createNewPermission(@Valid @RequestBody PermissionRequest permissionRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = permissionService.createNewPermisison(permissionRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

}