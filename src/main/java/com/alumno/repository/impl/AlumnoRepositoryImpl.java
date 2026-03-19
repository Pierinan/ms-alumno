package com.alumno.repository.impl;

import com.alumno.controller.schema.Alumno;
import com.alumno.repository.AlumnoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AlumnoRepositoryImpl implements AlumnoRepository {

    private final Map<String, Alumno> alumnos = new ConcurrentHashMap<>();


    @Override
    public Mono<Boolean> existsById(String id) {
        return Mono.defer(() -> Mono.just(alumnos.containsKey(id)));
    }

    @Override
    public Flux<Alumno> findActivos() {
        return Flux.fromIterable(alumnos.values())
                .filter(alumno -> "activo".equalsIgnoreCase(alumno.getEstado()));
    }

    @Override
    public Mono<Void> save(Alumno alumno) {
        return Mono.fromRunnable(() -> alumnos.put(alumno.getIdAlumno(), alumno))
                .then();
    }
}
