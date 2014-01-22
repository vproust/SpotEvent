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

public class ActiviteCreerEvenementConfV extends Activity {

	private VueCreerEvenementConfV vueCreerEvenementConfV;
	private Context context = this;

	private RadioButton radioConfVButton;
	private int confV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vueCreerEvenementConfV = new VueCreerEvenementConfV(ActiviteCreerEvenementConfV.this);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		vueCreerEvenementConfV.boutonSuivant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				int selectedId = vueCreerEvenementConfV.radioConfV.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				radioConfVButton = (RadioButton) findViewById(selectedId);
				String stringConfV = (String) radioConfVButton.getText();
				
				if(stringConfV.equals("Publique")){
					confV = 2;
				}
				else if(stringConfV.equals("Sur confirmation")){
					confV = 1;
				}
				else{
					confV = 0;
				}
				
				EvenementACreer.getEvenementACreer().setConfV(confV);
				
				Intent intent = new Intent(ActiviteCreerEvenementConfV.this,moduleCreerEvenement.ActiviteCreerEvenementConfP.class);
				startActivity(intent);
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