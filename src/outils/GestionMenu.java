package outils;

import spotevent.fabien.un.R;
import android.view.Menu;
import android.view.MenuItem;

public class GestionMenu {
	
	public static MenuItem majpos;
	public static MenuItem trace;
	public static MenuItem info;
	public static MenuItem inviter;
	public static MenuItem rafraichir;
	
	/**
	 * Cache tous les icones du menu par défaut.
	 * @param menu 
	 */
	public static void HideMenuOnCreation(Menu menu){
		MenuItem majpos = menu.findItem(R.id.menu_majpos);
		majpos.setVisible(false);
		GestionMenu.majpos = majpos;
		
        MenuItem trace = menu.findItem(R.id.menu_trace);
        trace.setVisible(false);
        GestionMenu.trace = trace;
        
        MenuItem info = menu.findItem(R.id.menu_info);
        info.setVisible(false);
        GestionMenu.info = info;
        
        MenuItem inviter = menu.findItem(R.id.menu_inviter);
        inviter.setVisible(false);
        GestionMenu.inviter = inviter;
        
        MenuItem rafraichir = menu.findItem(R.id.menu_refresh);
        rafraichir.setVisible(false);
        GestionMenu.rafraichir = rafraichir;
	}
}
