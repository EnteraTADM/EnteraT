/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las notas de las matrículas de los alumnos en las asignaturas 
 */

package com.enterat.bda;

import java.sql.Date;

public class Nota {

	//Atributos de clase
	private int id_nota;
	private Matricula matricula;
	private ConceptoNota concepto;
	private int nota;
	private Date fecha;
	private String observaciones;
	
	//Getters and Setters
	public int getId_nota() {
		return id_nota;
	}
	public void setId_nota(int id_nota) {
		this.id_nota = id_nota;
	}
	public Matricula getMatricula() {
		return matricula;
	}
	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	public ConceptoNota getConcepto() {
		return concepto;
	}
	public void setConcepto(ConceptoNota concepto) {
		this.concepto = concepto;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
		
}