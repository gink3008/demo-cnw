package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "dbo", catalog = "cnwnc")
public class RolesEntity {
    private int idRole;
    private int code;
    private String name;
    private Set<RoleControllerEntity> roleControllersByIdRole;
    private Set<RolePermissionEntity> rolePermissionsByIdRole;
    private Set<UserRoleEntity> userRolesByIdRole;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRole")
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return idRole == that.idRole &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, name);
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rolesByIdRole", cascade = CascadeType.ALL)
    public Set<RoleControllerEntity> getRoleControllersByIdRole() {
        return roleControllersByIdRole;
    }

    public void setRoleControllersByIdRole(Set<RoleControllerEntity> roleControllersByIdRole) {
        this.roleControllersByIdRole = roleControllersByIdRole;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rolesByIdRole", cascade = CascadeType.ALL)
    public Set<RolePermissionEntity> getRolePermissionsByIdRole() {
        return rolePermissionsByIdRole;
    }

    public void setRolePermissionsByIdRole(Set<RolePermissionEntity> rolePermissionsByIdRole) {
        this.rolePermissionsByIdRole = rolePermissionsByIdRole;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rolesByIdRole", cascade = CascadeType.ALL)
    public Set<UserRoleEntity> getUserRolesByIdRole() {
        return userRolesByIdRole;
    }

    public void setUserRolesByIdRole(Set<UserRoleEntity> userRolesByIdRole) {
        this.userRolesByIdRole = userRolesByIdRole;
    }
}
