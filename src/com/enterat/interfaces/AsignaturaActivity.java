package com.enterat.interfaces;

import java.util.ArrayList;

import com.enterat.R;
import com.enterat.bda.Matricula;

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

		//A�adir las asignaturas de cada alumno en el spinner
		Matricula matricula = new Matricula();		
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);				
		String asignaturas = matricula.asignaturasMatriculadasPorIdAlumno( preferences.getInt("id_alumno", 0) );		
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