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
            validateAlumnoInput(alumno);

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

    private void validateAlumnoInput(Alumno alumno) {
        if (alumno == null){
            throw new BusinessException(Constants.MSG_ALUMNO_OBLIGATORIO);
        }

        String idAlumno = alumno.getIdAlumno();
        String nombre = alumno.getNombre();
        String apellido = alumno.getApellido();
        String estado = alumno.getEstado();
        Integer edad = alumno.getEdad();

        if(idAlumno == null || idAlumno.isBlank()) {
            throw new BusinessException(Constants.MSG_ID_OBLIGATORIO);
        }
        if (nombre == null || nombre.isBlank()) {
            throw new BusinessException(Constants.MSG_NOMBRE_OBLIGATORIO);
        }
        if (apellido == null || apellido.isBlank()){
            throw new BusinessException(Constants.MSG_APELLIDO_OBLIGATORIO);
        }
        if (edad == null){
            throw new BusinessException(Constants.MSG_EDAD_OBLIGATORIA);
        }
        if (edad <= 0) {
            throw new BusinessException(Constants.MSG_EDAD_INVALIDA);
        }
        if (estado == null || estado.isBlank()){
            throw new BusinessException(Constants.MSG_ESTADO_OBLIGATORIO);
        }
        if (!estado.equalsIgnoreCase(ESTADO_ACTIVO)
                && !estado.equalsIgnoreCase(ESTADO_INACTIVO)){
            throw new BusinessException(Constants.MSG_ESTADO_INVALIDO);
        }
    }
}
