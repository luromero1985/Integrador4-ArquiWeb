package org.example.microviaje.feignClients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor localDateTimeInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Verificar si la URL contiene la variable fecha
                if (template.url().contains("{fecha}")) {
                    // Extraer la fecha y formatearla
                    String originalDate = template.url().split("/")[template.url().split("/").length - 1];
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                    LocalDateTime dateTime = LocalDateTime.parse(originalDate, formatter);
                    String formattedDate = dateTime.format(formatter);

                    // Reemplazar la fecha original en la URL con la fecha formateada
                    template.uri(template.url().replace(originalDate, formattedDate));
                }
            }
        };
    }
}
