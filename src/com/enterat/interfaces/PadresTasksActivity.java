package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.R;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PadresTasksActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_padres_tasks);	
		
		ObtenerTareasTask tareasTask = new ObtenerTareasTask();
		tareasTask.execute();	
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ordenar, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        
            case R.id.menu_ordenar: {
            	ordenarPorFecha();
                break;
            }

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
	
	private void ordenarPorFecha(){
	
		Toast.makeText(getApplicationContext(),"Ordenado correctamente", Toast.LENGTH_LONG).show();
		
	}
	
	
	private class ObtenerTareasTask extends AsyncTask<String,Integer,Void>
	{		
		@Override
        protected Void doInBackground(String... params) 
		{									
			obtenerTareasPorIdAlumno();		
			
			return null;
        }		
	}
	
	
	private void obtenerTareasPorIdAlumno(){
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
		int idCurso =  preferences.getInt("cursoAlumno", 0);
		
		String sql1 = "SELECT a.asignatura, t.fecha, t.concepto FROM CURSO c, ASIGNATURA a, TAREA t";
		String sql2 = " WHERE c.id_curso = " + idCurso + " and c.id_curso = a.id_curso and a.id_asignatura = t.id_asignatura";

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
					// TODO Auto-generated catch block
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