package modele;

import java.util.Observable;

/**
 * Compteur des points (Graines, Menhirs) d'un joueur.
 */

public class Compteur extends Observable {
	/**
	 * Nombre de Graines restants.
	 */
	private int nbGraine = 0;

	/**
	 * Nombre de Menhirs restants.
	 */
	private int nbMenhir = 0;

	/**
	 * Construit un nouveau compteur.
	 */
	public Compteur() {
		this.nbGraine = 0;
		this.nbMenhir = 0;
	}

	/**
	 * Incremente le nombre de Graines.
	 * 
	 * @param nbGraineAIncrementer
	 *            nombre de Graines ajoutees.
	 */
	public void augmenterGraine(int nbGraineAIncrementer) {
		setNbGraine(nbGraine + nbGraineAIncrementer);
	}

	/**
	 * Incremente nombre de Menhirs.
	 * 
	 * @param nbMenhirAIncrementer
	 *            nombre de Menhirs ajoutes.
	 */
	public void augmenterMenhir(int nbMenhirAIncrementer) {
		setNbMenhir(nbMenhir + nbMenhirAIncrementer);
	}

	/**
	 * Diminue le nombre de Graines.
	 * 
	 * @param nbGraineADiminuer
	 *            nombre de Graines retirees.
	 */
	public void diminuerGraine(int nbGraineADiminuer) {
		setNbGraine(Math.max(0, nbGraine - nbGraineADiminuer));
	}

	/**
	 * Diminue le nombre de Menhirs.
	 * 
	 * @param nbMenhirADiminuer
	 *            nombre de Menhirs retires.
	 */
	public void diminuerMenhir(int nbMenhirADiminuer) {
		setNbMenhir(Math.max(0, nbMenhir - nbMenhirADiminuer));
	}

	/**
	 * @return <tt>nbGraine</tt>
	 */

	public int getNbGraine() {
		return nbGraine;
	}

	/**
	 * @return <tt>nbMenhir</tt>
	 */
	public int getNbMenhir() {
		return nbMenhir;
	}

	/**
	 * @param nbGraine
	 *            le nombre de Graines a affecter <tt>nbGraine</tt>.
	 */
	public void setNbGraine(int nbGraine) {
		this.nbGraine = nbGraine;
		setChanged();
		notifyObservers();
	}

	/**
	 * @param nbMenhir
	 *            le nombre de Menhirs a affecter <tt>nbMenhir</tt>.
	 */
	public void setNbMenhir(int nbMenhir) {
		this.nbMenhir = nbMenhir;
		setChanged();
		notifyObservers();
	}
}