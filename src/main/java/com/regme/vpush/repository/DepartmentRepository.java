package com.regme.vpush.repository;

import com.regme.vpush.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by admin on 13.08.2018.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select d from Department d where d.code = :code")
    Department findByCode(@Param("code") String code);
}
