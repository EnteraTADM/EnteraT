/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los padres de la bda 
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

import android.content.Context;

import com.enterat.interfaces.LoginActivity;
import com.enterat.services.Conexion;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;

public class Padre{

	//Atributos de clase
	private int id_padre;
	private String nombre;
	private String apellidos;
	private Usuario usuario;
	private Alumno alumno;
	
	
	public Padre() {
		
		super();
		
		this.usuario = new Usuario();
		this.alumno  = new Alumno();
	}
	//Getters and Setters
	public int getId_padre() {
		return id_padre;
	}
	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
	public void obtenerDatosPadreAlumnoPorIdUsuario(Context context, int idUsuario){
				
		//		
		String sql1 = "SELECT p.nombre as nPadre, p.apellidos as aPadre, a.id_alumno as idAlumno, a.nombre as nAlumno, a.apellidos as aAlumno, a.id_curso, u.id_gcm as idGcm FROM USUARIO u, PADRE p, ALUMNO a ";
		String sql2 = "WHERE u.id_usuario = " + idUsuario + " and u.id_usuario = p.id_usuario and p.id_alumno = a.id_alumno";
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));
				
		
			//Obtener JSON con las asignaturas que imparte
			WSConection wsconect = new WSConection(context, pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {
				
				@Override
				public void getJsonFromWS(JSONObject json) {
					// TODO Auto-generated method stub
					setDataJson(json);
					
				}
			});
			//json = wsconect.getJsonresp();
			
			//json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE);

			//Si se ha obtenido...
				
	}

	
	public void setDataJson(JSONObject json){
		if(json != null)
		{		
			try{
				if (json.has("nPadre"))
				{
					this.setNombre( json.getString("nPadre") );							
				}
				if (json.has("aPadre"))
				{
					this.setApellidos( json.getString("aPadre") );							
				}
				if (json.has("idAlumno"))
				{
					this.getAlumno().setId_alumno( json.getInt("idAlumno") );							
				}
				if (json.has("nAlumno"))
				{
					this.getAlumno().setNombre( json.getString("nAlumno") );							
				}
				if (json.has("aAlumno"))
				{
					this.getAlumno().setApellidos( json.getString("aAlumno") );							
				}
				if (json.has("id_curso"))
				{
					this.getAlumno().getCurso().setId_curso( Integer.parseInt(json.getString("id_curso")) );							
				}		
				if (json.has("idGcm"))
				{
					this.getUsuario().setId_gcm( json.getString("idGcm") );							
				}
			}catch(JSONException ex){
				
			}
		}

	}
	
}