package com.hiep.democnw.Dao.RequestObject;

import com.hiep.democnw.Dao.RequestObject.RoleRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserRequest {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @NotEmpty
    @Size(max = 20)
    private String username;
    @NotEmpty
    @Size(max = 20)
    private String password;

    List<RoleRequest> roleRequests;
}
