package org.example.micromonopatin.TestUnitario;

import org.example.micromonopatin.DTO.MonopatinRequestDTO;
import org.example.micromonopatin.DTO.MonopatinResponseDTO;
import org.example.micromonopatin.controllers.MonopatinController;
import org.example.micromonopatin.services.MonopatinService;
import org.example.micromonopatin.services.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MonopatinControllerTest {

    @Mock
    private MonopatinService monopatinService;

    @InjectMocks
    private MonopatinController monopatinController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_Success() {
        List<MonopatinResponseDTO> mockResponse = List.of(
                new MonopatinResponseDTO(1L, 100, 34.56, -58.45, false, 1500, 50, 10)
        );
        when(monopatinService.findAll()).thenReturn(mockResponse);

        ResponseEntity<List<MonopatinResponseDTO>> response = monopatinController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(monopatinService, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        MonopatinResponseDTO mockResponse = new MonopatinResponseDTO(1L, 100, 34.56, -58.45, false, 1500, 50, 10);
        when(monopatinService.findById(1L)).thenReturn(mockResponse);

        ResponseEntity<MonopatinResponseDTO> response = monopatinController.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(monopatinService, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(monopatinService.findById(1L)).thenThrow(new NotFoundException("Not found"));

        ResponseEntity<MonopatinResponseDTO> response = monopatinController.findById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(monopatinService, times(1)).findById(1L);
    }

    @Test
    void testSave_Success() {
        MonopatinRequestDTO mockRequest = new MonopatinRequestDTO(1L, 100, 34.56, -58.45, false, 1500, 50, 10);
        MonopatinResponseDTO mockResponse = new MonopatinResponseDTO(1L, 100, 34.56, -58.45, false, 1500, 50, 10);

        when(monopatinService.save(mockRequest)).thenReturn(mockResponse);

        ResponseEntity<MonopatinResponseDTO> response = monopatinController.save(mockRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(monopatinService, times(1)).save(mockRequest);
    }

    @Test
    void testUpdate_Success() {
        MonopatinRequestDTO mockRequest = new MonopatinRequestDTO(1L, 120, 34.60, -58.50, true, 1400, 60, 15);
        MonopatinResponseDTO mockResponse = new MonopatinResponseDTO(1L, 120, 34.60, -58.50, true, 1400, 60, 15);

        when(monopatinService.update(1L, mockRequest)).thenReturn(mockResponse);

        ResponseEntity<MonopatinResponseDTO> response = monopatinController.update(1L, mockRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(monopatinService, times(1)).update(1L, mockRequest);
    }

    @Test
    void testUpdate_NotFound() {
        MonopatinRequestDTO mockRequest = new MonopatinRequestDTO(1L, 120, 34.60, -58.50, true, 1400, 60, 15);

        when(monopatinService.update(1L, mockRequest)).thenThrow(new NotFoundException("Not found"));

        ResponseEntity<MonopatinResponseDTO> response = monopatinController.update(1L, mockRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(monopatinService, times(1)).update(1L, mockRequest);
    }

    @Test
    void testDelete_Success() {
        doNothing().when(monopatinService).delete(1L);

        ResponseEntity<Void> response = monopatinController.delete(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(monopatinService, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        doThrow(new NotFoundException("Not found")).when(monopatinService).delete(1L);

        ResponseEntity<Void> response = monopatinController.delete(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(monopatinService, times(1)).delete(1L);
    }
}
