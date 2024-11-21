package org.example.microestacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.example.microestacion.repositories")
public class MicroEstacionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroEstacionApplication.class, args);
    }
}