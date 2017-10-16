package modele;

/**
 * Protege des Graines de son proprietaire. Active lorsqu'un autre joueur vole
 * des Graines et son proprietaire accepte de l'utiliser. Valable dans une seule
 * manche.
 */
public class ChienDeGarde extends CarteAllie {

	/**
	 * Construit une carte Chien De Garde.
	 * 
	 * @param id
	 *            <tt>idCarte</tt>
	 * @param nom
	 *            <tt>nomCarte</tt>
	 * @param tab
	 *            <tt>tabForce</tt>
	 */
	public ChienDeGarde(int id, String nom, int[] tab) {
		super(id, nom, tab);
	}

	/**
	 * Protege des Graines.
	 * 
	 * @param forceAttaquer
	 *            la force de voleur
	 * @param saison
	 *            la saison actuelle
	 * @return le nombre de Graines possibles a voler
	 */
	public int protegerGraine(int forceAttaquer, int saison) {
		int forceDefensive = calForce(saison);
		int nbGrainesRestants = getProprietaire().getCompteur().getNbGraine();
		if (forceAttaquer > forceDefensive) {
			if (nbGrainesRestants > (forceAttaquer - forceDefensive)) {
				return forceAttaquer - forceDefensive;
			} else {
				return nbGrainesRestants;
			}
		} else {
			return 0;
		}
	}
}