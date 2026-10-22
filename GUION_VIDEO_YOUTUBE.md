# 🎬 GUION PARA EL VÍDEO EXPLICATIVO EN YOUTUBE
## Actividad: Gestión de Funcionarios — IU Digital de Antioquia
**Asignatura:** Desarrollo de Software Seguro  
**Duración recomendada:** Entre 5 y 8 minutos  

---

### ⏱️ MINUTO 0:00 - 0:45 | 1. PRESENTACIÓN FORMAL DE INTEGRANTES (OBLIGATORIO POR RÚBRICA)
> **Pantalla:** Mostrar la portada del proyecto, el entorno de desarrollo (IDE) o la carátula con los datos de los integrantes.
> 
> **Voz en off / Presentador:**
> *"Cordial saludo profesor y compañeros. En este vídeo presentamos la sustentación de la actividad de desarrollo de software seguro para la Institución Universitaria Digital de Antioquia (IU Digital).*
> *El equipo de trabajo está conformado por: [Mencionar Nombres y Apellidos de los integrantes, hasta 4 personas].*
> *El objetivo principal de esta práctica es implementar técnicas de programación orientada a objetos en Java mediante el patrón DAO (Data Access Object), arquitectura en capas, control estricto de excepciones y buenas prácticas de seguridad informática, gestionando la información de los funcionarios, su grupo familiar y su formación académica conectada a una base de datos MySQL."*

---

### ⏱️ MINUTO 0:45 - 2:00 | 2. MOTOR DE BASE DE DATOS Y MODELO RELACIONAL (5 TABLAS)
> **Pantalla:** Mostrar MySQL Workbench / phpMyAdmin o el diagrama entidad-relación en pantalla.
>
> **Voz en off:**
> *"Para este proyecto seleccionamos el motor de base de datos **MySQL** con motor transaccional **InnoDB**. Diseñamos un modelo relacional normalizado compuesto por las 5 tablas especificadas en la rúbrica de evaluación:*
> 1. *`tipo_documento`: Catálogo de tipos de documento (CC, TI, CE, Pasaporte).*
> 2. *`estado_civil`: Catálogo de estados civiles (Soltero/a, Casado/a, Unión libre, etc.).*
> 3. *`funcionarios`: Nuestra tabla principal que almacena los datos personales del funcionario (tipo y número de documento único, nombres, apellidos, estado civil, sexo, dirección, teléfono y fecha de nacimiento).*
> 4. *`grupo_familiar`: Tabla que almacena los familiares de cada funcionario, conectada con clave foránea `id_funcionario` y regla `ON DELETE CASCADE`.*
> 5. *`formacion_academica`: Tabla que guarda los estudios, universidad y título obtenido por cada funcionario, igualmente vinculada mediante `ON DELETE CASCADE`.*
> 
> *A continuación mostramos el script `01_crear_base_datos.sql` donde se definen las claves primarias, foráneas e índices, y el script `02_datos_iniciales.sql` que realiza el poblado inicial de los registros para arrancar el sistema."*

---

### ⏱️ MINUTO 2:00 - 3:30 | 3. EXPLICACIÓN DE ARQUITECTURA: PATRÓN DAO Y EXCEPCIONES
> **Pantalla:** Mostrar el árbol de paquetes del proyecto en el IDE (`config`, `model`, `dao`, `service`, `exception`, `view`).
>
> **Voz en off:**
> *"En el código fuente en Java implementamos una arquitectura desacoplada por capas:*
> - *En el paquete **`model`** definimos las entidades POJO: `Funcionario`, `GrupoFamiliar` y `FormacionAcademica`.*
> - *En el paquete **`dao`** aplicamos el **Patrón DAO**: definimos contratos mediante interfaces como `FuncionarioDAO`, lo que permite separar completamente la lógica de negocio del acceso a datos. En `FuncionarioDAOImpl` utilizamos sentencias preparadas `PreparedStatement` para mitigar cualquier riesgo de inyección SQL, y controlamos los recursos mediante `try-with-resources`.*
> - *En el paquete **`exception`** creamos la clase `AccesoDatosException`. Esta excepción comprobada encapsula los errores técnicos de SQL (`SQLException`), traduciéndolos a mensajes claros y seguros para el usuario, por ejemplo, detectando violaciones de cédula duplicada con el SQLState `23000`.*
> - *En el paquete **`service`** se ubica la lógica de negocio (`FuncionarioService`), encargada de validar que los campos obligatorios existan y que la fecha de nacimiento no sea futura."*

