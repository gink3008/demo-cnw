package com.hiep.democnw.Dao.RequestObject;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ControllerRequest {
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

    @NotEmpty
    private int permissionId;

    public ControllerRequest(@NotEmpty @Size(max = 30) String name) {
        this.name = name;
    }
    public ControllerRequest() {
    }

}
