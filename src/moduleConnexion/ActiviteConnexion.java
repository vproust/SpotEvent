package moduleConnexion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.TesteurConnexionInternet;

import moduleCarte.ActiviteCarte;

import spotevent.fabien.un.R;
import spotevent.fabien.un.R.menu;
import accesBDD.ConnexionBDD;
import android.net.ConnectivityManager;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueConnexion = new VueConnexion(ActiviteConnexion.this);

		vueConnexion.boutonConnexion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on recupere l'id de la personne dans la bdd
				String emailTxt = vueConnexion.email.getText().toString();
				String mdpTxt = vueConnexion.mdp.getText().toString();

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
					Toast.makeText(ActiviteConnexion.this, "VŽrifiez votre connexion Internet",Toast.LENGTH_SHORT).show();
					return;
				}

				String idPersonne = ConnexionBDD.essayerConnexion(emailTxt,mdpTxt);
				//si l'id est prs0, il y a eu echec de connexion
				if(idPersonne!="0"&&idPersonne!="-1"){
					//on instancie l'utilisateur
					ConnexionBDD.instancierUtilisateur(idPersonne);
					Intent intent = new Intent(ActiviteConnexion.this, moduleListeEvenements.ActiviteListeEvenements.class);
					startActivity(intent);
				}
				else{
					Toast.makeText(ActiviteConnexion.this,R.string.echec_connexion,Toast.LENGTH_SHORT).show();
				}
			}
		});
		vueConnexion.boutonConnexionAnonyme.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on instancie l'utilisateur anonyme
				ConnexionBDD.instancierUtilisateurAnonyme();
				Intent intent = new Intent(ActiviteConnexion.this, moduleListeEvenements.ActiviteListeEvenements.class);
				startActivity(intent);
			}
		});
		//TTT Fonction de remplissage des champs email et mdp
		vueConnexion.boutonRemplirTTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueConnexion.email.setText("emaildetest@test.com");
				vueConnexion.mdp.setText("test");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activite_connexion, menu);
		return true;
	}

}
