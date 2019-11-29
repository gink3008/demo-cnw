package com.hiep.democnw.Entities.real;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_permission", schema = "dbo", catalog = "cnwnc")
public class RolePermissionEntity {
    private int idRolePermission;
    private int code;
    private int idPermission;
    private int idRole;
    private PermissionEntity permissionByIdPermission;
    private RolesEntity rolesByIdRole;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRolePermission")
    public int getIdRolePermission() {
        return idRolePermission;
    }

    public void setIdRolePermission(int idRolePermission) {
        this.idRolePermission = idRolePermission;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "idPermission")
    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Basic
    @Column(name = "idRole")
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return idRolePermission == that.idRolePermission &&
                code == that.code &&
                idPermission == that.idPermission &&
                idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolePermission, code, idPermission, idRole);
    }

    @ManyToOne
    @JoinColumn(name = "idPermission", referencedColumnName = "idPermission", nullable = false, insertable = false, updatable = false)  // do thong bao phai them insertable = false vaf updatetable = false nen them vao
    public PermissionEntity getPermissionByIdPermission() {
        return permissionByIdPermission;
    }

    public void setPermissionByIdPermission(PermissionEntity permissionByIdPermission) {
        this.permissionByIdPermission = permissionByIdPermission;
    }

    @ManyToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", nullable = false, insertable = false, updatable = false ) //tuong tu tren
    public RolesEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }
}
