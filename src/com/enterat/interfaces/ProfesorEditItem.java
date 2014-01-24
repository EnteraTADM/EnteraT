package com.enterat.interfaces;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enterat.R;
import com.enterat.bda.Anuncio;
import com.enterat.bda.Examen;
import com.enterat.bda.Incidencia;
import com.enterat.bda.Tarea;
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
		SharedPreferences prefe = getSharedPreferences("guardado_profedit", Context.MODE_PRIVATE);
		TareaUpdateTask tarUpd = new TareaUpdateTask();
		tarUpd.setConcepto(datos.getString("concepto"));
		tarUpd.setConceptoNew(prefe.getString("contenido",""));
		tarUpd.setFecha(datos.getString("fecha"));
		tarUpd.setTipo(datos.getInt("tipo"));
		tarUpd.setContext(ProfesorEditItem.this);
		tarUpd.execute();
	
	}
	
	
	
	private class TareaUpdateTask extends AsyncTask<String,Integer,Integer>
	{
		private int tipo;
		private String fecha;
		private String concepto;
		private String conceptoNew;
		int modificado = 0;
		private Context context;
		
		@Override
        protected Integer doInBackground(String... params) 
		{
			switch(tipo) { 
			case Constantes.SP_TAREA:
				//TAREA:
				Tarea tarea = new Tarea();
				modificado = tarea.modificarTarea(fecha, concepto, conceptoNew);

				break;
			case Constantes.SP_EXAMEN:
				//EXAMEN:
				Examen examen = new Examen();
				modificado = examen.modificarExamen(fecha, concepto, conceptoNew);

				break;
			case Constantes.SP_ANUNCIO:
				//Constantes.SP_ANUNCIO:
				Anuncio anuncio = new Anuncio();
				modificado = anuncio.modificarAnuncio(fecha, concepto, conceptoNew);

				break;
			case Constantes.SP_INCIDENCIA:
				//INCIDENCIA:
				Incidencia incidencia = new Incidencia();
				modificado = incidencia.modificarIncidencia(fecha, concepto, conceptoNew);

				break;
			default:

				break;
			}
			return modificado;
        }
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			
			if(result.compareTo(0) == 0){
				Toast.makeText(context, R.string.msg_modificar_fail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, R.string.msg_modificar_ok, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, ProfesorMain.class);
		        startActivity(intent);
		        finish();
			}			
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public void setConcepto(String concepto) {
			this.concepto = concepto;
		}

		public void setConceptoNew(String conceptoNew) {
			this.conceptoNew = conceptoNew;
		}

		public void setContext(Context context) {
			this.context = context;
		}
				
		
	}
	
}