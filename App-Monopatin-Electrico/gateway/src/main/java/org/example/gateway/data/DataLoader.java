package org.example.gateway.data;

import org.example.gateway.entity.Authority;
import org.example.gateway.repository.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    // Inyección del repositorio a través del constructor
    public DataLoader(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Cargar datos iniciales en la tabla Authority
        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority("ADMIN"));
            authorityRepository.save(new Authority("USUARIO"));
        }
    }
}
