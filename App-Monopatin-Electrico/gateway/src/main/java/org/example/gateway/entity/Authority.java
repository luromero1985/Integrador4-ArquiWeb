package org.example.gateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//Esta clase define los roles que tendrá un usuario.
public class Authority {

    @Id
    private String name;
}
