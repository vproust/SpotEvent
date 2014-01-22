package moduleInfosCarte;

import spotevent.fabien.un.R;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

public class VueInfosCarte {
	public TextView textIdEvt,textNomEvt;
	public Button boutonVueCarteTTT;
	
	public VueInfosCarte(Activity activite){
		activite.setContentView(R.layout.activite_infos_carte);
		textIdEvt = (TextView) activite.findViewById(R.id.textIdEvt);
		textNomEvt = (TextView) activite.findViewById(R.id.textNomEvt);
	}
}
