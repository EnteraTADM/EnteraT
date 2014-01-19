/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar que profesores imparten que asignaturas 
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

public class Imparte {

	//Atributos de clase
	private int id_imparte;
	private Profesor profesor;
	private Asignatura asignatura;
		
	public Imparte() {
		
		this.profesor   = new Profesor();
		this.asignatura = new Asignatura();
	}
	
	
	//Getters and Setters
	public int getId_imparte() {
		return id_imparte;
	}
	public void setId_imparte(int id_imparte) {
		this.id_imparte = id_imparte;
	}
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	private void buscarProfesorPorIdUsuario(int idUsuario){
	
		//1.- Buscar las asignaturas que imparte un profesor por su id_usuario		
		String sql1 = "SELECT id_profesor, nombre, apellidos FROM PROFESORES ";
		String sql2 = "WHERE id_usuario = " + idUsuario;

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		JSONObject json;		

		try {
			//Obtener JSON con las asignaturas que imparte
			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_OTROS);

			//Si se ha obtenido...
			if(json != null)
			{
				if (json.has("id_profesor"))
				{
					profesor.setId_profesor( Integer.parseInt(json.getString("id_profesor")) );											
				}
				if (json.has("nombre"))
				{
					profesor.setNombre( json.getString("nombre") );											
				}
				if (json.has("apellidos"))
				{
					profesor.setApellidos( json.getString("apellidos") );											
				}			
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
	}
	
	public String queImparteProfesorPorIdUsuario(int idUsuario){
		
		//
		buscarProfesorPorIdUsuario( idUsuario );
		
		//
		String asignaturas = "";
		
		//1.- Buscar las asignaturas que imparte un profesor por su id_usuario		
		String sql1 = "SELECT a.id_asignatura, a.asignatura FROM USUARIO u, PROFESORES p, IMPARTE i, ASIGNATURA a ";
		String sql2 = "WHERE u.id_usuario = " + idUsuario + " and u.id_usuario = p.id_usuario and p.id_profesor = i.id_profesor and i.id_asignatura = a.id_asignatura";
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));
				
		JSONObject json;		

		try {
			//Obtener JSON con las asignaturas que imparte
			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE);

			//Si se ha obtenido...
			if(json != null)
			{
				boolean continuar = true;
				int i = 0;
				
				while (continuar){
				
					String txt1 = "code"+i;
					String txt2 = "subject"+i;
					if (json.has(txt1))
					{
						//Guardar CÓDIGO asignatura
						asignaturas = asignaturas + json.getString(txt1) + "-";
						
						if (json.has(txt2))
						{
							//Guardar NOMBRE asignatura
							asignaturas = asignaturas + json.getString(txt2) + ",";
						}						
					}
					else{
						continuar = false;
					}
					i++;
				}				
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

		//Devolver asignaturas
		return asignaturas;
	}
	
}