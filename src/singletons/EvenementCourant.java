package singletons;

import grandModele.Evenement;

public class EvenementCourant {
	private static Evenement _evenement;
	
	private static Evenement getInstance() {
        if(_evenement==null) {
        	_evenement = new Evenement();
        }
        return _evenement;
    }
	public EvenementCourant(Evenement evt){
		//Evenement evtCourant = getInstance();
		//evtCourant = evt;
		//_evenement = evtCourant;
		_evenement = evt;
	}
	public static Evenement getEvenementCourant(){
		return _evenement;
	}
}
