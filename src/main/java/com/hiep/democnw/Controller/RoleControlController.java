package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.RoleControllerRequest;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
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
@RequestMapping("/api/roleControllers")
public class RoleControlController {
    @Autowired
    private RoleControllerService roleControllerService;
    @PostMapping("/post_roleControllers")
    public ResponseEntity<Void> create(@Valid @RequestBody RoleControllerRequest roleControllerRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = roleControllerService.createNewRoleController(roleControllerRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/userRole/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        httpHeaders.add("DetailResponse", "khong tao duoc");
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK);

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/userRole/delete_roleControllers/{code}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int code) {
        boolean check = roleControllerService.Delete(code);
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);

    }
    @PutMapping("userRole/update_roleControllers/{code}")
    public ResponseEntity<Boolean> update(@PathVariable("code") int code, @Valid @RequestBody RoleControllerRequest roleControllerRequest)
    {
        boolean check = roleControllerService.updateByCode(code,roleControllerRequest);  // nang cap sau nay : Code se la ma hoa cua ID, va chuyen ham updateByCode thanh ID de nang cao toc do
        if(check)
        {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
}
