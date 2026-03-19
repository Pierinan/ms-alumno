package com.alumno.business.impl;

import com.alumno.controller.schema.Alumno;
import com.alumno.exception.BusinessException;
import com.alumno.repository.AlumnoRepository;
import com.alumno.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Test
    void givenIdAlumnoNotExist_whenRegisterAlumno_thenSucessRegistry(){
        //given
        Alumno alumno = new Alumno("1", "Pierina", "Lopez", "activo", 24);

        when(alumnoRepository.existsById(alumno.getIdAlumno()))
                .thenReturn(Mono.just(false));
        when(alumnoRepository.save(alumno))
                .thenReturn(Mono.empty());

        //when
        Mono<Void> result = alumnoService.registrarAlumno(alumno);

        //then
        StepVerifier.create(result)
                .verifyComplete();

        verify(alumnoRepository).existsById(alumno.getIdAlumno());
        verify(alumnoRepository).save(alumno);
    }

    @Test
    void givenDuplicatedId_whenRegisterAlumno_thenReturnError(){
        //given
        Alumno alumno = new Alumno("1", "Teresa", "Chavez", "activo", 24);

        when(alumnoRepository.existsById(alumno.getIdAlumno()))
                .thenReturn(Mono.just(true));

        //when
        Mono<Void> result = alumnoService.registrarAlumno(alumno);

        //then
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException && throwable.getMessage().equals(Constants.MSG_ID_DUPLICADO))
                .verify();

        verify(alumnoRepository).existsById(alumno.getIdAlumno());
        verify(alumnoRepository, never()).save(any());
    }

    @Test
    void givenInvalidState_whenRegisterAlumno_thenReturnError() {
        // given
        Alumno alumno = new Alumno("1", "Peresa", "Perez", "activado", 20);

        // when
        Mono<Void> result = alumnoService.registrarAlumno(alumno);

        // then
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException
                                && throwable.getMessage().equals(Constants.MSG_ESTADO_INVALIDO))
                .verify();

        verifyNoInteractions(alumnoRepository);
    }

    @Test
    void givenGetAlumnos_whenAlumnosActive_returnAlumnos() {
        // given
        Alumno alumno1 = new Alumno("1", "Juan", "Perez", "ACTIVO", 20);
        Alumno alumno2 = new Alumno("2", "Ana", "Lopez", "ACTIVO", 22);

        when(alumnoRepository.findActivos())
                .thenReturn(Flux.just(alumno1, alumno2));

        // when
        Flux<Alumno> result = alumnoService.getAlumnosActivos();

        // then
        StepVerifier.create(result)
                .expectNext(alumno1)
                .expectNext(alumno2)
                .verifyComplete();

        verify(alumnoRepository).findActivos();
    }


}
