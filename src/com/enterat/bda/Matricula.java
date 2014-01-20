/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las matriculas de la bda 
 */

package com.enterat.bda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.services.Conexion;
import com.enterat.util.Constantes;

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

	//Funcion que devuelve las asignaturas de las que esta matriculado un alumno dado
	public String asignaturasMatriculadasPorIdAlumno(int idAlumno)
	{	
		//
		String asignaturas = "";
		
		String sql1 = "SELECT a.asignatura FROM ASIGNATURA a, ALUMNO al, MATRICULA m ";
		String sql2 = "WHERE al.id_alumno = " + idAlumno + " and a.id_asignatura = m.id_asignatura and al.id_alumno = m.id_alumno";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		JSONObject json;		

		try {
			//Obtener JSON
			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE);

			//Si se ha obtenido...
			if(json != null)
			{
				boolean continuar = true;
				int i = 0;
				
				while (continuar){				
					
					String txt2 = "subject"+i;
					if (json.has(txt2))
					{
						//Guardar Asignatura
						asignaturas = asignaturas + json.getString(txt2) + ",";												
					}
					else{
						continuar = false;
					}
					i++;
				}
			}	

		} catch (ClientProtocolException c)	{
			//throw new ExcepcionAplicacion(c.getMessage(),ExcepcionAplicacion.EXCEPCION_CONEXION_SERVIDOR);
		} catch (JSONException e) {
			e.printStackTrace();
			//throw new ExcepcionAplicacion(e.getMessage(),ExcepcionAplicacion.EXCEPCION_CONEXION_SERVIDOR);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//throw new ExcepcionAplicacion(e.getMessage(),ExcepcionAplicacion.EXCEPCION_CONEXION_SERVIDOR);
		} catch (IOException e) {
			e.printStackTrace();
			//throw new ExcepcionAplicacion(e.getMessage(),ExcepcionAplicacion.EXCEPCION_CONEXION_SERVIDOR);
		}
		catch (Exception e) {
			e.printStackTrace();
			//throw new ExcepcionAplicacion(e.getMessage(),ExcepcionAplicacion.EXCEPCION_CONEXION_SERVIDOR);
		}		
		
		//Devolver las asignaturas matriculadas
		return asignaturas;
	}

}