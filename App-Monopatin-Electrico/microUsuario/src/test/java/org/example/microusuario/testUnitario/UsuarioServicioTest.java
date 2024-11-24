package org.example.microusuario.testUnitario;


import org.example.microusuario.DTO.UsuarioRequestDTO;
import org.example.microusuario.DTO.UsuarioResponseDTO;
import org.example.microusuario.entities.Usuario;
import org.example.microusuario.feignClients.MonopatinFeignClient;
import org.example.microusuario.repositories.UsuarioRepository;
import org.example.microusuario.services.UsuarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UsuarioServicioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MonopatinFeignClient monopatinFeignClient;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @BeforeEach  // Se ejecuta antes de cada test
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));  // Simula que se retorna una lista con un usuario

        // Se invoca el método a probar
        List<UsuarioResponseDTO> result = usuarioServicio.findAll();

        // Se verifican los resultados
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        verify(usuarioRepository).findAll();  // Verifica que se haya llamado al método findAll del repositorio
    }

    @Test
    void testFindById() {
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));  // Simula que el repositorio retorna el usuario

        UsuarioResponseDTO result = usuarioServicio.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getNombre());
        verify(usuarioRepository).findById(1L);  // Verifica que el repositorio haya llamado findById con el ID 1
    }

    @Test
    void testSave() {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        Usuario usuarioNuevo = new Usuario(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());  // Simula que no existe el usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioNuevo);  // Simula la guarda del usuario

        UsuarioResponseDTO result = usuarioServicio.save(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
    }



    @Test
    void testUpdate() {

        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        Usuario usuarioExistente = new Usuario(1L, "Carlos", "Lopez", "carlos@mail.com", "987654321", 41.0, -3.0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);


        UsuarioResponseDTO result = usuarioServicio.update(1L, requestDTO);


        assertNotNull(result);
        assertEquals("Juan", result.getNombre());  // Verifica que el nombre haya sido actualizado a "Juan"
        assertEquals("Perez", result.getApellido());  // Verifica que el apellido haya sido actualizado a "Perez"
        verify(usuarioRepository).findById(1L);  // Verifica que se haya llamado a findById con el ID 1
        verify(usuarioRepository).save(any(Usuario.class));  // Verifica que se haya llamado a save con cualquier objeto Usuario
    }

    @Test
    void testDelete() {
        Usuario usuarioExistente = new Usuario(1L, "Juan", "Perez", "juan@mail.com", "123456789", 40.0, -3.0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));

        usuarioServicio.delete(1L);

        verify(usuarioRepository).findById(1L);  // Verifica que se haya llamado a findById con el ID 1
        verify(usuarioRepository).delete(usuarioExistente);  // Verifica que se haya llamado a delete con el usuario encontrado
    }
}
