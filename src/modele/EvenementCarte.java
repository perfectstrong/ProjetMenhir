package modele;

/**
 * Le transporteur des evenements emis par une carte.
 */
public class EvenementCarte {
	/**
	 * Evenement emis par une carte.
	 */
	private EvenementCarteType evenement;
	/**
	 * La carte emettant <tt>evenement</tt>
	 */
	private Carte objet;

	/**
	 * Instancie une <tt>EvenementCarte</tt>
	 * 
	 * @param evenenement
	 *            evenement emise par une carte
	 * @param objet
	 *            la carte emmenee
	 */
	public EvenementCarte(EvenementCarteType evenenement, Carte objet) {
		super();
		this.evenement = evenenement;
		this.objet = objet;
	}

	/**
	 * @return <tt>evenement</tt>
	 */
	public EvenementCarteType getEvenement() {
		return evenement;
	}

	/**
	 * 
	 * @return la carte emmenee
	 */
	public Carte getObjet() {
		return objet;
	}
}
