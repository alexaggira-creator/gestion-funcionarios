-- ======================================================================
-- INSTITUCIÓN UNIVERSITARIA DIGITAL DE ANTIOQUIA (IU DIGITAL)
-- Script de Poblado Inicial de Tablas
-- ======================================================================

USE gestion_funcionarios;

-- 1. Poblado de Tipos de Documento
INSERT INTO tipo_documento (codigo, descripcion) VALUES
('CC', 'Cédula de Ciudadanía'),
('TI', 'Tarjeta de Identidad'),
('CE', 'Cédula de Extranjería'),
('RC', 'Registro Civil'),
('Pasaporte', 'Pasaporte Internacional'),
('Otro', 'Otro Documento')
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- 2. Poblado de Estados Civiles
INSERT INTO estado_civil (codigo, descripcion) VALUES
('Soltero/a', 'Persona soltera'),
('Casado/a', 'Persona legalmente casada'),
('Unión libre', 'Unión marital de hecho'),
('Divorciado/a', 'Persona con divorcio legalizado'),
('Viudo/a', 'Persona cuyo cónyuge ha fallecido')
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- 3. Poblado de Funcionarios
INSERT INTO funcionarios
(tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento)
VALUES
('CC', '1001001001', 'Laura Marcela', 'Gómez Ruiz', 'Soltero/a', 'Femenino', 'Calle 10 # 20-30, Medellín', '3001112233', '1995-04-12'),
('CC', '1002002002', 'Andrés Felipe', 'Pérez Soto', 'Casado/a', 'Masculino', 'Carrera 45 # 12-18, Envigado', '3012223344', '1988-09-25'),
('CC', '1003003003', 'Camila Andrea', 'Torres Díaz', 'Unión libre', 'Femenino', 'Vereda El Rosario, Bello', '3023334455', '1992-01-08')
ON DUPLICATE KEY UPDATE nombres = VALUES(nombres);

-- 4. Poblado de Grupo Familiar (asociado a los funcionarios 1 y 2)
INSERT INTO grupo_familiar
(id_funcionario, tipo_identificacion, numero_identificacion, nombres, apellidos, fecha_nacimiento, rol_familiar)
VALUES
(1, 'TI', '1100110011', 'Samuel', 'Gómez López', '2018-06-20', 'Hijo/a'),
(2, 'CC', '2200220022', 'María Elena', 'Soto Gil', '1990-02-14', 'Cónyuge');

-- 5. Poblado de Formación Académica (asociada a los funcionarios 1, 2 y 3)
INSERT INTO formacion_academica (id_funcionario, universidad, nivel_estudio, titulo)
VALUES
(1, 'Universidad de Antioquia', 'Profesional', 'Administración de Empresas'),
(2, 'Politécnico Colombiano Jaime Isaza Cadavid', 'Tecnología', 'Gestión Administrativa'),
(3, 'IU Digital de Antioquia', 'Especialización', 'Gestión del Talento Humano');
