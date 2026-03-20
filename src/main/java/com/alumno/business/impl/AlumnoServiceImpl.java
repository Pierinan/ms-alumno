package com.alumno.business.impl;

import com.alumno.business.AlumnoService;
import com.alumno.controller.schema.Alumno;
import com.alumno.exception.BusinessException;
import com.alumno.repository.AlumnoRepository;
import com.alumno.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.alumno.util.Constants.ESTADO_ACTIVO;
import static com.alumno.util.Constants.ESTADO_INACTIVO;

@Service
@AllArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Override
    public Mono<Void> registrarAlumno(Alumno alumno) {
        return Mono.defer(() -> {
            validateAlumno(alumno);
            return alumnoRepository.existsById(alumno.getIdAlumno())
                    .flatMap(exists -> {
                        if (exists) {
                            return Mono.error(new BusinessException(Constants.MSG_ID_DUPLICADO));
                        }
                        return alumnoRepository.save(alumno);
                    });
        });
    }

    @Override
    public Flux<Alumno> getAlumnosActivos() {
        return alumnoRepository.findActivos();
    }

    private void validateAlumno(Alumno alumno) {
        String estado = alumno.getEstado();

        if (!estado.equalsIgnoreCase(ESTADO_ACTIVO)
                && !estado.equalsIgnoreCase(ESTADO_INACTIVO)){
            throw new BusinessException(Constants.MSG_ESTADO_INVALIDO);
        }
    }
}
