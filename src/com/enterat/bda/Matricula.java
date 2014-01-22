/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las matriculas de la bda 
 */

package com.enterat.bda;


public class Matricula {

	//Atributos de clase
	private int id_matricula;
	private Alumno alumno;
	private Asignatura asignatura;

	//Constructor
	public Matricula() {
		super();
		
		this.alumno = new Alumno();
		this.asignatura = new Asignatura();
	}
	//Getters and Setters
	public int getId_matricula() {
		return id_matricula;
	}
	public void setId_matricula(int id_matricula) {
		this.id_matricula = id_matricula;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	
	

}