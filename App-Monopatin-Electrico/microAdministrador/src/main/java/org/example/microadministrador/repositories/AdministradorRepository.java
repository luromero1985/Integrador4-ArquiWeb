package org.example.microadministrador.repositories;

import org.example.microadministrador.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

        @Query("SELECT a.precio FROM Administrador a " +
                "WHERE a.fecha < :fecha " +
                "ORDER BY a.fecha DESC")
        List<Float> findPrecioComunByFechaAnterior(@Param("fecha") LocalDateTime fecha);


        @Query("SELECT a.precioEspecial FROM Administrador a " +
                "WHERE a.fecha < :fecha " +
                "ORDER BY a.fecha DESC")
        List<Float> findPrecioEspecialByFechaAnterior(@Param("fecha") LocalDateTime fecha);


        @Query("SELECT a.topeKm FROM Administrador a")
        int getTopeKm();
}