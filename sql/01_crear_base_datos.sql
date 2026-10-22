-- ======================================================================
-- INSTITUCIÓN UNIVERSITARIA DIGITAL DE ANTIOQUIA (IU DIGITAL)
-- Actividad: Gestión de Funcionarios (Desarrollo de Software Seguro)
-- Motor: MySQL 8.0+ / MariaDB 10.4+
-- 
-- Modelo Relacional Normalizado:
-- 1. tipo_documento (Catálogo maestro)
-- 2. estado_civil (Catálogo maestro)
-- 3. funcionarios (Tabla principal CRUD)
-- 4. grupo_familiar (Relación 1 a N con funcionarios)
-- 5. formacion_academica (Relación 1 a N con funcionarios)
-- ======================================================================

CREATE DATABASE IF NOT EXISTS gestion_funcionarios
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_funcionarios;

-- 1. Tabla de Tipos de Documento
CREATE TABLE IF NOT EXISTS tipo_documento (
    codigo VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 2. Tabla de Estados Civiles
CREATE TABLE IF NOT EXISTS estado_civil (
    codigo VARCHAR(30) PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 3. Tabla Principal de Funcionarios
CREATE TABLE IF NOT EXISTS funcionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion VARCHAR(20) NOT NULL,
    numero_identificacion VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    estado_civil VARCHAR(30) NOT NULL,
    sexo VARCHAR(30) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    CONSTRAINT fk_funcionario_tipo_doc FOREIGN KEY (tipo_identificacion)
      REFERENCES tipo_documento(codigo) ON UPDATE CASCADE,
    CONSTRAINT fk_funcionario_estado_civil FOREIGN KEY (estado_civil)
      REFERENCES estado_civil(codigo) ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 4. Tabla de Grupo Familiar (Relación 1 a N con Funcionarios)
CREATE TABLE IF NOT EXISTS grupo_familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT NOT NULL,
    tipo_identificacion VARCHAR(20),
    numero_identificacion VARCHAR(30),
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    rol_familiar VARCHAR(50) NOT NULL,
    CONSTRAINT fk_familiar_funcionario FOREIGN KEY (id_funcionario)
      REFERENCES funcionarios(id_funcionario) ON DELETE CASCADE,
    CONSTRAINT fk_familiar_tipo_doc FOREIGN KEY (tipo_identificacion)
      REFERENCES tipo_documento(codigo) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

-- 5. Tabla de Formación Académica (Relación 1 a N con Funcionarios)
CREATE TABLE IF NOT EXISTS formacion_academica (
    id_formacion INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT NOT NULL,
    universidad VARCHAR(150) NOT NULL,
    nivel_estudio VARCHAR(60) NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    CONSTRAINT fk_formacion_funcionario FOREIGN KEY (id_funcionario)
      REFERENCES funcionarios(id_funcionario) ON DELETE CASCADE
) ENGINE=InnoDB;
