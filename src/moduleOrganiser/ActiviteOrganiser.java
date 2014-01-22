package moduleOrganiser;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import moduleListeEvenements.ActiviteListeEvenements;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;
import com.mapquest.android.maps.GeoPoint;

import singletons.EvenementCourant;
import singletons.Utilisateur;

import outils.GestionMenu;
import outils.TesteurConnexionInternet;

import spotevent.fabien.un.R;
import grandModele.Evenement;
import grandModele.Marqueur;
import accesBDD.AccesBDDInstanciationUtilisateur;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ActiviteOrganiser extends Activity implements OnSlideMenuItemClickListener{

	private Context context;
	private int resultatInstanciation;
	private ModeleOrganiser modeleOrganiser;
	private VueOrganiser vueOrganiser;
	private ThikReceiver thickReceiver;
	private SlideMenu slidemenu;
	
	private AccesBDDInstanciationUtilisateur accesBDDInstanciationUtilisateur;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueOrganiser = new VueOrganiser(ActiviteOrganiser.this);
		modeleOrganiser = new ModeleOrganiser(ActiviteOrganiser.this);
		context = ActiviteOrganiser.this;

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.slide, this, 333);
		slidemenu.setHeaderImage(getResources().getDrawable(R.drawable.ic_launcher));
		
		accesBDDInstanciationUtilisateur = new AccesBDDInstanciationUtilisateur();

		new InstancierUtilisateurAsync().execute();

		//observer du modele
		MiseAJourListe miseAJourListe = new MiseAJourListe();
		modeleOrganiser.addObserver(miseAJourListe);

		//on instancie le receveur pour la mise a jour de la liste toutes les minutes
		this.registerReceiver(
				thickReceiver = new ThikReceiver(), 
				new IntentFilter(Intent.ACTION_TIME_TICK));
		
		vueOrganiser.boutonCreerEvenement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActiviteOrganiser.this, moduleCreerEvenement.ActiviteCreerEvenement.class);
				startActivity(intent);
			}
		});

	}

	public void stopReceiver(){
		if(thickReceiver!=null){
			System.out.println("Stop le receveur qui met a jour la liste toutes les minutes");
			ActiviteOrganiser.this.unregisterReceiver(thickReceiver);
			thickReceiver = null;
		}
	}

	class InstancierUtilisateurAsync extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
		}
		protected String doInBackground(String... args) {
			resultatInstanciation = accesBDDInstanciationUtilisateur.instanciationUtilisateur(Utilisateur.getUtilisateur().getIdPersonne());
			return null;
		}
		protected void onPostExecute(String file_url) {
			//si pas d'erreur de connexion
			if(resultatInstanciation==1){
				modeleOrganiser.setDateActuelle(Utilisateur.getUtilisateur().getDateMiseAJour());
			}
			else{
				Toast.makeText(ActiviteOrganiser.this, R.string.probleme_internet,Toast.LENGTH_SHORT).show();
			}
			//ON REACTIVE LE BOUTON RAFRAICHIR
		}
	}

	public class ThikReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0){
				// Do something
				modeleOrganiser.plusUneMinute();
				System.out.println("Date actuelle:"+modeleOrganiser.getDateActuelle());
			}
		}
	}

	@Override
	public void onDestroy(){
		stopReceiver();
		super.onDestroy();
	}

	@Override
	public void onPause(){
		stopReceiver();
		super.onPause();
	}

	/*@Override
	public void onResume(){
		super.onResume();
		if(modeleOrganiser!=null){
			modeleOrganiser.stopManagerDePositionAutomatique();
			new InstancierUtilisateurAsync().execute();
		}
	}*/

	//methode qui observe le modeleListeEvenement et recharge la liste
	public class MiseAJourListe implements Observer{

		@Override
		public void update(Observable observable, Object data) {
			System.out.println("Mise a jour de la liste");
			String[] listeEvenementsEnCours = modeleOrganiser.arrayListeEvenementToString(modeleOrganiser.getArrayEvenementsEnCours(),"C");
			String[] listeEvenementsAVenir = modeleOrganiser.arrayListeEvenementToString(modeleOrganiser.getArrayEvenementsAVenir(),"V");
			String[] listeEvenementsPasses = modeleOrganiser.arrayListeEvenementToString(modeleOrganiser.getArrayEvenementsPasses(),"P");

			//si la liste d'�v�nements en cours est non vide
			if(listeEvenementsEnCours.length!=0){

				vueOrganiser.titreListeEvtEnCours.setVisibility(0);

				vueOrganiser.listeEvtEnCoursListView.setAdapter(new ArrayAdapter<String>(ActiviteOrganiser.this, android.R.layout.simple_list_item_1,listeEvenementsEnCours));

				vueOrganiser.listeEvtEnCoursListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleOrganiser.getArrayEvenementsEnCours().get(position);
						new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteOrganiser.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueOrganiser.titreListeEvtEnCours.setVisibility(8);
				vueOrganiser.listeEvtEnCoursListView.setVisibility(8);
			}

			//si la liste d'�v�nements a venir est non vide
			if(listeEvenementsAVenir.length!=0){

				vueOrganiser.titreListeEvtAVenir.setVisibility(0);

				vueOrganiser.listeEvtAVenirListView.setAdapter(new ArrayAdapter<String>(ActiviteOrganiser.this, android.R.layout.simple_list_item_1,listeEvenementsAVenir));

				vueOrganiser.listeEvtAVenirListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleOrganiser.getArrayEvenementsAVenir().get(position);
						int idEvt = evt.getIdEvt();
						EvenementCourant evenementCourant = new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteOrganiser.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueOrganiser.titreListeEvtAVenir.setVisibility(8);
				vueOrganiser.listeEvtAVenirListView.setVisibility(8);
			}

			//si la liste d'�v�nements pass�s est non vide
			if(listeEvenementsPasses.length!=0){

				vueOrganiser.titreListeEvtPasses.setVisibility(0);

				vueOrganiser.listeEvtPassesListView.setAdapter(new ArrayAdapter<String>(ActiviteOrganiser.this, android.R.layout.simple_list_item_1,listeEvenementsPasses));

				vueOrganiser.listeEvtPassesListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleOrganiser.getArrayEvenementsPasses().get(position);
						int idEvt = evt.getIdEvt();
						EvenementCourant evenementCourant = new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteOrganiser.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueOrganiser.titreListeEvtPasses.setVisibility(8);
				vueOrganiser.listeEvtPassesListView.setVisibility(8);
			}
		}
	}



	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch(itemId) {
		case R.id.item_one:
			ActiviteOrganiser.this.finish();
			Toast.makeText(this, "Item one selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_two:
			ActiviteOrganiser.this.finish();
			break;
		case R.id.item_three:
			ActiviteOrganiser.this.finish();
			Toast.makeText(this, "Item three selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_four:
			break;
		}

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
		GestionMenu.rafraichir.setVisible(true);     



		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slidemenu.show();
			return true;
		case R.id.menu_refresh:
			// Comportement du bouton "Rafraichir"
			//ON DESACTIVE LE BOUTON RAFRAICHIR
			new InstancierUtilisateurAsync().execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}