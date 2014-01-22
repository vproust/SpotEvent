package moduleOrganiser;

import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class VueOrganiser {
	public TextView titreListeEvtEnCours,titreListeEvtAVenir,titreListeEvtPasses;
	public ListView listeEvtEnCoursListView,listeEvtAVenirListView,listeEvtPassesListView;
	public Button boutonCreerEvenement;
	
	public VueOrganiser(Activity activite){
		activite.setContentView(R.layout.activite_organiser);
		titreListeEvtEnCours = (TextView) activite.findViewById(R.id.titreListeEvtEnCours);
		titreListeEvtAVenir = (TextView) activite.findViewById(R.id.titreListeEvtAVenir);
		titreListeEvtPasses = (TextView) activite.findViewById(R.id.titreListeEvtPasses);
		listeEvtEnCoursListView = (ListView) activite.findViewById(R.id.listeEvtEnCours);
		listeEvtAVenirListView = (ListView) activite.findViewById(R.id.listeEvtAVenir);
		listeEvtPassesListView = (ListView) activite.findViewById(R.id.listeEvtPasses);
		boutonCreerEvenement = (Button) activite.findViewById(R.id.boutonCreerEvenement);
	}
}
