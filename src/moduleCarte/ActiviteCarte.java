package moduleCarte;

import grandModele.Personne;

import java.util.Iterator;

import moduleConnexion.ActiviteConnexion;

import outils.GestionMenu;

import singletons.EvenementCourant;
import singletons.Utilisateur;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import spotevent.fabien.un.R;
import accesBDD.AccesBDDCompleterEvenement;
import accesBDD.AccesBDDconnexion;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActiviteCarte extends MapActivity {

	protected VueCarte vueCarte;
	protected ModeleCarte modeleCarte;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueCarte = new VueCarte(ActiviteCarte.this);
		modeleCarte = new ModeleCarte(ActiviteCarte.this,vueCarte.map);
		
		modeleCarte.setMap();
				
		//TTT
		//Toast.makeText(ActiviteCarte.this, EvenementCourant.getEvenementCourant().getIdEvt()+"",Toast.LENGTH_SHORT).show();
		///TTT
		//EvenementBDD.setMarqueurs(ActiviteCarte.this);
		modeleCarte.completerEvenementAsync();
	}

	public boolean isRouteDisplayed() {
		return false;
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
        GestionMenu.majpos.setVisible(true);  
        GestionMenu.trace.setVisible(true);
        GestionMenu.info.setVisible(true);
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			Toast.makeText(this, "Le bouton qui ferme l'activite", Toast.LENGTH_SHORT).show();
			finish();
			return true;
		case R.id.menu_info:
			Intent intent = new Intent(ActiviteCarte.this, moduleInfosCarte.ActiviteInfosCarte.class);
			startActivity(intent);
			return true;
		case R.id.menu_majpos:
			modeleCarte.miseAJourPositionManuelle();
			return true;
		case R.id.menu_refresh:
			// Comportement du bouton "Rafraichir"
			modeleCarte.completerEvenementAsync();
			return true;
		case R.id.menu_trace:
			modeleCarte.toogleTraces();
			//si la trace est activ�e on modifie le contenu de l'item trace
			if(modeleCarte.trace){
				GestionMenu.trace.setTitle("Masquer les traces");
			}
			else{
				GestionMenu.trace.setTitle("Afficher les traces");
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}