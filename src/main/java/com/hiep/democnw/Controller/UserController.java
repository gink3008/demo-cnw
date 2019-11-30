package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Dao.RequestObject.UserRoleRequest;
import com.hiep.democnw.Entities.real.PermissionEntity;
import com.hiep.democnw.Entities.real.RolesEntity;
import com.hiep.democnw.Entities.real.UsersEntity;
import com.hiep.democnw.Dao.RequestObject.UserRequest;
import com.hiep.democnw.Dao.Services.UserService;
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
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    public static HashMap<Long, String> box = new HashMap<>();

    @PostMapping("/create_user")
    public ResponseEntity<Void> createNewUser(@Valid @RequestBody UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = userService.createNewUser(userRequest);
        UriComponents uriComponents;
        HttpHeaders httpHeaders = new HttpHeaders();
        if (check) {
            uriComponents = uriComponentsBuilder.path("api/users/check : {check}").buildAndExpand(check);
            httpHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);

        }
        uriComponents = uriComponentsBuilder.path("api/users/check : flase}").build();
        httpHeaders.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getlist_user")
    public ResponseEntity<List<UserRequest>> getAllUsers() {
        List<UserRequest> listUserRequests = new ArrayList<>();
        for (UsersEntity user : userService.getAllUsers())
        {
            List<ControllerRequest> roleRequest = new ArrayList<>();
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(user.getUsername());
            userRequest.setPassword(user.getPassword());
            userRequest.setRoleRequests(userService.getRoleRequestByUser(user));
            listUserRequests.add(userRequest);
        }
        return ResponseEntity.ok(listUserRequests);
    }

    @GetMapping("/getlist_user/{username}")
    public ResponseEntity<Object> getUserByID(Session session, @PathVariable("username") String username) {
        UsersEntity users = userService.findByUsername(username);
        if (users != null) {
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(users.getUsername());
            userRequest.setPassword(users.getPassword());
            userRequest.setRoleRequests(userService.getRoleRequestByUser(users));
            return ResponseEntity.ok(userRequest);
        }

        return new ResponseEntity<Object>("Not Found User", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update_user/{username}")
    public ResponseEntity<Boolean> updateUserByName(@PathVariable("username") String username,@Valid @RequestBody UserRequest userRequest) {
        UsersEntity usersEntity = userService.findByUsername(userRequest.getUsername());
        int id = usersEntity.getIdUser();
        if (usersEntity != null) {
//            System.out.print(roleRequest.getName());
            boolean check = userService.updateByID(username,userRequest);
            if(check)
            {
                return new ResponseEntity<Boolean>(check, HttpStatus.OK); // doan nay chua hoan thanh

            }
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

}
