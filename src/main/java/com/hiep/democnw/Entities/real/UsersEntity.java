package com.hiep.democnw.Entities.real;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", schema = "dbo", catalog = "cnwnc")
public class UsersEntity {
    private int idUser;
    private int code;
    private String password;
    private String username;
    private Set<UserRoleEntity> userRolesByIdUser;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return idUser == that.idUser &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, password, username);
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "usersByIdUser", cascade = CascadeType.ALL)
    public Set<UserRoleEntity> getUserRolesByIdUser() {
        return userRolesByIdUser;
    }

    public void setUserRolesByIdUser(Set<UserRoleEntity> userRolesByIdUser) {
        this.userRolesByIdUser = userRolesByIdUser;
    }
}
