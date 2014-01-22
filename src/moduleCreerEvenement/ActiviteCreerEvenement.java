package moduleCreerEvenement;

import grandModele.Evenement;
import grandModele.Personne;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.GestionMenu;
import outils.TesteurConnexionInternet;

import moduleCarte.ActiviteCarte;

import singletons.EvenementACreer;
import singletons.Utilisateur;
import spotevent.fabien.un.R;
import spotevent.fabien.un.R.menu;
import accesBDD.AccesBDDInstanciationUtilisateur;
import accesBDD.AccesBDDconnexion;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActiviteCreerEvenement extends Activity {

	private VueCreerEvenement vueCreerEvenement;
	private Context context = this;

	private int idPersonne;
	private String nomEvenement,dateDebut,dateFin,heureDebut,heureFin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueCreerEvenement = new VueCreerEvenement(ActiviteCreerEvenement.this);

		vueCreerEvenement.boutonSuivant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on recupere l'id de la personne dans la bdd
				nomEvenement = vueCreerEvenement.nomEvenement.getText().toString();
				dateDebut = vueCreerEvenement.dateDebut.getText().toString();
				heureDebut = vueCreerEvenement.heureDebut.getText().toString();
				dateFin = vueCreerEvenement.dateFin.getText().toString();
				heureFin = vueCreerEvenement.heureFin.getText().toString();

				Evenement evenementACreer = new Evenement();
				evenementACreer.setNomEvt(nomEvenement);
				evenementACreer.setDateDebutEvt(11);
				evenementACreer.setDateFinEvt(12);
				new EvenementACreer(evenementACreer);

				if (nomEvenement.equals("")) {
					Toast.makeText(ActiviteCreerEvenement.this,R.string.nom_evenement_ou_date_empty,Toast.LENGTH_SHORT).show();
					return;
				}

				//on teste si une connexion a internet existe
				if(!TesteurConnexionInternet.estConnecte(context)){
					Toast.makeText(ActiviteCreerEvenement.this, R.string.probleme_internet,Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent(ActiviteCreerEvenement.this,moduleCreerEvenement.ActiviteCreerEvenementConfV.class);
				startActivity(intent);
			}
		});

		//TTT Fonctions de remplissage des champs email et mdp
		vueCreerEvenement.buttonTestRemplir.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//on remplit les champs email et mdp
				vueCreerEvenement.nomEvenement.setText("Evenement a creer");
				vueCreerEvenement.dateDebut.setText("01/06/2013");
				vueCreerEvenement.heureDebut.setText("13:01");
				vueCreerEvenement.dateFin.setText("01/08/2013");
				vueCreerEvenement.heureFin.setText("14:06");
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.topbar, menu);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		GestionMenu.HideMenuOnCreation(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			Toast.makeText(this, "Le bouton qui ferme l'activite", Toast.LENGTH_SHORT).show();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
