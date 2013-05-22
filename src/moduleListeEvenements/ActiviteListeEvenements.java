package moduleListeEvenements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import singletons.EvenementCourant;
import singletons.Utilisateur;

import moduleConnexion.ActiviteConnexion;

import spotevent.fabien.un.R;
import grandModele.Evenement;
import accesBDD.ConnexionBDD;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ActiviteListeEvenements extends Activity {

	private ArrayList<Evenement> arrayListeEvenements;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VueListeEvenements vue = new VueListeEvenements(ActiviteListeEvenements.this);
		vue.textIdPersonne.setText(Utilisateur.getUtilisateur().getIdPersonne());

		arrayListeEvenements = Utilisateur.getUtilisateur().getArrayListeEvenements();	
		String[] listeEvenements = Utilisateur.getUtilisateur().arrayListeEvenementToString(arrayListeEvenements);

		//si la liste d'événements en cours est non vide
		if(listeEvenements.length!=0){
			
			vue.titreListeEvtEnCours.setVisibility(0);
			
			vue.listeEvtEnCoursListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeEvenements));

			vue.listeEvtEnCoursListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
					Evenement evt = arrayListeEvenements.get(position);
					String idEvt = evt.getIdEvt();
					EvenementCourant evenementCourant = new EvenementCourant(evt);
					Intent intent = new Intent(ActiviteListeEvenements.this, moduleCarte.ActiviteCarte.class);
					startActivity(intent);
				}
			});
		}

		vue.boutonVueConnexionTTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActiviteListeEvenements.this, moduleConnexion.ActiviteConnexion.class);
				startActivity(intent);
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