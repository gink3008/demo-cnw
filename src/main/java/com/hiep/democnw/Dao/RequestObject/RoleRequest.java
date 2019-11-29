package com.hiep.democnw.Dao.RequestObject;

import com.hiep.democnw.Entities.real.PermissionEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleRequest {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @NotEmpty
    @Size(max = 30)
    private String name;

    private List<PermissionRequest> permissionList;
    private List<ControllerRequest> controllerList;
    public RoleRequest(@NotEmpty @Size(max = 30) String name) {
        this.name = name;
    }

    public RoleRequest() {
    }
}
