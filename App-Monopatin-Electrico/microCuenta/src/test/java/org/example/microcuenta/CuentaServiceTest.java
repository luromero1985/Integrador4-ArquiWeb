package org.example.microcuenta;

import org.example.microcuenta.DTO.CuentaRequestDTO;
import org.example.microcuenta.DTO.CuentaResponseDTO;
import org.example.microcuenta.entities.Cuenta;
import org.example.microcuenta.repositories.CuentaRepository;
import org.example.microcuenta.services.CuentaService;
import org.example.microcuenta.services.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

    @InjectMocks
    private CuentaService cuentaService;

    @Mock
    private CuentaRepository cuentaRepository;

    public CuentaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        Cuenta cuenta = new Cuenta(1L, 10L, LocalDate.now(), 100.0f, true);
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        // Act
        CuentaResponseDTO response = cuentaService.findById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getId_usuario());
        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> cuentaService.findById(1L));
    }

    @Test
    void testSaveNewCuenta() {
        // Arrange
        CuentaRequestDTO request = new CuentaRequestDTO(1L, 10L, LocalDate.now(), 200.0f, true);
        Cuenta cuentaToSave = new Cuenta(null, 10L, request.getFechaAlta(), request.getSaldo(), request.isActiva());
        Cuenta savedCuenta = new Cuenta(1L, 10L, request.getFechaAlta(), request.getSaldo(), request.isActiva());
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(savedCuenta);

        // Act
        CuentaResponseDTO response = cuentaService.save(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }
}