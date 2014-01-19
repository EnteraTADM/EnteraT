CREATE TABLE CURSO (
       id_curso INT NOT NULL
     , curso CHAR(10)
);

CREATE TABLE CONCEPTO (
       id_concepto INT NOT NULL
     , concepto CHAR(25)
);

CREATE TABLE USUARIO (
       id_usuario INT NOT NULL
     , usr CHAR(10)
     , psw CHAR(10)
     , tipo INT
);

CREATE TABLE ALUMNO (
       id_alumno INT NOT NULL
     , nombre CHAR(25)
     , apellidos CHAR(40)
     , tutor CHAR(40)
     , id_curso INT
     , id_usuario INT
);

CREATE TABLE PROFESORES (
       id_profesor INT NOT NULL
     , nombre CHAR(25)
     , apellidos CHAR(40)
     , id_usuario INT
     , id_asignatura INT NOT NULL
);

CREATE TABLE ASIGNATURA (
       id_asignatura INT NOT NULL
     , codigo CHAR(4)
     , asignatura CHAR(15)
     , descripcion CHAR(60)
     , id_curso INT
     , id_profesor INT
     , contenido CHAR(100)
     , evaluacion CHAR(100)
     , libros CHAR(100)
     , id CHAR(10) NOT NULL
);

CREATE TABLE MATRICULA (
       id_matricula INT NOT NULL
     , id_alumno INT
     , id_asignatura INT
);

CREATE TABLE NOTA (
       id_nota INT NOT NULL
     , id_matricula INT
     , id_concepto INT
     , nota INT
     , fecha DATE
     , observaciones CHAR(100)
     , notificado BOOL
);

CREATE TABLE INCIDENCIA (
       id_incidencia INT NOT NULL
     , id_alumno INT
     , id_asignatura INT
     , fecha DATE
     , tipo INT
     , contenido CHAR(100)
     , leido BOOL
);

CREATE TABLE TAREA (
       id_itarea INT NOT NULL
     , id_alumno INT
     , id_asignatura INT
     , fecha DATE
     , concepto CHAR(50)
     , observaciones CHAR(100)
     , leido BOOL
);

CREATE TABLE EXAMEN (
       id_examen INT NOT NULL
     , id_alumno INT
     , id_asignatura INT
     , fecha DATE
     , contenido CHAR(60)
     , leido BOOL
);

CREATE TABLE IMPARTE (
       id CHAR(10) NOT NULL
     , id_profesor INT
     , id_asignatura INT
);

ALTER TABLE CURSO
  ADD CONSTRAINT PK_CURSO
      PRIMARY KEY (id_curso);

ALTER TABLE CONCEPTO
  ADD CONSTRAINT PK_CONCEPTO
      PRIMARY KEY (id_concepto);

ALTER TABLE USUARIO
  ADD CONSTRAINT PK_USUARIO
      PRIMARY KEY (id_usuario);

ALTER TABLE ALUMNO
  ADD CONSTRAINT PK_ALUMNO
      PRIMARY KEY (id_alumno);

ALTER TABLE PROFESORES
  ADD CONSTRAINT PK_PROFESORES
      PRIMARY KEY (id_profesor);

ALTER TABLE ASIGNATURA
  ADD CONSTRAINT PK_ASIGNATURA
      PRIMARY KEY (id_asignatura);

ALTER TABLE MATRICULA
  ADD CONSTRAINT PK_MATRICULA
      PRIMARY KEY (id_matricula);

ALTER TABLE NOTA
  ADD CONSTRAINT PK_NOTA
      PRIMARY KEY (id_nota);

ALTER TABLE INCIDENCIA
  ADD CONSTRAINT PK_INCIDENCIA
      PRIMARY KEY (id_incidencia);

ALTER TABLE TAREA
  ADD CONSTRAINT PK_TAREA
      PRIMARY KEY (id_itarea);

ALTER TABLE EXAMEN
  ADD CONSTRAINT PK_EXAMEN
      PRIMARY KEY (id_examen);

ALTER TABLE IMPARTE
  ADD CONSTRAINT PK_IMPARTE
      PRIMARY KEY (id);

ALTER TABLE ALUMNO
  ADD CONSTRAINT FK_ALUMNO_1
      FOREIGN KEY (id_usuario)
      REFERENCES USUARIO (id_usuario);

ALTER TABLE ALUMNO
  ADD CONSTRAINT FK_ALUMNO_2
      FOREIGN KEY (id_curso)
      REFERENCES CURSO (id_curso);

ALTER TABLE PROFESORES
  ADD CONSTRAINT FK_PROFESORES_1
      FOREIGN KEY (id_usuario)
      REFERENCES USUARIO (id_usuario);

ALTER TABLE ASIGNATURA
  ADD CONSTRAINT FK_ASIGNATURA_1
      FOREIGN KEY (id_profesor)
      REFERENCES PROFESORES (id_profesor);

ALTER TABLE ASIGNATURA
  ADD CONSTRAINT FK_ASIGNATURA_2
      FOREIGN KEY (id_curso)
      REFERENCES CURSO (id_curso);

ALTER TABLE MATRICULA
  ADD CONSTRAINT FK_MATRICULA_1
      FOREIGN KEY (id_alumno)
      REFERENCES ALUMNO (id_alumno);

ALTER TABLE MATRICULA
  ADD CONSTRAINT FK_MATRICULA_2
      FOREIGN KEY (id_asignatura)
      REFERENCES ASIGNATURA (id_asignatura);

ALTER TABLE NOTA
  ADD CONSTRAINT FK_NOTA_1
      FOREIGN KEY (id_matricula)
      REFERENCES MATRICULA (id_matricula);

ALTER TABLE NOTA
  ADD CONSTRAINT FK_NOTA_2
      FOREIGN KEY (id_concepto)
      REFERENCES CONCEPTO (id_concepto);

ALTER TABLE INCIDENCIA
  ADD CONSTRAINT FK_INCIDENCIA_1
      FOREIGN KEY (id_alumno)
      REFERENCES ALUMNO (id_alumno);

ALTER TABLE INCIDENCIA
  ADD CONSTRAINT FK_INCIDENCIA_2
      FOREIGN KEY (id_asignatura)
      REFERENCES ASIGNATURA (id_asignatura);

ALTER TABLE TAREA
  ADD CONSTRAINT FK_TAREA_1
      FOREIGN KEY (id_alumno)
      REFERENCES ALUMNO (id_alumno);

ALTER TABLE TAREA
  ADD CONSTRAINT FK_TAREA_2
      FOREIGN KEY (id_asignatura)
      REFERENCES ASIGNATURA (id_asignatura);

ALTER TABLE EXAMEN
  ADD CONSTRAINT FK_EXAMEN_1
      FOREIGN KEY (id_alumno)
      REFERENCES ALUMNO (id_alumno);

ALTER TABLE EXAMEN
  ADD CONSTRAINT FK_EXAMEN_2
      FOREIGN KEY (id_asignatura)
      REFERENCES ASIGNATURA (id_asignatura);

ALTER TABLE IMPARTE
  ADD CONSTRAINT FK_IMPARTE_1
      FOREIGN KEY (id_profesor)
      REFERENCES PROFESORES (id_profesor);

ALTER TABLE IMPARTE
  ADD CONSTRAINT FK_IMPARTE_2
      FOREIGN KEY (id_asignatura)
      REFERENCES ASIGNATURA (id_asignatura);

