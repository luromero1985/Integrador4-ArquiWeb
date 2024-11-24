package org.example.microusuario.testUnitario;

import org.example.microusuario.DTO.ReporteMonopatinesCercanosDTO;
import org.example.microusuario.DTO.UsuarioRequestDTO;
import org.example.microusuario.DTO.UsuarioResponseDTO;
import org.example.microusuario.controllers.UsuarioController;
import org.example.microusuario.services.UsuarioServicio;
import org.example.microusuario.services.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioServicio usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Lista de todos los Usuarios")
    void testFindAll() {
        // Configuramos el mock
        UsuarioResponseDTO usuario1 = new UsuarioResponseDTO(1L, "Carlos", "Gomez", "carlos.gomez@example.com", "123456789", 785340.4, 333111.20);
        UsuarioResponseDTO usuario2 = new UsuarioResponseDTO(2L, "Ana", "Lopez", "ana.lopez@example.com", "987654321", 329856.0, 220343.12);
        List<UsuarioResponseDTO> usuariosMock = Arrays.asList(usuario1, usuario2);
// Definimos qué debe devolver el mock cuando se llama al método
        when(usuarioService.findAll()).thenReturn(usuariosMock);

        // Llamamos al método del controlador
        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.findAll();

        // Verificarmos el resultado
        assertEquals(200, response.getStatusCodeValue());

        assertEquals(2, response.getBody().size());
        assertEquals("Carlos", response.getBody().get(0).getNombre());

        // Verificamos que se llamó al servicio
        verify(usuarioService, times(1)).findAll();
    }


    @Test
    @DisplayName("Busqueda de un usuario por id, prueba exitosa")
    void testFindById_Success() {
        // Configuramos el mock
        UsuarioResponseDTO usuarioMock = new UsuarioResponseDTO(1L, "Carlos", "Gomez", "carlos.gomez@example.com", "123456789", 785340.4, 333111.20);
        when(usuarioService.findById(1L)).thenReturn(usuarioMock);

        // Llamamos al método del controlador
        ResponseEntity<UsuarioResponseDTO> response = usuarioController.findById(1L);

        // Verificamos el resultado
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Carlos", response.getBody().getNombre());

        // Verificar que se llamó al servicio
        verify(usuarioService, times(1)).findById(1L);
    }

//    @Test
//    @DisplayName("Busqueda de un usuario por id, prueba de fallo")
//    void testFindById_NotFound() {
//        // Configuramos el mock
//        when(usuarioService.findById(1L)).thenThrow(new RuntimeException("Usuario no encontrado"));
//
//        // Llamamos al método del controlador
//        ResponseEntity<UsuarioResponseDTO> response = usuarioController.findById(1L);
//
//        // Verificamos el resultado
//        assertEquals(404, response.getStatusCodeValue());
//
//        // Verificamos que se llamó al servicio
//        verify(usuarioService, times(1)).findById(1L);
//    }

    @Test
    @DisplayName("Guardar un usuario")
    void testSave() {
        UsuarioRequestDTO usuarioRequest = new UsuarioRequestDTO(1L, "Carlos", "Gomez", "carlos.gomez@example.com", "123456789", 785340.4, 333111.20);
        UsuarioResponseDTO usuarioResponse = new UsuarioResponseDTO(1L, "Carlos", "Gomez", "carlos.gomez@example.com", "123456789", 785340.4, 333111.20);

        when(usuarioService.save(usuarioRequest)).thenReturn(usuarioResponse);

        // Llamamos al método del controlador
        ResponseEntity<UsuarioResponseDTO> response = usuarioController.save(usuarioRequest);

        // Verificamos el resultado
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Carlos", response.getBody().getNombre());

        // Verificamos que se llamó al servicio
        verify(usuarioService, times(1)).save(usuarioRequest);
    }

    @Test
    @DisplayName("Borrar un usuario, prueba exitosa")
    void testDelete_Success() {
        // Configurar el mock
        doNothing().when(usuarioService).delete(1L);
        ResponseEntity<Void> response = usuarioController.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).delete(1L);
    }

//    @Test
//    @DisplayName("Borrar un usuario, prueba fallida")
//    void testDelete_NotFound() {
//        doThrow(new RuntimeException("Usuario no encontrado")).when(usuarioService).delete(1L);
//        ResponseEntity<Void> response = usuarioController.delete(1L);
//        assertEquals(404, response.getStatusCodeValue());
//        verify(usuarioService, times(1)).delete(1L);
//    }

    @Test
    @DisplayName("reporte")
    void testGenerarReporteDeMonopatinesCercanos() {
        ReporteMonopatinesCercanosDTO monopatin1 = new ReporteMonopatinesCercanosDTO(1L, 785340.4, 333111.20);
        ReporteMonopatinesCercanosDTO monopatin2 = new ReporteMonopatinesCercanosDTO(2L, 785345.6, 333116.80);
        List<ReporteMonopatinesCercanosDTO> reportMock = Arrays.asList(monopatin1, monopatin2);

        when(usuarioService.generarReporteD(785340.4, 333111.20, 500.0)).thenReturn(reportMock);
        ResponseEntity<List<ReporteMonopatinesCercanosDTO>> response =
                usuarioController.generarReporteDeMonopatinesCercanos(785340.4, 333111.20, 500.0);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());

        verify(usuarioService, times(1)).generarReporteD(785340.4, 333111.20, 500.0);
    }


    @Test
    @DisplayName("Editar usuario, prueba exitosa")
    void testUpdate_Success() {
        UsuarioRequestDTO request = new UsuarioRequestDTO(1L, "Carlos", "Perez", "carlos.perez@example.com", "987654321", 785340.4, 333111.20);
        UsuarioResponseDTO mockResponse = new UsuarioResponseDTO(1L, "Carlos", "Perez", "carlos.perez@example.com", "987654321", 785340.4, 333111.20);

        when(usuarioService.update(1L, request)).thenReturn(mockResponse);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.update(1L, request);

        assertNotNull(response);
        assertEquals("Perez", response.getBody().getApellido());
        verify(usuarioService, times(1)).update(1L, request);
    }

    @Test
    @DisplayName("Editar un usuario, prueba exitosa")
    void testUpdate_NotFound() {
        UsuarioRequestDTO request = new UsuarioRequestDTO(1L, "Carlos", "Perez", "carlos.perez@example.com", "987654321", 785340.4, 333111.20);
        when(usuarioService.update(1L, request)).thenThrow(NotFoundException.class);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.update(1L, request);

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioService, times(1)).update(1L, request);
    }
}

