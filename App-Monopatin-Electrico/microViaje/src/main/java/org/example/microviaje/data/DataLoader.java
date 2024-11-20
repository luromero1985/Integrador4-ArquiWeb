package org.example.microviaje.data;

import org.example.microviaje.entities.Viaje;
import org.example.microviaje.repositories.ViajeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final ViajeRepository viajeRepository;

    public DataLoader(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Viaje viaje1 = new Viaje(1L, // ID
                LocalDateTime.of(2024, 1, 15, 14, 0),
                LocalDateTime.of(2024, 1, 15, 15, 30),
                40543212L, -38312415L, // Inicio: Estacion A
                40353454L, -38123876L, // Fin: Estacion B
                1L, 2L,
                null, null,
                null);

        Viaje viaje2 = new Viaje(2L, // ID
                LocalDateTime.of(2024, 2, 20, 9, 0),
                LocalDateTime.of(2024, 2, 20, 10, 30),
                40353454L, -38123876L, // Inicio: Estacion B
                40543321L, -38312300L, // Fin: Estacion C
                2L, 1L,
                null, null,
                null);

        Viaje viaje3 = new Viaje(3L, // ID
                LocalDateTime.of(2024, 3, 10, 12, 0),
                LocalDateTime.of(2024, 3, 10, 13, 30),
                40543321L, -38312300L, // Inicio: Estacion C
                40453000L, -38312000L, // Fin: Estacion D
                3L, 4L,
                LocalDateTime.of(2024, 3, 10, 12, 30), // Inicio pausa
                LocalDateTime.of(2024, 3, 10, 12, 50), // Fin pausa
                null);

        Viaje viaje4 = new Viaje(4L, // ID
                LocalDateTime.of(2024, 4, 5, 14, 0),
                LocalDateTime.of(2024, 4, 5, 15, 30),
                40453000L, -38312000L, // Inicio: Estacion D
                40353645L, -38123456L, // Fin: Estacion E
                4L, 5L,
                null, null,
                null);

        Viaje viaje5 = new Viaje(5L, // ID
                LocalDateTime.of(2024, 5, 20, 8, 0),
                LocalDateTime.of(2024, 5, 20, 9, 30),
                40353645L, -38123456L, // Inicio: Estacion E
                40454000L, -38312100L, // Fin: Estacion F
                6L, 7L,
                LocalDateTime.of(2024, 5, 20, 8, 40), // Inicio pausa
                LocalDateTime.of(2024, 5, 20, 8, 55), // Fin pausa
                null);

        Viaje viaje6 = new Viaje(6L, // ID
                LocalDateTime.of(2024, 6, 10, 17, 0),
                LocalDateTime.of(2024, 6, 10, 18, 30),
                40454000L, -38312100L, // Inicio: Estacion F
                40353645L, -38123456L, // Fin: Estacion E
                5L, 6L,
                LocalDateTime.of(2024, 6, 10, 17, 45), // Inicio pausa
                LocalDateTime.of(2024, 6, 10, 18, 0), // Fin pausa
                null);

        Viaje viaje7 = new Viaje(7L, // ID
                LocalDateTime.of(2024, 7, 15, 10, 0),
                LocalDateTime.of(2024, 7, 15, 11, 30),
                40543212L, -38312415L, // Inicio: Estacion A
                40353454L, -38123876L, // Fin: Estacion B
                7L, 8L,
                LocalDateTime.of(2024, 7, 15, 10, 45), // Inicio pausa
                LocalDateTime.of(2024, 7, 15, 10, 50), // Fin pausa
                null);

        Viaje viaje8 = new Viaje(8L, // ID
                LocalDateTime.of(2024, 8, 25, 16, 0),
                LocalDateTime.of(2024, 8, 25, 17, 30),
                40543321L, -38312300L, // Inicio: Estacion C
                40453000L, -38312000L, // Fin: Estacion D
                2L, 4L,
                LocalDateTime.of(2024, 8, 25, 16, 45), // Inicio pausa
                LocalDateTime.of(2024, 8, 25, 17, 5), // Fin pausa
                null);

        Viaje viaje9 = new Viaje(9L, // ID
                LocalDateTime.of(2024, 9, 10, 20, 0),
                LocalDateTime.of(2024, 9, 10, 21, 30),
                40453000L, -38312000L, // Inicio: Estacion D
                40353645L, -38123456L, // Fin: Estacion E
                3L, 2L,
                LocalDateTime.of(2024, 9, 10, 20, 30), // Inicio pausa
                LocalDateTime.of(2024, 9, 10, 20, 50), // Fin pausa
                null);

        Viaje viaje10 = new Viaje(10L, // ID
                LocalDateTime.of(2024, 10, 5, 18, 0),
                LocalDateTime.of(2024, 10, 5, 19, 30),
                40543212L, -38312415L, // Inicio: Estacion A
                40353454L, -38123876L, // Fin: Estacion B
                8L, 9L,
                null, null,
                null);

// Guardar los viajes
        viajeRepository.save(viaje1);
        viajeRepository.save(viaje2);
        viajeRepository.save(viaje3);
        viajeRepository.save(viaje4);
        viajeRepository.save(viaje5);
        viajeRepository.save(viaje6);
        viajeRepository.save(viaje7);
        viajeRepository.save(viaje8);
        viajeRepository.save(viaje9);
        viajeRepository.save(viaje10);


        System.out.println("Datos iniciales cargados en la base de datos para microViaje.");
    }
}