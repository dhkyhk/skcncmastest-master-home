package skcnc.msa3.domain.jpa.primary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skcnc.msa3.domain.jpa.primary.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {

}
