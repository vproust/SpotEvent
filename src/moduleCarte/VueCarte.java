package moduleCarte;

import com.mapquest.android.maps.MapView;

import android.app.Activity;
import android.widget.Button;
import spotevent.fabien.un.R;

public class VueCarte {
	
	public MapView map;
	public Button boutonInfosCarteTTT,boutonMiseAJourPosition,boutonToogleTrace;
	
	public VueCarte(Activity activite){
		activite.setContentView(R.layout.activite_carte);
		map = (MapView) activite.findViewById(R.id.map);
		boutonInfosCarteTTT = (Button) activite.findViewById(R.id.boutonInfosCarteTTT);
		boutonMiseAJourPosition = (Button) activite.findViewById(R.id.boutonMiseAJourPosition);
		boutonToogleTrace = (Button) activite.findViewById(R.id.boutonToogleTrace);
	}
}
