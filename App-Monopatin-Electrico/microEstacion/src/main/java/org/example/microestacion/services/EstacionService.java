package org.example.microestacion.services;

import org.example.microestacion.DTO.EstacionRequestDTO;
import org.example.microestacion.DTO.EstacionResponseDTO;
import org.example.microestacion.entities.Estacion;
import org.example.microestacion.repositories.EstacionRepository;
import org.example.microestacion.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;
    @Autowired
    public EstacionService(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }
    // Obtener todas las estaciones
    public List<EstacionResponseDTO> findAll() {
        return estacionRepository.findAll().stream()
                .map(this::mapToEstacionResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar estación por ID
    public EstacionResponseDTO findById(String id) {
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La estación con ID " + id + " no fue encontrada"));
        return mapToEstacionResponseDTO(estacion);
    }

    // Guardar una nueva estación
    public EstacionResponseDTO save(@Valid EstacionRequestDTO estacionRequestDTO) {
        // Crear una nueva estación basada en el DTO
        Estacion estacionNueva = new Estacion();
        estacionNueva.setLatitud(estacionRequestDTO.getLatitud());
        estacionNueva.setLongitud(estacionRequestDTO.getLongitud());

        // Guardar la estación en la base de datos
        Estacion estacionGuardada = estacionRepository.save(estacionNueva);
        return mapToEstacionResponseDTO(estacionGuardada);
    }

    // Actualizar una estación existente
    public EstacionResponseDTO update(String id, @Valid EstacionRequestDTO estacionRequestDTO) {
        // Buscar la estación existente
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La estación con ID " + id + " no fue encontrada"));

        // Actualizar los valores
        estacion.setLatitud(estacionRequestDTO.getLatitud());
        estacion.setLongitud(estacionRequestDTO.getLongitud());

        // Guardar los cambios en la base de datos
        Estacion estacionActualizada = estacionRepository.save(estacion);
        return mapToEstacionResponseDTO(estacionActualizada);
    }

    // Eliminar una estación
    public void delete(String id) {
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La estación con ID " + id + " no fue encontrada"));
        estacionRepository.delete(estacion);
    }

    // Mapeo de Entidad -> DTO de Respuesta
    private EstacionResponseDTO mapToEstacionResponseDTO(Estacion estacion) {
        EstacionResponseDTO responseDTO = new EstacionResponseDTO();
        responseDTO.setId(estacion.getId());
        responseDTO.setLatitud(estacion.getLatitud());
        responseDTO.setLongitud(estacion.getLongitud());
        return responseDTO;
    }
}
