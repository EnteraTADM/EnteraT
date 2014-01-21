package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.enterat.R;
import com.enterat.bda.Incidencia;
import com.enterat.util.MyListAdapter;

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

public class PadresIncidenciasActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_padres_tasks);	
		
		//
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		Incidencia incidencia = new Incidencia();		
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);				
		ArrayList<Incidencia> listaIncidencias = incidencia.obtenerIncidencias( preferences.getInt("cursoAlumno", 0) );
		
		for(int i = 0; i < listaIncidencias.size(); i++){
			
			incidencia = listaIncidencias.get(i);
			
			item = new HashMap<String, Object>();		
			item.put("Icon", android.R.drawable.ic_menu_edit);
			item.put("Title", incidencia.getAsignatura().getAsignatura());
			item.put("Date", incidencia.getFecha());
			item.put("Description", incidencia.getContenido());
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
	
}