package moduleCarte;

import com.mapquest.android.maps.MapView;

import android.app.Activity;
import android.widget.Button;
import spotevent.fabien.un.R;

public class VueCarte {
	
	public MapView map;
	
	public VueCarte(Activity activite){
		activite.setContentView(R.layout.activite_carte);
		map = (MapView) activite.findViewById(R.id.map);

	}
}
