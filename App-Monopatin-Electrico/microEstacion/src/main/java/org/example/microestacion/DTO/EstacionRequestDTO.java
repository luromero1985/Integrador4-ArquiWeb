package org.example.microestacion.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionRequestDTO {

    @NotNull(message = "El id no puede ser nulo.")
    private String id; // Cambiado a String para coincidir con la entidad

    @NotNull(message = "La latitud es un campo obligatorio.")
    private Double latitud; // Cambiado a Double para coincidir con la entidad

    @NotNull(message = "La longitud es un campo obligatorio.")
    private Double longitud; // Cambiado a Double para coincidir con la entidad
}
