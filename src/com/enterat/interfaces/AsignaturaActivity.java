package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.bda.Anuncio;
import com.enterat.bda.Examen;
import com.enterat.bda.Incidencia;
import com.enterat.bda.Tarea;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;
import com.enterat.util.MyListAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class AsignaturaActivity extends Activity {

	private MyListAdapter arrayAdapter;

	private ListView tareas_list;
	private ListView examenes_list;
	private ListView anuncios_list;	
	private ListView incidencias_list;

	//TODO
	private int idAsignatura = 0;
	private int idCurso      = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignaturas);

		//
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);
		idCurso =  preferences.getInt("cursoAlumno", 0);
		
		//
		final TabHost tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		//
		ObtenerAsignaturasTask asigTask = new ObtenerAsignaturasTask();
		asigTask.execute();	

		final Spinner sp = (Spinner) findViewById(R.id.asignaturas_spinner);		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		   
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							
								
				String asignatura = (String) sp.getSelectedItem();
				if (asignatura.compareTo("Sin asignaturas") == 0){				
					idAsignatura = -1;					
				}
				else{
					String[] array_spinner = asignatura.split("-");
					idAsignatura = Integer.parseInt( array_spinner[0] );
				}				
				
				
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

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}

		});
		
		//
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
		ObtenerTareasTask tareasTask = new ObtenerTareasTask();
		tareasTask.execute();		
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

		String sql1 = "SELECT a.id_asignatura, a.asignatura FROM ASIGNATURA a, ALUMNO al, MATRICULA m ";
		String sql2 = "WHERE al.id_alumno = " + idAlumno + " and a.id_asignatura = m.id_asignatura and al.id_alumno = m.id_alumno";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
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

			String txt1 = "code"+i;			
			String txt2 = "subject"+i;
			
			if (json.has(txt1)){
				try {
					asignaturas = asignaturas + json.getString(txt1) + "-";

					if (json.has(txt2))
					{
						asignaturas = asignaturas + json.getString(txt2) + ",";
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

	//
	private void obtenerTareasAsignatura()
	{
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
		int idCurso =  preferences.getInt("cursoAlumno", 0);

		String sql1 = "SELECT a.asignatura, t.fecha, t.concepto FROM CURSO c, ASIGNATURA a, TAREA t";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and t.id_asignatura = " + idAsignatura + " and c.id_curso = a.id_curso and a.id_asignatura = t.id_asignatura";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				//Si se ha obtenido...
				if(json != null)
				{
					rellenarDatosTareas(json);
				}				
			}
		});				
	}

	//
	private void rellenarDatosTareas(JSONObject json)
	{
		ArrayList<Tarea> listaTareas = new ArrayList<Tarea>();
		HashMap<String, Object> item;		
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		Tarea tarea = null;		

		boolean continuar = true;
		int i = 0;

		while (continuar){

			String txt1 = "subject"+i;
			String txt2 = "contenido"+i;
			String txt3 = "date"+i;
			if (json.has(txt1))
			{
				tarea = new Tarea();

				try {
					tarea.getAsignatura().setAsignatura( json.getString( txt1 ) );				
					if (json.has( txt2 )) { tarea.setContenido( json.getString( txt2 ) ); }
					if (json.has( txt3 )) { tarea.setFecha( json.getString( txt3 ) ); }

				} catch (JSONException e) {
					e.printStackTrace();
				}	
				listaTareas.add( tarea );
			}
			else{
				continuar = false;
			}
			i++;
		}

		for(int j = 0; j < listaTareas.size(); j++){

			tarea = listaTareas.get(j);

			item = new HashMap<String, Object>();		
			item.put("Icon", android.R.drawable.ic_menu_agenda);
			item.put("Title", tarea.getAsignatura().getAsignatura());
			item.put("Date", tarea.getFecha());
			item.put("Description", tarea.getContenido());
			data.add(item);	
		}

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

	private void obtenerExamenesAsignatura()
	{
		String sql1 = "SELECT a.asignatura, e.fecha, e.concepto FROM CURSO c, ASIGNATURA a, EXAMEN e";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and e.id_asignatura = " + idAsignatura + " and c.id_curso = a.id_curso and a.id_asignatura = e.id_asignatura";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				//Si se ha obtenido...
				if(json != null)
				{
					rellenarDatosExamenes(json);
				}				
			}
		});
	}

	private void rellenarDatosExamenes(JSONObject json){

		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		ArrayList<Examen> listaExamens = new ArrayList<Examen>();

		Examen examen = new Examen();		

		boolean continuar = true;
		int i = 0;

		while (continuar){

			String txt1 = "subject"+i;
			String txt2 = "contenido"+i;
			String txt3 = "date"+i;
			if (json.has(txt1))
			{
				examen = new Examen();

				try {
					examen.getAsignatura().setAsignatura( json.getString( txt1 ) );					
					if (json.has( txt2 )) { examen.setContenido( json.getString( txt2 ) ); }
					if (json.has( txt3 )) { examen.setFecha( json.getString( txt3 ) ); }

				} catch (JSONException e) {
					e.printStackTrace();
				}
				listaExamens.add( examen );
			}
			else{
				continuar = false;
			}
			i++;
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

	private void obtenerAnunciosAsignatura()
	{
		String sql1 = "SELECT a.asignatura, t.fecha, t.concepto FROM CURSO c, ASIGNATURA a, ANUNCIO t";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and t.id_asignatura = " + idAsignatura + " and c.id_curso = a.id_curso and a.id_asignatura = t.id_asignatura";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				//Si se ha obtenido...
				if(json != null)
				{
					rellenarDatosAnuncios(json);
				}				
			}
		});		
	}

	private void rellenarDatosAnuncios(JSONObject json){
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		ArrayList<Anuncio> listaAnuncios = new ArrayList<Anuncio>();

		Anuncio anuncio = new Anuncio();		

		boolean continuar = true;
		int i = 0;

		while (continuar){

			String txt1 = "subject"+i;
			String txt2 = "contenido"+i;
			String txt3 = "date"+i;
			if (json.has(txt1))
			{
				anuncio = new Anuncio();

				try {
					anuncio.getAsignatura().setAsignatura( json.getString( txt1 ) );

					if (json.has( txt2 )) { anuncio.setContenido( json.getString( txt2 ) ); }
					if (json.has( txt3 )) { anuncio.setFecha( json.getString( txt3 ) ); }

				} catch (JSONException e) {
					e.printStackTrace();
				}	

				listaAnuncios.add( anuncio );
			}
			else{
				continuar = false;
			}
			i++;
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

	private void obtenerIncidenciasAsignatura()
	{
		String sql1 = "SELECT a.asignatura, t.fecha, t.concepto FROM CURSO c, ASIGNATURA a, INCIDENCIA t";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and t.id_asignatura = " + idAsignatura + " and c.id_curso = a.id_curso and a.id_asignatura = t.id_asignatura";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sqlquery1", sql1));
		pairs.add(new BasicNameValuePair("sqlquery2", sql2));

		new WSConection(pairs, "service.executeSQL.php", Constantes.SQL_CONSULTAR, Constantes.SERV_IMPARTE, new IConexion() {

			@Override
			public void getJsonFromWS(JSONObject json) {
				//Si se ha obtenido...
				if(json != null)
				{
					rellenarDatosIncidencias(json);
				}				
			}
		});
	}

	private void rellenarDatosIncidencias(JSONObject json)
	{	
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		ArrayList<Incidencia> listaIncidencias = new ArrayList<Incidencia>();

		Incidencia incidencia = new Incidencia();		

		boolean continuar = true;
		int i = 0;

		while (continuar){

			String txt1 = "subject"+i;
			String txt2 = "contenido"+i;
			String txt3 = "date"+i;
			if (json.has(txt1))
			{
				incidencia = new Incidencia();

				try {
					incidencia.getAsignatura().setAsignatura( json.getString( txt1 ) );				
					if (json.has( txt2 )) { incidencia.setContenido( json.getString( txt2 ) ); }
					if (json.has( txt3 )) { incidencia.setFecha( json.getString( txt3 ) ); }

				} catch (JSONException e) {
					e.printStackTrace();
				}	
				listaIncidencias.add( incidencia );
			}
			else{
				continuar = false;
			}
			i++;
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

		arrayAdapter = new MyListAdapter(this, R.layout.list_view_item, data);
		incidencias_list = (ListView) findViewById(R.id.incidencias_listView);
		incidencias_list.setAdapter(arrayAdapter);
	}

}