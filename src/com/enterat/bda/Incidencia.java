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

public class Incidencia {
	//Atributos de clase
	private int id_incidencia;
	private Asignatura asignatura;
	private Alumno alumno;
	private String fecha;
	private String contenido;
	private int leido;

	public Incidencia() {
		super();

		this.asignatura = new Asignatura();
		this.alumno 	= new Alumno();
	}
	//Getters and Setters
	public int getId_incidencia() {
		return id_incidencia;
	}
	public void setId_incidencia(int id_incidencia) {
		this.id_incidencia = id_incidencia;
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
	public int insertarIncidencia(int idProfesor)
	{
		JSONObject json;

		int id 			 = this.getId_incidencia();
		int asignatura 	 = this.getAsignatura().getId_asignatura();
		int alumno		 = this.getAlumno().getId_alumno();
		String contenido = this.getContenido();
		String fecha 	 = this.getFecha();

		//
		String sql1 = "INSERT INTO INCIDENCIA (id_incidencia, id_alumno, id_asignatura, fecha, concepto, leido, id_profesor)";
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
				//Incidencia insertada
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

		//Incidencia NO insertada
		return 0;
	}


	//ELIMINAR INCIDENCIA
	public int eliminarIncidencia(String asignatura, String fecha, String concepto)
	{
		//
		String sql1 = "DELETE FROM INCIDENCIA ";
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

		//Incidencia eliminada
		return 1;
	}

}
