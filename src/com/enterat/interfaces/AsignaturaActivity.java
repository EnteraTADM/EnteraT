package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.services.IConexion;
import com.enterat.services.WSConection;
import com.enterat.util.Constantes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

public class AsignaturaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignaturas);

		ObtenerAsignaturasTask asigTask = new ObtenerAsignaturasTask();
		asigTask.execute();	
		
	}
	
	private class ObtenerAsignaturasTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
        protected Void doInBackground(String... params) 
		{									
			obtenerAsignaturasMatriculadasPorIdAlumno();		
			
			return null;
        }			
		
	}
	
	
	private void obtenerAsignaturasMatriculadasPorIdAlumno(){
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
	
	
	
	private void rellenarDatos(JSONObject json){
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
		
		//TODO
		//Implementar el cambio de pesta�as del tabhost		
		
		ArrayAdapter<String> arrayAdapter;
		ArrayList<String> list = new ArrayList<String>();
		
		ListView anuncios_list = (ListView) findViewById(R.id.anuncios_listView);
		ListView tareas_list = (ListView) findViewById(R.id.tareas_listView);
		ListView incidencias_list = (ListView) findViewById(R.id.incidencias_listView);
		ListView examenes_list = (ListView) findViewById(R.id.examenes_listView);

		list.add("Item1");
		list.add("Item2");
		list.add("Item3");
		list.add("Item4");
		list.add("Item5");

		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec=tabs.newTabSpec("anuncios_tab");
		spec.setContent(R.id.anuncios_tab);
		spec.setIndicator(getResources().getString(R.string.txt_anuncios) );
		tabs.addTab(spec);

		spec=tabs.newTabSpec("tareas_tab");
		spec.setContent(R.id.tareas_tab);
		spec.setIndicator( getResources().getString(R.string.txt_tareas) );
		tabs.addTab(spec);

		spec=tabs.newTabSpec("incidencias_tab");
		spec.setContent(R.id.incidencias_tab);
		spec.setIndicator( getResources().getString(R.string.txt_incidencias) );
		tabs.addTab(spec);

		spec=tabs.newTabSpec("examenes_tab");
		spec.setContent(R.id.examenes_tab);
		spec.setIndicator( getResources().getString(R.string.txt_examenes) );
		tabs.addTab(spec);

		tabs.setCurrentTab(0);

		arrayAdapter = new ArrayAdapter<String>(this,R.layout.list, R.id.list_item, list);

		anuncios_list.setAdapter(arrayAdapter);
		tareas_list.setAdapter(arrayAdapter);
		incidencias_list.setAdapter(arrayAdapter);
		examenes_list.setAdapter(arrayAdapter);
	}

}