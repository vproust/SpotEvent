package moduleInscription;


import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueInscription{
	public Button boutonCreerPersonne;
	public EditText prenom, nom, email,mdp, confirmation;
	
	public VueInscription(Activity activite){
		activite.setContentView(R.layout.activite_inscription);
		prenom = (EditText) activite.findViewById(R.id.editPrenom);
		nom = (EditText) activite.findViewById(R.id.editNom);
		email = (EditText) activite.findViewById(R.id.editemail);
		mdp = (EditText) activite.findViewById(R.id.editMDP);
		confirmation = (EditText) activite.findViewById(R.id.editConfirmation);
		boutonCreerPersonne = (Button) activite.findViewById(R.id.ButtonEnvoyer);
	}
}
