package moduleListeEvenements;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

public class ActiviteListeEvenements extends Activity implements OnSlideMenuItemClickListener{

	private Context context;
	private int resultatInstanciation;
	private ModeleListeEvenements modeleListeEvenements;
	private VueListeEvenements vueListeEvenements;
	private ThikReceiver thickReceiver;
	private SlideMenu slidemenu;
	
	private AccesBDDInstanciationUtilisateur accesBDDInstanciationUtilisateur;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueListeEvenements = new VueListeEvenements(ActiviteListeEvenements.this);
		modeleListeEvenements = new ModeleListeEvenements(ActiviteListeEvenements.this);
		context = ActiviteListeEvenements.this;

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.slide, this, 333);
		slidemenu.setHeaderImage(getResources().getDrawable(R.drawable.ic_launcher));
		
		accesBDDInstanciationUtilisateur = new AccesBDDInstanciationUtilisateur();

		new InstancierUtilisateurAsync().execute();

		//observer du modele
		MiseAJourListe miseAJourListe = new MiseAJourListe();
		modeleListeEvenements.addObserver(miseAJourListe);

		//on instancie le receveur pour la mise a jour de la liste toutes les minutes
		this.registerReceiver(
				thickReceiver = new ThikReceiver(), 
				new IntentFilter(Intent.ACTION_TIME_TICK));

	}

	public void stopReceiver(){
		if(thickReceiver!=null){
			System.out.println("Stop le receveur qui met a jour la liste toutes les minutes");
			ActiviteListeEvenements.this.unregisterReceiver(thickReceiver);
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
				modeleListeEvenements.setDateActuelle(Utilisateur.getUtilisateur().getDateMiseAJour());
				modeleListeEvenements.managerDePositionAutomatique();
			}
			else{
				Toast.makeText(ActiviteListeEvenements.this, R.string.probleme_internet,Toast.LENGTH_SHORT).show();
				ActiviteListeEvenements.this.finish();
			}
			//ON REACTIVE LE BOUTON RAFRAICHIR
		}
	}

	public class ThikReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0){
				// Do something
				modeleListeEvenements.plusUneMinute();
				System.out.println("Date actuelle:"+modeleListeEvenements.getDateActuelle());
			}
		}
	}

	@Override
	public void onDestroy(){
		stopReceiver();
		modeleListeEvenements.stopManagerDePositionAutomatique();
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
		if(modeleListeEvenements!=null){
			modeleListeEvenements.stopManagerDePositionAutomatique();
			new InstancierUtilisateurAsync().execute();
		}
	}*/

	//methode qui observe le modeleListeEvenement et recharge la liste
	public class MiseAJourListe implements Observer{

		@Override
		public void update(Observable observable, Object data) {
			System.out.println("Mise a jour de la liste");
			String[] listeEvenementsEnCours = modeleListeEvenements.arrayListeEvenementToString(modeleListeEvenements.getArrayEvenementsEnCours(),"C");
			String[] listeEvenementsAVenir = modeleListeEvenements.arrayListeEvenementToString(modeleListeEvenements.getArrayEvenementsAVenir(),"V");
			String[] listeEvenementsPasses = modeleListeEvenements.arrayListeEvenementToString(modeleListeEvenements.getArrayEvenementsPasses(),"P");

			//si la liste d'�v�nements en cours est non vide
			if(listeEvenementsEnCours.length!=0){

				vueListeEvenements.titreListeEvtEnCours.setVisibility(0);

				vueListeEvenements.listeEvtEnCoursListView.setAdapter(new ArrayAdapter<String>(ActiviteListeEvenements.this, android.R.layout.simple_list_item_1,listeEvenementsEnCours));

				vueListeEvenements.listeEvtEnCoursListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleListeEvenements.getArrayEvenementsEnCours().get(position);
						new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteListeEvenements.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueListeEvenements.titreListeEvtEnCours.setVisibility(8);
				vueListeEvenements.listeEvtEnCoursListView.setVisibility(8);
			}

			//si la liste d'�v�nements a venir est non vide
			if(listeEvenementsAVenir.length!=0){

				vueListeEvenements.titreListeEvtAVenir.setVisibility(0);

				vueListeEvenements.listeEvtAVenirListView.setAdapter(new ArrayAdapter<String>(ActiviteListeEvenements.this, android.R.layout.simple_list_item_1,listeEvenementsAVenir));

				vueListeEvenements.listeEvtAVenirListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleListeEvenements.getArrayEvenementsAVenir().get(position);
						int idEvt = evt.getIdEvt();
						EvenementCourant evenementCourant = new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteListeEvenements.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueListeEvenements.titreListeEvtAVenir.setVisibility(8);
				vueListeEvenements.listeEvtAVenirListView.setVisibility(8);
			}

			//si la liste d'�v�nements pass�s est non vide
			if(listeEvenementsPasses.length!=0){

				vueListeEvenements.titreListeEvtPasses.setVisibility(0);

				vueListeEvenements.listeEvtPassesListView.setAdapter(new ArrayAdapter<String>(ActiviteListeEvenements.this, android.R.layout.simple_list_item_1,listeEvenementsPasses));

				vueListeEvenements.listeEvtPassesListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
						Evenement evt = modeleListeEvenements.getArrayEvenementsPasses().get(position);
						int idEvt = evt.getIdEvt();
						EvenementCourant evenementCourant = new EvenementCourant(evt);
						Intent intent = new Intent(ActiviteListeEvenements.this, moduleCarte.ActiviteCarte.class);
						startActivity(intent);
					}
				});
			}
			//on recache le titre et la liste
			else{
				vueListeEvenements.titreListeEvtPasses.setVisibility(8);
				vueListeEvenements.listeEvtPassesListView.setVisibility(8);
			}
			//si toutes les listes sont vides, on dit a l'utilisateur comment en chercher un
			if((listeEvenementsPasses.length==0)&&(listeEvenementsAVenir.length==0)&&(listeEvenementsEnCours.length==0)){
				vueListeEvenements.textViewAideRecherche.setVisibility(0);
			}
			//on rechache l'aide
			else{
				vueListeEvenements.textViewAideRecherche.setVisibility(8);
			}
		}
	}



	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch(itemId) {
		case R.id.item_one:
			Toast.makeText(this, "Item one selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_two:
			break;
		case R.id.item_three:
			Toast.makeText(this, "Item three selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_four:
			Toast.makeText(this, "Item four selected", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ActiviteListeEvenements.this, moduleOrganiser.ActiviteOrganiser.class);
			startActivity(intent);
			break;
		case R.id.item_five:
			Toast.makeText(this, "Item five selected", Toast.LENGTH_SHORT).show();
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
			modeleListeEvenements.stopManagerDePositionAutomatique();
			new InstancierUtilisateurAsync().execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}