package moduleConnexion;

import spotevent.fabien.un.R;
import spotevent.fabien.un.R.id;
import spotevent.fabien.un.R.layout;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class VueConnexion {
	public Button boutonConnexion,boutonSInscrire;
	public Button boutonRemplir1TTT,boutonRemplir2TTT,boutonRemplir3TTT,boutonRemplir4TTT;
	public EditText email,mdp;
	
	public VueConnexion(Activity activite){
		activite.setContentView(R.layout.activite_connexion);
		email = (EditText) activite.findViewById(R.id.editTextEmail);
		mdp = (EditText) activite.findViewById(R.id.editTextMDP);
		boutonConnexion = (Button) activite.findViewById(R.id.boutonConnecter);
		boutonSInscrire = (Button) activite.findViewById(R.id.boutonSInscrire);
		boutonRemplir1TTT = (Button) activite.findViewById(R.id.boutonRemplir1TTT);
		boutonRemplir2TTT = (Button) activite.findViewById(R.id.boutonRemplir2TTT);
		boutonRemplir3TTT = (Button) activite.findViewById(R.id.boutonRemplir3TTT);
		boutonRemplir4TTT = (Button) activite.findViewById(R.id.boutonRemplir4TTT);
	}
}