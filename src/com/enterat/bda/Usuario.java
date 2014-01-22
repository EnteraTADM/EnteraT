package com.enterat.bda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.enterat.services.Conexion;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;

public class Usuario {
	
	private int idUsuario   = 0;
	private String user	    = "";
	private String password = "";
	private int tipo;
	private Date fecha;
	private String id_gcm;

	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getId_gcm() {
		return id_gcm;
	}
	public void setId_gcm(String id_gcm) {
		this.id_gcm = id_gcm;
	}
	
	//Funcion para identificarse al abrir la aplicacion
	public static Usuario identificarse(String usuario, String clave)
	{
		String sql1 = "SELECT id_usuario, tipo FROM USUARIO ";
		String sql2 = "WHERE usr LIKE '" + usuario + "' AND psw LIKE '" + clave + "'";
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));
				
		JSONObject json;		
		Usuario user = null;
		
		try {
			//Obtener JSON
			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_LOGIN);
			
			//Si se ha obtenido...
			if(json != null)
			{
				//
				user = new Usuario();
				//
				user.setUser(usuario);
				user.setPassword(clave);
						
				//
				if (json.has("id_usuario"))
				{							
					user.setIdUsuario( Integer.parseInt(json.getString("id_usuario")) );						
				}
				
				//
				if (json.has("tipo"))
				{							
					user.setTipo( Integer.parseInt(json.getString("tipo")) );						
				}
			}	
				
		} catch (ClientProtocolException c)	{
			
			//TODO tratar TODAS las excepciones !!!
			
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
		
		
		//Devolver el usuario identificado
		return user;
	}
	
	
	public boolean updateGcmId(Context context, Integer idUser, String idGcm)
	{
		String sql1 = "UPDATE USUARIO SET id_gcm = "+idGcm;
		String sql2 = " WHERE id_usuario = " + idUser;
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));
				
		Usuario user = null;
		
		
			//Obtener JSON
			
			//json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_MODIFICAR, Constantes.SERV_LOGIN);
//			WSConection wsconect = new WSConection(context, pairs, "service.executeSQL.php", Constantes.SQL_MODIFICAR, Constantes.SERV_LOGIN);
//			wsconect.execute();
//			json = wsconect.getJsonresp();
			
			//Si se ha obtenido...
			
			//Obtener JSON con las asignaturas que imparte
			WSConection wsconect = new WSConection(context, pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {
				
				@Override
				public void getJsonFromWS(JSONObject json) {
					// TODO Auto-generated method stub
					setDataJson(json);
				}
			});
			
			return true;
	
	}
	
	public boolean setDataJson(JSONObject json){
		return true;

	}
}