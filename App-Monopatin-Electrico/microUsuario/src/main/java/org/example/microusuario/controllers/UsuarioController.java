package org.example.microusuario.controllers;


import org.example.microcuenta.DTO.CuentaResponseDTO;
import org.example.microusuario.DTO.ReporteMonopatinesCercanosDTO;
import org.example.microusuario.DTO.UsuarioRequestDTO;
import org.example.microusuario.DTO.UsuarioResponseDTO;
import org.example.microusuario.services.UsuarioServicio;
import org.example.microusuario.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController{
    @Autowired
    private UsuarioServicio usuarioService;


    @GetMapping("")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(this.usuarioService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id){
      try {
          return ResponseEntity.ok(this.usuarioService.findById(id));
      }
      catch(NotFoundException e){
          return ResponseEntity.notFound().build();
      }
    }


    @PostMapping("")
    public ResponseEntity<UsuarioResponseDTO> save(@RequestBody UsuarioRequestDTO usuario){
            UsuarioResponseDTO newUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(newUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        try{
            return ResponseEntity.ok( this.usuarioService.update(id, usuarioRequestDTO));
        }
        catch( NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            this.usuarioService.findById(id);
            this.usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // g) Como usuario quiero lun listado de los monopatines cercanos a mi zona, para poder encontrar
    // un monopatín cerca de mi ubicación

    @GetMapping("/reporteG/latitud/{latitud}/longitud/{longitud}/rango/{rango}")
    public ResponseEntity<List<ReporteMonopatinesCercanosDTO>> generarReporteDeMonopatinesCercanos(
            @PathVariable double latitud, @PathVariable double longitud, @PathVariable double rango
    ) {
        try {
            return ResponseEntity.ok(usuarioService.generarReporteD(latitud,longitud,rango));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

