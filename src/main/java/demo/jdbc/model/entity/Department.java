package demo.jdbc.model.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Department implements Serializable {
    private Integer id;
    private String name;
}
