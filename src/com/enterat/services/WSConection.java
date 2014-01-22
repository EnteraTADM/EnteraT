package com.enterat.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.enterat.util.Constantes;

import android.content.Context;
import android.os.AsyncTask;

public class WSConection  {
	
		// URL del directorio donde se encuentran los servicios del servidor
		private final static String url = "http://www.appservices.eshost.es/servicioweb/";
		List<NameValuePair> pairs;
		String servicioWeb;
		int tipoConsulta;
		int servicio;
		private JSONObject jsonresp;
		Context context;
		
	
		public WSConection(List<NameValuePair> pairs, String servicioWeb, int tipoConsulta, int servicio, IConexion iconex){
			this.pairs = pairs;
			this.servicioWeb = servicioWeb;
			this.tipoConsulta = tipoConsulta;
			this.servicio = servicio;
			Object[] objs = new Object[1];
			objs[0] = (Object) iconex;
			new WSConectionTask().execute(objs); 
		}

		
	private class WSConectionTask extends AsyncTask<Object, Void, JSONObject>  {
	private IConexion iconx;
	
	@Override
	protected JSONObject doInBackground(Object... objs) {
		try {
			this.iconx = (IConexion) objs[0];
			jsonresp = obtenerJsonDelServicio(pairs, servicioWeb, tipoConsulta, servicio);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonresp;
	}
	
	@Override
    protected void onPostExecute(JSONObject result){
        iconx.getJsonFromWS(result);
    }
	
	
	//Metodo generico para obtener JSON para cualquier servicio
	private JSONObject obtenerJsonDelServicio(List<NameValuePair> pairs, String servicioWeb, int tipoConsulta, int servicio) throws ClientProtocolException, IOException, JSONException {
			
			HttpClient client = new DefaultHttpClient();		
			JSONObject json   = null;		
					
			HttpPost request = new HttpPost(url + servicioWeb);		
			request.setHeader("Accept","application/json");	
			request.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = client.execute(request);
			HttpEntity entity 	  = response.getEntity(); 
			String responseString = null;
			
			if (entity != null) { 
				InputStream stream = entity.getContent(); 
				BufferedReader reader = new BufferedReader(	new InputStreamReader(stream) ); 
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}		 
				stream.close(); 
				responseString = sb.toString();	

				//			
				if(responseString.startsWith("[") && responseString.endsWith("]")){
					responseString = responseString.substring(1, responseString.length()-1);
				}
				
				if (tipoConsulta == Constantes.SQL_INSERTAR){
					responseString = "{'isOk':'True'}";
				}
					
				//Modificar la cadena JSON devuelta por el servicio web, para crear correctamente el objeto JSON
				if (servicio == Constantes.SERV_IMPARTE){
					boolean continuar = true;
					String responseString_Ant = ""; 
					int i = 0;
					while (continuar){
						responseString_Ant = responseString;
						responseString = responseString_Ant.replaceFirst("id_asignatura","code" + i);
						responseString = responseString.replaceFirst("asignatura","subject" + i);
						responseString = responseString.replaceFirst("concepto","contenido" + i);
						responseString = responseString.replaceFirst("fecha","date" + i);
						continuar = (!responseString.equalsIgnoreCase(responseString_Ant));
						i++;
					}
					responseString = responseString.replace("{","");
					responseString = responseString.replace("}","");
					responseString = "{" + responseString + "}";
				}
				
				//
				json = new JSONObject(responseString);			
			}	
			
			return json;	
		}
	}
}
