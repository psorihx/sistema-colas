package com.idat.sistema_colas.controller;

import com.idat.sistema_colas.entity.TipoTramite;
import com.idat.sistema_colas.entity.Turno;
import com.idat.sistema_colas.repository.TipoTramiteRepository;
import com.idat.sistema_colas.repository.TurnoRepository;
import com.idat.sistema_colas.service.TipoTramiteService;
import com.idat.sistema_colas.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TurnoController {

    @Autowired
    private TipoTramiteService tipoTramiteService;

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/seleccion-tramite")
    public String seleccionTramite(Model model) {
        List<TipoTramite> tramites = tipoTramiteService.findAll();
        model.addAttribute("tramites", tramites);
        return "seleccion-tramite";
    }

    @PostMapping("/generar-turno")
    public String generarTurno(
            @RequestParam("idTipo") Integer idTipo,
            @RequestParam("nombreCliente") String nombreCliente,
            @RequestParam("dni") String dni,
            Model model) {
        Turno turno = turnoService.createTurno(nombreCliente, dni, idTipo);
        TipoTramite tipoTramite = tipoTramiteService.findById(idTipo);
        model.addAttribute("turno", turno);
        model.addAttribute("tipoTramite", tipoTramite);
        return "ticket";
    }

    @GetMapping("/confirmar/{numeroTurno}")
    public String confirmarTurno(@PathVariable String numeroTurno, Model model) {
        Turno turno = turnoService.findByNumeroTurno(numeroTurno);
        TipoTramite tipoTramite = tipoTramiteService.findById(turno.getIdTipo());
        model.addAttribute("turno", turno);
        model.addAttribute("tipoTramite", tipoTramite);
        return "confirmar-turno";
    }

    @PostMapping("/confirmar-asistencia")
    public String confirmarAsistencia(@RequestParam("numeroTurno") String numeroTurno) {
        turnoService.confirmarAsistencia(numeroTurno);
        return "redirect:/exito";
    }

    @GetMapping("/exito")
    public String exito() {
        return "exito";
    }

    @GetMapping("/ver-turnos")
    public String verTurnos(Model model) {
        List<Turno> turnos = turnoRepository.findAll();
        Map<Integer, String> tipoTramiteMap = tipoTramiteRepository.findAll().stream()
                .collect(Collectors.toMap(TipoTramite::getId, TipoTramite::getNombre));
        model.addAttribute("turnos", turnos);
        model.addAttribute("tipoTramiteMap", tipoTramiteMap);
        return "ver-turnos";
    }

    @GetMapping("/administrar-turnos")
    public String administrarTurnos(Model model) {
        List<Turno> turnos = turnoRepository.findAll();
        Map<Integer, String> tipoTramiteMap = tipoTramiteRepository.findAll().stream()
                .collect(Collectors.toMap(TipoTramite::getId, TipoTramite::getNombre));
        model.addAttribute("turnos", turnos);
        model.addAttribute("tipoTramiteMap", tipoTramiteMap);
        return "administrar-turnos";
    }

    @PostMapping("/cambiar-estado")
    public String cambiarEstado(@RequestParam("idTurno") Integer idTurno,
                                 @RequestParam("estado") String estado) {
        Turno turno = turnoRepository.findById(idTurno).orElse(null);
        if (turno != null) {
            turno.setEstado(Turno.EstadoTurno.valueOf(estado));
            if ("EN_ATENCION".equals(estado)) {
                turno.setHoraLlamado(LocalDateTime.now());
            } else if ("FINALIZADO".equals(estado)) {
                turno.setHoraFinalizacion(LocalDateTime.now());
            }
            turnoRepository.save(turno);
        }
        return "redirect:/administrar-turnos";
    }

    @PostMapping("/actualizar-ventanilla")
    public String actualizarVentanilla(@RequestParam("idTurno") Integer idTurno,
                                       @RequestParam("ventanilla") Integer ventanilla) {
        Turno turno = turnoRepository.findById(idTurno).orElse(null);
        if (turno != null) {
            turno.setVentanilla(ventanilla);
            turnoRepository.save(turno);
        }
        return "redirect:/administrar-turnos";
    }
}
