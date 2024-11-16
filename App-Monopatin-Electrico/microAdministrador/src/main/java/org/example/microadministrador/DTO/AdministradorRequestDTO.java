package org.example.microadministrador.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorRequestDTO {
    @NotNull(message = "El ID es obligatorio.")
    private Long id;

    @NotNull(message = "El precio es obligatorio.")
    private float precio;

    @NotNull(message = "El precio especial es obligatorio.")
    private float precioEspecial;

    @NotNull(message = "La fecha es obligatoria.")
    private LocalDateTime fecha;

    @NotNull(message = "El campo topeKm es obligatorio.")
    @NotEmpty(message = "El campo topeKm no  puede estar vacío")
    private int topeKm;

}