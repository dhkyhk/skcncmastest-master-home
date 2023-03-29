package skcnc.msa3.domain.jpa.primary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity {
    @Id
    @Column(length = 100)
    protected String id;

    @Column(length = 100)
    protected String password;
}
