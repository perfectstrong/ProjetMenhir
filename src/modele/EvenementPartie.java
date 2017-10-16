package modele;

/**
 * Le transporteur des evenements emis par une partie.
 *
 */
public class EvenementPartie {
	/**
	 * Evenement emis.
	 */
	private EvenementPartieType evenement;
	/**
	 * Partie emettrice.
	 */
	private Partie partie;
	/**
	 * Objets supplementaires dans un evenement.
	 */
	private Object[] objetsSupplementaires;

	/**
	 * @return <tt>evenement</tt>
	 */
	public EvenementPartieType getEvenement() {
		return evenement;
	}

	/**
	 * @return <tt>objet</tt>
	 */
	public Partie getObjet() {
		return partie;
	}

	/**
	 * @param evenement evenement emis
	 * @param partie emettrice
	 */
	public EvenementPartie(EvenementPartieType evenement, Partie partie) {
		super();
		this.evenement = evenement;
		this.partie = partie;
	}

	/**
	 * @return <tt>objetsSupplementaires</tt>
	 */
	public Object[] getObjetsSupplementaires() {
		return objetsSupplementaires;
	}

	/**
	 * @param evenement evenement emis
	 * @param partie emettrice
	 * @param objetsSupplementaires d'autres objets 
	 */
	public EvenementPartie(EvenementPartieType evenement, Partie partie,
			Object[] objetsSupplementaires) {
		super();
		this.evenement = evenement;
		this.partie = partie;
		this.objetsSupplementaires = objetsSupplementaires;
	}
}