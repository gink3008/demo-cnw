package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_controller", schema = "dbo", catalog = "cnwnc")
public class RoleControllerEntity {
    private int idRoleController;
    private int code;
    private int idController;
    private int idRole;
    private ControllerEntity controllerByIdController;
    private RolesEntity rolesByIdRole;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRoleController")
    public int getIdRoleController() {
        return idRoleController;
    }

    public void setIdRoleController(int idRoleController) {
        this.idRoleController = idRoleController;
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
    @Column(name = "idController")
    public int getIdController() {
        return idController;
    }

    public void setIdController(int idController) {
        this.idController = idController;
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
        RoleControllerEntity that = (RoleControllerEntity) o;
        return idRoleController == that.idRoleController &&
                code == that.code &&
                idController == that.idController &&
                idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoleController, code, idController, idRole);
    }

    @ManyToOne
    @JoinColumn(name = "idController", referencedColumnName = "idController", nullable = false,  insertable = false, updatable = false)
    public ControllerEntity getControllerByIdController() {
        return controllerByIdController;
    }

    public void setControllerByIdController(ControllerEntity controllerByIdController) {
        this.controllerByIdController = controllerByIdController;
    }

    @ManyToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", nullable = false,  insertable = false, updatable = false)
    public RolesEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }
}
