package org.example.microviaje.services;

import feign.Client;
import jakarta.transaction.Transactional;

import org.example.microviaje.DTO.ReporteMonopatinPorCantViajesPorAnioDTO;
import org.example.microviaje.DTO.ViajeRequestDTO;
import org.example.microviaje.DTO.ViajeResponseDTO;
import org.example.microviaje.entities.Viaje;
import org.example.microviaje.feignClients.AdministradorFeignClient;
import org.example.microviaje.repositories.ViajeRepository;
import org.example.microviaje.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private AdministradorFeignClient administradorfeignClient;


    public List<ViajeResponseDTO> findAll() {
        List<Viaje> viajes = viajeRepository.findAll();
        return viajes.stream().map(this::mapToViajeResponseDTO).collect(Collectors.toList());
    }

    public ViajeResponseDTO findById(Long id) {
        Viaje viaje = viajeRepository.findById(id).orElse(null);
        return viaje != null ? mapToViajeResponseDTO(viaje) : new ViajeResponseDTO();
    }

    @Transactional
    public ViajeResponseDTO save(ViajeRequestDTO viajeRequestDTO) {
        ViajeResponseDTO viajeResponseDTO = new ViajeResponseDTO();

        Viaje viaje = viajeRepository.findById(viajeRequestDTO.getId()).orElse(null);
        if (viaje == null) {
            Viaje viajeNuevo = new Viaje();
            viajeNuevo.setInicio(viajeRequestDTO.getInicio());
            viajeNuevo.setFin(viajeRequestDTO.getFin());
            viajeNuevo.setId_usuario(viajeRequestDTO.getId_usuario());
            viajeNuevo.setId_monopatin(viajeRequestDTO.getId_monopatin());
            viajeNuevo.setPrecioTotal(0F);
            viajeNuevo.setIncioEnPausa(viajeRequestDTO.getIncioEnPausa());
            viajeNuevo.setFinEnPausa(viajeRequestDTO.getFinEnPausa());
            viajeNuevo.setLatitudFin(viajeRequestDTO.getLatitudFin());
            viajeNuevo.setLongitudFin(viajeRequestDTO.getLongitudFin());
            viajeNuevo.setLatitudInicio(viajeRequestDTO.getLatitudInicio());
            viajeNuevo.setLongitudInicio(viajeRequestDTO.getLongitudInicio());


            Viaje viajeGuardado = viajeRepository.save(viajeNuevo);
            viajeResponseDTO= mapToViajeResponseDTO(viajeGuardado);
        } else {
            System.out.println("El viaje con ese id ya existe");
        }
        return viajeResponseDTO;
    }

    public ViajeResponseDTO update(Long id, ViajeRequestDTO viajeRequestDTO) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El viaje con ID " + id + " no fue encontrado")
        );

        if (viajeRequestDTO.getInicio() != null) {
            viaje.setInicio(viajeRequestDTO.getInicio());
        }
        if (viajeRequestDTO.getFin() != null) {
            viaje.setFin(viajeRequestDTO.getFin());
        }
        if (viajeRequestDTO.getId_usuario() != null) {
            viaje.setId_usuario(viajeRequestDTO.getId_usuario());
        }
        if (viajeRequestDTO.getId_monopatin() != null) {
            viaje.setId_monopatin(viajeRequestDTO.getId_monopatin());
        }
        if (viajeRequestDTO.getPrecioTotal() != null) {
            viaje.setPrecioTotal(viajeRequestDTO.getPrecioTotal());
        }

        if (viajeRequestDTO.getIncioEnPausa() != null) {
            viaje.setIncioEnPausa(viajeRequestDTO.getIncioEnPausa());
        }
        if (viajeRequestDTO.getFinEnPausa() != null) {
            viaje.setFinEnPausa(viajeRequestDTO.getFinEnPausa());
        }
        if (viajeRequestDTO.getLatitudFin() != null) {
            viaje.setLatitudFin(viajeRequestDTO.getLatitudFin());
        }
        if (viajeRequestDTO.getLongitudFin() != null) {
            viaje.setLongitudFin(viajeRequestDTO.getLongitudFin());
        }
        if (viajeRequestDTO.getLatitudInicio() != null) {
            viaje.setLatitudInicio(viajeRequestDTO.getLatitudInicio());
        }
        if (viajeRequestDTO.getLongitudInicio() != null) {
            viaje.setLongitudInicio(viajeRequestDTO.getLongitudInicio());
        }


        Viaje updatedViaje = viajeRepository.save(viaje);
        return mapToViajeResponseDTO(updatedViaje);
    }

    public void delete(Long id) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El viaje con ID " + id + " no fue encontrado")
        );
        viajeRepository.delete(viaje);
    }

    /*
        public Double findPrecioById(Long id) {
            Viaje viaje = viajeRepository.findById(id).orElse(null);
            return viaje.getPrecioTotal();
        }


    */

    public ViajeResponseDTO updatePrecio(Long id) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("El viaje con ID " + id + " no fue encontrado")
        );

        //TIEMPO DE VIAJE
        Float minutosViaje = 0.F;
        LocalDateTime inicioDeViaje = viaje.getInicio();
        LocalDateTime finDeViaje = viaje.getFin();
        if(inicioDeViaje!=null && finDeViaje!=null) {
            Duration duracionViaje = Duration.between(inicioDeViaje, finDeViaje);
            minutosViaje =Float.valueOf(duracionViaje.toMinutes());
        }



        //TIEMPO EN PAUSA
        LocalDateTime tiempoEnPausaInicio=viaje.getIncioEnPausa();
        LocalDateTime tiempoEnpausaFin = viaje.getFinEnPausa();

        Float minutosPausa = 0.0f;
        if(tiempoEnPausaInicio!=null && tiempoEnpausaFin!=null) {
            Duration duracionPausa = Duration.between(tiempoEnPausaInicio, tiempoEnpausaFin);
            minutosPausa = Float.valueOf(duracionPausa.toMinutes());
        }



        Float tarifa = 0.0F;

        try {
            System.out.println(finDeViaje);
            String fechaFormateada = finDeViaje.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            if (minutosPausa >= 15) {


                 tarifa = administradorfeignClient.getTarifaEspecial(fechaFormateada);
                System.out.println(tarifa);

            } else {
                Float aux = administradorfeignClient.getTarifaComun(fechaFormateada);
                System.out.println(aux);
                tarifa = aux;
            }

            // Asegúrate de que no quede en null
            if (tarifa == null) {
                tarifa = 0.0F; // Valor por defecto
            }

        } catch (Exception e) {
            System.err.println("Error al obtener la tarifa: " + e.getMessage());
            tarifa = 0.0F; // Asignar un valor por defecto en caso de error
        }

        System.out.println("Tarifa final: " + tarifa);

        float precioActualizado = minutosViaje * tarifa;
        viaje.setPrecioTotal(precioActualizado);
        viajeRepository.save(viaje);
        return this.mapToViajeResponseDTO(viaje);

    }


    private Float calcularDistanciaKilometros(Long lat1, Long lon1, Long lat2, Long lon2) {

       /*
       *   //DISTANCIA RECORRIDA
        Float distanciaRecorrida = calcularDistanciaKilometros(
                viaje.getLatitudInicio(), viaje.getLongitudInicio(),
                viaje.getLatitudFin(), viaje.getLongitudFin()
        );
       *

       * */






        final int RADIO_TIERRA = 6371;

        double lat1Double = lat1.doubleValue();
        double lon1Double = lon1.doubleValue();
        double lat2Double = lat2.doubleValue();
        double lon2Double = lon2.doubleValue();

        double latDistancia = Math.toRadians(lat2Double - lat1Double);
        double lonDistancia = Math.toRadians(lon2Double - lon1Double);
        double a = Math.sin(latDistancia / 2) * Math.sin(latDistancia / 2)
                + Math.cos(Math.toRadians(lat1Double)) * Math.cos(Math.toRadians(lat2Double))
                * Math.sin(lonDistancia / 2) * Math.sin(lonDistancia / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        return (float) (RADIO_TIERRA * c);
    }


    private ViajeResponseDTO mapToViajeResponseDTO(Viaje viaje) {
        ViajeResponseDTO responseDTO = new ViajeResponseDTO();
        responseDTO.setId(viaje.getId());
        responseDTO.setInicio(viaje.getInicio());
        responseDTO.setFin(viaje.getFin());
        responseDTO.setId_usuario(viaje.getId_usuario());
        responseDTO.setId_monopatin(viaje.getId_monopatin());
        responseDTO.setPrecioTotal(viaje.getPrecioTotal());
        responseDTO.setIncioEnPausa(viaje.getIncioEnPausa());
        responseDTO.setFinEnPausa(viaje.getFinEnPausa());
        responseDTO.setLatitudFin(viaje.getLatitudFin());
        responseDTO.setLongitudFin(viaje.getLongitudFin());
        responseDTO.setLatitudInicio(viaje.getLatitudInicio());
        responseDTO.setLongitudInicio(viaje.getLongitudInicio());

        return responseDTO;
    }


    public List<ReporteMonopatinPorCantViajesPorAnioDTO> getMonopatinByCantViajeYAnio(int cantViajes, int anio) {
        // Crear el inicio y el fin del año
        LocalDateTime inicioAnio = LocalDateTime.of(anio, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime finAnio = LocalDateTime.of(anio + 1, Month.JANUARY, 1, 0, 0, 0, 0);

        // Obtener la lista de resultados
        List<Object[]> id_monopatines = viajeRepository.getMonopatinByCantViajeYAnio(cantViajes, inicioAnio, finAnio);

        // Mapear los resultados a DTOs
        return id_monopatines.stream().map(id_monopatin -> {
            // id_monopatin[0] es el id_monopatin y id_monopatin[1] es el conteo de viajes
            Long idMonopatin = (Long) id_monopatin[0];
            Long cantViajesRealizados = (Long) id_monopatin[1];

            // Devolver el DTO con los valores
            return new ReporteMonopatinPorCantViajesPorAnioDTO(idMonopatin, cantViajesRealizados, anio);
        }).collect(Collectors.toList());
    }
}

