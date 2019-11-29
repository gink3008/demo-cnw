package com.hiep.democnw.Dao;

import com.hiep.democnw.Entities.real.UsersEntity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAO2 {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<UsersEntity> findAll() {
        return entityManager.createQuery("SELECT e FROM import com.hiep.democnw.Entities.UserEntity e", UsersEntity.class).getResultList();
    }
}