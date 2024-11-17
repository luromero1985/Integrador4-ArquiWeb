package org.example.microviaje.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "microAdministrador", url = "http://localhost:8090/administrador", configuration = FeignConfig.class)
public interface AdministradorFeignClient {
    @GetMapping("tarifaEspecialParaFecha/{fecha}")
    Float getTarifaEspecial(@PathVariable("fecha") String fecha);

    @GetMapping("tarifaComunParaFecha/{fecha}")
    Float getTarifaComun(@PathVariable("fecha") String fecha);



}
