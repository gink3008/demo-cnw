package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Controller", schema = "dbo", catalog = "cnwnc")
public class ControllerEntity {
    private int idController;
    private String Method;
    private String nameController;
    private Integer idPermission;
    private PermissionEntity permissionByIdPermission;
    private Collection<RoleControllerEntity> roleControllersByIdController;
    private int code;

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        this.Method = method;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idController")
    public int getIdController() {
        return idController;
    }

    public void setIdController(int idController) {
        this.idController = idController;
    }

    @Basic
    @Column(name = "nameController")
    public String getNameController() {
        return nameController;
    }

    public void setNameController(String nameController) {
        this.nameController = nameController;
    }

    @Basic
    @Column(name = "idPermission")
    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerEntity that = (ControllerEntity) o;
        return idController == that.idController &&
                Objects.equals(nameController, that.nameController) &&
                Objects.equals(idPermission, that.idPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idController, nameController, idPermission);
    }

    @ManyToOne
    @JoinColumn(name = "idPermission", referencedColumnName = "idPermission", insertable = false, updatable = false)// do thong bao phai them insertable = false vaf updatetable = false nen them vao
    public PermissionEntity getPermissionByIdPermission() {
        return permissionByIdPermission;
    }

    public void setPermissionByIdPermission(PermissionEntity permissionByIdPermission) {
        this.permissionByIdPermission = permissionByIdPermission;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "controllerByIdController")
    public Collection<RoleControllerEntity> getRoleControllersByIdController() {
        return roleControllersByIdController;
    }

    public void setRoleControllersByIdController(Collection<RoleControllerEntity> roleControllersByIdController) {
        this.roleControllersByIdController = roleControllersByIdController;
    }
}
