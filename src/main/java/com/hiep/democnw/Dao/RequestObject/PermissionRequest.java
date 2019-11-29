package com.hiep.democnw.Dao.RequestObject;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PermissionRequest {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @NotEmpty
    @Size(max = 255)
    private String url;

    @NotEmpty
    @Size(max = 255)
    private String name;
}
