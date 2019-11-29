package com.hiep.democnw.Dao.Repository;


import com.hiep.democnw.Entities.real.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRespository extends JpaRepository<UsersEntity, Integer> {
}
