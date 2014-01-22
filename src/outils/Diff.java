package outils;

public class Diff {
	public long sec;
	public long min;
	public long heure;
	public long jour;
	public long mois;
	
	public Diff(){
		sec = 0;
		min = 0;
		heure = 0;
		jour = 0;
	}
	
	public static String friendlyTimeDiff(long plusPetiteDate, long plusGrandeDate){
		Diff diff = differenceTemps(plusPetiteDate,plusGrandeDate);
		String friendlyTimeDiff = "";

		//si il y a plus d'un mois
		if(diff.mois>0){
			friendlyTimeDiff=friendlyTimeDiff+diff.mois+" mois";
		}
		//si il y a plus d'un jour
		if(diff.jour>0){
			//au moins 2 jours
			if(diff.jour>1){
				friendlyTimeDiff=friendlyTimeDiff+diff.jour+" jours";
			}
			else{
				friendlyTimeDiff=friendlyTimeDiff+diff.jour+" jour";
			}
		}
		//sinon si il y a plus d'une heure
		else if(diff.heure>0){
			//au moins 2 heures
			if(diff.heure>1){
				friendlyTimeDiff=friendlyTimeDiff+diff.heure+" heures";
			}
			else{
				friendlyTimeDiff=friendlyTimeDiff+diff.heure+" heure";
			}
		}
		//sinon si il y a plus d'une minute
		else if(diff.min>0){
			friendlyTimeDiff=friendlyTimeDiff+diff.min+" min";
		}
		//si il y a moins d'une minute
		else{
			friendlyTimeDiff=friendlyTimeDiff+"quelques secondes";
		}
		return friendlyTimeDiff;
	}
	public String il_y_a(long date){
		if(date==0){
			return "jamais actualisé";
		}
		Diff diff = differenceTemps(date,System.currentTimeMillis());
		String il_y_a = "il y a ";

		//si il y a plus d'un jour
		if(diff.jour>0){
			il_y_a=il_y_a+diff.jour+" j";
		}
		//sinon si il y a plus d'une heure
		else if(diff.heure>0){
			il_y_a=il_y_a+diff.heure+" h";
		}
		//sinon si il y a plus d'une minute
		else if(diff.min>0){
			il_y_a=il_y_a+diff.min+" min";
		}
		//si il y a moins d'une minute
		else{
			il_y_a=il_y_a+"quelques secondes";
		}
		return il_y_a;
	}
	public static Diff differenceTemps(long date1, long date2){
		Diff diff = new Diff();                 // Initialisation du retour
		long tmp = date2 - date1;

		tmp = (long) Math.floor(tmp);             // Nombre de secondes entre les 2 dates
		diff.sec = tmp % 60;                    // Extraction du nombre de secondes

		tmp = (long) Math.floor((tmp-diff.sec)/60);    // Nombre de minutes (partie entière)
		diff.min = tmp % 60;                    // Extraction du nombre de minutes

		tmp = (long) Math.floor((tmp-diff.min)/60);    // Nombre d'heures (entières)
		diff.heure = tmp % 24;                   // Extraction du nombre d'heures

		tmp = (long) Math.floor((tmp-diff.heure)/24);   // Nombre de jours restants
		diff.jour = tmp;

		tmp = (long) Math.floor((tmp-diff.jour)/31);   // Nombre de mois restants
		diff.mois = tmp;

		return diff;
	}

}
