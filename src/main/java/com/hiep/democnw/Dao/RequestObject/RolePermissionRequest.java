package com.hiep.democnw.Dao.RequestObject;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RolePermissionRequest {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @NotEmpty
    @Size(max = 20)
    private int Code;

    @NotEmpty
    private int roleId;

    @NotEmpty
    private int permissionId;
}
