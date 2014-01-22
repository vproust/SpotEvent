package accesBDD;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class AccesBDDajouterPersonne {
	private static final String TAG_SUCCESS= "success";
	private static final String TAG_idPersonne= "idPersonne";

	int success;
	int resultat,idPersonne;
	private JSONParserV2 jsonParser = new JSONParserV2();
	private static String url_ajouterPersonne = "http://fabienrichard.fr/projects/trackpack/Creer/creerPersonne.php";



	public int ajouterPersonne(String nom, String prenom, String email, String mdp){

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("prenom", prenom));
			params.add(new BasicNameValuePair("nom", nom));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("mdp", mdp));
			
			System.out.println(params.toString());
			JSONObject json = jsonParser.faireHttpRequest(url_ajouterPersonne, "POST", params);
			System.out.println("fichier json :" + json.toString());
			// Checking for SUCCESS TAG
			try {
				success = json.getInt(TAG_SUCCESS);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (success == 1) {
				try {
					idPersonne = json.getInt(TAG_idPersonne);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				resultat = idPersonne;
			}
			else{
				resultat = success;
			}

		return resultat;
	}

}