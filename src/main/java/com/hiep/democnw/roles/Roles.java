package com.hiep.democnw.roles;

import java.io.Serializable;

//
//@Entity
//@Table(name = "role")
//@Data
public class Roles implements Serializable {
    public Roles(String role_admin) {
    }

//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Integer id;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.EAGER)
//    private Set<Users> users;
//    public Roles ()
//    {
//    }
//    public Roles(String name) {
//        this.name = name;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Roles roles = (Roles) o;
//
//        if (!id.equals(roles.id)) return false;
//        return name.equals(roles.name);
//    }

//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + name.hashCode();
//        return result;
//    }
}