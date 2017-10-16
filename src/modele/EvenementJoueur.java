package modele;

/**
 * Le transporteur des evenements emis par un joueur.
 * 
 */
public class EvenementJoueur {

	/**
	 * Evenement emis par un joueur.
	 */
	private EvenementJoueurType evenement;
	/**
	 * Le joueur emettant <tt>evenement</tt>
	 */
	private JoueurPhysique objet;
	/**
	 * Les objets supplementaires.
	 */
	private Object[] objetsSupplementaires;

	/**
	 * Construit un transporteur normal.
	 * 
	 * @param evenement
	 *            evenement emis
	 * @param objet
	 *            emetteur
	 */
	public EvenementJoueur(EvenementJoueurType evenement, JoueurPhysique objet) {
		super();
		this.evenement = evenement;
		this.objet = objet;
	}

	/**
	 * Construit un transporteur avec des objets supplementaires.
	 * 
	 * @param evenement
	 *            evenement emis
	 * @param objet
	 *            emetteur
	 * @param objetsSupplementaires
	 *            d'autres objets
	 */
	public EvenementJoueur(EvenementJoueurType evenement, JoueurPhysique objet,
			Object[] objetsSupplementaires) {
		super();
		this.evenement = evenement;
		this.objet = objet;
		this.objetsSupplementaires = objetsSupplementaires;
	}

	/**
	 * @return ce qui s'est passe
	 */
	public EvenementJoueurType getEvenement() {
		return evenement;
	}

	/**
	 * @return <tt>objet</tt> emetteur
	 */
	public JoueurPhysique getObjet() {
		return objet;
	}

	/**
	 * @return <tt>objetSupplementaires</tt>
	 */
	public Object[] getObjetSupplementaires() {
		return objetsSupplementaires;
	}
}
