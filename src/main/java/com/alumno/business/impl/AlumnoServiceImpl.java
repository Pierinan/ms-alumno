package com.alumno.business.impl;

import com.alumno.business.AlumnoService;
import com.alumno.controller.schema.Alumno;
import com.alumno.repository.AlumnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Override
    public Mono<Void> registrarAlumno(Alumno alumno) {
        validateAlumnoInput(alumno);

        return alumnoRepository.existsById(alumno.getIdAlumno())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("El id del alumno ya existe, ingrese otro."));
                    }
                    return alumnoRepository.save(alumno);
                });
    }

    @Override
    public Flux<Alumno> getActiveAlumnos() {
        return alumnoRepository.findActivos();
    }

    private void validateAlumnoInput(Alumno alumno) {
            if(alumno.getIdAlumno() == null || alumno.getIdAlumno().isBlank()) {
                throw new IllegalArgumentException("El ID del alumno es obligatorio");
            }
            if (alumno.getNombre() == null || alumno.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre del alumno es obligatorio");
            }
            if (alumno.getEdad() != null){
                if(alumno.getEdad() <= 0) {
                    throw new IllegalArgumentException("La edad del alumno debe ser mayor a cero");
                }
                throw new IllegalArgumentException("La edad del alumno es un campo obligatorio");
            }
            if (alumno.getEstado() == null || alumno.getEstado().isBlank()){
                if (!alumno.getEstado().equalsIgnoreCase("activo") ||
                        alumno.getEstado().equalsIgnoreCase("inactivo")){
                    throw new IllegalArgumentException("El estado debe ser 'ACTIVO' o 'INACTIVO'.");
                }
                throw new IllegalArgumentException("Definir el estado de actividad del alumno es obligatorio.");
            }
    }
}
