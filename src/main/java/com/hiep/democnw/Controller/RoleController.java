package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import com.hiep.democnw.Dao.Services.RolePermissionService;
import com.hiep.democnw.Dao.Services.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/getlist_role")
    public ResponseEntity<List<RoleRequest>> getListRole() {
        List<RolesEntity> rolesEntities = roleService.getAllRoles();
        List<RoleRequest> roleRequests = new ArrayList<>();
        if(rolesEntities != null)
        {
            for (RolesEntity roles : rolesEntities) {
                RoleRequest roleRequest = new RoleRequest();
                roleRequest.setCode(roles.getCode());
                roleRequest.setName(roles.getName());
                roleRequest.setControllerList(roleService.getControllerRequestByRole(roles));
                roleRequest.setPermissionList(roleService.getPermissionRequestByRole(roles));
                roleRequests.add(roleRequest);
            }
        }

        return ResponseEntity.ok(roleRequests);
    }

    @GetMapping("/getlist_role/{id}")
    public ResponseEntity<Object> getlist(Session session, @PathVariable("id") int id) {
        RolesEntity role = roleService.getRoleById(id);
        if (role != null) {
            RoleRequest roleRequest = new RoleRequest();
            roleRequest.setName(role.getName());
            return ResponseEntity.ok(roleRequest);
        }

        return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update_role/{name}")
    public ResponseEntity<Void> update(@PathVariable("name") String name,@Valid @RequestBody RoleRequest roleRequest, UriComponentsBuilder uriComponentsBuilder) {
        RolesEntity role = roleService.findByRoleName(name);
        int id = role.getIdRole();
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/id : {id}").buildAndExpand(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        if (role != null) {
//            System.out.print(roleRequest.getName());
            roleService.updateRoleByID(id,roleRequest);
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create_role")
    public ResponseEntity<Void> create(@Valid @RequestBody RoleRequest roleRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = roleService.createNewRole(roleRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_role")
    public ResponseEntity<Boolean> delete(@Valid @RequestBody RoleRequest roleRequest, UriComponentsBuilder uriComponentsBuilder)
        {
            boolean check = roleService.deleteRole(roleRequest);
            if(check)
            {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            }

            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);

        }
    }
