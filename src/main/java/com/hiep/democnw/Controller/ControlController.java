package com.hiep.democnw.Controller;

import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Dao.RequestObject.PermissionRequest;
import com.hiep.democnw.Dao.Services.ControllerService;
import com.hiep.democnw.Dao.Services.PermissionService;
import com.hiep.democnw.Dao.Services.RolePermissionService;
import com.hiep.democnw.Entities.real.ControllerEntity;
import com.hiep.democnw.Entities.real.PermissionEntity;
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
@RequestMapping("/api/controllers")
public class ControlController {
    @Autowired
    private ControllerService controllerService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/getlist_controller")
    public ResponseEntity<List<ControllerRequest>> getListRole() {
        List<ControllerEntity> controllerEntities = controllerService.getAllControllers();
        List<ControllerRequest> listcontrollerRequests = new ArrayList<>();
        if(controllerEntities != null)
        {
            for (ControllerEntity controllerEntity : controllerEntities) {
                ControllerRequest controllerRequest = new ControllerRequest();
                controllerRequest.setCode(controllerEntity.getCode());
                controllerRequest.setName(controllerEntity.getNameController());
                controllerRequest.setMethod(controllerEntity.getMethod());
                listcontrollerRequests.add(controllerRequest);
            }
        }

        return ResponseEntity.ok(listcontrollerRequests);
    }

    @GetMapping("/getlist_controller/{id}")
    public ResponseEntity<Object> getUserByID(Session session, @PathVariable("id") int id) {
        ControllerEntity controllerEntity = controllerService.getControllerById(id);
        if (controllerEntity != null) {
            ControllerRequest controllerRequest = new ControllerRequest();
            controllerRequest.setCode(controllerEntity.getCode());
            controllerRequest.setName(controllerEntity.getNameController());
            controllerRequest.setMethod(controllerEntity.getMethod());
            return ResponseEntity.ok(controllerRequest);
        }

        return new ResponseEntity<Object>("Not Found User", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/update_controller/{name}")
    public ResponseEntity<Void> updatePermission(@PathVariable("name") String name, @Valid @RequestBody ControllerRequest controllerRequest, UriComponentsBuilder uriComponentsBuilder) {
        ControllerEntity controllerEntity = controllerService.findByControllerName(name);
        int id = controllerEntity.getIdController();
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/id : {id}").buildAndExpand(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        if (controllerEntity != null) {
//            System.out.print(roleRequest.getName());
            controllerService.updateControllerByID(id,controllerRequest);
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create_controller")
    public ResponseEntity<Void> create(@Valid @RequestBody ControllerRequest controllerRequest, UriComponentsBuilder uriComponentsBuilder) {
        boolean check = controllerService.createNewController(controllerRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("api/role/check : {check}").buildAndExpand(check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        httpHeaders.add("DetailResponse", "khong tao duoc");
        if (check) {
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK); // doan nay chua hoan thanh

        }
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.BAD_REQUEST);
    }

}
