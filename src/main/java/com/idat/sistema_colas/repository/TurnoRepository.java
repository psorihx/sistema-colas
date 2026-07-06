package com.idat.sistema_colas.repository;

import com.idat.sistema_colas.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    @Query("SELECT t FROM Turno t WHERE DATE(t.fechaRegistro) = CURRENT_DATE ORDER BY t.idTurno DESC LIMIT 1")
    Optional<Turno> findLastTurnoOfToday();

    Optional<Turno> findByNumeroTurno(String numeroTurno);
}
