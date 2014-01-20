package com.enterat.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.enterat.R;
import com.enterat.util.MyListAdapter;

import android.os.Bundle;
import android.app.Activity;
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
		
		
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Matemàtiques");
		item.put("Date", "14/10/2013");
		item.put("Description", "pàg. 41 - act. 1, 2 i 3");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Socials");
		item.put("Date", "14/10/2013");
		item.put("Description", "Repasar el tema 2");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Naturals");
		item.put("Date", "10/10/2013");
		item.put("Description", "pàg. 31 - act. 5, 6 i 9");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Matemàtiques");
		item.put("Date", "09/10/2013");
		item.put("Description", "pàg. 40 - act. 3, 5 i 7");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Anglés");
		item.put("Date", "09/10/2013");
		item.put("Description", "NoteBook exercices 14 and 15");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Valencià");
		item.put("Date", "09/10/2013");
		item.put("Description", "Llegir lectura IV");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Matemàtiques");
		item.put("Date", "08/10/2013");
		item.put("Description", "pàg. 38 - act. 1, 2 i 3");
		data.add(item);

		item = new HashMap<String, Object>();		
		item.put("Icon", android.R.drawable.ic_menu_edit);
		item.put("Title", "Castellà");
		item.put("Date", "07/10/2013");
		item.put("Description", "pàg. 12 - act. 1");
		data.add(item);

		
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