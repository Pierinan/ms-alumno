package com.alumno.business;

import com.alumno.controller.schema.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoService {

    public Mono<Void> registrarAlumno(Alumno alumno);

    public Flux<Alumno> getActiveAlumnos();

}
