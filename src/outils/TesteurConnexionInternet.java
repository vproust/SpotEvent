package outils;

import android.content.Context;
import android.net.ConnectivityManager;

public class TesteurConnexionInternet {
	public static boolean estConnecte(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()){
			return true;
		}else{
			return false;
		}
	}
}
