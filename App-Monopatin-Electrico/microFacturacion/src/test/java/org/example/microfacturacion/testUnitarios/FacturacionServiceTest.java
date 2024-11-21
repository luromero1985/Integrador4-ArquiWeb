package org.example.microfacturacion.testUnitarios;

import org.example.microfacturacion.DTO.FacturacionRequestDTO;
import org.example.microfacturacion.DTO.FacturacionResponseDTO;
import org.example.microfacturacion.entities.Facturacion;
import org.example.microfacturacion.repositories.FacturacionRepository;
import org.example.microfacturacion.services.FacturacionService;
import org.example.microfacturacion.services.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturacionServiceTest {

    @Mock
    private FacturacionRepository facturacionRepository;

    @InjectMocks
    private FacturacionService facturacionService;

    FacturacionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Facturacion facturacion = new Facturacion(id, 10L, 20L, 100.0F, null);
        when(facturacionRepository.findById(id)).thenReturn(Optional.of(facturacion));

        FacturacionResponseDTO result = facturacionService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(facturacionRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(facturacionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> facturacionService.findById(id));
        verify(facturacionRepository, times(1)).findById(id);
    }

    @Test
    void testSave_NewFacturacion() {
        FacturacionRequestDTO request = new FacturacionRequestDTO();
        request.setId(1L);
        request.setLatitud(10L);
        request.setLongitud(20L);
        request.setPrecioFinal(100.0F);

        Facturacion facturacion = new Facturacion();
        facturacion.setId(request.getId());
        facturacion.setLatitud(request.getLatitud());
        facturacion.setLongitud(request.getLongitud());
        facturacion.setPrecioFinal(request.getPrecioFinal());

        when(facturacionRepository.findById(request.getId())).thenReturn(Optional.empty());
        when(facturacionRepository.save(any(Facturacion.class))).thenReturn(facturacion);

        FacturacionResponseDTO result = facturacionService.save(request);

        assertNotNull(result);
        assertEquals(request.getId(), result.getId());
        verify(facturacionRepository, times(1)).save(any(Facturacion.class));
    }
}