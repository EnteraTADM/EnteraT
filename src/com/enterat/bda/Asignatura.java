/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las asignaturas de la bda 
 */

package com.enterat.bda;

public class Asignatura {

	//Atributos de clase
	private int id_asignatura;
	private String asignatrua;
	private String codigo;
	private String descripcion;
	private Curso curso;
	private String contenido;
	private String evaluacion;
	
	//Getters and Setters
	public int getId_asignatura() {
		return id_asignatura;
	}
	public void setId_asignatura(int id_asignatura) {
		this.id_asignatura = id_asignatura;
	}
	public String getAsignatrua() {
		return asignatrua;
	}
	public void setAsignatrua(String asignatrua) {
		this.asignatrua = asignatrua;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}
	
}