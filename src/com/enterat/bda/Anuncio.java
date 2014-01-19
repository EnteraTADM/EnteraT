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

public class Anuncio {
	//Atributos de clase
		private int id_anuncio;
		private Asignatura asignatura;
		private Alumno alumno;
		private String fecha;
		private String contenido;
		private int leido;

		//Getters and Setters
		public int getId_anuncio() {
			return id_anuncio;
		}
		public void setId_anuncio(int id_anuncio) {
			this.id_anuncio = id_anuncio;
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
		public int insertarAnuncio()
		{
			JSONObject json;

			int id 			 = this.getId_anuncio();
			int asignatura 	 = this.getAsignatura().getId_asignatura();
			int alumno		 = this.getAlumno().getId_alumno();
			String contenido = this.getContenido();
			String fecha 	 = this.getFecha();

			//
			String sql1 = "INSERT INTO ANUNCIO (id_anuncio, id_alumno, id_asignatura, fecha, contenido, leido)";
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
					//anuncio insertado
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

			//Anuncio NO insertado
			return 0;
		}

}
