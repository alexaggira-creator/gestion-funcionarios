# Gestión de Funcionarios — IU Digital de Antioquia

Aplicación de escritorio académica desarrollada en **Java Swing**, conectada a **MySQL** mediante **JDBC**, implementando el **patrón DAO** (*Data Access Object*), arquitectura por capas, validaciones de negocio y manejo robusto de excepciones.

La solución incluye la gestión integral (CRUD) de:
1. **Funcionarios**
2. **Grupo familiar de cada funcionario** (relación 1 a N)
3. **Formación académica de cada funcionario** (relación 1 a N)

---

## Requisitos del Sistema
- **Java Development Kit (JDK):** Versión 21 o superior.
- **Base de Datos:** MySQL Server (o MariaDB / XAMPP / WAMP) y MySQL Workbench o phpMyAdmin.
- **Entorno de Desarrollo:** Antigravity IDE o Apache NetBeans 19+.
- **Driver JDBC:** `mysql-connector-j` 9.5.0 (gestionado vía Maven o presente en `.m2`).

---

## 1. Configurar y Crear la Base de Datos en MySQL

1. Inicia tu servidor local de MySQL (por ejemplo, mediante XAMPP, el servicio de Windows o Docker).
2. Abre **MySQL Workbench** (o la herramienta cliente de tu preferencia) y conéctate como usuario administrador (ej. `root`).
3. Abre y ejecuta el archivo:
   - `sql/01_crear_base_datos.sql`
   *(Crea la base de datos `gestion_funcionarios` y las tablas `funcionarios`, `grupo_familiar` y `formacion_academica` con borrado en cascada `ON DELETE CASCADE`).*
4. Abre y ejecuta el archivo:
   - `sql/02_datos_iniciales.sql`
   *(Inserta los datos ficticios de prueba en las 3 tablas).*
5. Verifica que las tablas contengan los registros ejecutando:
   ```sql
   USE gestion_funcionarios;
   SELECT * FROM funcionarios;
   SELECT * FROM grupo_familiar;
   SELECT * FROM formacion_academica;
   ```

---

## 2. Configurar Credenciales de Conexión

La clase `src/main/java/com/iudigital/gestion/config/Conexion.java` lee variables de entorno del sistema o toma valores predeterminados:
- **`DB_URL`** (predeterminado: `jdbc:mysql://localhost:3306/gestion_funcionarios?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true`)
- **`DB_USER`** (predeterminado: `root`)
- **`DB_PASSWORD`** (predeterminado: `""` vacío)

Si tu usuario `root` de MySQL tiene contraseña (por ejemplo `admin123` o `root`), puedes:
- **Opción A (Recomendada en terminal/PowerShell):**
  ```powershell
  $env:DB_PASSWORD="tu_contraseña"
  ```
- **Opción B:** Modificar directamente los valores predeterminados en `Conexion.java` en tu copia local.

---

## 3. Cómo Ejecutar la Aplicación

### Desde la Terminal en Antigravity / PowerShell:
Si tienes Maven instalado:
```powershell
mvn clean compile exec:java
```

Si ejecutas directamente con el compilador de Java (`javac` y `java`):
```powershell
# 1. Compilar clases
$jar = "$env:USERPROFILE\.m2\repository\com\mysql\mysql-connector-j\9.5.0\mysql-connector-j-9.5.0.jar"
javac -cp "$jar" -d target/classes (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })

# 2. Ejecutar la aplicación
java -cp "target/classes;$jar" com.iudigital.gestion.Main
```

### Desde Apache NetBeans:
1. Abre NetBeans y ve a **File > Open Project**.
2. Selecciona la carpeta `gestion-funcionarios` (donde se encuentra `pom.xml`).
3. Haz clic derecho sobre el proyecto y selecciona **Clean and Build**.
4. Haz clic en **Run Project** (o presiona `F6`). La clase principal es `com.iudigital.gestion.Main`.

---

## 4. Guía Paso a Paso para Probar Cada Operación

La interfaz gráfica cuenta con 3 pestañas principales en un `JTabbedPane`:

### Pestaña 1: 🏢 Funcionarios
- **Listar:** Al iniciar la aplicación, la tabla muestra automáticamente los funcionarios registrados.
- **Crear:**
  1. Diligencia los campos del formulario (número de identificación, nombres, apellidos, estado civil, sexo, dirección, teléfono y fecha de nacimiento en formato `AAAA-MM-DD`, ej. `1990-05-15`).
  2. Haz clic en **Crear Funcionario**. Verás un mensaje de confirmación y el funcionario aparecerá en la tabla.
