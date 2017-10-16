package modele;

/**
 * La carte speciale. Joueurs choissisent de soit en piocher 1, soit prendre 2
 * Graines. Valable uniquement dans une manche.
 */
public abstract class CarteAllie extends Carte {

	/**
	 * Le tableau de la force. 1 ligne; 4 colonnes correspondants aux 4 saisons
	 * respectivement.
	 */
	private int[] tabForce = new int[4];

	/**
	 * Construit une carte Allie en generale.
	 * 
	 * @param id <tt>idCarte</tt>
	 * @param nom <tt>nomCarte</tt>
	 * @param tab <tt>tabForce</tt>
	 */

	public CarteAllie(int id, String nom, int[] tab) {
		super(id, nom);
		tabForce = tab;
	}

	/**
	 * Calcule la force dans une saison donnee.
	 * 
	 * @param saison saison actuelle
	 * @return force la force dans la saison donne
	 */
	public int calForce(int saison) {
		return tabForce[saison];
	}

	/**
	 * @return <tt>tabForce</tt>
	 */
	public int[] getTabForce() {
		return tabForce;
	}
}