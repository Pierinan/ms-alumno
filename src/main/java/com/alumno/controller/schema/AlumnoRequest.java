package com.alumno.controller.schema;

import jakarta.validation.constraints.*;

public class AlumnoRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private Boolean estado;

    @NotNull
    @Min(1)
    @Max(120)
    private Integer edad;

    // getters y setters
}