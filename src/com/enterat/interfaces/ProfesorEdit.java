package com.enterat.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.bda.Anuncio;
import com.enterat.bda.Examen;
import com.enterat.bda.Incidencia;
import com.enterat.bda.Tarea;
import com.enterat.services.Conexion;
import com.enterat.util.Constantes;
import com.enterat.util.MyListAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ProfesorEdit extends Activity {
	
	Tarea tarea			  = new Tarea();
	Examen examen		  = new Examen();
	Anuncio anuncio		  = new Anuncio();
	Incidencia incidencia = new Incidencia();
	
	ArrayList<Tarea> listaTareas		   = new ArrayList<Tarea>();
	ArrayList<Examen> listaExamens 		   = new ArrayList<Examen>();
	ArrayList<Anuncio> listaAnuncios 	   = new ArrayList<Anuncio>();
	ArrayList<Incidencia> listaIncidencias = new ArrayList<Incidencia>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_padres_tasks);	

		ObtenerContenidoTask contenidoTask = new ObtenerContenidoTask();
		contenidoTask.setContext(ProfesorEdit.this);
		contenidoTask.execute();	
	}

	//
	private class ObtenerContenidoTask extends AsyncTask<String,Integer,Void>
	{			
		private Context context;
		
		public void setContext(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			HashMap<String, Object> item;
			ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
			
			for(int j = 0; j < listaTareas.size(); j++){

				tarea = listaTareas.get(j);

				item = new HashMap<String, Object>();		
				item.put("Icon", android.R.drawable.ic_menu_agenda);
				item.put("Title", tarea.getAsignatura().getAsignatura());
				item.put("Date", tarea.getFecha());
				item.put("Description", tarea.getContenido());
				data.add(item);	
			}
			for(int j = 0; j < listaExamens.size(); j++){

				examen = listaExamens.get(j);

				item = new HashMap<String, Object>();		
				item.put("Icon", android.R.drawable.ic_menu_edit);
				item.put("Title", examen.getAsignatura().getAsignatura());
				item.put("Date", examen.getFecha());
				item.put("Description", examen.getContenido());
				data.add(item);	
			}
			for(int j = 0; j < listaAnuncios.size(); j++){

				anuncio = listaAnuncios.get(j);

				item = new HashMap<String, Object>();		
				item.put("Icon", android.R.drawable.ic_menu_my_calendar);
				item.put("Title", anuncio.getAsignatura().getAsignatura());
				item.put("Date", anuncio.getFecha());
				item.put("Description", anuncio.getContenido());
				data.add(item);	
			}
			for(int j = 0; j < listaIncidencias.size(); j++){

				incidencia = listaIncidencias.get(j);

				item = new HashMap<String, Object>();		
				item.put("Icon", android.R.drawable.ic_menu_info_details);
				item.put("Title", incidencia.getAsignatura().getAsignatura());
				item.put("Date", incidencia.getFecha());
				item.put("Description", incidencia.getContenido());
				data.add(item);	
			}
			
			MyListAdapter adapter = new MyListAdapter(context, R.layout.list_view_item, data);
			ListView list = (ListView) findViewById(R.id.lvList);
			list.setAdapter(adapter);		

			OnItemClickListener listener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					//
					preguntarEliminarContenido(arg0, arg1, arg2, arg3);
				}
			};
			list.setOnItemClickListener(listener);

			registerForContextMenu(list);
		}

		@Override
		protected Void doInBackground(String... params) 
		{
			SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
			int idProfesor =  preferences.getInt("idProfe", 0);

			//Obtener TAREAS			
			String sql1 = "SELECT t.concepto, t.fecha, a.asignatura FROM TAREA t, ASIGNATURA a";
			String sql2 = " WHERE t.id_profesor = " + idProfesor + " and t.id_asignatura = a.id_asignatura";			
			obtenerContenidoPorIdProfesor(sql1, sql2, Constantes.SP_TAREA);

			//Obtener EXAMENES
			sql1 = "SELECT e.concepto, e.fecha, a.asignatura FROM EXAMEN e, ASIGNATURA a";
			sql2 = " WHERE e.id_profesor = " + idProfesor + " and e.id_asignatura = a.id_asignatura";	
			obtenerContenidoPorIdProfesor(sql1, sql2, Constantes.SP_EXAMEN);

			//Obtener ANUNCIOS
			sql1 = "SELECT an.concepto, an.fecha, a.asignatura FROM ANUNCIO an, ASIGNATURA a";
			sql2 = " WHERE an.id_profesor = " + idProfesor + " and an.id_asignatura = a.id_asignatura";	
			obtenerContenidoPorIdProfesor(sql1, sql2, Constantes.SP_ANUNCIO);

			//Obtener INCIDENCIAS
			sql1 = "SELECT i.concepto, i.fecha, a.asignatura FROM INCIDENCIA i, ASIGNATURA a";
			sql2 = " WHERE i.id_profesor = " + idProfesor + " and i.id_asignatura = a.id_asignatura";	
			obtenerContenidoPorIdProfesor(sql1, sql2, Constantes.SP_INCIDENCIA);

			return null;
		}		
	}

	private void obtenerContenidoPorIdProfesor(String sql1, String sql2, final int tipo){

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		JSONObject json;
		try {
			json = Conexion.obtenerJsonDelServicio(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE);
			if(json != null)
			{
				rellenarDatos(json, tipo);
			}			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

	private void rellenarDatos(JSONObject json, int tipo){		

		boolean continuar = true;
		int i = 0;

		while (continuar){

			String txt1 = "subject"+i;
			String txt2 = "contenido"+i;
			String txt3 = "date"+i;
			if (json.has(txt1))
			{
				try {
					
					switch(tipo) { 
					case Constantes.SP_TAREA:

						tarea = new Tarea();

						tarea.getAsignatura().setAsignatura( json.getString( txt1 ) );					
						if (json.has( txt2 )) { tarea.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { tarea.setFecha( json.getString( txt3 ) ); }

						listaTareas.add( tarea );
						
						break;
					case Constantes.SP_EXAMEN:

						examen = new Examen();

						examen.getAsignatura().setAsignatura( json.getString( txt1 ) );					
						if (json.has( txt2 )) { examen.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { examen.setFecha( json.getString( txt3 ) ); }

						listaExamens.add( examen );

						break;
					case Constantes.SP_ANUNCIO:

						anuncio = new Anuncio();

						anuncio.getAsignatura().setAsignatura( json.getString( txt1 ) );					
						if (json.has( txt2 )) { anuncio.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { anuncio.setFecha( json.getString( txt3 ) ); }

						listaAnuncios.add( anuncio );
						
						break;
					case Constantes.SP_INCIDENCIA:

						incidencia = new Incidencia();

						incidencia.getAsignatura().setAsignatura( json.getString( txt1 ) );					
						if (json.has( txt2 )) { incidencia.setContenido( json.getString( txt2 ) ); }
						if (json.has( txt3 )) { incidencia.setFecha( json.getString( txt3 ) ); }

						listaIncidencias.add( incidencia );
						
						break;
					default:

						break;
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			else{
				continuar = false;
			}
			i++;
		}
	}

	//
	private void preguntarEliminarContenido(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3){
    	
    	//Mostrar diálogo para preguntar si se planta o continua...
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Modificar entrada");
        dialogBuilder.setMessage("¿Desea modificar este contenido?");

        //NO ELIMINAR
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	//                
            }
        });

        //SI ELIMINAR
        dialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	//
                modificarContenido(arg0, arg1, arg2, arg3);
            }
        });

        Dialog dialog = dialogBuilder.create();
        dialog.show();
    }
	
	
	private void modificarContenido(AdapterView<?> arg0, View arg1, int arg2, long arg3){
		MyListAdapter adapter = (MyListAdapter) arg0.getAdapter();
		HashMap<String, Object> item = adapter.getItem((int) arg3);

		
		
		int icono 		  = (Integer) item.get("Icon");
		String asignatura = (String) item.get("Title");
		String fecha	  = (String) item.get("Date");
		String concepto	  = (String) item.get("Description");
		int tipo = 0;
		String tipoS = "";
		
		switch(icono) { 
		case android.R.drawable.ic_menu_agenda:
			//TAREA:
			tipo = Constantes.SP_TAREA;
			tipoS = "Tarea";
			break;
		case android.R.drawable.ic_menu_edit:
			//EXAMEN:
			tipo = Constantes.SP_EXAMEN;
			tipoS = "Examen";
			break;
		case android.R.drawable.ic_menu_my_calendar:
			//Constantes.SP_ANUNCIO:
			tipo = Constantes.SP_ANUNCIO;
			tipoS = "Anuncio";
			break;
		case android.R.drawable.ic_menu_info_details:
			//INCIDENCIA:
			tipo = Constantes.SP_INCIDENCIA;
			tipoS = "Incidencia";
			break;
		default:

			break;
		}
		
		Bundle b = new Bundle();
		b.putInt("tipo", tipo);
		b.putString("asignatura", asignatura);
		b.putString("fecha", fecha);
		b.putString("concepto", concepto);
		b.putString("tipoS", tipoS);
		
		Intent intent = new Intent(this, ProfesorEditItem.class);
		intent.putExtras(b);
        startActivity(intent);
		
	}
	

}