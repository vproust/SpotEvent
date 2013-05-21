package moduleCarte;

import grandModele.Personne;

import java.util.Iterator;

import singletons.EvenementCourant;
import singletons.Utilisateur;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import spotevent.fabien.un.R;
import accesBDD.EvenementBDD;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
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
		
		vueCarte.boutonInfosCarteTTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActiviteCarte.this, moduleInfosCarte.ActiviteInfosCarte.class);
				startActivity(intent);
				//overridePendingTransition(0,0);
			}
		});
		
		vueCarte.boutonMiseAJourPosition.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				modeleCarte.miseAJourPositionManuelle();
			}
		});
		
		vueCarte.boutonToogleTrace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				modeleCarte.toogleTraces();
				//si la trace est activée
				if(modeleCarte.trace){
					vueCarte.boutonToogleTrace.setText("Masquer les traces");
				}
				else{
					vueCarte.boutonToogleTrace.setText("Afficher les traces");
				}
			}
		});
		

		//TTT
		Toast.makeText(ActiviteCarte.this, EvenementCourant.getEvenementCourant().getIdEvt(),Toast.LENGTH_SHORT).show();
		///TTT
		EvenementBDD.setMarqueurs(ActiviteCarte.this);
		
		modeleCarte.afficherDerniersMarqueurs();
	}

	

	public boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activite_connexion, menu);
		return true;
	}

}