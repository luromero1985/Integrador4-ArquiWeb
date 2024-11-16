package org.example.microviaje.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMonopatinPorCantViajesPorAnioDTO {

    private Long id_monopatin;
    private Long cantViajes;
    private int anio;

}
