package accesBDD;

import grandModele.Evenement;
import grandModele.Personne;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import singletons.Utilisateur;

import android.util.Log;

public class AccesBDDInstanciationUtilisateur {
	int idPrs;
	int resultat;
	// Creating JSON Parser object
	JSONParserV2 jParser = new JSONParserV2();

	// url to get all products list
	private static String url_instanciationUtilisateur = "http://fabienrichard.fr/projects/trackpack/Lecture/instanciationUtilisateur";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TIMESTAMP = "timestamp";
	private static final String TAG_PERSONNE= "personne";
	private static final String TAG_idPrs= "idPrs";
	private static final String TAG_email= "email";
	private static final String TAG_nom= "nom";
	private static final String TAG_prenom= "prenom";
	private static final String TAG_mdp= "mdp";
	private static final String TAG_EVENEMENTS= "evenements";
	private static final String TAG_idEvt= "idEvt";
	private static final String TAG_nomEvt= "nomEvt";
	private static final String TAG_dateDebutEvt= "dateDebut";
	private static final String TAG_dateFinEvt= "dateFin";
	private static final String TAG_role= "role";
	private static final String TAG_partagePosition= "partagePosition";
	private static final String TAG_numero= "numero";
	
	// products JSONArray
	JSONArray personne = null;

	public int instanciationUtilisateur(int idPrs){
		this.idPrs = idPrs;
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("idPrs", idPrs+""));
		// getting JSON string from URL
		JSONObject json = new JSONObject();
		//json = new JSONObject("{\"personne\":{\"idPrs\":\"1\",\"email\":\"antoin@aol.fr\",\"mdp\":\"lovechrichri\",\"nom\":\"Pigeau\",\"prenom\":\"Antoine\",\"evenements\":[{\"idEvt\":\"2\",\"nomEvt\":\"regateJ80\"}]},\"success\":1}");
		json = jParser.faireHttpRequest(url_instanciationUtilisateur, "GET", params);

		// Check your log cat for JSON reponse
		//em.out.println("fichier json :" + json.toString());
		Log.d("Personne", json.toString());


		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				//date de recuperation des donnees
				long dateMAJ = json.getLong(TAG_TIMESTAMP);
				
				// personne trouvée
				// On récupère la personne
				JSONObject personneJson = json.getJSONObject(TAG_PERSONNE);

				String id = personneJson.getString(TAG_idPrs);
				String email = personneJson.getString(TAG_email);

				String mdp = personneJson.getString(TAG_mdp);
				String nom = personneJson.getString(TAG_nom);

				String prenom = personneJson.getString(TAG_prenom);

				//System.out.println("utilisateur"+json.toString());
				System.out.println("email:"+email);

				System.out.println("id:"+id);

				// Création d’un nouvel objet personne
				Personne personne = new Personne();

				personne.setIdPersonne(Integer.parseInt(id));
				personne.setNomPersonne(nom);
				personne.setPrenomPersonne(prenom);
				personne.setDateMiseAJour(dateMAJ);

				//teste si l'utilisateur intervient au moins dans un evenement
				if(json.optJSONArray(TAG_EVENEMENTS)!=null){
					//recupere le tableau des evenements
					JSONArray evenementsJSON = json.getJSONArray(TAG_EVENEMENTS);
					for (int j = 0; j < evenementsJSON.length(); j++) {
						
						JSONObject d = evenementsJSON.getJSONObject(j);
						
						int idEvt = d.getInt(TAG_idEvt);
						String nomEvt = d.getString(TAG_nomEvt);
						long dateDebutEvt = d.getLong(TAG_dateDebutEvt);
						long dateFinEvt = d.getLong(TAG_dateFinEvt);
						String role = d.getString(TAG_role);
						String partagePosition = d.getString(TAG_partagePosition);
						boolean boolPartagePosition = (Integer.parseInt(partagePosition)!=0);
						//int numero = d.getInt(TAG_numero);
						
						Evenement evenement = new Evenement();
						evenement.setIdEvt(idEvt);
						evenement.setNomEvt(nomEvt);
						evenement.setDateDebutEvt(dateDebutEvt);
						evenement.setDateFinEvt(dateFinEvt);
						evenement.setRole(role);
						evenement.setPartagePosition(boolPartagePosition);
						//evenement.setNumero(numero);
						
						personne.ajouterEvenement(evenement);
					}
				}
				new Utilisateur(personne);
				resultat=1;

			} else {
				System.out.println("-1");
				resultat = success;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultat;
	}
}

