package moduleCreerEvenement;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueCreerEvenementConfV {

	public RadioGroup radioConfV;
	public RadioButton radioConfVButton;
	public Button boutonSuivant;

	public VueCreerEvenementConfV(Activity activite){
		activite.setContentView(R.layout.activite_creer_evenement_confv);
		radioConfV = (RadioGroup) activite.findViewById(R.id.radioConfV);
		boutonSuivant = (Button) activite.findViewById(R.id.boutonSuivant);
	}
}