- **Seleccionar:** Haz clic sobre cualquier fila de la tabla. Sus datos se cargarán en el formulario y quedará fijado como el **funcionario activo** para las pestañas de familiares y estudios.
- **Actualizar:** Modifica cualquier dato del funcionario seleccionado y pulsa **Actualizar Funcionario**.
- **Eliminar:** Con un funcionario seleccionado, pulsa **Eliminar Funcionario**. El sistema solicitará confirmación advirtiendo que sus familiares y estudios también se eliminarán en cascada.
- **Limpiar:** Deja en blanco los campos para ingresar un nuevo registro.

### Pestaña 2: 👨‍👩‍👧‍👦 Grupo Familiar
- En la parte superior verás un banner verde que indica: `👤 Funcionario seleccionado: [ID] Nombre Apellido`.
- *Nota:* Si no has seleccionado ningún funcionario en la pestaña 1, verás una advertencia naranja y el sistema te pedirá seleccionar uno antes de registrar datos.
- **Listar:** Muestra únicamente los familiares del funcionario seleccionado.
- **Registrar Familiar:** Ingresa tipo de documento, número, nombres, apellidos, fecha de nacimiento opcional (`AAAA-MM-DD`) y rol/parentesco (Hijo/a, Cónyuge, Padre, Madre, etc.). Haz clic en **Guardar Familiar**.
- **Actualizar Familiar:** Haz clic en una fila de la tabla de familiares, modifica los campos deseados y pulsa **Actualizar Familiar**.
- **Eliminar Familiar:** Selecciona la fila del familiar y pulsa **Eliminar Familiar**.

### Pestaña 3: 🎓 Formación Académica
- Muestra el banner informativo con el funcionario activo.
- **Listar:** Carga los estudios y títulos académicos pertenecientes al funcionario activo.
- **Registrar Estudio:** Ingresa el nivel de estudio (Técnico, Tecnología, Profesional, Especialización, Maestría, Doctorado), universidad o institución y título obtenido. Pulsa **Guardar Estudio**.
- **Actualizar Estudio:** Selecciona un estudio de la tabla, ajusta los campos y pulsa **Actualizar Estudio**.
- **Eliminar Estudio:** Selecciona un estudio de la tabla y pulsa **Eliminar Estudio**.

---

## 5. Arquitectura y Organización del Código

El proyecto sigue una arquitectura por capas desacopladas con el patrón DAO:

```
src/main/java/com/iudigital/gestion/
├── Main.java                               # Punto de entrada (Lanza UI con LookAndFeel nativo en EDT)
├── config/
│   └── Conexion.java                       # Obtención de conexiones JDBC (DriverManager)
├── model/
│   ├── Funcionario.java                    # Entidad Funcionario
│   ├── GrupoFamiliar.java                  # Entidad Grupo Familiar
│   └── FormacionAcademica.java             # Entidad Formación Académica
├── dao/
│   ├── FuncionarioDAO.java                 # Contrato DAO (listar, obtenerPorId, crear, actualizar, eliminar)
│   ├── FuncionarioDAOImpl.java             # Implementación JDBC con PreparedStatement y try-with-resources
│   ├── GrupoFamiliarDAO.java               # Contrato DAO de familiares por funcionario
│   ├── GrupoFamiliarDAOImpl.java           # Implementación JDBC de familiares
│   ├── FormacionAcademicaDAO.java          # Contrato DAO de formación por funcionario
│   └── FormacionAcademicaDAOImpl.java      # Implementación JDBC de formación académica
├── service/
│   ├── FuncionarioService.java             # Reglas de negocio y validaciones de funcionarios
│   ├── GrupoFamiliarService.java           # Reglas de negocio y validaciones de familiares
│   └── FormacionAcademicaService.java      # Reglas de negocio y validaciones de formación
├── exception/
│   └── AccesoDatosException.java           # Excepción personalizada comprobada (Checked Exception)
└── view/
    └── FuncionarioView.java                # Interfaz de escritorio Swing con JTabbedPane y manejo seguro de errores
```

---

## 6. Información Académica
- **Institución:** IU Digital de Antioquia
- **Programa:** Tecnología en Desarrollo de Software / Ingeniería de Software
- **Asignatura:** Programación en Java / Bases de Datos
- **Patrones y Tecnologías:** Java 21, Swing, JDBC, MySQL, Patrón DAO, Arquitectura en Capas.
