package org.example.microadministrador;


import org.example.microadministrador.DTO.*;
import org.example.microadministrador.entities.Administrador;
import org.example.microadministrador.feignClients.CuentaFeignClient;
import org.example.microadministrador.feignClients.FacturacionFeignClient;
import org.example.microadministrador.feignClients.MonopatinFeignClient;
import org.example.microadministrador.feignClients.ViajeFeignClient;
import org.example.microadministrador.repositories.AdministradorRepository;
import org.example.microadministrador.services.AdministradorService;
import org.example.microadministrador.services.exception.FechaNulaException;
import org.example.microadministrador.services.exception.NotFoundException;
import org.example.microadministrador.services.exception.TarifaNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AdministradorServiceTest {

    @InjectMocks
    private AdministradorService administradorService;

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private MonopatinFeignClient monopatinFeignClient;

    @Mock
    private CuentaFeignClient cuentaFeignClient;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_AdministradorEncontrado() {
        Long adminId = 1L;
        Administrador administrador = new Administrador(adminId, 500.0f, 600.0f, LocalDateTime.now(), 100);

        when(administradorRepository.findById(adminId)).thenReturn(Optional.of(administrador));

        AdministradorResponseDTO result = administradorService.findById(adminId);

        assertNotNull(result);
        assertEquals(adminId, result.getId());
        verify(administradorRepository).findById(adminId);
    }

    @Test
    void testFindById_NotFound() {
        Long adminId = 1L;

        when(administradorRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> administradorService.findById(adminId));
        verify(administradorRepository).findById(adminId);
    }

    @Test
    void testSave_NuevoAdministrador() {
        AdministradorRequestDTO requestDTO = new AdministradorRequestDTO(1L, 500.0f, 600.0f, LocalDateTime.now(), 100);
        Administrador administrador = new Administrador(requestDTO.getId(), 500.0f, 600.0f, requestDTO.getFecha(), 100);

        when(administradorRepository.findById(requestDTO.getId())).thenReturn(Optional.empty());
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administrador);

        AdministradorResponseDTO result = administradorService.save(requestDTO);

        assertNotNull(result);
        assertEquals(requestDTO.getId(), result.getId());
        verify(administradorRepository).findById(requestDTO.getId());
        verify(administradorRepository).save(any(Administrador.class));
    }


    @Test
    void testGetTarifaComun_FechaValida() {
        LocalDateTime fecha = LocalDateTime.now();
        float expectedTarifa = 500.0f;

        when(administradorRepository.findPrecioComunByFechaAnterior(fecha)).thenReturn(List.of(expectedTarifa));

        float result = administradorService.getTarifaComun(fecha);

        assertEquals(expectedTarifa, result);
        verify(administradorRepository).findPrecioComunByFechaAnterior(fecha);
    }

    @Test
    void testGetTarifaComun_FechaNula() {
        assertThrows(FechaNulaException.class, () -> administradorService.getTarifaComun(null));
    }

    @Test
    void testUpdateEstadoCuenta() {
        Long cuentaId = 1L;
        boolean estado = true;

        administradorService.updateEstadoCuenta(cuentaId, estado);

        verify(cuentaFeignClient).updateEstadoCuenta(cuentaId, estado);
    }

    @Test
    void testGetEstadoDeMonopatines() {
        EstadoDeMonopatinesDTO estado = new EstadoDeMonopatinesDTO(10L, 5L);

        when(monopatinFeignClient.getEstadoDeMonopatines()).thenReturn(estado);

        EstadoDeMonopatinesDTO result = administradorService.getEstadoDeMonopatines();

        assertNotNull(result);
        assertEquals(estado.getMonopatinesEnMantenimiento(), result.getMonopatinesEnMantenimiento());
        verify(monopatinFeignClient).getEstadoDeMonopatines();
    }
}
