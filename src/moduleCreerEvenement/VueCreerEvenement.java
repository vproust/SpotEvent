package moduleCreerEvenement;

import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueCreerEvenement {
	public Button boutonSuivant,buttonTestRemplir;
	public EditText nomEvenement,dateDebut,dateFin,heureDebut,heureFin;
	
	public VueCreerEvenement(Activity activite){
		activite.setContentView(R.layout.activite_creer_evenement);
		nomEvenement = (EditText) activite.findViewById(R.id.nomEvenementEditText);
		dateDebut = (EditText) activite.findViewById(R.id.dateDebutEditText);
		heureDebut = (EditText) activite.findViewById(R.id.heureDebutEditText);
		dateFin = (EditText) activite.findViewById(R.id.dateFinEditText);
		heureFin = (EditText) activite.findViewById(R.id.heureFinEditText);
		boutonSuivant = (Button) activite.findViewById(R.id.boutonSuivant);
		buttonTestRemplir = (Button) activite.findViewById(R.id.buttonTestRemplir);
	}
}
