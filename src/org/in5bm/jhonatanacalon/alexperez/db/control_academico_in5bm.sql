DROP DATABASE IF EXISTS db_control_academico_in5bm;
CREATE DATABASE IF NOT EXISTS db_control_academico_in5bm
DEFAULT CHARACTER SET utf8 
DEFAULT COLLATE utf8_general_ci;

/*
* Estudiantes:
* Jhonatan Jose Acalón Ajanel - 2021048
* Alex Gabriel Perez Dubon - 2021095
*
* Código técnico: IN5BM
*
* Grupo: 1
*
* Fecha creación: 11/04/2022
* Fecha modificación: 02/06/2022
*
*/

USE db_control_academico_in5bm;

DROP TABLE IF EXISTS alumnos;
DROP TABLE IF EXISTS instructores;
DROP TABLE IF EXISTS salones;
DROP TABLE IF EXISTS carreras_tecnicas;
DROP TABLE IF EXISTS horarios;
DROP TABLE IF EXISTS cursos;
DROP TABLE IF EXISTS asignaciones_alumnos;

CREATE TABLE IF NOT EXISTS alumnos(
	carne VARCHAR(7) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
	apellido2 VARCHAR(15) NULL,
    CONSTRAINT pk_alumnos PRIMARY KEY(carne)
);

CREATE TABLE IF NOT EXISTS instructores(
	id INT NOT NULL AUTO_INCREMENT,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
	apellido2 VARCHAR(15) NULL,
    direccion VARCHAR(45) NULL,
    email VARCHAR(45) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    fecha_nacimiento DATE NULL,
    CONSTRAINT pk_instructores PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45) NULL,
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR(15) NULL,
    nivel INT NULL,
    CONSTRAINT pk_salones PRIMARY KEY (codigo_salon)
);

CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6) NOT NULL,
    carrera VARCHAR(45) NOT NULL,
    grado VARCHAR(10) NOT NULL,
    seccion CHAR(1) NOT NULL,
    jornada VARCHAR(10) NOT NULL,
    CONSTRAINT pk_carreras_tecnicas PRIMARY KEY (codigo_tecnico)
);

