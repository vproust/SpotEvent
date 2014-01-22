package accesBDD;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AccesBDDconnexion {
	int success;
	int resultat;
	String idPrs;
	// Creating JSON Parser object
	JSONParserV2 jParser = new JSONParserV2();

	// url to get all products list
	private static String url_connexion = "http://fabienrichard.fr/projects/trackpack/Lecture/essayerConnexion.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_personne= "personne";
	private static final String TAG_idPrs= "idPrs";

	public int essayerConnexion(String email, String mdp){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("mdp", mdp));
		// getting JSON string from URL
		JSONObject json = jParser.faireHttpRequest(url_connexion, "GET", params);
		// Check your log cat for JSON reponse
		if(json!=null){
			//System.out.println("fichier json :" + json.toString());
			Log.d("Personne", json.toString());


			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					System.out.println("success");
					// products found
					// Getting Array of Products
					JSONObject personne = json.getJSONObject(TAG_personne);
					System.out.println("personne" + personne.toString());

					idPrs = personne.getString(TAG_idPrs);
					System.out.println(idPrs);
					resultat =Integer.parseInt(idPrs);

				} else {
					System.out.println("-1");
					resultat = success;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return resultat;
		}
		else{
			return -1;
		}
	}
}

