/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los conceptos de las notas de los alumnos 
 */

package com.enterat.bda;

public class ConceptoNota {

	//Atributos de clase
	private int id_concepto;
	private String concepto;
	
	//Getters and Setters
	public int getId_concepto() {
		return id_concepto;
	}
	public void setId_concepto(int id_concepto) {
		this.id_concepto = id_concepto;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}	
		
}