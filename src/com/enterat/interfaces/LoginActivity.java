package com.enterat.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.bda.Imparte;
import com.enterat.bda.Padre;
import com.enterat.bda.Usuario;
import com.enterat.services.Conexion;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private String asignaturas = "";
	private String nombre	   = "";
	private String apellidos   = "";
	private String nombrePadre		= "";
	private String apellidosPadre	= "";
	private int idAlumno		  	= 0;
	private String nombreAlumno		= "";
	private String apellidosAlumno	= "";
	private int cursoAlumno			= 0;
	private String idGcm = "";
	private GoogleCloudMessaging gcm;
	private final String GCM_SERVICE_ID = "271634907647";
	private Usuario usuario;
	private Context context;
	private Padre padre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_login);
		
		this.setContext(LoginActivity.this);
		
		//Recuperar datos del usuario...
		usuario = recuperarPreferenciasLogIn();
		
		//...si existe
		if(usuario.getIdUsuario() != 0){
		
			//LogInAsyncTask task = new LogInAsyncTask();
			//
			//task.setUser( user.getUser() );
			//task.setPassword( user.getPassword() );
			//task.setContext( LoginActivity.this );
			//task.execute();			
			
			if(usuario.getTipo() == Constantes.PROFESOR){
				Intent intent = new Intent(this, ProfesorAdd.class);
		        startActivity(intent);
			}
			else{
				if(usuario.getTipo() == Constantes.PADRE){
					Intent intent = new Intent(this, PadresMainActivity.class);
			        startActivity(intent);
				}
			}
			
			//Se destruye esta actividad
			finish();
		}
	}

	public void loginClick(View v) {

		// Comprobar que hay conexion a INTERNET !!!
		if (Conexion.isConnected(LoginActivity.this)) {
			
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);

			final String user = textUser.getText().toString();
			final String pass = textPass.getText().toString();

			LogInAsyncTask task = new LogInAsyncTask();
			task.setUser(user);
			task.setPassword(pass);
			//task.setContext(context);
			task.execute();
			
		} else {
			// Mostrar error de conexion
			showNoConnectionWarning();
		}
	}

	public void showNoConnectionWarning() {
		// Mostrar TOAST --> NO INTERNET !!!
		Toast.makeText(this,getResources().getString(R.string.msg_sin_conexion),Toast.LENGTH_SHORT).show();
	}

	//
	private Usuario recuperarPreferenciasLogIn() {

		
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);

		usuario = new Usuario();
		usuario.setIdUsuario(preferences.getInt("idUsuario", 0));
		usuario.setUser(preferences.getString("usuario", ""));
		usuario.setPassword(preferences.getString("password", ""));
		usuario.setTipo(preferences.getInt("tipo", 0));
		usuario.setId_gcm(preferences.getString("idGcm", ""));
		
		asignaturas = preferences.getString("asignaturas", "");
		nombre		= preferences.getString("nombre", "");
		apellidos	= preferences.getString("apellidos", "");
		
		nombrePadre 	= preferences.getString("nombrePadre", "");
		apellidosPadre	= preferences.getString("apellidosPadre", "");
		idAlumno		= preferences.getInt("id_alumno", 0);
		nombreAlumno	= preferences.getString("nombreAlumno", "");
		apellidosAlumno = preferences.getString("apellidosAlumno", "");
		cursoAlumno		= preferences.getInt("cursoAlumno", 0);
		idGcm = preferences.getString("idGcm", "");
		
		return usuario;
	}

	
	private void guardarPreferenciasLogIn(Usuario user) {

		//
		SharedPreferences preferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		//
		editor.putInt("idUsuario", user.getIdUsuario());
		editor.putString("usuario", user.getUser());
		editor.putString("password", user.getPassword());
		editor.putInt("tipo", user.getTipo());
		
		editor.putString("asignaturas", asignaturas);
		editor.putString("nombre", nombre);
		editor.putString("apellidos", apellidos);
		
		editor.putString("nombrePadre", nombrePadre);
		editor.putString("apellidosPadre", apellidosPadre);
		editor.putInt("id_alumno", idAlumno);
		editor.putString("nombreAlumno", nombreAlumno);
		editor.putString("apellidosAlumno", apellidosAlumno);
		editor.putInt("cursoAlumno", cursoAlumno);
		editor.putString("idGcm", idGcm);
		
		editor.commit();
	}
	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
	
	
	// **********************************************************************
	// TAREA ASINCRONA --> LOG IN
	// **********************************************************************
	private class LogInAsyncTask extends AsyncTask<Void, Void, Usuario> {

		private String user, pass;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			super.onPostExecute(usuario);
						
			String mensaje;
			
			//Si existe el usuario
			if(usuario != null){
				
				//Crear mensaje login OK
				mensaje = getResources().getString(R.string.msg_login_ok);
								
				//Dependiendo del tipo de usario...
				if(usuario.getTipo() == Constantes.PROFESOR){
					//Guardar las asignaturas que imparte dicho profesor...
					Imparte imparte = new Imparte();
					asignaturas = imparte.queImparteProfesorPorIdUsuario( usuario.getIdUsuario() );
					
					//..as� como su nombre y apellidos
					nombre    = imparte.getProfesor().getNombre();
					apellidos = imparte.getProfesor().getApellidos();
					
					//...mostrar men� de Profesor...
					Intent intent = new Intent(context, ProfesorAdd.class);
			        startActivity(intent);
			      
			        //Guardar datos en Preferences
					guardarPreferenciasLogIn( usuario );
					
					//Se destruye esta actividad
					finish();
				}
				else{
					if(usuario.getTipo() == Constantes.PADRE){
						TareaObtenerDatosTask tareaDatos = new TareaObtenerDatosTask();	
						tareaDatos.setUsuario(usuario);
						tareaDatos.execute();											
						
					}
				}
				
				
			}
			else			
			{
				//Si no existe el usuario... crear mensaje de error
				mensaje = getResources().getString(R.string.msg_login_no_ok);
				Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
			}
			
			//Mostrar mensaje
			
		}
		
		@Override
		protected Usuario doInBackground(Void... arg0) {			
			//Realizar identificacion con el servidor
			return Usuario.identificarse(user, pass);
		}

		// Atributos necesarios para conectar con el servidor
