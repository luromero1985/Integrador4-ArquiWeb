package org.example.microfacturacion.data;

import org.example.microfacturacion.entities.Facturacion;
import org.example.microfacturacion.repositories.FacturacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final FacturacionRepository facturacionRepository;

    public DataLoader(FacturacionRepository facturacionRepository) {
        this.facturacionRepository = facturacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Facturaciones acorde a los viajes
        Facturacion factura1 = new Facturacion(1L, 40543212L, -38312415L, 250.75f, LocalDate.of(2024, 1, 15));
        Facturacion factura2 = new Facturacion(2L, 40353454L, -38123876L, 450.50f, LocalDate.of(2024, 2, 20));
        Facturacion factura3 = new Facturacion(3L, 40543321L, -38312300L, 600.25f, LocalDate.of(2024, 3, 10));
        Facturacion factura4 = new Facturacion(4L, 40453000L, -38312000L, 320.90f, LocalDate.of(2024, 4, 5));
        Facturacion factura5 = new Facturacion(5L, 40353645L, -38123456L, 710.00f, LocalDate.of(2024, 5, 20));
        Facturacion factura6 = new Facturacion(6L, 40454000L, -38312100L, 560.30f, LocalDate.of(2024, 6, 10));
        Facturacion factura7 = new Facturacion(7L, 40543212L, -38312415L, 275.40f, LocalDate.of(2024, 7, 15));
        Facturacion factura8 = new Facturacion(8L, 40543321L, -38312300L, 830.80f, LocalDate.of(2024, 8, 25));
        Facturacion factura9 = new Facturacion(9L, 40453000L, -38312000L, 415.60f, LocalDate.of(2024, 9, 10));
        Facturacion factura10 = new Facturacion(10L, 40543212L, -38312415L, 340.20f, LocalDate.of(2024, 10, 5));


        facturacionRepository.save(factura1);
        facturacionRepository.save(factura2);

        System.out.println("Datos iniciales cargados en la base de datos para microFacturacion.");
    }
}