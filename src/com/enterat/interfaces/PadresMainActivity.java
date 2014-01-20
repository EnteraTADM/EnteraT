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

public class PadresMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_padres_main);
		
		//
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);
		
		TextView padreText = (TextView)findViewById( R.id.padreTextView );
		padreText.setText( "Padre: " + preferences.getString("nombrePadre", "") + " " + preferences.getString("apellidosPadre", "")  );
		
		TextView alumnoText = (TextView)findViewById( R.id.alumnoTextView );
		alumnoText.setText( "Alumno: " + preferences.getString("nombreAlumno", "") + " " + preferences.getString("apellidosAlumno", "") );
		
		TextView cursoText = (TextView)findViewById( R.id.cursoTextView );
		cursoText.setText( "Curso: " + preferences.getInt("cursoAlumno", 0) );		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
		
		SharedPreferences preferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
		
		// Comprueba que el usuario que se va a desconectar es el que esta guardado en SharedPreferences
		SharedPreferences.Editor editor = preferences.edit();
	
		// Borra las SharedPreferences
		editor.clear();
		editor.commit();
		
		//Ir al menú principal
		Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);		
        
        //Se destruye esta actividad
      	finish();
	}		
	
	//	
	public void asignaturasClick(View v) {
		startActivity(new Intent(this, AsignaturaActivity.class));
	}
	
	//	
	public void tareasClick(View v) {
		startActivity(new Intent(this, PadresTasksActivity.class));
	}

	public void examenesClick(View v) {
		startActivity(new Intent(this, PadresTasksActivity.class));
	}

	public void anuncionsClick(View v) {
		startActivity(new Intent(this, PadresTasksActivity.class));
	}

	public void incidenciasClick(View v) {
		startActivity(new Intent(this, PadresTasksActivity.class));
	}	

}