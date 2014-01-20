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
	public int insertarExamen()
	{
		JSONObject json;

		int id 			 = this.getId_examen();
		int asignatura 	 = this.getAsignatura().getId_asignatura();
		int alumno		 = this.getAlumno().getId_alumno();
		String contenido = this.getContenido();
		String fecha 	 = this.getFecha();

		//
		String sql1 = "INSERT INTO EXAMEN (id_examen, id_alumno, id_asignatura, fecha, concepto, leido)";
		String sql2 = "VALUES (" + id + ", " + alumno + ", " + asignatura + ", '" + fecha + "', '" + contenido + "', " + leido + ")";

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

	//OBTENER EXAMENES
	public ArrayList<Examen> obtenerExamens( int idCurso )
	{
		Examen examen;
		ArrayList<Examen> listaExamens = new ArrayList<Examen>();

		//
		String sql1 = "SELECT a.asignatura, e.fecha, e.concepto FROM CURSO c, ASIGNATURA a, EXAMEN e";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and c.id_curso = a.id_curso and a.id_asignatura = e.id_asignatura";

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

					String txt1 = "subject"+i;
					String txt2 = "contenido"+i;
					String txt3 = "date"+i;
					if (json.has(txt1))
					{
						examen = new Examen();

						examen.getAsignatura().setAsignatura( json.getString( txt1 ) );	
						if (json.has( txt2 )) { examen.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { examen.setFecha( json.getString( txt3 ) ); }

						listaExamens.add( examen );
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

		//
		return listaExamens;
	}

}