package modele;

import java.util.Observable;

/**
 * Carte generale, inclus Carte Ingredient et Carte Allie.
 */
public abstract class Carte extends Observable {

	/**
	 * Marque cette carte.
	 */
	protected int idCarte;

	/**
	 * Nom de cette carte.
	 */
	protected String nomCarte;

	/**
	 * <code>true</code> si cette carte est disponible a jouer.<br>
	 * <code>false</code> si elle a ete jouee.
	 */
	private boolean estValable = true;

	/**
	 * Indique le proprietaire de cette carte.
	 */
	public JoueurPhysique proprietaire = null;

	/**
	 * Construit une carte en general.
	 * 
	 * @param id
	 *            le numero d'identification de cette carte
	 * @param nom
	 *            le nom de cette carte
	 */
	public Carte(int id, String nom) {
		idCarte = id;
		nomCarte = nom;
	}

	/**
	 * @return <tt>estValable</tt>
	 */

	public boolean getEstValable() {
		return estValable;
	}

	/**
	 * @return <tt>idCarte</tt>
	 */
	public int getIdCarte() {
		return idCarte;
	}

	/**
	 * @return <tt>nomCarte</tt>
	 */
	public String getNomCarte() {
		return nomCarte;
	}

	/**
	 * @return <tt>proprietaire</tt>
	 */

	public JoueurPhysique getProprietaire() {
		return proprietaire;
	}

	/**
	 * Depose cette carte a la champ.
	 */
	public void setChoisie() {
		// TODO Auto-generated method stub
		setChanged();
		notifyObservers(new EvenementCarte(EvenementCarteType.EST_CHOISIE, this));
	}

	/**
	 * @param estValable
	 *            la valeur boolean a affecter.
	 */
	public void setEstValable(boolean estValable) {
		this.estValable = estValable;
	}

	/**
	 * Jete cette carte a cote.
	 */
	public void setFinie() {
		// TODO Auto-generated method stub
		setEstValable(false);
		setChanged();
		notifyObservers(new EvenementCarte(EvenementCarteType.FINIE, this));
	}

	/**
	 * Setter of the property <tt>proprietaire</tt>
	 * 
	 * @param proprietaire
	 *            proprietaire a affecter.
	 * 
	 */
	public void setProprietaire(JoueurPhysique proprietaire) {
		this.proprietaire = proprietaire;
	}
}