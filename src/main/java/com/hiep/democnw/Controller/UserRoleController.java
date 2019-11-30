package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
import com.hiep.democnw.Dao.Services.ControllerService;
import com.hiep.democnw.Dao.Services.RoleControllerService;
import com.hiep.democnw.Dao.Services.UserRoleService;
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
@RequestMapping("/api/controllers")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @PostMapping("/post_controller")
    public ResponseEntity<Void> create(@Valid @RequestBody UserRoleRequest userRoleRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = userRoleService.createNewUserRole(userRoleRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/userRole/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        httpHeaders.add("DetailResponse", "khong tao duoc");
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK);

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/userController/delete_userRole/{code}")
    public ResponseEntity<Boolean> delete(@PathVariable("code") int code) {
        boolean check = userRoleService.Delete(code);
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);

    }
    @PutMapping("userRole/update_userRole/{code}")
    public ResponseEntity<Boolean> update(@PathVariable("code") int code, @Valid @RequestBody UserRoleRequest userRoleRequest)
    {
        boolean check = userRoleService.updateByCode(code,userRoleRequest);  // nang cap sau nay : Code se la ma hoa cua ID, va chuyen ham updateByCode thanh ID de nang cao toc do
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
}
