package com.hiep.democnw.Dao.Repository;

import com.hiep.democnw.Entities.real.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
}
