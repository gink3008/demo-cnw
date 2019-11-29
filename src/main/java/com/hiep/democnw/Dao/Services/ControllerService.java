package com.hiep.democnw.Dao.Services;

import com.hiep.democnw.Dao.Repository.ControllerRepository;
import com.hiep.democnw.Dao.RequestObject.ControllerRequest;
import com.hiep.democnw.Entities.real.ControllerEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ControllerService {
    @Autowired
    private ControllerRepository controllerRepository;

    public Boolean createNewController(ControllerRequest controllerRequest) {
        for (ControllerEntity controllerEntity : getAllControllers()) {
            if (controllerEntity.getNameController().equals(controllerRequest.getName())) {
                return false;
            }
        }
        ControllerEntity controllerEntity = new ControllerEntity();
        controllerEntity.setCode(controllerRequest.getCode());
        controllerEntity.setNameController(controllerRequest.getName());
        controllerEntity.setIdPermission(controllerRequest.getPermissionId());
        controllerRepository.save(controllerEntity);
        return true;
    }

    public List<ControllerEntity> getAllControllers() {
        return controllerRepository.findAll();
    }

    public ControllerEntity getRoleById(Integer id) {
        Optional<ControllerEntity> byId = controllerRepository.findById(id);
        if (byId.isPresent() == false) {
            throw new UserUnknowException(String.format("Role have id : '%s' not found" + id));
        }

        return byId.get();
    }

    public Boolean updateRoleByID(Integer id, ControllerRequest controllerRequest) {
        Optional<ControllerEntity> byId = controllerRepository.findById(id);
        if (controllerRepository.existsById(id)) {
            ControllerEntity controller = byId.get();
            controller.setCode(controllerRequest.getCode());
            controller.setNameController(controllerRequest.getName());
            controller.setIdPermission(controllerRequest.getPermissionId());
            controllerRepository.save(controller);
            return true;
        }
        return false;

    }

    public ControllerEntity findByRolename(String name) {
        for (ControllerEntity controllerEntity : getAllControllers()) {
            if (controllerEntity.getNameController().equals(name)) {
                return controllerEntity;
            }
        }
        return null;
    }
}
