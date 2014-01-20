package com.enterat.interfaces;

import java.util.ArrayList;

import com.enterat.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class AsignaturaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignaturas);

		
		//TODO
		//Añadir las asignaturas de cada alumno en el spinner
		//Consulta BDA
		
		
		//TODO
		//Implementar el cambio de pestañas del tabhost
		
		
		
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