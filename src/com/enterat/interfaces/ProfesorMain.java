package com.enterat.interfaces;

import com.enterat.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfesorMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profesor_main);
		
		//Recuperar nombre y apellidos del profesor
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);		
		TextView nombreText = (TextView)findViewById( R.id.nombre_t );
		nombreText.setText( preferences.getString("nombre", "") + " " + preferences.getString("apellidos", "") );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);
		return true;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        
            case R.id.cerrar_sesion: {
                cerrarSesion();
                break;
            }

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
	
	//Salir
	private void cerrarSesion() {

		// Borra las SharedPreferences
		SharedPreferences preferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();

		// Borra las SharedPreferences
		SharedPreferences preferences2 = getSharedPreferences("guardado_profadd", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor2 = preferences2.edit();
		editor2.clear();
		editor2.commit();				

		//Ir al menu principal
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

		//Se destruye esta actividad
		finish();
	}

	public void insertarClick(View v) {
		startActivity(new Intent(this, ProfesorAdd.class));
	}
	
	public void modificarClick(View v) {
		//Mostrar mensaje
		Toast.makeText(this, "modificar...", Toast.LENGTH_LONG).show();
	}
	
	public void eliminarClick(View v) {
		//Mostrar mensaje
		Toast.makeText(this, "eliminar...", Toast.LENGTH_LONG).show();
	}
	
}