package moduleConnexion;

import grandModele.Personne;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.TesteurConnexionInternet;

import moduleCarte.ActiviteCarte;

import singletons.Utilisateur;
import spotevent.fabien.un.R;
import spotevent.fabien.un.R.menu;
import accesBDD.AccesBDDInstanciationUtilisateur;
import accesBDD.AccesBDDconnexion;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ActiviteConnexion extends Activity {

	private VueConnexion vueConnexion;
	private Context context = this;

	private int idPersonne;
	private String emailTxt,mdpTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueConnexion = new VueConnexion(ActiviteConnexion.this);

		vueConnexion.boutonConnexion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on recupere l'id de la personne dans la bdd
				emailTxt = vueConnexion.email.getText().toString();
				mdpTxt = vueConnexion.mdp.getText().toString();

				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(emailTxt);
				if (!m.matches()) {
					Toast.makeText(ActiviteConnexion.this, R.string.email_format_error,Toast.LENGTH_SHORT).show();
					return;
				}
				if (emailTxt.equals("") || mdpTxt.equals("")) {
					Toast.makeText(ActiviteConnexion.this,
							R.string.email_or_password_empty,
							Toast.LENGTH_SHORT).show();
					return;
				}

				//on teste si une connexion a internet existe
				if(!TesteurConnexionInternet.estConnecte(context)){
					Toast.makeText(ActiviteConnexion.this, R.string.probleme_internet,Toast.LENGTH_SHORT).show();
					return;
				}

				new EssayerConnexionAsync().execute();

			}
		});
		vueConnexion.boutonSInscrire.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ActiviteConnexion.this.finish();
				Intent intent = new Intent(ActiviteConnexion.this, moduleInscription.ActiviteInscription.class);
				startActivity(intent);
			}
		});
		//TTT Fonctions de remplissage des champs email et mdp
		vueConnexion.boutonRemplir1TTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueConnexion.email.setText("david.bonachera@etu.univ-nantes.fr");
				vueConnexion.mdp.setText("david");
			}
		});
		vueConnexion.boutonRemplir2TTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueConnexion.email.setText("valentin.lhommeau@etu.univ-nantes.fr");
				vueConnexion.mdp.setText("valentin");
			}
		});
		vueConnexion.boutonRemplir3TTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueConnexion.email.setText("valentin.proust@etu.univ-nantes.fr");
				vueConnexion.mdp.setText("valentin");
			}
		});
		vueConnexion.boutonRemplir4TTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueConnexion.email.setText("fabien.richard@etu.univ-nantes.fr");
				vueConnexion.mdp.setText("fabien");
			}
		});
	}

	class EssayerConnexionAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
		}
		protected String doInBackground(String... args) {
			AccesBDDconnexion accesBDDconnexion = new AccesBDDconnexion();
			idPersonne = accesBDDconnexion.essayerConnexion(emailTxt,mdpTxt);
			return null;
		}
		protected void onPostExecute(String file_url) {
			if(idPersonne==0){
				Toast.makeText(ActiviteConnexion.this,R.string.echec_connexion,Toast.LENGTH_SHORT).show();
				return;
			}
			if(idPersonne==-1){
				Toast.makeText(ActiviteConnexion.this,R.string.probleme_internet,Toast.LENGTH_SHORT).show();
				return;
			}
			//on instancie l'utilisateur avec son id
			Personne personne = new Personne();
			personne.setIdPersonne(idPersonne);
			new Utilisateur(personne);
			Intent intent = new Intent(ActiviteConnexion.this, moduleListeEvenements.ActiviteListeEvenements.class);
			finish();
			startActivity(intent);

		}
	}

}
