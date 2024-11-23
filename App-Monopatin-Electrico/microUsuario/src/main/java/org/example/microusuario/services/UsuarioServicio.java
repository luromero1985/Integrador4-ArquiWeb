package org.example.microusuario.services;

import jakarta.transaction.Transactional;
import org.example.microusuario.DTO.ReporteMonopatinesCercanosDTO;
import org.example.microusuario.DTO.UsuarioRequestDTO;
import org.example.microusuario.DTO.UsuarioResponseDTO;

import org.example.microusuario.feignClients.MonopatinFeignClient;
import org.example.microusuario.repositories.UsuarioRepository;
import org.example.microusuario.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.security.ResourceServerTokenRelayAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    @Autowired
    private ResourceServerTokenRelayAutoConfiguration resourceServerTokenRelayAutoConfiguration;

    public List<UsuarioResponseDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::mapToUsuarioResponseDTO).collect(Collectors.toList());
    }

    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        if (usuario == null) {
            return usuarioResponseDTO;
        }
        usuarioResponseDTO = mapToUsuarioResponseDTO(usuario);
        return usuarioResponseDTO;
    }


    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        Usuario usuario = usuarioRepository.findById(usuarioRequestDTO.getId()).orElse(null);
        if (usuario== null) {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setNombre(usuarioRequestDTO.getNombre());
            usuarioNuevo.setApellido(usuarioRequestDTO.getApellido());
            usuarioNuevo.setEmail(usuarioRequestDTO.getEmail());
            usuarioNuevo.setTelefono(usuarioRequestDTO.getTelefono());
            usuarioNuevo.setId(usuarioRequestDTO.getId());
            usuarioNuevo.setLatitud(usuarioRequestDTO.getLatitud());
            usuarioNuevo.setLongitud(usuarioRequestDTO.getLongitud());

           Usuario usuarioGuardado= usuarioRepository.save(usuarioNuevo);

            usuarioResponseDTO = mapToUsuarioResponseDTO(usuarioGuardado);

        } else {
            System.out.println("El usuario con ese id ya existe");
        }
        return usuarioResponseDTO;
    }


    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        Usuario usuario = usuarioRepository.findById(usuarioRequestDTO.getId()).orElseThrow(
                () -> new RuntimeException("El usuario con ese ID" + id + "no fue encontrado")
        );
        if (usuario != null) {
            usuario.setId(usuarioRequestDTO.getId());
            if (usuarioRequestDTO.getNombre() != null) {
                usuario.setNombre(usuarioRequestDTO.getNombre());
            }
            if (usuarioRequestDTO.getApellido() != null) {
                usuario.setApellido(usuarioRequestDTO.getApellido());
            }
            if (usuarioRequestDTO.getEmail() != null) {
                usuario.setEmail(usuarioRequestDTO.getEmail());
            }
            if (usuarioRequestDTO.getTelefono() != null) {
                usuario.setTelefono(usuarioRequestDTO.getTelefono());
            }
            // la longitud y latitud pueden ser 0
            usuario.setLatitud(usuarioRequestDTO.getLatitud());
            usuario.setLongitud(usuarioRequestDTO.getLongitud());
            usuarioRepository.save(usuario);

            usuarioResponseDTO = mapToUsuarioResponseDTO(usuario);

        }
        return usuarioResponseDTO;
    }

    public void delete(Long id) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El usuario con ese ID" + id + "no fue encontrado")
        );

        usuarioRepository.delete(usuario);

    }

    private UsuarioResponseDTO mapToUsuarioResponseDTO(Usuario usuario) {

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNombre(usuario.getNombre());
        responseDTO.setApellido(usuario.getApellido());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setTelefono(usuario.getTelefono());
        responseDTO.setLatitud(usuario.getLatitud());
        responseDTO.setLongitud(usuario.getLongitud());
        return responseDTO;
    }


    public List<ReporteMonopatinesCercanosDTO> generarReporteD(double latitud, double longitud, double rango) {
        return monopatinFeignClient.getReporteDeMonopatinesCercanos(latitud, longitud, rango);

    }
}