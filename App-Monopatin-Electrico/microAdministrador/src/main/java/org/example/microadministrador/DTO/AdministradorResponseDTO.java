package org.example.microadministrador.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorResponseDTO {

    private Long id;

    private float precio;

    private float precioEspecial;

    private LocalDateTime fecha;
    private Integer topeKm;


}