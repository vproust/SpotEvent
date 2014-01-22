package moduleCreerEvenement;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueCreerEvenementConfP {

	public RadioGroup radioConfP;
	public RadioButton radioConfPButton;
	public Button boutonSuivant;

	public VueCreerEvenementConfP(Activity activite){
		activite.setContentView(R.layout.activite_creer_evenement_confp);
		radioConfP = (RadioGroup) activite.findViewById(R.id.radioConfP);
		boutonSuivant = (Button) activite.findViewById(R.id.boutonSuivant);
	}
}

