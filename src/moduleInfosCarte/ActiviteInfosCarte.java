package moduleInfosCarte;

import moduleCarte.ActiviteCarte;
import outils.GestionMenu;
import singletons.EvenementCourant;
import spotevent.fabien.un.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActiviteInfosCarte extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VueInfosCarte vue = new VueInfosCarte(ActiviteInfosCarte.this);
		
		vue.textIdEvt.setText(EvenementCourant.getEvenementCourant().getIdEvt()+"");
		vue.textNomEvt.setText(EvenementCourant.getEvenementCourant().getNomEvt());

	}
	
	// A partir de la c'est le menu (topbar) et les actions des boutons de ce même menu.
	
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