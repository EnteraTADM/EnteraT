package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;
import com.enterat.util.MyListAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class AsignaturaActivity extends Activity {

	private MyListAdapter arrayAdapter;

	private ListView tareas_list;//	  = (ListView) findViewById(R.id.tareas_listView);
	private ListView examenes_list;// 	  = (ListView) findViewById(R.id.examenes_listView);
	private ListView anuncios_list;// 	  = (ListView) findViewById(R.id.anuncios_listView);	
	private ListView incidencias_list;// = (ListView) findViewById(R.id.incidencias_listView);


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignaturas);

		//
		ObtenerAsignaturasTask asigTask = new ObtenerAsignaturasTask();
		asigTask.execute();	

		//
		final TabHost tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec=tabs.newTabSpec("tareas_tab");
		spec.setContent(R.id.tareas_tab);
		spec.setIndicator( getResources().getString(R.string.txt_tareas) );
		tabs.addTab(spec);

		spec=tabs.newTabSpec("examenes_tab");
		spec.setContent(R.id.examenes_tab);
		spec.setIndicator( getResources().getString(R.string.txt_examenes) );
		tabs.addTab(spec);

		spec=tabs.newTabSpec("anuncios_tab");
		spec.setContent(R.id.anuncios_tab);
		spec.setIndicator(getResources().getString(R.string.txt_anuncios) );
		tabs.addTab(spec);		

		spec=tabs.newTabSpec("incidencias_tab");
		spec.setContent(R.id.incidencias_tab);
		spec.setIndicator( getResources().getString(R.string.txt_incidencias) );
		tabs.addTab(spec);

		tabs.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				int i = tabs.getCurrentTab();

				if (i == 0) {
					//Mostrar TAREAS...
					ObtenerTareasTask tareasTask = new ObtenerTareasTask();
					tareasTask.execute();

				} else if (i == 1) {
					//Mostrar EXAMENES...
					ObtenerExamenesTask examenesTask = new ObtenerExamenesTask();
					examenesTask.execute();										

				} else if (i == 2) {
					//Mostrar ANUNCIOS...
					ObtenerAnunciosTask anunciosTask = new ObtenerAnunciosTask();
					anunciosTask.execute();

				} else if (i == 3) {
					//Mostrar INCIDENCIAS...
					ObtenerIncidenciasTask incidenciasTask = new ObtenerIncidenciasTask();
					incidenciasTask.execute();

				}
			}
		});

		//Por defecto mostrar las tareas
		tabs.setCurrentTab(0);

		//Mostrar TAREAS...
		HashMap<String, Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		item.put("Icon", android.R.drawable.ic_menu_info_details);
		item.put("Title", "asdf");
		item.put("Date", "asdf");
		item.put("Description", "asdf");
		data.add(item);		

		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		tareas_list = (ListView) findViewById(R.id.tareas_listView);
		tareas_list.setAdapter(arrayAdapter);

	}

	//TAREA ASÍNCRONA
	private class ObtenerAsignaturasTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
		protected Void doInBackground(String... params) 
		{									
			obtenerAsignaturasMatriculadasPorIdAlumno();		

			return null;
		}
	}

	//
	private void obtenerAsignaturasMatriculadasPorIdAlumno()
	{
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
		int idAlumno = preferences.getInt("id_alumno", 0);

		String sql1 = "SELECT a.asignatura FROM ASIGNATURA a, ALUMNO al, MATRICULA m ";
		String sql2 = "WHERE al.id_alumno = " + idAlumno + " and a.id_asignatura = m.id_asignatura and al.id_alumno = m.id_alumno";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				// TODO Auto-generated method stub
				//Si se ha obtenido...
				if(json != null)
				{
					rellenarDatos(json);
				}

			}
		});				
	}

	//
	private void rellenarDatos(JSONObject json)
	{
		String asignaturas = "";
		boolean continuar = true;
		int i = 0;

		while (continuar){				

			String txt2 = "subject"+i;
			if (json.has(txt2))
			{
				try {
					asignaturas = asignaturas + json.getString(txt2) + ",";

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}												
			}
			else{
				continuar = false;
			}
			i++;
		}

		String[] array_spinner = asignaturas.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_spinner);				
		Spinner sp = (Spinner) findViewById(R.id.asignaturas_spinner);
		sp.setAdapter(adapter);
		sp.setSelection(0);		
	}

	//TAREA ASÍNCRONA
	private class ObtenerTareasTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
		protected Void doInBackground(String... params) 
		{								
			//						

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			obtenerTareasAsignatura();
		}
	}

	private void obtenerTareasAsignatura(){

		HashMap<String, Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		item.put("Icon", android.R.drawable.ic_menu_info_details);
		item.put("Title", "asdf");
		item.put("Date", "asdf");
		item.put("Description", "asdf");
		data.add(item);	

		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		tareas_list = (ListView) findViewById(R.id.tareas_listView);
		tareas_list.setAdapter(arrayAdapter);

	}

	//TAREA ASÍNCRONA
	private class ObtenerExamenesTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
		protected Void doInBackground(String... params) 
		{								
			//						

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			obtenerExamenesAsignatura();
		}
	}

	private void obtenerExamenesAsignatura(){

		HashMap<String, Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		item.put("Icon", android.R.drawable.ic_delete);
		item.put("Title", "qwert");
		item.put("Date", "qwert");
		item.put("Description", "qwert");
		data.add(item);	


		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		examenes_list = (ListView) findViewById(R.id.examenes_listView);
		examenes_list.setAdapter(arrayAdapter);
	}

	//TAREA ASÍNCRONA
	private class ObtenerAnunciosTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
		protected Void doInBackground(String... params) 
		{								
			//						

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			obtenerAnunciosAsignatura();
		}
	}

	private void obtenerAnunciosAsignatura(){

		HashMap<String, Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		item.put("Icon", android.R.drawable.ic_dialog_email);
		item.put("Title", "poiuy");
		item.put("Date", "poiuy");
		item.put("Description", "poiuy");
		data.add(item);	


		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		anuncios_list = (ListView) findViewById(R.id.anuncios_listView);
		anuncios_list.setAdapter(arrayAdapter);
	}

	//TAREA ASÍNCRONA
	private class ObtenerIncidenciasTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
		protected Void doInBackground(String... params) 
		{								
			//						

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			obtenerIncidenciasAsignatura();
		}
	}

	private void obtenerIncidenciasAsignatura(){

		HashMap<String, Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		item.put("Icon", android.R.drawable.ic_menu_add);
		item.put("Title", "ñlkjh");
		item.put("Date", "ñlkjh");
		item.put("Description", "ñlkjh");
		data.add(item);	


		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		incidencias_list = (ListView) findViewById(R.id.incidencias_listView);
		incidencias_list.setAdapter(arrayAdapter);
	}

}