//		public void setContext(Context context) {
//			this.context = context;
//		}

		public void setUser(String user) {
			this.user = user;
		}

		public void setPassword(String pass) {
			this.pass = pass;
		}
	}
	
	
	
	
	private class TareaObtenerDatosTask extends AsyncTask<String,Integer,Void>
	{
		private Usuario usuario;
		
		@Override
        protected Void doInBackground(String... params) 
		{									
			obtenerDatosPadreAlumnoPorIdUsuario(usuario);
			
			return null;
        }
		
		
		public void setUsuario(Usuario user) {
			this.usuario = user;
		}
	}
	
	public void obtenerDatosPadreAlumnoPorIdUsuario(Usuario us){
		
		this.usuario = us;
				
		String sql1 = "SELECT p.nombre as nPadre, p.apellidos as aPadre, a.id_alumno as idAlumno, a.nombre as nAlumno, a.apellidos as aAlumno, a.id_curso, u.id_gcm as idGcm FROM USUARIO u, PADRE p, ALUMNO a ";
		String sql2 = "WHERE u.id_usuario = " + us.getIdUsuario() + " and u.id_usuario = p.id_usuario and p.id_alumno = a.id_alumno";
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));
				
		
			//Obtener JSON con las asignaturas que imparte
			WSConection wsconect = new WSConection(context, pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {
				
				@Override
				public void getJsonFromWS(JSONObject json) {
					// TODO Auto-generated method stub
					if(setDataJson(json, usuario)){
						openPadreActivity(padre);
					}
					
				}
			});				
	}

	
	public boolean setDataJson(JSONObject json, Usuario us){
		if(json != null)
		{		
			try{
				padre = new Padre();
				padre.setUsuario(us);
				
				if (json.has("nPadre"))
				{
					padre.setNombre( json.getString("nPadre") );							
				}
				if (json.has("aPadre"))
				{
					padre.setApellidos( json.getString("aPadre") );							
				}
				if (json.has("idAlumno"))
				{
					padre.getAlumno().setId_alumno( json.getInt("idAlumno") );							
				}
				if (json.has("nAlumno"))
				{
					padre.getAlumno().setNombre( json.getString("nAlumno") );							
				}
				if (json.has("aAlumno"))
				{
					padre.getAlumno().setApellidos( json.getString("aAlumno") );							
				}
				if (json.has("id_curso"))
				{
					padre.getAlumno().getCurso().setId_curso( Integer.parseInt(json.getString("id_curso")) );							
				}		
				if (json.has("idGcm"))
				{
					padre.getUsuario().setId_gcm( json.getString("idGcm") );							
				}
				return true;
			}catch(JSONException ex){
				
			}
		}
		return false;

	}
	
	public void openPadreActivity(Padre padre){
		nombrePadre		= padre.getNombre();
		apellidosPadre	= padre.getApellidos();
		idAlumno		= padre.getAlumno().getId_alumno();
		nombreAlumno	= padre.getAlumno().getNombre();
		apellidosAlumno	= padre.getAlumno().getApellidos();
		cursoAlumno		= padre.getAlumno().getCurso().getId_curso();
		
		if(padre.getUsuario() != null && padre.getUsuario().getId_gcm() != null && !padre.getUsuario().getId_gcm().equals("") && !padre.getUsuario().getId_gcm().equals("null")){
			idGcm			= padre.getUsuario().getId_gcm();
		}	
			//Guardar datos en Preferences
			guardarPreferenciasLogIn(usuario);
			
			//...o mostrar menú de Padre
			Intent intent = new Intent(context, PadresMainActivity.class);
	        startActivity(intent);
	             			
			//Se destruye esta actividad
			finish();
			Toast.makeText(context, "LOGIN OK", Toast.LENGTH_LONG).show();
		
		
		
	}

}