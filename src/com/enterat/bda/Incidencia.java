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
	public int insertarIncidencia()
	{
		JSONObject json;

		int id 			 = this.getId_incidencia();
		int asignatura 	 = this.getAsignatura().getId_asignatura();
		int alumno		 = this.getAlumno().getId_alumno();
		String contenido = this.getContenido();
		String fecha 	 = this.getFecha();

		//
		String sql1 = "INSERT INTO INCIDENCIA (id_incidencia, id_alumno, id_asignatura, fecha, concepto, leido)";
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
				//Incidencia insertada
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

		//Incidencia NO insertada
		return 0;
	}

	//OBTENER INCIDENCIAS
	public ArrayList<Incidencia> obtenerIncidencias( int idCurso )
	{
		Incidencia incidencia;
		ArrayList<Incidencia> listaIncidencias = new ArrayList<Incidencia>();

		//
		String sql1 = "SELECT a.asignatura, t.fecha, t.concepto FROM CURSO c, ASIGNATURA a, INCIDENCIA t";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and c.id_curso = a.id_curso and a.id_asignatura = t.id_asignatura";

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
						incidencia = new Incidencia();

						incidencia.getAsignatura().setAsignatura( json.getString( txt1 ) );	
						if (json.has( txt2 )) { incidencia.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { incidencia.setFecha( json.getString( txt3 ) ); }

						listaIncidencias.add( incidencia );
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
		return listaIncidencias;
	}


}