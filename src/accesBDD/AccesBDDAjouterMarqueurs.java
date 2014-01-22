package accesBDD;

import grandModele.Marqueur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class AccesBDDAjouterMarqueurs {
	
	private static final String TAG_SUCCESS= "success";

	int success;
	int resultat=0;
	private JSONParserV2 jsonParser = new JSONParserV2();
	private static String url_ajouterMarqueurs = "http://fabienrichard.fr/projects/trackpack/Creer/creerMarqueur.php";



	public int ajouterMarqueur(ArrayList<Marqueur> marqueurs){

		Iterator<Marqueur> i=marqueurs.iterator(); // on crée un	 Iterator pour parcourir notre Set
		while(i.hasNext()) // tant qu'on a un suivant
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Marqueur courant = i.next();
			params.add(new BasicNameValuePair("idPrs", courant.getParticipant().getIdPersonne()+""));
			params.add(new BasicNameValuePair("idEvt", courant.getEvenement().getIdEvt()+""));
			params.add(new BasicNameValuePair("longitude", String.valueOf(courant.getGeo().getLongitude())));
			params.add(new BasicNameValuePair("latitude", String.valueOf(courant.getGeo().getLatitude())));

			JSONObject json = jsonParser.faireHttpRequest(url_ajouterMarqueurs, "POST", params);
			System.out.println("fichier json :" + json.toString());
			// Checking for SUCCESS TAG
			try {
				success = json.getInt(TAG_SUCCESS);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (success == 1) {
				resultat =1;
			}
			else{
				resultat = success;
			}
		}

		return resultat;
	}

}
