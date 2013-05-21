package moduleListeEvenements;

import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class VueListeEvenements {
	public TextView textIdPersonne,titreListeEvtEnCours;
	public ListView listeEvtEnCoursListView;
	public Button boutonVueConnexionTTT;
	
	public VueListeEvenements(Activity activite){
		activite.setContentView(R.layout.activite_liste_evenements);
		titreListeEvtEnCours = (TextView) activite.findViewById(R.id.titreListeEvtEnCours);
		textIdPersonne = (TextView) activite.findViewById(R.id.textIdPersonne);
		listeEvtEnCoursListView = (ListView) activite.findViewById(R.id.listeEvtEnCours);
		boutonVueConnexionTTT = (Button) activite.findViewById(R.id.boutonVueConnexionTTT);
	}
}
