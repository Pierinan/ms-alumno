package com.alumno.controller.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Alumno {
    private String idAlumno;
    private String nombre;
    private String apellido;
    private String estado;
    private Integer edad;
}