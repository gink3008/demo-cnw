package com.hiep.democnw.Dao.Repository;

import com.hiep.democnw.Entities.real.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    RolesEntity findByName(String name);
}
