/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las tareas de la bda 
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
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;

public class Tarea {

	public Tarea() {
		super();

		this.asignatura = new Asignatura();
		this.alumno 	= new Alumno();		
	}

	//Atributos de clase
	private int id_tarea;
	private Asignatura asignatura;
	private Alumno alumno;
	private String fecha;
	private String contenido;
	private String observaciones;
	private int leido;

	//Getters and Setters
	public int getId_tarea() {
		return id_tarea;
	}
	public void setId_tarea(int id_tarea) {
		this.id_tarea = id_tarea;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getLeido() {
		return leido;
	}
	public void setLeido(int leido) {
		this.leido = leido;
	}

	//INSERTAR TAREA
	public int insertarTarea(int idProfesor)
	{
		JSONObject json;

		int id 			 = this.getId_tarea();
		int alumno		 = this.getAlumno().getId_alumno();
		int asignatura 	 = this.getAsignatura().getId_asignatura();
		String contenido = this.getContenido();
		String fecha 	 = this.getFecha();		
		String observa   = this.getObservaciones();
		int leido		 = this.getLeido();

		//
		String sql1 = "INSERT INTO TAREA (id_itarea, id_alumno, id_asignatura, fecha, concepto, observaciones, leido, id_profesor)";
		String sql2 = "VALUES (" + id + ", " + alumno + ", " + asignatura + ", '" + fecha + "', '" + contenido + "', '" + observa + "', " + leido + ", " + idProfesor + ")";

		//
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));				

		//Obtener JSON
		try {

			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_INSERTAR, Constantes.SERV_OTROS);

			if(json != null)
			{
				//Tarea insertada
				return 1;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}		

		//Tarea NO insertada
		return 0;
	}

	//ELIMINAR TAREA
	public int eliminarTarea(String asignatura, String fecha, String concepto)
	{
		//
		String sql1 = "DELETE FROM TAREA ";
		String sql2 = "WHERE fecha = '" + fecha + "' and concepto = '" + concepto + "'";

		//
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));				

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				// TODO Auto-generated method stub

			}
		});

		//Tarea eliminada
		return 1;
	}
	
	public int modificarTarea(String fecha, String concepto, String conceptoNew)
	{
		//
		String sql1 = "UPDATE TAREA SET concepto = '"+conceptoNew+"' ";
		String sql2 = "WHERE fecha = '" + fecha + "' and concepto = '" + concepto + "'";

		//
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));				

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_MODIFICAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				// TODO Auto-generated method stub

			}
		});

		//Tarea eliminada
		return 1;
	}

}