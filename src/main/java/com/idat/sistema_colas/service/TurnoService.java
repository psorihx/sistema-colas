package com.idat.sistema_colas.service;

import com.idat.sistema_colas.entity.Turno;
import com.idat.sistema_colas.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public Turno createTurno(String nombreCliente, String dni, Integer idTipo) {
        String numeroTurno = generateNumeroTurno();
        Turno turno = new Turno(numeroTurno, nombreCliente, dni, idTipo);
        return turnoRepository.save(turno);
    }

    private String generateNumeroTurno() {
        Turno lastTurno = turnoRepository.findLastTurnoOfToday().orElse(null);
        int nextNumber = 1;
        if (lastTurno != null) {
            String lastNum = lastTurno.getNumeroTurno();
            nextNumber = Integer.parseInt(lastNum.substring(1)) + 1;
        }
        return String.format("T%04d", nextNumber);
    }

    public Turno findByNumeroTurno(String numeroTurno) {
        return turnoRepository.findByNumeroTurno(numeroTurno).orElse(null);
    }

    public Turno confirmarAsistencia(String numeroTurno) {
        Turno turno = findByNumeroTurno(numeroTurno);
        if (turno != null) {
            turno.setEstado(Turno.EstadoTurno.EN_ATENCION);
            return turnoRepository.save(turno);
        }
        return null;
    }
}
