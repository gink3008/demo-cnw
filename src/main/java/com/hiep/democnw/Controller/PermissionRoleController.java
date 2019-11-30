package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.RolePermissionRequest;
import com.hiep.democnw.Dao.Services.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/permissionRole")
public class PermissionRoleController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @PostMapping("/post_permissionRoles")
    public ResponseEntity<Void> create(@Valid @RequestBody RolePermissionRequest rolePermissionRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = rolePermissionService.createNewRolePerMission(rolePermissionRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/userRole/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        httpHeaders.add("DetailResponse", "khong tao duoc");
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK);

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/userRole/delete_permissionRoles/{code}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int code) {
        boolean check = rolePermissionService.Delete(code);
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);

    }
    @PutMapping("userRole/update_permissionRoles/{code}")
    public ResponseEntity<Boolean> update(@PathVariable("code") int code, @Valid @RequestBody RolePermissionRequest rolePermissionRequest)
    {
        boolean check = rolePermissionService.updateByCode(code,rolePermissionRequest);  // nang cap sau nay : Code se la ma hoa cua ID, va chuyen ham updateByCode thanh ID de nang cao toc do
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
}
