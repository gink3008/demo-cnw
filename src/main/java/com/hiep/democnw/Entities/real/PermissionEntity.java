package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "permission", schema = "dbo", catalog = "cnwnc")
public class PermissionEntity {
    private int idPermission;
    private int code;
    private String namePermission;
    private String url;
    private List<ControllerEntity> controllersByIdPermission;
    private List<RolePermissionEntity> rolePermissionsByIdPermission;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPermission")
    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Basic
    @Column(name = "namePermission")
    public String getNamePermission() {
        return namePermission;
    }

    public void setNamePermission(String namePermission) {
        this.namePermission = namePermission;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return idPermission == that.idPermission &&
                Objects.equals(namePermission, that.namePermission) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, namePermission, url);
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "permissionByIdPermission")
    public List<ControllerEntity> getControllersByIdPermission() {
        return controllersByIdPermission;
    }

    public void setControllersByIdPermission(List<ControllerEntity> controllersByIdPermission) {
        this.controllersByIdPermission = controllersByIdPermission;
    }

    @OneToMany(mappedBy = "permissionByIdPermission")
    public Collection<RolePermissionEntity> getRolePermissionsByIdPermission() {
        return rolePermissionsByIdPermission;
    }

    public void setRolePermissionsByIdPermission(List<RolePermissionEntity> rolePermissionsByIdPermission) {
        this.rolePermissionsByIdPermission = rolePermissionsByIdPermission;
    }
}
