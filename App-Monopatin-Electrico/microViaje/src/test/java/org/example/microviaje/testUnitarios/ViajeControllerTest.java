package org.example.microviaje.testUnitarios;

import org.example.microviaje.DTO.ViajeRequestDTO;
import org.example.microviaje.DTO.ViajeResponseDTO;
import org.example.microviaje.controllers.ViajeController;
import org.example.microviaje.services.ViajeService;
import org.example.microviaje.services.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ViajeControllerTest {

    @Mock
    private ViajeService viajeService;

    @InjectMocks
    private ViajeController viajeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testFindAll_ReturnsListOfViajes() {
        ViajeResponseDTO mockViaje = new ViajeResponseDTO();
        mockViaje.setId(1L);
        mockViaje.setInicio(LocalDateTime.now());
        mockViaje.setFin(LocalDateTime.now().plusMinutes(10));

        Mockito.when(viajeService.findAll()).thenReturn(List.of(mockViaje));

        List<ViajeResponseDTO> result = viajeController.findAll().getBody(); // Devuelve el cuerpo del ResponseEntity

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testFindById_ReturnsViaje() {
        ViajeResponseDTO mockViaje = new ViajeResponseDTO();
        mockViaje.setId(1L);
        mockViaje.setInicio(LocalDateTime.now());

        Mockito.when(viajeService.findById(1L)).thenReturn(mockViaje);

        ViajeResponseDTO result = viajeController.findById(1L).getBody();

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindById_ThrowsNotFoundException() {
        Mockito.when(viajeService.findById(1L)).thenThrow(new NotFoundException("Viaje no encontrado"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> viajeController.findById(1L));

        assertEquals("Viaje no encontrado", exception.getMessage());
    }

    @Test
    public void testSave_ReturnsSavedViaje() {
        ViajeRequestDTO request = new ViajeRequestDTO();
        request.setId_usuario(2L);
        request.setId_monopatin(3L);

        ViajeResponseDTO response = new ViajeResponseDTO();
        response.setId(1L);

        Mockito.when(viajeService.save(any(ViajeRequestDTO.class))).thenReturn(response);

        ViajeResponseDTO result = viajeController.save(request).getBody();

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testUpdate_ReturnsUpdatedViaje() {
        ViajeRequestDTO request = new ViajeRequestDTO();
        request.setId_usuario(2L);

        ViajeResponseDTO response = new ViajeResponseDTO();
        response.setId(1L);

        Mockito.when(viajeService.update(eq(1L), any(ViajeRequestDTO.class))).thenReturn(response);

        ViajeResponseDTO result = viajeController.update(1L, request).getBody();

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testDelete_NoExceptionThrown() {
        Mockito.doNothing().when(viajeService).delete(1L);

        assertDoesNotThrow(() -> viajeController.delete(1L));
    }

    @Test
    public void testDelete_ThrowsNotFoundException() {
        Mockito.doThrow(new NotFoundException("Viaje no encontrado")).when(viajeService).delete(1L);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> viajeController.delete(1L));

        assertEquals("Viaje no encontrado", exception.getMessage());
    }
}
