package accesBDD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
 
/**
 * JSonParserV2 permet d'effectuer des requ�tes HTTP
 * @author Valentin Proust
 *  
 */
public class JSONParserV2 {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	String url;
	String method;
	List<NameValuePair> params;
	//Ce bool�en est pass� � vrai si la connexion au serveur PHP est impossible
	boolean problemeInternet = false;

	// constructeur
	public JSONParserV2() {

	}
	//Fonction qui renvoie l'objet JSON r�cup�r� sur le serveur PHP
	/**
	 * Fonction qui effectue la requ�te HTTP
	 * @param url url de la page PHP � interroger
	 * @param method m�thode � utiliser (GET ou POST)
	 * @param params liste des param�tres
	 * @return l'objet JSON r�cup�r�
	 *  
	 */
	public JSONObject faireHttpRequest(final String url, final String method,
			final List<NameValuePair> params) {
		this.url = url;
		this.method = method;
		this.params = params;

		try {

			// Si la m�thode POST est demand�e
			if(method == "POST"){
				// On effectue la requ�te HTTP avec les param�tres
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				System.out.println("valentin"+is.toString());
				
			 // m�thode GET demand�e
			}else if(method == "GET"){
				// On effectue la requ�te HTTP avec les param�tres
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				String urlApasser = "";
				urlApasser = url + "?" + paramString;
				System.out.println("urlApasser"+urlApasser);
				HttpGet httpGet = new HttpGet(urlApasser);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("probleme internet");
			problemeInternet = true; // Si on r�cup�re une exception, on met problemeInternet a vrai
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			System.out.println("probleme internet");
			problemeInternet = true;
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("probleme internet");
			problemeInternet = true;
			e.printStackTrace();
		}
		
		if(problemeInternet){ // Si il y a un probl�me internet, il faut retourner l'objet JSON avec success:-1
			try {
				jObj = new JSONObject("{\"success\":-1}");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println("fichier json :" + json.toString());
			return jObj;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
			//System.out.println("fichier json json :" + json.toString());
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return jObj;

	}
}
