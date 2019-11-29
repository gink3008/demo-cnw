package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_role", schema = "dbo", catalog = "cnwnc")
public class UserRoleEntity {
    private int idRoleUser;
    private int code;
    private int idUser;
    private int idRole;
    private UsersEntity usersByIdUser;
    private RolesEntity rolesByIdRole;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRoleUser")
    public int getIdRoleUser() {
        return idRoleUser;
    }

    public void setIdRoleUser(int idRoleUser) {
        this.idRoleUser = idRoleUser;
    }

    @Basic
    @Column(name = "idUser")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
        UserRoleEntity that = (UserRoleEntity) o;
        return idUser == that.idUser &&
                idRole == that.idRole &&
                Objects.equals(idRoleUser, that.idRoleUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoleUser, idUser, idRole);
    }

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false,  insertable = false, updatable = false)
    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
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
