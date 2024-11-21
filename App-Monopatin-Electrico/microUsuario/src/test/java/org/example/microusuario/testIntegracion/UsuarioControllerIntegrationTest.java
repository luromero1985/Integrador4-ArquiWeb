package org.example.microusuario.testIntegracion;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microusuario.DTO.UsuarioRequestDTO;
import org.example.microusuario.entities.Usuario;
import org.example.microusuario.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveUsuario() throws Exception {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(
                5L,
                "John",
                "Doe",
                "john.doe@example.com",
                "123456789",
                40.7128,
                -74.0060
        );

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isOk());

        Optional<Usuario> usuario = usuarioRepository.findById(5L);
        assert usuario.isPresent();
        assert usuario.get().getEmail().equals("john.doe@example.com");
    }

    @Test
    public void testFindUsuarioById() throws Exception {
        Usuario usuario = new Usuario(2L, "Jane", "Smith", "jane.smith@example.com", "987654321", 40.73061, -73.935242);
        usuarioRepository.save(usuario);

        mockMvc.perform(get("/usuario/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Jane")))
                .andExpect(jsonPath("$.apellido", is("Smith")));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        Usuario usuario = new Usuario(3L, "Alice", "Johnson", "alice.johnson@example.com", "1122334455", 34.0522, -118.2437);
        usuarioRepository.save(usuario);

        mockMvc.perform(delete("/usuario/3"))
                .andExpect(status().isNoContent());

        Optional<Usuario> deletedUsuario = usuarioRepository.findById(3L);
        assert deletedUsuario.isEmpty();
    }
}