---

### ⏱️ MINUTO 3:30 - 6:30 | 4. DEMOSTRACIÓN EN VIVO DE LA APLICACIÓN (CRUD COMPLETO)
> **Pantalla:** La ventana de la aplicación Java Swing en funcionamiento.
>
> **Paso a Paso a Realizar en Pantalla:**
> 1. **Listar:** Mostrar cómo al abrir la aplicación la tabla de Funcionarios ya carga los datos poblados inicialmente.
> 2. **Crear Funcionario:**
>    - Diligenciar un nuevo funcionario (ejemplo: Cédula 100999888, Nombre 'Carlos Alberto', Apellido 'Restrepo Cano', Teléfono '3109998877', Fecha '1993-07-21').
>    - Hacer clic en **Crear Funcionario**. Mostrar el mensaje emergente de éxito y ver la nueva fila agregada a la tabla.
> 3. **Validación de Errores / Excepciones:**
>    - Intentar crear otro funcionario con la **misma cédula** para demostrar cómo el sistema captura la excepción y muestra el aviso de identificación duplicada.
>    - Intentar crear un funcionario dejando campos vacíos o con formato de fecha inválido para mostrar las validaciones del servicio.
> 4. **Seleccionar y Actualizar:**
>    - Hacer clic en una fila de la tabla para cargar sus datos en el formulario.
>    - Cambiar el teléfono o la dirección y pulsar **Actualizar Funcionario**. Confirmar que la tabla se actualiza.
> 5. **Pestaña Grupo Familiar (Relación 1 a N):**
>    - Pasar a la pestaña **Grupo Familiar**. Mostrar cómo el banner verde indica claramente el funcionario seleccionado.
>    - Agregar un familiar (ejemplo: Hija, Nombre 'Valentina Restrepo', Rol 'Hijo/a').
>    - Presionar **Guardar Familiar** y ver que aparece en la tabla de familiares de ese funcionario.
> 6. **Pestaña Formación Académica (Relación 1 a N):**
>    - Pasar a la pestaña **Formación Académica**.
>    - Registrar un estudio (Universidad: 'IU Digital de Antioquia', Nivel: 'Especialización', Título: 'Seguridad Informática').
>    - Guardar y mostrar el registro en la tabla.
> 7. **Eliminar Funcionario y Cascada:**
>    - Regresar a la pestaña **Funcionarios**, seleccionar el funcionario creado y pulsar **Eliminar Funcionario**.
>    - Mostrar el diálogo de advertencia sobre la eliminación en cascada de sus familiares y estudios asociados. Confirmar y verificar que desaparece.

---

### ⏱️ MINUTO 6:30 - 7:00 | 5. CONCLUSIÓN Y CIERRE
> **Pantalla:** Volver a mostrar el repositorio Git / GitHub o la portada final.
>
> **Voz en off:**
> *"Como conclusión, hemos desarrollado satisfactoriamente la aplicación de escritorio en Java Swing, cumpliendo al 100% con los requerimientos del caso de estudio de la IU Digital: implementación rigurosa del patrón DAO, manejo seguro de excepciones comprobadas, modelo relacional con sus 5 tablas y relaciones en cascada, y el CRUD completo y funcional.*
> *El código fuente se encuentra versionado y disponible en el repositorio Git según las indicaciones de la entrega.*
> *Muchas gracias por su atención."*
