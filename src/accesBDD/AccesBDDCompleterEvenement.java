package accesBDD;

import grandModele.Evenement;
import grandModele.Marqueur;
import grandModele.Personne;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import singletons.EvenementCourant;

import android.content.Context;
import android.widget.Toast;

import com.mapquest.android.maps.GeoPoint;

public class AccesBDDCompleterEvenement {
	private Context context;

	private int idEvt;
	private int success;
	private int resultat;
	// Creating JSON Parser object
	JSONParserV2 jParser = new JSONParserV2();

	// url to get all products list
	private static String url_connexion = "http://fabienrichard.fr/projects/trackpack/Lecture/instanciationEvenement";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_participants= "participants";
	private static final String TAG_idPrs= "idPrs";
	private static final String TAG_LATITUDE= "latitude";
	private static final String TAG_LONGITUDE= "longitude";
	private static final String TAG_nomEvenement= "nomEvenement";
	private static final String TAG_timestamp= "timestamp";
	private static final String TAG_prenom= "prenom";
	private static final String TAG_nom= "nom";
	private static final String TAG_numero= "numero";
	private static final String TAG_partagePosition= "partagePosition";
	private static final String TAG_marqueurs= "marqueurs";
	private static final String TAG_infosEvenement = "infosEvenement";
	private static final String TAG_dateDebut= "dateDebut";
	private static final String TAG_dateFin= "dateFin";

	// products JSONArray
	JSONArray personne = null;

	public AccesBDDCompleterEvenement(Context context){
		this.context = context;
	}

	public int completerEvenement(int idEvt){
		this.idEvt = idEvt;
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("idEvt", idEvt+""));
		// getting JSON string from URL
		JSONObject json = new JSONObject();
		json = jParser.faireHttpRequest(url_connexion, "GET", params);
		/*try {
			json = new JSONObject("{\"success\":1,\"infosEvenement\":{\"id\":\"1\",\"nomEvenement\":\"regate de Noel\",\"dateDebut\":\"1369237316\",\"dateFin\":\"1369330916\",\"confP\":\"0\",\"confV\":\"0\",\"maj\":\"2013\"},\"participants\":[{\"idPrs\":\"1\",\"nom\":\"Bonachera\",\"prenom\":\"David\",\"role\":\"S\",\"partagePosition\":\"1\",\"numero\":null,\"icone\":null,\"marqueurs\":[{\"idMarq\":\"1\",\"latitude\":\"0\",\"longitude\":\"0\",\"timestamp\":\"5764\"},{\"idMarq\":\"2\",\"latitude\":\"0\",\"longitude\":\"0\",\"timestamp\":\"1369226831\"},{\"idMarq\":\"7\",\"latitude\":\"35\",\"longitude\":\"38\",\"timestamp\":\"1369231832\"},{\"idMarq\":\"8\",\"latitude\":\"35\",\"longitude\":\"38\",\"timestamp\":\"1369232288\"},{\"idMarq\":\"9\",\"latitude\":\"35\",\"longitude\":\"38\",\"timestamp\":\"1369232289\"},{\"idMarq\":\"10\",\"latitude\":\"35\",\"longitude\":\"38\",\"timestamp\":\"1369232395\"}]},{\"idPrs\":\"2\",\"nom\":\"Lhommeau\",\"prenom\":\"Valentin\",\"role\":\"S\",\"partagePosition\":\"1\",\"numero\":\"12\",\"icone\":null,\"marqueurs\":[{\"idMarq\":\"12\",\"latitude\":\"0\",\"longitude\":\"0\",\"timestamp\":\"1369263168\"}]},{\"idPrs\":\"3\",\"nom\":\"Proust\",\"prenom\":\"Valentin\",\"role\":\"S\",\"partagePosition\":\"1\",\"numero\":\"12\",\"icone\":null}]}");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		// Check your log cat for JSON reponse
		//System.out.println("fichier json :" + json.toString());

		try {
			// Checking for SUCCESS TAG
			System.out.println("Success tag détécté");
			success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// evenement trouvée
				// On récupère l'évènement
				JSONObject evenementJson = json.getJSONObject(TAG_infosEvenement);

				// Storing each json item in variable

				String nomEvenement = evenementJson.getString(TAG_nomEvenement);

				String dateDebut = evenementJson.getString(TAG_dateDebut);

				String dateFin = evenementJson.getString(TAG_dateFin);

				// Création d’un nouvel objet personne
				Evenement evenement = new Evenement();
				evenement.setIdEvt(idEvt);
				evenement.setNomEvt(nomEvenement);
				System.out.println("Evt instancié");

				if(json.optJSONArray(TAG_participants)!=null){

					JSONArray participantsJSON = json.getJSONArray(TAG_participants);

					System.out.println("nombre de participants : "+participantsJSON.length());

					for (int j = 0; j < participantsJSON.length(); j++) {
						JSONObject participantJson = participantsJSON.getJSONObject(j);

						String idPrs = participantJson.getString(TAG_idPrs);
						String prenom = participantJson.getString(TAG_prenom);
						String nom = participantJson.getString(TAG_nom);
						int numero = participantJson.getInt(TAG_numero);
						int partagePosition = participantJson.getInt(TAG_partagePosition);

						//Nouvelle personne
						Personne personne = new Personne();
						personne.setIdPersonne(Integer.parseInt(idPrs));
						personne.setPrenomPersonne(prenom);
						personne.setNomPersonne(nom);
						//si le numero est différent de 0
						if(numero!=0){
							personne.setNumPersonne(numero,context);


							if(participantJson.optJSONArray(TAG_marqueurs)!=null){
								JSONArray marqueursJSON = participantJson.getJSONArray(TAG_marqueurs);

								System.out.println("tqg ;qraueur ="+json.optJSONArray(TAG_marqueurs));

								System.out.println("nombre de marqueurs : "+marqueursJSON.length());

								for (int i = 0; i < marqueursJSON.length(); i++) {
									System.out.println("Marqueur "+i);
									JSONObject marqueurJson = marqueursJSON.getJSONObject(i);
									String latitude = marqueurJson.getString(TAG_LATITUDE);
									String longitude = marqueurJson.getString(TAG_LONGITUDE);
									String date = marqueurJson.getString(TAG_timestamp);

									//Nouveau marqueur
									Marqueur marqueur = new Marqueur(new GeoPoint(Double.parseDouble(latitude),Double.parseDouble(longitude)));
									marqueur.setDate(Long.valueOf(date));
									marqueur.setParticipant(personne);

									evenement.ajouterMarqueur(marqueur);
									personne.ajouterMarqueur(marqueur);
									marqueur.setEvenement(evenement);
								}
							}
						}
						personne.ajouterEvenement(evenement);
						evenement.ajouterParticipant(personne);
					}
				}
				// On met à jour l'évènement courant
				EvenementCourant evt = new EvenementCourant(evenement);
				System.out.println("evenement courant"+evt.getEvenementCourant().getNomEvt());
				resultat = 1;
			}
			else {
				System.out.println("-1");
				resultat = success;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return success;
	}
}
