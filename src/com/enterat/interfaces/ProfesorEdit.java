package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
import com.enterat.bda.Examen;
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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProfesorEdit extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_padres_tasks);	
		
		ObtenerExamenesTask examenesTask = new ObtenerExamenesTask();
		examenesTask.execute();	
	}

	
	
	
	private class ObtenerExamenesTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
        protected Void doInBackground(String... params) 
		{									
			obtenerExamenesPorIdAlumno();		
			
			return null;
        }		
	}
	
	private void obtenerExamenesPorIdAlumno(){
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
		int idCurso =  preferences.getInt("cursoAlumno", 0);
		
		String sql1 = "SELECT a.asignatura, e.fecha, e.concepto FROM CURSO c, ASIGNATURA a, EXAMEN e";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and c.id_curso = a.id_curso and a.id_asignatura = e.id_asignatura";

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
					// TODO Auto-generated catch block
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
		
		MyListAdapter adapter = new MyListAdapter(this, R.layout.list_view_item, data);
		ListView list = (ListView) findViewById(R.id.lvList);
		list.setAdapter(adapter);		
		
		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(arg0.getContext(), getResources().getString(R.string.row) + ": " + arg3, Toast.LENGTH_SHORT).show();
			}
		};
		list.setOnItemClickListener(listener);
		
		registerForContextMenu(list);			
	}
	
}