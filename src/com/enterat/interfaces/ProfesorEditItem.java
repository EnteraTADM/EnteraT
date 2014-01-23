package com.enterat.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enterat.R;
import com.enterat.bda.Alumno;
import com.enterat.bda.Anuncio;
import com.enterat.bda.Asignatura;
import com.enterat.bda.Examen;
import com.enterat.bda.Incidencia;
import com.enterat.bda.Tarea;
import com.enterat.services.Conexion;
import com.enterat.util.Constantes;

public class ProfesorEditItem extends Activity{

	Bundle datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_profesor_edit);
		
		datos = getIntent().getExtras();
	
		
		//Recuperar asignaturas que imparte el profesor
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);
				
		TextView asignatura = (TextView)findViewById(R.id.asignatura_TextView2);
		asignatura.setText(datos.getString("asignatura"));
			
		EditText contenido = (EditText)findViewById(R.id.contenido_t2);
		contenido.setText(datos.getString("concepto"));
		
		TextView fecha = (TextView)findViewById(R.id.dataTextView2);
		fecha.setText(datos.getString("fecha"));
		
		TextView tipoS = (TextView)findViewById(R.id.Type_TextView2);
		tipoS.setText(datos.getString("tipoS"));
		
		SharedPreferences pref = getSharedPreferences("guardado_profedit",Context.MODE_PRIVATE);
		SharedPreferences.Editor editar=pref.edit();
		
		editar.putString("contenido",contenido.getText().toString());
		editar.putString("asignatura",asignatura.getText().toString());
		editar.putString("fecha",fecha.getText().toString());
		editar.putString("tipoS",tipoS.getText().toString());
		
		editar.commit();
	}
		
	@Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences pref = getSharedPreferences("guardado_profedit",Context.MODE_PRIVATE);
		SharedPreferences.Editor editar=pref.edit( );

		TextView asignatura = (TextView)findViewById(R.id.asignatura_TextView2);
		asignatura.setText(datos.getString("asignatura"));
			
		EditText contenido = (EditText)findViewById(R.id.contenido_t2);
		contenido.setText(datos.getString("concepto"));
		
		TextView fecha = (TextView)findViewById(R.id.dataTextView2);
		fecha.setText(datos.getString("fecha"));
		
		TextView tipoS = (TextView)findViewById(R.id.Type_TextView2);
		tipoS.setText(datos.getString("tipoS"));

		editar.putString("contenido",contenido.getText().toString());
		editar.putString("asignatura",asignatura.getText().toString());
		editar.putString("fecha",fecha.getText().toString());
		editar.putString("tipoS",tipoS.getText().toString());

		editar.commit( );
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Recuperamos datos al rotar pantalla o al volver a esta activity
		SharedPreferences prefe = getSharedPreferences("guardado_profedit", Context.MODE_PRIVATE);
				
		// Recuperamos lo escrito en el edit-text
		String text_contenido = prefe.getString("contenido","");
		EditText contenido = (EditText)findViewById(R.id.contenido_t2);
		contenido.setText(text_contenido);

		String text_asignatura = prefe.getString("asignatura","");
		TextView asignatura = (TextView)findViewById(R.id.asignatura_TextView2);
		asignatura.setText(text_asignatura);
		
		String text_fecha = prefe.getString("fecha","");
		TextView fecha = (TextView)findViewById(R.id.dataTextView2);
		fecha.setText(text_fecha);
		
		String text_tipoS = prefe.getString("tipoS","");
		TextView tipoS = (TextView)findViewById(R.id.Type_TextView2);
		tipoS.setText(text_tipoS);
	}	
	
	//Boton publicar	
	public void publicarClick(View v) {


		switch(datos.getInt("tipo")) {
		//INSERTAR ANUNCIO
		case Constantes.SP_ANUNCIO:			

			Anuncio anuncio = new Anuncio();				
					
			
//			TareaInsertarAnuncioTask anuncTask = new TareaInsertarAnuncioTask();
//			anuncTask.setIdProfesor(idProfesor);
//			anuncTask.setAnuncio(anuncio);
//			anuncTask.setContext(ProfesorEditItem.this);
//			anuncTask.execute();			

			break;
			
		//INSERTAR TAREA
		case Constantes.SP_TAREA: 
			
			Tarea tarea = new Tarea();				
	

//			TareaInsertarTareaTask tareaTask = new TareaInsertarTareaTask();
//			tareaTask.setIdProfesor(idProfesor);
//			tareaTask.setTarea(tarea);
//			tareaTask.setContext(ProfesorEditItem.this);
//			tareaTask.execute();		
			
			break;
			
		//INSERTAR INCIDENCIA
		case Constantes.SP_INCIDENCIA: 

			Incidencia incidencia = new Incidencia();				

//			incidencia.setId_incidencia(0);
//			incidencia.setAsignatura(asignatura);
//			incidencia.setAlumno(alumno);
//			incidencia.setContenido(contenido);
//			incidencia.setFecha(fecha);
//			incidencia.setLeido(0);				
//
//			TareaInsertarIncidenciaTask incidenciaTask = new TareaInsertarIncidenciaTask();
//			incidenciaTask.setIdProfesor(idProfesor);
//			incidenciaTask.setIncidencia(incidencia);
//			incidenciaTask.setContext(ProfesorEditItem.this);
//			incidenciaTask.execute();		

			break;
			
		//INSERTAR EXAMEN
		case Constantes.SP_EXAMEN: 
		
			Examen examen = new Examen();				

//			examen.setId_examen(0);
//			examen.setAsignatura(asignatura);
//			examen.setAlumno(alumno);
//			examen.setContenido(contenido);
//			examen.setFecha(fecha);
//			examen.setLeido(0);				
//
//			TareaInsertarExamenTask examenTask = new TareaInsertarExamenTask();
//			examenTask.setIdProfesor(idProfesor);
//			examenTask.setExamen(examen);
//			examenTask.setContext(ProfesorEditItem.this);
//			examenTask.execute();		

			break;			 
		default: 
			//error !!
			break;
		}
		
	
	}
	
	
	private class TareaInsertarAnuncioTask extends AsyncTask<String,Integer,Integer>
	{
		private Anuncio anuncio;
		private Context context;
		private int idProfesor;

		@Override
        protected Integer doInBackground(String... params) 
		{
			if (anuncio.insertarAnuncio(idProfesor) == 0){
				//ERROR
				return Integer.valueOf(0);				
			}
			else{
				//INSERTADA OK
				return Integer.valueOf(1);								
			}
			
        }
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			
			if(result.compareTo(0) == 0){
				Toast.makeText(context, R.string.msg_publicar_anuncio_fail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, R.string.msg_publicar_anuncio_ok, Toast.LENGTH_LONG).show();
				notificar( this.anuncio.getAsignatura().getId_asignatura() );
			}
			
		}		
		
		public void setAnuncio(Anuncio anuncio) {
			this.anuncio = anuncio;
		}
		
		public void setContext(Context context) {
			this.context = context;
		}
		
		public void setIdProfesor(int IdProfesor) {
			this.idProfesor = IdProfesor;
		}
	}
	
	
	private class TareaInsertarTareaTask extends AsyncTask<String,Integer,Integer>
	{
		private Tarea tarea;
		private Context context;
		private int idProfesor;
		
		@Override
        protected Integer doInBackground(String... params) 
		{
			if (tarea.insertarTarea(idProfesor) == 0){
				//ERROR
				return Integer.valueOf(0);				
			}
			else{
				//INSERTADA OK
				return Integer.valueOf(1);								
			}			
        }
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			
			if(result.compareTo(0) == 0){
				Toast.makeText(context, R.string.msg_publicar_tarea_fail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, R.string.msg_publicar_tarea_ok, Toast.LENGTH_LONG).show();
				notificar( this.tarea.getAsignatura().getId_asignatura() );
			}			
		}
				
		public void setTarea(Tarea tarea) {
			this.tarea = tarea;
		}
		
		public void setContext(Context context) {
			this.context = context;
		}
		
		public void setIdProfesor(int IdProfesor) {
			this.idProfesor = IdProfesor;
		}
	}
	
	
	private class TareaInsertarIncidenciaTask extends AsyncTask<String,Integer,Integer>
	{
		private Incidencia incidencia;
		private Context context;
		private int idProfesor;
		
		@Override
        protected Integer doInBackground(String... params) 
		{
			if (incidencia.insertarIncidencia(idProfesor) == 0){
				//ERROR
				return Integer.valueOf(0);				
			}
			else{
				//INSERTADA OK
				return Integer.valueOf(1);								
			}			
        }
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			
			if(result.compareTo(0) == 0){
				Toast.makeText(context, R.string.msg_publicar_incidencia_fail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, R.string.msg_publicar_incidencia_ok, Toast.LENGTH_LONG).show();
				notificar( this.incidencia.getAsignatura().getId_asignatura() );
			}			
		}
				
		public void setIncidencia(Incidencia incidencia) {
			this.incidencia = incidencia;
		}
		
		public void setContext(Context context) {
			this.context = context;
		}
		
		public void setIdProfesor(int IdProfesor) {
			this.idProfesor = IdProfesor;
		}
	}
	
	
	private class TareaInsertarExamenTask extends AsyncTask<String,Integer,Integer>
	{
		private Examen examen;
		private Context context;
		private int idProfesor;
		
		@Override
        protected Integer doInBackground(String... params) 
		{
			if (examen.insertarExamen(idProfesor) == 0){
				//ERROR
				return Integer.valueOf(0);				
			}
			else{
				//INSERTADA OK
				return Integer.valueOf(1);								
			}			
        }
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			
			if(result.compareTo(0) == 0){
				Toast.makeText(context, R.string.msg_publicar_examen_fail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, R.string.msg_publicar_examen_ok, Toast.LENGTH_LONG).show();
				notificar( this.examen.getAsignatura().getId_asignatura() );
			}			
		}
				
		public void setExamen(Examen examen) {
			this.examen = examen;
		}
		
		public void setContext(Context context) {
			this.context = context;
		}
		
		public void setIdProfesor(int IdProfesor) {
			this.idProfesor = IdProfesor;
		}		
	}
	
	//
	public void notificar(int idAsignatura){
		NotificationTask notifTask = new NotificationTask();
		notifTask.setIdAsignatura( idAsignatura );
		notifTask.execute();
	}
	
	//
	private class NotificationTask extends AsyncTask<String,Integer,Void>
	{		
		private int IdAsignatura;
		
		@Override
        protected Void doInBackground(String... params) 
		{
			//String misXurros = "APA91bGETnNSIIMCOtvtjIR1LBHyhetVKpBOLwiDQH0kYjo57TWXWQtil_rOLbBsI8JmMJuop3woYCE6JvCy_iCVucX7DH7zBEn-Cx9YIj6Gi0SrvJ4LaXof2Mllg5CrNvu0WbpZ7J-8iPNzBHXWxivULSyuy1efpQ,APA91bFUA-QEIj70qZb6K0jRCaIPbFsFHfUQvNLnJDwX_uvGyyZVHs89dvfdfN7YBtOxwRO9Dizdvzi5YTopcIou-l2Kv8X1H5Ap621actqU0xuF_xymoFL6CEDVy9qR83MfqCtSgyrlusxgnnztx1YthG8n-gFf3Q";
			String misXurros = recuperaXurros( this.IdAsignatura );
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("xurros", misXurros));
			
			HttpClient client = new DefaultHttpClient();		
			HttpPost request = new HttpPost("http://www.appservices.eshost.es/servicioweb/gcm3.php");		
				
			try {
				request.setHeader("Accept","application/json");
				request.setEntity(new UrlEncodedFormEntity(pairs));
				
				//HttpResponse response = client.execute(request);
				client.execute(request);
				
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;			
        }
		
		private String recuperaXurros( int idAsignatura ){
			
			JSONObject json;
			String xurros = "";
			
			//
			String sql1 = "SELECT u.id_gcm FROM ASIGNATURA asi, CURSO c, USUARIO u, PADRE p, ALUMNO a ";
			String sql2 = "WHERE asi.id_asignatura = " + idAsignatura + " and asi.id_curso = c.id_curso and c.id_curso = a.id_curso and a.id_alumno = p.id_alumno and p.id_usuario = u.id_usuario";

			//
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("sqlquery1", sql1));
			pairs.add(new BasicNameValuePair("sqlquery2", sql2));				

			//Obtener JSON
			try {

				json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE);

				if(json != null)
				{
					boolean continuar = true;
					int i = 0;
					
					while (continuar){
					
						String txt1 = "xurro"+i;						
						if (json.has(txt1))
						{
							//Guardar
							xurros = xurros + json.getString(txt1) + ",";
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

			//
			return xurros;			
		}
		
		public void setIdAsignatura( int idAsignatura ){
			this.IdAsignatura = idAsignatura;
		}
		
	}
}