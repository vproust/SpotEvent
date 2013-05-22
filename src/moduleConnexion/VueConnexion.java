package moduleConnexion;

import spotevent.fabien.un.R;
import spotevent.fabien.un.R.id;
import spotevent.fabien.un.R.layout;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueConnexion {
	public Button boutonConnexion,boutonConnexionAnonyme,boutonRemplirTTT;
	public EditText email,mdp;
	
	public VueConnexion(Activity activite){
		activite.setContentView(R.layout.activite_connexion);
		email = (EditText) activite.findViewById(R.id.editTextEmail);
		mdp = (EditText) activite.findViewById(R.id.editTextMDP);
		boutonConnexion = (Button) activite.findViewById(R.id.boutonConnecter);
		boutonConnexionAnonyme = (Button) activite.findViewById(R.id.boutonConnexionAnonyme);
		boutonRemplirTTT = (Button) activite.findViewById(R.id.boutonRemplirTTT);
	}
}