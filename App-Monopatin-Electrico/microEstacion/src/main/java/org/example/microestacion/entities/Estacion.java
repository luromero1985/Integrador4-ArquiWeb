package org.example.microestacion.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.jetbrains.annotations.NotNull;
import lombok.*;

@Document(collection = "estacion") // Mapea a la colección "estaciones" en MongoDB
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Estacion {

    @Id // Identificador único para MongoDB
    private String id;

    @NotNull
    private Double latitud; // Usamos Double para representar coordenadas con decimales

    @NotNull
    private Double longitud;
}
