package moduleCreerEvenement;

import outils.GestionMenu;
import grandModele.Evenement;
import singletons.EvenementACreer;
import spotevent.fabien.un.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActiviteCreerEvenementConfP extends Activity {

	private VueCreerEvenementConfP vueCreerEvenementConfP;
	private Context context = this;

	private RadioButton radioConfPButton;
	private int confP;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueCreerEvenementConfP = new VueCreerEvenementConfP(ActiviteCreerEvenementConfP.this);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		vueCreerEvenementConfP.boutonSuivant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				int selectedId = vueCreerEvenementConfP.radioConfP.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				radioConfPButton = (RadioButton) findViewById(selectedId);
				String stringConfP = (String) radioConfPButton.getText();
				
				if(stringConfP.equals("Tout le monde")){
					confP = 2;
				}
				else if(stringConfP.equals("Sur confirmation")){
					confP = 1;
				}
				else{
					confP = 0;
				}
				
				EvenementACreer.getEvenementACreer().setConfP(confP);
				
				//TTT
				Evenement test = EvenementACreer.getEvenementACreer();
				System.out.println("Nom "+test.getNomEvt());
				System.out.println("Debut "+test.getDateDebutEvt());
				System.out.println("Fin "+test.getDateFinEvt());
				System.out.println("ConfV "+test.getConfV());
				System.out.println("ConfP "+test.getConfP());
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