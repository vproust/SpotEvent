package moduleInscription;

import grandModele.Personne;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.TesteurConnexionInternet;

import singletons.Utilisateur;
import spotevent.fabien.un.R;

import moduleInscription.ActiviteInscription.CreerPersonneAsync;
import accesBDD.AccesBDDajouterPersonne;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActiviteInscription extends Activity {
	private VueInscription vueCreerPersonne;
	private Context context = this;
	private String prenomTxt,nomTxt,emailTxt,mdpTxt,confirmationTxt;
	private int resultat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueCreerPersonne = new VueInscription(ActiviteInscription.this);

		vueCreerPersonne.boutonCreerPersonne.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				prenomTxt =vueCreerPersonne.prenom.getText().toString();
				nomTxt = vueCreerPersonne.nom.getText().toString();
				emailTxt = vueCreerPersonne.email.getText().toString();
				mdpTxt = vueCreerPersonne.mdp.getText().toString();
				confirmationTxt = vueCreerPersonne.confirmation.getText().toString();

				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(emailTxt);
				if (!m.matches()) {
					Toast.makeText(ActiviteInscription.this, R.string.email_format_error,Toast.LENGTH_SHORT).show();
					return;
				}
				if (!mdpTxt.equals(confirmationTxt)) {
					Toast.makeText(ActiviteInscription.this,
							R.string.confirmation_does_not_match,
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (emailTxt.equals("") || mdpTxt.equals("") || nomTxt.equals("") || nomTxt.equals("") ) {
					Toast.makeText(ActiviteInscription.this,
							R.string.field_empty,
							Toast.LENGTH_SHORT).show();
					return;
				}

				//on teste si une connexion a internet existe
				if(!TesteurConnexionInternet.estConnecte(context)){
					Toast.makeText(ActiviteInscription.this, R.string.probleme_internet,Toast.LENGTH_SHORT).show();
					return;
				}

				new CreerPersonneAsync().execute();

			}
		});
	}

	class CreerPersonneAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
		}
		protected String doInBackground(String... args) {
			AccesBDDajouterPersonne accesBDDajouterPersonne = new AccesBDDajouterPersonne();
			resultat = accesBDDajouterPersonne.ajouterPersonne(nomTxt,prenomTxt,emailTxt,mdpTxt);
			return null;
		}
		protected void onPostExecute(String file_url) {
			if(resultat==0){
				Toast.makeText(ActiviteInscription.this,R.string.echec_connexion,Toast.LENGTH_SHORT).show();
				return;
			}
			else if(resultat==-1){
				Toast.makeText(ActiviteInscription.this,R.string.probleme_internet,Toast.LENGTH_SHORT).show();
				return;
			}
			else{
				//Toast.makeText(ActiviteInscription.this,R.string.confirmation_inscription,Toast.LENGTH_SHORT).show();
				//on instancie l'utilisateur avec son id
				Personne personne = new Personne();
				personne.setIdPersonne(resultat);
				new Utilisateur(personne);
				Intent intent = new Intent(ActiviteInscription.this, moduleListeEvenements.ActiviteListeEvenements.class);
				finish();
				startActivity(intent);
			}
		}
	}

}