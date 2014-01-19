/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los alumnos de la bda 
 */

package com.enterat.bda;

public class Alumno {

	//Atributos de clase
	private int id_alumno;
	private String nombre;
	private String apellidos;
	private Curso curso;
	private Profesor tutor;
	
	//Constructor
	public Alumno() {
		super();
		
		this.curso = new Curso();
		this.tutor = new Profesor();
	}
	
	//Getters and Setters
	public int getId_alumno() {
		return id_alumno;
	}
	public void setId_alumno(int id_alumno) {
		this.id_alumno = id_alumno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}	
	public Profesor getTutor() {
		return tutor;
	}
	public void setTutor(Profesor tutor) {
		this.tutor = tutor;
	}

}