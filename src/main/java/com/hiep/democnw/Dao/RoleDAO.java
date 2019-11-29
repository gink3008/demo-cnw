package com.hiep.democnw.Dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    public long insert (UserEntity users) {
//        entityManager.persist(users);
//        return users.getId();
//    }

}
