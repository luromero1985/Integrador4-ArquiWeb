package org.example.microestacion.controllers;

import org.example.microestacion.DTO.EstacionRequestDTO;
import org.example.microestacion.DTO.EstacionResponseDTO;
import org.example.microestacion.services.EstacionService;
import org.example.microestacion.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController  // Indica que esta clase maneja peticiones REST
@RequestMapping("/estacion")  // Mapea las URL de la API
public class EstacionController {
@Autowired
    // Inyección de dependencias
    private EstacionService estacionService;
    @Autowired
    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }
    // Obtener todas las estaciones
    @GetMapping("")
    public ResponseEntity<List<EstacionResponseDTO>> findAll() {
        return ResponseEntity.ok(estacionService.findAll());
    }

    // Obtener una estación por ID
    @GetMapping("/{id}")
    public ResponseEntity<EstacionResponseDTO> findById(@PathVariable String id) { // Cambiado a String
        try {
            return ResponseEntity.ok(estacionService.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva estación
    @PostMapping("")
    public ResponseEntity<EstacionResponseDTO> save(@RequestBody EstacionRequestDTO estacionRequestDTO) {
        EstacionResponseDTO newEstacion = estacionService.save(estacionRequestDTO);
        return ResponseEntity.ok(newEstacion);
    }

    // Actualizar una estación existente
    @PutMapping("/{id}")
    public ResponseEntity<EstacionResponseDTO> update(@PathVariable String id, @RequestBody EstacionRequestDTO estacionRequestDTO) { // Cambiado a String
        try {
            return ResponseEntity.ok(estacionService.update(id, estacionRequestDTO));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una estación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { // Cambiado a String
        try {
            estacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
