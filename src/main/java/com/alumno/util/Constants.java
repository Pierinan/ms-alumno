package com.alumno.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String ESTADO_ACTIVO = "activo";
    public static final String ESTADO_INACTIVO = "inactivo";
    public static final String MSG_ID_OBLIGATORIO = "El ID del alumno es obligatorio";
    public static final String MSG_NOMBRE_OBLIGATORIO = "El nombre del alumno es obligatorio";
    public static final String MSG_APELLIDO_OBLIGATORIO = "El apellido del alumno es obligatorio";
    public static final String MSG_EDAD_OBLIGATORIA = "La edad del alumno es obligatoria";
    public static final String MSG_EDAD_INVALIDA = "La edad del alumno debe ser mayor a cero";
    public static final String MSG_ESTADO_OBLIGATORIO = "El estado del alumno es obligatorio";
    public static final String MSG_ESTADO_INVALIDO = "El estado debe ser ACTIVO o ESTADO_INACTIVO";
    public static final String MSG_ID_DUPLICADO = "El id del alumno ya existe";
    public static final String MSG_ALUMNO_OBLIGATORIO = "Debe enviar datos de un alumno.";
}
