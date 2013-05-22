package moduleInfosCarte;

import singletons.EvenementCourant;
import spotevent.fabien.un.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ActiviteInfosCarte extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VueInfosCarte vue = new VueInfosCarte(ActiviteInfosCarte.this);
		
		vue.textIdEvt.setText(EvenementCourant.getEvenementCourant().getIdEvt());
		vue.textNomEvt.setText(EvenementCourant.getEvenementCourant().getNomEvt());

		vue.boutonVueCarteTTT.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ActiviteInfosCarte.this.finish();
				//Intent intent = new Intent(ActiviteInfosCarte.this, moduleCarte.ActiviteCarte.class);
				//startActivity(intent);
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