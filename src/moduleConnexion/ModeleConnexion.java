package moduleConnexion;

import accesBDD.ConnexionBDD;

public class ModeleConnexion {
	public static String essayerConnexion(String email, String mdp){
		return ConnexionBDD.essayerConnexion(email, mdp);
	}
}