CREATE TABLE IF NOT EXISTS horarios(
	id INT NOT NULL AUTO_INCREMENT,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT(1) NULL,
    martes TINYINT(1) NULL,
    miercoles TINYINT(1) NULL,
    jueves TINYINT(1) NULL,
    viernes TINYINT(1) NULL,
    CONSTRAINT pk_horarios PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cursos(
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255) NOT NULL,
    ciclo YEAR NULL,
    cupo_maximo INT NULL,
    cupo_minimo INT NULL,
	carrera_tecnica_id 	VARCHAR(128) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    CONSTRAINT pk_cursos PRIMARY KEY (id),
    CONSTRAINT fk_cursos_carreras_tecnicas
		FOREIGN KEY (carrera_tecnica_id)
		REFERENCES carreras_tecnicas (codigo_tecnico)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_horarios
		FOREIGN KEY (horario_id)
        REFERENCES horarios (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_instructores
		FOREIGN KEY (instructor_id)
		REFERENCES instructores (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_salones
		FOREIGN KEY (salon_id)
        REFERENCES salones (codigo_salon)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id INT NOT NULL AUTO_INCREMENT,
    alumno_id VARCHAR(7) NOT NULL,
    curso_id INT NOT NULL,
    fecha_asignacion DATETIME NULL,
    CONSTRAINT pk_asignaciones_alumnos PRIMARY KEY (id),
    CONSTRAINT fk_asignaciones_alumnos_alumno
		FOREIGN KEY (alumno_id)
        REFERENCES alumnos (carne)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_asignaciones_alumnos_curso
		FOREIGN KEY (curso_id)
        REFERENCES cursos (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- CRUD entidad alumnos----------------------------------------------------------------------------------------------------------------
-- CREATE alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
	IN _carne VARCHAR(7),
    IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
)
BEGIN
	INSERT INTO alumnos(
		carne,
        nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2
    )
	VALUES
	(
		_carne,
		_nombre1,
		_nombre2,
		_nombre3,
		_apellido1,
		_apellido2
    );
END $$
DELIMITER ;

-- READ alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
	SELECT
		alumnos.carne,
        alumnos.nombre1,
        alumnos.nombre2,
        alumnos.nombre3,
        alumnos.apellido1,
        alumnos.apellido2
	FROM
		alumnos;
END $$
DELIMITER ;

-- READ alumnos(ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(
	IN _carne VARCHAR(7)
)
BEGIN
	SELECT 
		alumnos.carne,
        alumnos.nombre1,
        alumnos.nombre2,
        alumnos.nombre3,
        alumnos.apellido1,
        alumnos.apellido2    
    FROM
		alumnos
	WHERE
		alumnos.carne=_carne;
END $$
DELIMITER ;

-- UPDATE alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR(7),
    IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
)
BEGIN
	UPDATE 
		alumnos
    SET 
		alumnos.nombre1=_nombre1,
        alumnos.nombre2=_nombre2,
        alumnos.nombre3=_nombre3,
        alumnos.apellido1=_apellido1,
        alumnos.apellido2=_apellido2
	WHERE
		alumnos.carne=_carne;
END $$
DELIMITER ;

-- DELETE alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(
	IN _carne VARCHAR(7)
)
BEGIN
	DELETE FROM
		alumnos
	WHERE
		alumnos.carne=_carne;
END $$
DELIMITER ;

-- CRUD entidad instructores----------------------------------------------------------------------------------------------------------------
-- CREATE instructores
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create(
	IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE
)
BEGIN
	INSERT INTO instructores(
		nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2,
        direccion,
        email,
        telefono,
        fecha_nacimiento
    )
    VALUES
    (
		_nombre1,
        _nombre2,
        _nombre3,
        _apellido1,
        _apellido2,
        _direccion,
        _email,
        _telefono,
        _fecha_nacimiento
    );
END $$
DELIMITER ;

-- READ instructores
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
	SELECT
		instructores.id,
        instructores.nombre1,
        instructores.nombre2,
        instructores.nombre3,
        instructores.apellido1,
        instructores.apellido2,
        instructores.direccion,
        instructores.email,
        instructores.telefono,
        instructores.fecha_nacimiento
	FROM
		instructores;
END $$
DELIMITER ;

-- READ instructores(ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(
	IN _id INT
)
BEGIN
	SELECT 
		instructores.id,
        instructores.nombre1,
        instructores.nombre2,
        instructores.nombre3,
        instructores.apellido1,
        instructores.apellido2,
        instructores.direccion,
        instructores.email,
        instructores.telefono,
        instructores.fecha_nacimiento
    FROM
		instructores
	WHERE
		instructores.id=_id;
END $$
DELIMITER ;

-- UPDATE instructores
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update(
	IN _id INT,
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
   	IN _nombre3 VARCHAR(15),
   	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE	
)
BEGIN
	UPDATE
		instructores
	SET
		instructores.nombre1=_nombre1,
        instructores.nombre2=_nombre2,
        instructores.nombre3=_nombre3,
        instructores.apellido1=_apellido1,
        instructores.apellido2=_apellido2,
        instructores.direccion=_direccion,
        instructores.email=_email,
        instructores.telefono=_telefono,
        instructores.fecha_nacimiento=_fecha_nacimiento
	WHERE
		instructores.id=_id;
END $$
DELIMITER ;

-- DELETE instructores
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		instructores
	WHERE
		instructores.id=_id;
END $$
DELIMITER ;

-- CRUD entidad salones----------------------------------------------------------------------------------------------------------------
-- CREATE salones
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create(
	IN _codigo_salon VARCHAR(5),
    IN _descripcion VARCHAR(45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
)
BEGIN
	INSERT INTO salones(
		codigo_salon,
        descripcion,
        capacidad_maxima,
        edificio,
        nivel
    ) 
    VALUES
    (
		_codigo_salon,
        _descripcion,
        _capacidad_maxima,
        _edificio,
        _nivel
    );
END $$
DELIMITER ;

-- READ salones
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
	SELECT
		salones.codigo_salon,
        salones.descripcion,
		salones.capacidad_maxima,
        salones.edificio,
        salones.nivel
	FROM
		salones;
END $$
DELIMITER ;

-- READ salones (ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(
	IN _codigo_salon VARCHAR(5)
)
BEGIN
	SELECT
		salones.codigo_salon,
        salones.descripcion,
		salones.capacidad_maxima,
        salones.edificio,
        salones.nivel
	FROM
		salones
	WHERE
		salones.codigo_salon=_codigo_salon;
END $$
DELIMITER ;

-- UPDATE salones
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update(
	IN _codigo_salon VARCHAR(5),
    IN _descripcion VARCHAR(45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
)
BEGIN
	UPDATE
		salones
	SET
        salones.descripcion=_descripcion,
        salones.capacidad_maxima=_capacidad_maxima,
        salones.edificio=_edificio,
        salones.nivel=_nivel
	WHERE
		salones.codigo_salon=_codigo_salon;
END $$
DELIMITER ;

-- DELETE salones
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(
	IN _codigo_salon VARCHAR(5)
)
BEGIN
	DELETE FROM
		salones
	WHERE
		salones.codigo_salon=_codigo_salon;
END $$
DELIMITER ;

-- CRUD entidad carreras_tecnicas----------------------------------------------------------------------------------------------------------------
-- CREATE carreras_tecnicas
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create(
	IN _codigo_tecnico VARCHAR(6),
    IN _carrera VARCHAR(45),
    IN _grado VARCHAR(10),
    IN _seccion CHAR(1),
    IN _jornada VARCHAR(10)
)
BEGIN 
	INSERT INTO carreras_tecnicas(
		codigo_tecnico,
		carrera,
        grado,
        seccion,
        jornada
    )
	VALUES
    (
		_codigo_tecnico,
		_carrera,
      	_grado,
        _seccion,
        _jornada
    );
END $$
DELIMITER ;

-- READ carreras_tecnicas
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
	SELECT
		carreras_tecnicas.codigo_tecnico,
        carreras_tecnicas.carrera,
       	carreras_tecnicas.grado,
        carreras_tecnicas.seccion,
        carreras_tecnicas.jornada
	FROM
		carreras_tecnicas;	
END $$
DELIMITER ;

-- READ carreras_tecnicas (ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(
	IN _codigo_tecnico VARCHAR(6)
)
BEGIN
	SELECT
		carreras_tecnicas.codigo_tecnico,
        carreras_tecnicas.carrera,
       	carreras_tecnicas.grado,
        carreras_tecnicas.seccion,
        carreras_tecnicas.jornada
	FROM
		carreras_tecnicas
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;
END $$
DELIMITER ;

-- UPDATE carreras_tecnicas
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update(
	IN _codigo_tecnico VARCHAR(6),
    IN _carrera VARCHAR(45),
    IN _grado VARCHAR(10),
    IN _seccion CHAR(1),
    IN _jornada VARCHAR(10)
)
BEGIN
	UPDATE
		carreras_tecnicas
	SET
		carreras_tecnicas.carrera=_carrera,
        carreras_tecnicas.grado=_grado,
        carreras_tecnicas.seccion=_seccion,
        carreras_tecnicas.jornada=_jornada
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;
END $$
DELIMITER ;

-- DELETE carreras_tecnicas
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete(
	IN _codigo_tecnico VARCHAR(6)
)
BEGIN
	DELETE FROM
		carreras_tecnicas
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;	
END $$
DELIMITER ;

-- CRUD entidad horarios----------------------------------------------------------------------------------------
-- CREATE horarios
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create(
	IN _horario_inicio TIME,
	IN _horario_final TIME,
  	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
  	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
    IN _viernes TINYINT(1)
)
BEGIN 
	INSERT INTO horarios(
		horario_inicio,
		horario_final,
		lunes,
		martes,
		miercoles,
		jueves,
		viernes
    )
	VALUES
    (
		_horario_inicio,
    	_horario_final,
  		_lunes,
    	_martes,
  		_miercoles,
    	_jueves,
    	_viernes
	);
END $$
DELIMITER ;

-- READ horarios
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
	SELECT
		horarios.id,
		horarios.horario_inicio,
		horarios.horario_final,
		horarios.lunes,
		horarios.martes,
		horarios.miercoles,
		horarios.jueves,
		horarios.viernes
	FROM
		horarios;
END $$
DELIMITER ;

-- READ horarios (ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
CREATE PROCEDURE sp_horarios_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		horarios.id,
		horarios.horario_inicio,
		horarios.horario_final,
		horarios.lunes,
		horarios.martes,
		horarios.miercoles,
		horarios.jueves,
		horarios.viernes
	FROM
		horarios
	WHERE
		horarios.id=_id;
END $$
DELIMITER ;

-- UPDATE horarios
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update(
	IN _id INT,
	IN _horario_inicio TIME,
	IN _horario_final TIME,
  	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
  	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
    IN _viernes TINYINT(1)
)
BEGIN
	UPDATE
		horarios
	SET
		horarios.horario_inicio=_horario_inicio,
		horarios.horario_final=_horario_final,
		horarios.lunes=_lunes,
		horarios.martes=_martes,
		horarios.miercoles=_miercoles,
		horarios.jueves=_jueves,
		horarios.viernes=_viernes
	WHERE
		horarios.id=_id;
END $$

-- DELETE horarios
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		horarios
	WHERE
		horarios.id=_id;
END $$
DELIMITER ;

-- CRUD entidad cursos----------------------------------------------------------------------------------------
-- CREATE cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create(
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
  	IN _cupo_maximo INT,   
	IN _cupo_minimo INT,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
)
BEGIN 
	INSERT INTO cursos(
		nombre_curso,
		ciclo,
  		cupo_maximo,   
		cupo_minimo,
		carrera_tecnica_id,
		horario_id,
		instructor_id,
		salon_id
	)
	VALUES
    (
		_nombre_curso,
    	_ciclo,
  		_cupo_maximo,
		_cupo_minimo,
		_carrera_tecnica_id,
		_horario_id,
		_instructor_id,
		_salon_id
	);
END $$
DELIMITER ;

-- READ cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
	SELECT
		cursos.id,
		cursos.nombre_curso,
		cursos.ciclo,
  		cursos.cupo_maximo,   
		cursos.cupo_minimo,
		cursos.carrera_tecnica_id,
		cursos.horario_id,
		cursos.instructor_id,
		cursos.salon_id
	FROM
		cursos;
END $$
DELIMITER ;

-- READ cursos(ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		cursos.id,
		cursos.nombre_curso,
		cursos.ciclo,
  		cursos.cupo_maximo,   
		cursos.cupo_minimo,
		cursos.carrera_tecnica_id,
		cursos.horario_id,
		cursos.instructor_id,
		cursos.salon_id
	FROM
		cursos
	WHERE
		cursos.id=_id;
END $$
DELIMITER ;

-- UPDATE cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update(
	IN _id INT,
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
  	IN _cupo_maximo INT,   
	IN _cupo_minimo INT,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
)
BEGIN
	UPDATE
		cursos
	SET
		cursos.nombre_curso=_nombre_curso,
		cursos.ciclo=_ciclo,
  		cursos.cupo_maximo=_cupo_maximo,
		cursos.cupo_minimo=_cupo_minimo,
		cursos.carrera_tecnica_id=_carrera_tecnica_id,
		cursos.horario_id=_horario_id,
		cursos.instructor_id=_instructor_id,
		cursos.salon_id=_salon_id
	WHERE
		cursos.id=_id;	 
END $$

-- DELETE cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		cursos
	WHERE
		cursos.id=_id;	
END $$
DELIMITER ;

-- CRUD entidad asignaciones_alumnos----------------------------------------------------------------------------------------
-- CREATE asignaciones_alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create(
	IN _alumno_id VARCHAR(7),
	IN _curso_id INT,
  	IN _fecha_asignacion DATETIME
)
BEGIN 
	INSERT INTO asignaciones_alumnos(
		alumno_id,
		curso_id,
		fecha_asignacion
	)
	VALUES
    (
		_alumno_id,
		_curso_id,
  		_fecha_asignacion
	);
END $$
DELIMITER ;

-- READ asignaciones_alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN
	SELECT
		asignaciones_alumnos.id,
		asignaciones_alumnos.alumno_id,
		asignaciones_alumnos.curso_id,
		asignaciones_alumnos.fecha_asignacion
	FROM
		asignaciones_alumnos;
END $$
DELIMITER ;

-- READ asignaciones_alumnos(ID)
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		asignaciones_alumnos.id,
		asignaciones_alumnos.alumno_id,
		asignaciones_alumnos.curso_id,
		asignaciones_alumnos.fecha_asignacion
	FROM
		asignaciones_alumnos
	WHERE
		asignaciones_alumnos.id=_id;
END $$
DELIMITER ;

-- UPDATE asignaciones_alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
	IN _id INT,
    IN _alumno_id VARCHAR(7),
    IN _curso_id INT,
  	IN _fecha_asignacion DATETIME
)
BEGIN
	UPDATE
		asignaciones_alumnos
	SET
		asignaciones_alumnos.alumno_id=_alumno_id,
		asignaciones_alumnos.curso_id=_curso_id,
		asignaciones_alumnos.fecha_asignacion=_fecha_asignacion
	WHERE
		asignaciones_alumnos.id=_id; 
END $$

-- DELETE asignaciones_alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		asignaciones_alumnos
	WHERE
		asignaciones_alumnos.id=_id;	
END$$
DELIMITER ;
-- --------calls alumnos-------------------------------------------
CALL sp_alumnos_create("2021001","Juan","Paco","Pedro","Perez","Lopez");
CALL sp_alumnos_create("2021002","Marta","Lorena","","Gonzalez","Lopez");
CALL sp_alumnos_create("2021003","Maria","Jimena","","Ramirez","");
CALL sp_alumnos_create("2021004","Julian","Alberto","Belarmino","Tzun","Acabal");
CALL sp_alumnos_create("2021005","Alex","Gabriel","Manuel","Archila","Marroquin");
CALL sp_alumnos_create("2021006","Donaldo","Henry","","Lopez","Urizar");
CALL sp_alumnos_create("2021007","Jhonatan","Gabriel","Ramiro","Casasola","Sanchez");
CALL sp_alumnos_create("2021008","Pedro","Armando","Daniel","Dubon","Carbajal");
CALL sp_alumnos_create("2021009","Jimena","Rosario","","Hernandez","Vazques");
CALL sp_alumnos_create("2021010","Melany","Mariela","","Felipe","Canel");

-- ----------call instructores-----------------------------------
CALL sp_instructores_create("Marco","Gabriel","Francisco","Tzun","Perez","18 av a lote 124 col las victorias","marco@kinal.edu","45678987",'2000/11/15');
CALL sp_instructores_create("Juan","Francisco","","Dubon","Garcia","20 av a lote 12 col la eurecka","juan@kinal.edu","11665543",'1995/10/28');
CALL sp_instructores_create("Enrrique","Miguel","","Garcia","Pereida","15 av a lote 22 col el godoy","enrrique@kinal.edu","45336788",'1980/12/03');
CALL sp_instructores_create("Franklin","Armando","Daniel","Baten","Ramirez","14 av a lote 1 col cobihode","francklin@kinal.edu","11335577",'2000/06/05');
CALL sp_instructores_create("Esvin","Uriel","Sabdiel","Rossel","Tuna","19 av a lote 32 col las marias","esvin@kinal.edu","56789432",'2002/03/16');
CALL sp_instructores_create("Ervin","Antonio","","Hernandez","Gomez","13 av a lote 14 col santa fe","ervin@kinal.edu","09812367",'1991/07/15');
CALL sp_instructores_create("Angel","Gabriel","Tercero","Godoy","Felipe","11 av a lote 24 col los olivos","angel@kinal.edu","75849302",'1950/01/22');
CALL sp_instructores_create("Carlos","Andres","","Romero","Sanabria","22 av a lote 76 col ixhuacut","carlos@kinal.edu","16547890",'1982/12/29');
CALL sp_instructores_create("Kevin","Elias","Rodolfo","Pineda","Molina","25 av a lote 33 col xococ","kevin@kinal.edu","45465432",'1967/11/11');
CALL sp_instructores_create("Jhonatan","Antonio","","Sanum","Morales","12 av a lote 65 col las margaritas","jhon@kinal.edu","64321789",'2001/12/19');

-- ------call salones-------------------------------------------------------
CALL sp_salones_create("A1","Taller",20,"E5",2);
CALL sp_salones_create("A2","Calculo",15,"E2",3);
CALL sp_salones_create("A3","Social",10,"E3",1);
CALL sp_salones_create("A4","Ingles",15,"E5",3);
CALL sp_salones_create("A5","Lenguaje",15,"E1",3);
CALL sp_salones_create("A6","Matematicas",20,"E3",2);
CALL sp_salones_create("A7","Etica profecional",20,"E1",2);
CALL sp_salones_create("A8","Laboratorio",20,"E2",2);
CALL sp_salones_create("A9","Electromecanica",10,"E3",1);
CALL sp_salones_create("A10","Lab. Computadoras",15,"E4",3);

-- ----call carreras_tecnicas-----------------
CALL sp_carreras_tecnicas_create("IN5BM","Informatica","5to","B","Matutina");
CALL sp_carreras_tecnicas_create("ME5AV","Mecanica","5to","A","Vespertina");
CALL sp_carreras_tecnicas_create("DI5AM","Dibujo","5to","A","Matutina");
CALL sp_carreras_tecnicas_create("EL5BV","Electricidad","5to","B","Vespertina");
CALL sp_carreras_tecnicas_create("EA5BV","Electronica","5to","B","Vespertina");
CALL sp_carreras_tecnicas_create("MA5AM","Medicina","5to","A","Matutina");
CALL sp_carreras_tecnicas_create("FA5AM","Farmaceutica","5to","A","Matutina");
CALL sp_carreras_tecnicas_create("PS5AV","Psicologia","5to","A","Vespertina");
CALL sp_carreras_tecnicas_create("MU5BV","Musica","5to","B","Vespertina");
CALL sp_carreras_tecnicas_create("CO5CM","Contabilidad","5to","C","Matutina");

-- -----call horarios------------------------------
CALL sp_horarios_create('07:00:00','12:30:00',0,1,1,0,1);
CALL sp_horarios_create('07:00:00','12:30:00',1,1,1,1,1);
CALL sp_horarios_create('07:00:00','12:30:00',0,0,1,1,1);
CALL sp_horarios_create('07:00:00','12:30:00',1,0,0,1,1);
CALL sp_horarios_create('07:00:00','12:30:00',1,1,0,0,0);
CALL sp_horarios_create('07:00:00','12:30:00',0,1,0,1,0);
CALL sp_horarios_create('07:00:00','12:30:00',1,0,1,0,1);
CALL sp_horarios_create('07:00:00','12:30:00',1,0,0,1,1);
CALL sp_horarios_create('07:00:00','12:30:00',1,1,0,1,0);
CALL sp_horarios_create('07:00:00','12:30:00',0,1,0,0,1);

-- --------call cursos-----------------------------------------
CALL sp_cursos_create("matematicas",2022,20,10,"IN5BM",1,1,"A6");
CALL sp_cursos_create("lenguaje",2022,30,10,"ME5AV",2,2,"A5");
CALL sp_cursos_create("sociales",2022,15,10,"PS5AV",3,3,"A3");
CALL sp_cursos_create("ingles",2022,25,10,"EL5BV",4,4,"A4");
CALL sp_cursos_create("etica profesional",2022,15,10,"FA5AM",5,5,"A7");
CALL sp_cursos_create("quimica",2022,25,10,"CO5CM",6,6,"A9");
CALL sp_cursos_create("calculo",2022,20,10,"EA5BV",7,7,"A2");
CALL sp_cursos_create("dibujo",2022,30,10,"DI5AM",8,8,"A1");
CALL sp_cursos_create("musica",2022,15,10,"MU5BV",9,9,"A10");
CALL sp_cursos_create("estadistica",2022,20,10,"MA5AM",10,10,"A8");

-- --------call_asignaciones_alumnos-----------------------
CALL sp_asignaciones_alumnos_create("2021001",1,'2022-01-15 07:00');
CALL sp_asignaciones_alumnos_create("2021002",2,'2022-01-15 07:10');
CALL sp_asignaciones_alumnos_create("2021003",3,'2022-01-15 07:20');
CALL sp_asignaciones_alumnos_create("2021004",4,'2022-01-15 07:30');
CALL sp_asignaciones_alumnos_create("2021005",5,'2022-01-15 07:40');
CALL sp_asignaciones_alumnos_create("2021006",6,'2022-01-15 07:50');
CALL sp_asignaciones_alumnos_create("2021007",7,'2022-01-15 08:00');
CALL sp_asignaciones_alumnos_create("2021008",8,'2022-01-15 08:10');
CALL sp_asignaciones_alumnos_create("2021009",9,'2022-01-15 08:20');
CALL sp_asignaciones_alumnos_create("2021010",10,'2022-01-15 08:30');