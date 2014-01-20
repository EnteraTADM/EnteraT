package com.enterat.interfaces;

import com.enterat.R;
import com.enterat.bda.Imparte;
import com.enterat.bda.Padre;
import com.enterat.bda.Usuario;
import com.enterat.services.Conexion;
import com.enterat.util.Constantes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_login);
		
		//Recuperar datos del usuario...
		Usuario user = recuperarPreferenciasLogIn();
		
		//...si existe
		if(user.getIdUsuario() != 0){
		
			//LogInAsyncTask task = new LogInAsyncTask();
			//
			//task.setUser( user.getUser() );
			//task.setPassword( user.getPassword() );
			//task.setContext( LoginActivity.this );
			//task.execute();
			
			if(user.getTipo() == Constantes.PROFESOR){
				Intent intent = new Intent(this, ProfesorAdd.class);
		        startActivity(intent);
			}
			else{
				if(user.getTipo() == Constantes.PADRE){
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

			//
			LogInAsyncTask task = new LogInAsyncTask();
			task.setUser(user);
			task.setPassword(pass);
			task.setContext(LoginActivity.this);
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

		//
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);

		//
		Usuario user = new Usuario();
		user.setIdUsuario(preferences.getInt("idUsuario", 0));
		user.setUser(preferences.getString("usuario", ""));
		user.setPassword(preferences.getString("password", ""));
		user.setTipo(preferences.getInt("tipo", 0));
		
		asignaturas = preferences.getString("asignaturas", "");
		nombre		= preferences.getString("nombre", "");
		apellidos	= preferences.getString("apellidos", "");
		
		nombrePadre 	= preferences.getString("nombrePadre", "");
		apellidosPadre	= preferences.getString("apellidosPadre", "");
		idAlumno		= preferences.getInt("id_alumno", 0);
		nombreAlumno	= preferences.getString("nombreAlumno", "");
		apellidosAlumno = preferences.getString("apellidosAlumno", "");
		cursoAlumno		= preferences.getInt("cursoAlumno", 0);

		return user;
	}

	//
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

		editor.commit();
	}
	
	// **********************************************************************
	// TAREA ASINCRONA --> LOG IN
	// **********************************************************************
	private class LogInAsyncTask extends AsyncTask<Void, Void, Usuario> {

		private String user, pass;
		private Context context;

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
					
					//..así como su nombre y apellidos
					nombre    = imparte.getProfesor().getNombre();
					apellidos = imparte.getProfesor().getApellidos();
					
					//...mostrar menú de Profesor...
					Intent intent = new Intent(context, ProfesorAdd.class);
			        startActivity(intent);
				}
				else{
					if(usuario.getTipo() == Constantes.PADRE){
						
						//Guardar datos del padre y del alumno...
						Padre padre = new Padre();						
						padre.obtenerDatosPadreAlumnoPorIdUsuario( usuario.getIdUsuario() );
						
						nombrePadre		= padre.getNombre();
						apellidosPadre	= padre.getApellidos();
						idAlumno		= padre.getAlumno().getId_alumno();
						nombreAlumno	= padre.getAlumno().getNombre();
						apellidosAlumno	= padre.getAlumno().getApellidos();
						cursoAlumno		= padre.getAlumno().getCurso().getId_curso();
						
						//...o mostrar menú de Padre
						Intent intent = new Intent(context, PadresMainActivity.class);
				        startActivity(intent);
					}
				}
				
				//Guardar datos en Preferences
				guardarPreferenciasLogIn( usuario );
				
				//Se destruye esta actividad
				finish();
			}
			else			
			{
				//Si no existe el usuario... crear mensaje de error
				mensaje = getResources().getString(R.string.msg_login_no_ok);
			}
			
			//Mostrar mensaje
			Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected Usuario doInBackground(Void... arg0) {			
			//Realizar identificacion con el servidor
			return Usuario.identificarse(user, pass);
		}

		// Atributos necesarios para conectar con el servidor
		public void setContext(Context context) {
			this.context = context;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public void setPassword(String pass) {
			this.pass = pass;
		}
	}

}