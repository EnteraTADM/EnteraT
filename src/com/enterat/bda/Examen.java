/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los examenes de la bda 
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

public class Examen {

	public Examen() {
		super();

		this.asignatura = new Asignatura();
		this.alumno 	= new Alumno();		
	}

	//Atributos de clase
	private int id_examen;
	private Asignatura asignatura;
	private Alumno alumno;
	private String fecha;
	private String contenido;
	private int leido;

	//Getters and Setters
	public int getId_examen() {
		return id_examen;
	}
	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
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
	public int getLeido() {
		return leido;
	}
	public void setLeido(int leido) {
		this.leido = leido;
	}

	//INSERTAR EXAMEN
	public int insertarExamen(int idProfesor)
	{
		JSONObject json;

		int id 			 = this.getId_examen();
		int asignatura 	 = this.getAsignatura().getId_asignatura();
		int alumno		 = this.getAlumno().getId_alumno();
		String contenido = this.getContenido();
		String fecha 	 = this.getFecha();

		//
		String sql1 = "INSERT INTO EXAMEN (id_examen, id_alumno, id_asignatura, fecha, concepto, leido, id_profesor)";
		String sql2 = "VALUES (" + id + ", " + alumno + ", " + asignatura + ", '" + fecha + "', '" + contenido + "', " + leido + ", " + idProfesor + ")";

		//
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));				

		//Obtener JSON
		try {

			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_INSERTAR, Constantes.SERV_OTROS);

			if(json != null)
			{
				//Examen insertado
				return 1;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		//Examen NO insertado
		return 0;
	}

	//ELIMINAR EXAMEN
	public int eliminarExamen(String asignatura, String fecha, String concepto)
	{
		//
		String sql1 = "DELETE FROM EXAMEN ";
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

		//Examen eliminado
		return 1;
	}
	
	
	public int modificarExamen(String fecha, String concepto, String conceptoNew)
	{
		//
		String sql1 = "UPDATE EXAMEN SET concepto = '"+conceptoNew+"' ";
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