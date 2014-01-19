package com.enterat.util;

public class Constantes {
	
	//Constantes para indicar el tipo de usuario de la aplicacion
	public static final int PROFESOR = 1;
	public static final int PADRE    = 2;
	public static final int ADMIN 	 = 3;
	
	//Constantes para indicar el tipo en el spiner de insertar (profesores)
	public static final int SP_ANUNCIO	  = 0;
	public static final int SP_TAREA      = 1;
	public static final int SP_INCIDENCIA = 2;
	public static final int SP_EXAMEN	  = 3;
	
	//Constantes para indicar el tipo de consulta a ejecutar
	public static final int SQL_CONSULTAR = 0;
	public static final int SQL_INSERTAR  = 1;
	public static final int SQL_MODIFICAR = 2;
	public static final int SQL_ELIMINAR  = 3;
	
	//Constantes para indicar el servicio a ejecutar
	public static final int SERV_OTROS    = 0;
	public static final int SERV_LOGIN   = 1;
	public static final int SERV_IMPARTE = 2;
	public static final int SERV_HIJOS = 3;
	
	static class BDD{
		static enum TABLAS{
			ASIGNATURAS,
			CONCEPTO,
			CURSO,
			EXAMEN,
			IMPARTE,
			INCIDENCIA,
			MATRICULA,
			NOTA,
			PADRE,
			PROFESORES,
			TAREA,
			USUARIO
		}
		
		static enum ASIGNATURAS{
			
		}
		static enum CONCEPTO{
			
		}
		static enum CURSO {}
		static enum EXAMEN {}
		static enum IMPARTE{}
		static enum INCIDENCIA{}
		static enum MATRICULA{}
		static enum NOTA{}
		static enum PADRE{}
		static enum PROFESORES{}
		static enum TAREA{}
		static enum USUARIO{}
		
	
	}	
		
}
