package org.example.gateway.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.gateway.entity.User;
import org.example.gateway.repository.AuthorityRepository;
import org.example.gateway.repository.UserRepository;
import org.example.gateway.service.dto.user.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
/*
* Encriptar la contraseña al crear un usuario.
*  Al guardar un nuevo usuario en la base de datos, hay que usar el encoder:
* */
    public long saveUser( UserDTO request ) {
        final var user = new User( request.getUsername() );
        user.setPassword( passwordEncoder.encode( request.getPassword() ) );
        final var roles =  this.authorityRepository.findAllById( request.getAuthorities() );
        user.setAuthorities( roles );
        final var result = this.userRepository.save( user );
        return result.getId();
    }
}

