# Servicio de ejemplo: MS Alumnos (Spring WebFlux)

Descripción
-----------
Proyecto de demostración que implementa un microservicio reactivo para la gestión básica de "alumnos" usando Spring Boot WebFlux y Reactor (Mono/Flux).

Este servicio expone endpoints reactivos para registrar alumnos y listar alumnos activos. Está pensado como ejemplo didáctico para mostrar:
- Uso de Spring WebFlux y programación reactiva (Mono, Flux).
- Validación con Jakarta Validation.
- Manejo de errores de negocio mediante excepciones específicas.

Requisitos
----------
- JDK: Java 17 (el proyecto usa toolchains configurado a Java 17). Se recomienda instalar Temurin/Adoptium, Oracle JDK 17 u otra distribución compatible.
- Gradle: se incluye el Gradle Wrapper en `gradle/wrapper/gradle-wrapper.properties` que apunta a Gradle 8.14.4. Usa siempre el wrapper (`gradlew` / `gradlew.bat`) para garantizar la versión correcta.

Comprobado en este proyecto
- Spring Boot plugin: 3.5.11
- Gradle wrapper: 8.14.4

Cómo compilar y ejecutar (Windows PowerShell)
-------------------------------------------
1) Abrir PowerShell en la carpeta raíz del proyecto (donde está `gradlew.bat`).

2) Compilar y ejecutar tests:

```powershell
.\gradlew.bat clean build
```

3) Ejecutar en modo desarrollo (arranca la aplicación con Spring Boot):

```powershell
.\gradlew.bat bootRun
```

4) O crear el jar ejecutable y ejecutarlo manualmente:

```powershell
.\gradlew.bat bootJar
java -jar .\build\libs\service-1.0.0.jar
```

5) Ejecutar solo los tests:

```powershell
.\gradlew.bat test
```

Endpoints principales
--------------------
Por defecto la aplicación está configurada para ejecutarse en el puerto 8080 (ver `src/main/resources/application.yml`). Además, en `application.yml` aparece la propiedad `spring.webflux.base-path: /ms-alumnos`.

Con la configuración incluida, la URL base aproximada es:

http://localhost:8080/ms-alumnos

Endpoints disponibles (controlador `AlumnoController`):
- POST /alumnos
  - Descripción: registra un alumno. Valida campos mediante Jakarta Validation.
  - Código de respuesta esperado: 201 CREATED si se registra correctamente.
  - Body de ejemplo (JSON):

```json
{
  "idAlumno": "1",
  "nombre": "Juan",
  "apellido": "Pérez",
  "estado": "activo",
  "edad": 20
}
```

Ejemplo (PowerShell - Invoke-RestMethod):

```powershell
$payload = '{"idAlumno":"1","nombre":"Juan","apellido":"Perez","estado":"activo","edad":20}'
Invoke-RestMethod -Uri http://localhost:8080/ms-alumnos/alumnos -Method Post -ContentType "application/json" -Body $payload -UseBasicParsing
```

Ejemplo (curl):

```bash
curl -X POST "http://localhost:8080/ms-alumnos/alumnos" -H "Content-Type: application/json" -d '{"idAlumno":"1","nombre":"Juan","apellido":"Perez","estado":"activo","edad":20}'
```

- GET /alumnos/activos
  - Descripción: devuelve un flujo (Flux) con los alumnos cuyo estado es ACTIVO.
  - Ejemplo:

```bash
curl http://localhost:8080/ms-alumnos/alumnos/activos
```

Comportamiento y validaciones
-----------------------------
- Validaciones de entrada: la clase `Alumno` declara restricciones con Jakarta Validation (@NotBlank, @NotNull, @Positive). Si faltan campos o son inválidos el framework devolverá errores de validación.
- Validación de estado: el servicio valida que `estado` sea "activo" o "inactivo" (case-insensitive). Si no, lanza una `BusinessException` con el mensaje definido en `Constants.MSG_ESTADO_INVALIDO`.
- Duplicidad de ID: antes de guardar se verifica si ya existe el id; si existe se responde con `BusinessException` (`Constants.MSG_ID_DUPLICADO`).
- Repositorio reactivo: el proyecto usa un `AlumnoRepository` con API reactiva (retorna `Mono`/`Flux`) y el servicio (`AlumnoServiceImpl`) trabaja en un estilo reactivo sin bloquear.

Notas y recomendaciones
-----------------------
- Usa siempre el wrapper incluido (`gradlew` / `gradlew.bat`) para mantener la versión de Gradle esperada por el proyecto.
- Si necesitas cambiar el puerto o la base-path, actualiza `src/main/resources/application.yml`.
- Lombok está presente (compileOnly + annotationProcessor). Asegúrate de tener soporte para Lombok en tu IDE o instala el plugin correspondiente.

Estructura mínima relevante
---------------------------
- `src/main/java/com/alumno/controller/AlumnoController.java` → endpoints REST.
- `src/main/java/com/alumno/business/impl/AlumnoServiceImpl.java` → lógica reactiva de negocio.
- `src/main/java/com/alumno/controller/schema/Alumno.java` → DTO con validaciones.
- `src/main/java/com/alumno/util/Constants.java` → mensajes y constantes usadas por la aplicación.

Contacto
--------
Proyecto de ejemplo — modificar y adaptar según necesidades. Si necesitas que incluya un Dockerfile o instrucciones adicionales (CORS, seguridad, conexión a base de datos reactiva), dime y las añado.

