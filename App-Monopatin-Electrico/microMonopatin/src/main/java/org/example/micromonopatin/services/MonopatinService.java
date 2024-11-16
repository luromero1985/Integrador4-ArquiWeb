package org.example.micromonopatin.services;

import jakarta.transaction.Transactional;
import org.example.micromonopatin.DTO.*;
import org.example.micromonopatin.entities.Monopatin;
import org.example.micromonopatin.repositories.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonopatinService{

    @Autowired
    private MonopatinRepository monopatinRepository;

    public List<MonopatinResponseDTO> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return monopatines.stream().map(this::mapToMonopatinResponseDTO).collect(Collectors.toList());
    }

    public MonopatinResponseDTO findById(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
        return monopatin != null ? mapToMonopatinResponseDTO(monopatin) : new MonopatinResponseDTO();
    }

    @Transactional
    public MonopatinResponseDTO save(MonopatinRequestDTO monopatinRequestDTO) {
        MonopatinResponseDTO monopatinResponseDTO = new MonopatinResponseDTO();

        Monopatin monopatin =monopatinRepository.findById(monopatinRequestDTO.getId()).orElse(null);
        if(monopatin==null) {
            Monopatin monopatinNuevo = new Monopatin();
            monopatinNuevo.setId(monopatinRequestDTO.getId());
            monopatinNuevo.setKmTotales(monopatinRequestDTO.getKmTotales());
            monopatinNuevo.setLatitud(monopatinRequestDTO.getLatitud());
            monopatinNuevo.setLongitud(monopatinRequestDTO.getLongitud());
            monopatinNuevo.setEnMantenimiento(monopatinRequestDTO.isEnMantenimiento());
            monopatinNuevo.setKmParaMantenimiento(monopatinRequestDTO.getKmParaMantenimiento());
            monopatinNuevo.setTiempoDeUso(monopatinRequestDTO.getTiempoDeUso());
            monopatinNuevo.setTiempoEnPausa(monopatinRequestDTO.getTiempoEnPausa());

            Monopatin monopatinRta = monopatinRepository.save(monopatinNuevo);
            monopatinResponseDTO = mapToMonopatinResponseDTO(monopatinRta);
        } else{
            System.out.println("El monopatin con ese id ya existe");
        }

        return monopatinResponseDTO;
    }

    public MonopatinResponseDTO update(Long id, MonopatinRequestDTO monopatinRequestDTO) {
        Monopatin monopatin = monopatinRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El monopatín con ID " + id + " no fue encontrado")
        );

        if (monopatinRequestDTO.getKmTotales() != 0) {
            monopatin.setKmTotales(monopatinRequestDTO.getKmTotales());
        }

        monopatin.setLatitud(monopatinRequestDTO.getLatitud());
        monopatin.setLongitud(monopatinRequestDTO.getLongitud());

        if (monopatinRequestDTO.getKmParaMantenimiento() != 0) {
            monopatin.setKmParaMantenimiento(monopatinRequestDTO.getKmParaMantenimiento());
        }
        monopatin.setEnMantenimiento(monopatinRequestDTO.isEnMantenimiento());

        Monopatin updatedMonopatin = monopatinRepository.save(monopatin);
        return mapToMonopatinResponseDTO(updatedMonopatin);
    }

    public void delete(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El monopatín con ID " + id + " no fue encontrado")
        );
        monopatinRepository.delete(monopatin);
    }

    private MonopatinResponseDTO mapToMonopatinResponseDTO(Monopatin monopatin) {
        MonopatinResponseDTO responseDTO = new MonopatinResponseDTO();
        responseDTO.setId(monopatin.getId());
        responseDTO.setKmTotales(monopatin.getKmTotales());
        responseDTO.setLatitud(monopatin.getLatitud());
        responseDTO.setLongitud(monopatin.getLongitud());

        responseDTO.setEnMantenimiento(monopatin.isEnMantenimiento());
        responseDTO.setKmParaMantenimiento(monopatin.getKmParaMantenimiento());
        return responseDTO;
    }

    public List<ReporteKilometrajeDTO> obtenerReporteKilometraje() {
        List<Object[]> result = monopatinRepository.reporteKilometraje();
        return result.stream()
                .map(obj -> new ReporteKilometrajeDTO((Long) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    public List<ReporteTiempoDTO> obtenerReporteTiempoConPausas() {

        List<Object[]> result = monopatinRepository.reporteTiempoConPausas();
        return result.stream()
                .map(obj -> new ReporteTiempoDTO((Long) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    public List<ReporteTiempoDTO> obtenerReporteTiempoSinPausas() {
        List<Object[]> result = monopatinRepository.reporteTiempoSinPausas();
        return result.stream()
                .map(obj -> new ReporteTiempoDTO((Long) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    public void updateMantenimiento(Long id, boolean enMantenimiento) {
        Monopatin monopatin = monopatinRepository.findById(id).orElseThrow(() -> new RuntimeException("El monopatín con ID " + id + " no fue encontrado"));
        if(!enMantenimiento){
            monopatin.setKmParaMantenimiento(0);
        }
        monopatin.setEnMantenimiento(enMantenimiento);
        monopatinRepository.save(monopatin);
    }



    public List<ReporteMonopatinesCercanosDTO> getReporteDeMonopatinesCercanos(double latitudUsuario, double longitudUsuario,double rango) {
        List<Monopatin> monopatines = monopatinRepository.getReporteDeMonopatinesCercanos(latitudUsuario, longitudUsuario, rango);
        return monopatines.stream()
                .map(monopatin -> new ReporteMonopatinesCercanosDTO(
                        monopatin.getId(),
                        monopatin.getLatitud(),
                        monopatin.getLongitud()
                ))
                .collect(Collectors.toList());
    }

    public EstadoDeMonopatinesDTO obtenerEstadoDeMonopatines() {
        Long cantTrue = monopatinRepository.contarMonopatinesPorEstado(true);
        Long cantFalse = monopatinRepository.contarMonopatinesPorEstado(false);
        return new EstadoDeMonopatinesDTO(cantTrue,cantFalse);
    }
}