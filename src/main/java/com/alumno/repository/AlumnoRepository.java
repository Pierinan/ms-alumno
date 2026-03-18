package com.alumno.repository;

import com.alumno.controller.schema.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepository {

    Mono<Boolean> existsById(String id);

    Flux<Alumno> findActivos();

    Mono<Void> save(Alumno alumno);

}
