package com.regme.vpush;

import com.regme.vpush.domain.Department;
import com.regme.vpush.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by admin on 13.08.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Test
    public void whenFindByCode_thenReturnDepartment() {
       // String code = "440011";

        Department department = new Department("fmsTestName", "440011");
        this.departmentRepository.save(department);
        Department foundDepartment = departmentRepository.findByCode("440011");
        assertThat(foundDepartment.getName()).isEqualTo(department.getName());
        this.departmentRepository.delete(foundDepartment);
    }
}
