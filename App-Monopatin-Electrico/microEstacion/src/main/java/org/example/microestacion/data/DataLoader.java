package org.example.microestacion.data;


import org.example.microestacion.entities.Estacion;
import org.example.microestacion.repositories.EstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner  {

    private final EstacionRepository estacionRepository;

    @Autowired
    public DataLoader(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Empezando");
        Estacion estacion1 = new Estacion(null, 40.7128, -74.0060); // Nueva York
        Estacion estacion2 = new Estacion(null, 34.0522, -118.2437); // Los Angeles

        // Guardar las estaciones en la base de datos
        estacionRepository.save(estacion1);
        estacionRepository.save(estacion2);

        System.out.println("Datos cargados en la colección 'estacion'.");
    }
}
