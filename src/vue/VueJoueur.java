package vue;

import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * L'interface graphique d'un joueur (virtuel ou physique).
 * 
 * 
 */
public abstract class VueJoueur extends JPanel {
	/**
	 * L'endroit auquel on va mettre les faces des cartes.
	 */
	protected JPanel mainDuJoueur;
	/**
	 * Nom du joueur.
	 */
	protected JLabel nomDuJoueur;
	/**
	 * La face du compteur de ce joueur.
	 */
	protected VueCompteur vueCompteur;
	/**
	 * Permet l'utilisateur de choisir pour attaquer.
	 */
	protected boolean valideAChoisir;
	/**
	 * La face symbolique.
	 */
	protected JLabel visage;
	/**
	 * L'image representant le sexe.
	 */
	protected Image imageRepresentant;
	private static final long serialVersionUID = -2559034152867909773L;

	/**
	 * @param vueCompteur
	 *            le <tt>vueCompteur</tt> a affecter.
	 */
	public void setVueCompteur(VueCompteur vueCompteur) {
		this.vueCompteur = vueCompteur;
	}
}
