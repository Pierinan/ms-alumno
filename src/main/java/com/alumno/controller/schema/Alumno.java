package com.alumno.controller.schema;

import com.alumno.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {

    @NotBlank(message = Constants.MSG_ID_OBLIGATORIO)
    private String idAlumno;

    @NotBlank(message = Constants.MSG_NOMBRE_OBLIGATORIO)
    private String nombre;

    @NotBlank(message = Constants.MSG_APELLIDO_OBLIGATORIO)
    private String apellido;

    @NotBlank(message = Constants.MSG_ESTADO_OBLIGATORIO)
    private String estado;

    @NotNull(message = Constants.MSG_EDAD_OBLIGATORIA)
    @Positive(message = Constants.MSG_EDAD_INVALIDA)
    private Integer edad;
}