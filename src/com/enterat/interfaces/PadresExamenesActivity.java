package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.enterat.R;
import com.enterat.bda.Examen;
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

public class PadresExamenesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_padres_tasks);	
		
		//
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		Examen examen = new Examen();		
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);				
		ArrayList<Examen> listaExamens = examen.obtenerExamens( preferences.getInt("cursoAlumno", 0) );
		
		for(int i = 0; i < listaExamens.size(); i++){
			
			examen = listaExamens.get(i);
			
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