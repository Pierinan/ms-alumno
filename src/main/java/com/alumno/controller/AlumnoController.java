package com.alumno.controller;

import com.alumno.business.AlumnoService;
import com.alumno.controller.schema.Alumno;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registrarAlumno(@RequestBody Alumno alumno) {
        return alumnoService.registrarAlumno(alumno);
    }

    @GetMapping("/activos")
    public Flux<Alumno> obtenerActivos() {
        return alumnoService.getAlumnosActivos();
    }
}
