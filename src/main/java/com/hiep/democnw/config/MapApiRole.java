package com.hiep.democnw.config;

import com.hiep.democnw.Dao.Services.PermissionService;
import com.hiep.democnw.Dao.Services.RoleControllerService;
import com.hiep.democnw.Dao.Services.RoleService;
import com.hiep.democnw.Entities.real.ControllerEntity;
import com.hiep.democnw.Entities.real.PermissionEntity;
import com.hiep.democnw.Entities.real.RoleControllerEntity;
import com.hiep.democnw.Entities.real.RolesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
@Service
public class MapApiRole {
    @Autowired
    private RoleControllerService roleControllerService ;
    @Autowired
    private PermissionService permissionService;
    static HashMap<String, Set<String>> apiRole = new HashMap<>();
    protected HashMap<String, Set<String>> getAllApi()
    {
        List<PermissionEntity> permissionEntities = permissionService.getAllPermision();
        List<RoleControllerEntity> roleControllerEntities = roleControllerService.getAllRoleController();
        if (roleControllerEntities != null) {
            for(RoleControllerEntity roleControllerEntity : roleControllerEntities)
            {
                Set<String> listString = new HashSet<>();
                String a = roleControllerEntity.getControllerByIdController().getMethod() + ":"
                        + roleControllerEntity.getControllerByIdController().getPermissionByIdPermission().getUrl()
                        + roleControllerEntity.getControllerByIdController().getNameController() + "/**";
                System.out.print("\n Class : MapApiRole \n " + a + " \n");

                if(!apiRole.containsKey(a))
                {
                    listString.add(roleControllerEntity.getRolesByIdRole().getName());
                    apiRole.put(a,listString);
                }
                else {
                    listString = apiRole.get(a);
                    listString.add(roleControllerEntity.getRolesByIdRole().getName());
                    apiRole.replace(a,listString);
                }
            }
        }
        return apiRole;
    }
}
