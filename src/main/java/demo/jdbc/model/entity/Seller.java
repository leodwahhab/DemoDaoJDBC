package demo.jdbc.model.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Seller implements Serializable {
    private Integer id;
    private String name, email;
    private Date birthDate;
    private Double baseSalary;
    private Department department;
}
