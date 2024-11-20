package org.example.microfacturacion.testUnitarios;


import org.example.microfacturacion.DTO.FacturacionRequestDTO;
import org.example.microfacturacion.DTO.FacturacionResponseDTO;
import org.example.microfacturacion.DTO.ReporteFacturacionRangoDeMesesDTO;
import org.example.microfacturacion.controllers.FacturacionController;
import org.example.microfacturacion.services.FacturacionService;
import org.example.microfacturacion.services.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturacionControllerTest {

    @Mock
    private FacturacionService facturacionService;

    @InjectMocks
    private FacturacionController facturacionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Lista de todas las facturaciones")
    void testFindAll() {
        FacturacionResponseDTO factura1 = new FacturacionResponseDTO(1L, 40543212L, -38312415L, 250.75f, LocalDate.of(2023, 10, 12));
        FacturacionResponseDTO factura2 = new FacturacionResponseDTO(2L, 40353454L, -38123876L, 450.50f, LocalDate.of(2023, 11, 14));
        List<FacturacionResponseDTO> facturacionesMock = Arrays.asList(factura1, factura2);

        when(facturacionService.findAll()).thenReturn(facturacionesMock);

        ResponseEntity<List<FacturacionResponseDTO>> response = facturacionController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(40543212L, response.getBody().get(0).getLatitud());

        verify(facturacionService, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar facturación por ID - éxito")
    void testFindById_Success() {
        FacturacionResponseDTO facturaMock = new FacturacionResponseDTO(1L, 40543212L, -38312415L, 250.75f, LocalDate.of(2023, 10, 12));

        when(facturacionService.findById(1L)).thenReturn(facturaMock);

        ResponseEntity<FacturacionResponseDTO> response = facturacionController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(250.75f, response.getBody().getPrecioFinal());

        verify(facturacionService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar facturación por ID - fallo")
    void testFindById_NotFound() {
        when(facturacionService.findById(1L)).thenThrow(new NotFoundException("Facturación no encontrada"));

        ResponseEntity<FacturacionResponseDTO> response = facturacionController.findById(1L);

        assertEquals(404, response.getStatusCodeValue());

        verify(facturacionService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Guardar una facturación")
    void testSave() {
        FacturacionRequestDTO request = new FacturacionRequestDTO(1L, 40543212L, -38312415L, 250.75f, LocalDate.of(2023, 10, 12));
        FacturacionResponseDTO responseMock = new FacturacionResponseDTO(1L, 40543212L, -38312415L, 250.75f, LocalDate.of(2023, 10, 12));

        when(facturacionService.save(request)).thenReturn(responseMock);

        ResponseEntity<FacturacionResponseDTO> response = facturacionController.save(request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(250.75f, response.getBody().getPrecioFinal());

        verify(facturacionService, times(1)).save(request);
    }

    @Test
    @DisplayName("Actualizar una facturación - éxito")
    void testUpdate_Success() {
        FacturacionRequestDTO request = new FacturacionRequestDTO(1L, 40353454L, -38123876L, 450.50f, LocalDate.of(2023, 11, 14));
        FacturacionResponseDTO responseMock = new FacturacionResponseDTO(1L, 40353454L, -38123876L, 450.50f, LocalDate.of(2023, 11, 14));

        when(facturacionService.update(1L, request)).thenReturn(responseMock);

        ResponseEntity<FacturacionResponseDTO> response = facturacionController.update(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(450.50f, response.getBody().getPrecioFinal());

        verify(facturacionService, times(1)).update(1L, request);
    }

    @Test
    @DisplayName("Actualizar una facturación - fallo")
    void testUpdate_NotFound() {
        FacturacionRequestDTO request = new FacturacionRequestDTO(1L, 40353454L, -38123876L, 450.50f, LocalDate.of(2023, 11, 14));

        when(facturacionService.update(1L, request)).thenThrow(new NotFoundException("Facturación no encontrada"));

        ResponseEntity<FacturacionResponseDTO> response = facturacionController.update(1L, request);

        assertEquals(404, response.getStatusCodeValue());

        verify(facturacionService, times(1)).update(1L, request);
    }

    @Test
    @DisplayName("Eliminar una facturación - éxito")
    void testDelete_Success() {
        doNothing().when(facturacionService).delete(1L);

        ResponseEntity<Void> response = facturacionController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());

        verify(facturacionService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Eliminar una facturación - fallo")
    void testDelete_NotFound() {
        doThrow(new NotFoundException("Facturación no encontrada")).when(facturacionService).delete(1L);

        ResponseEntity<Void> response = facturacionController.delete(1L);

        assertEquals(404, response.getStatusCodeValue());

        verify(facturacionService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Generar reporte de facturación por rango de meses")
    void testGenerarReporteDeFacturacion() {
        ReporteFacturacionRangoDeMesesDTO reporteMock = new ReporteFacturacionRangoDeMesesDTO(700.25f);

        when(facturacionService.generarReporteDeFacturacion(1, 2, 2023)).thenReturn(reporteMock);

        ResponseEntity<ReporteFacturacionRangoDeMesesDTO> response = facturacionController.getFacturacionEntreMesesDeUnAnio(1, 2, 2023);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(700.25f, response.getBody().getMontoTotal());

        verify(facturacionService, times(1)).generarReporteDeFacturacion(1, 2, 2023);
    }
}

