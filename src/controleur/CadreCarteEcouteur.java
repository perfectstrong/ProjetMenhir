package controleur;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import vue.VueCarte;

/**
 * Marque une carte si la souris y se deplace.
 * 
 */
public class CadreCarteEcouteur extends MouseAdapter {
	/**
	 * La face representant la carte.
	 */
	private VueCarte vueCarte;
	/**
	 * La borde initiale (normale) de <tt>vueCarte</tt>.
	 */
	private Border bordeInitiale;
	/**
	 * La borde ajoutee pour marquer.
	 */
	private Border bordeInsistant = BorderFactory.createLineBorder(Color.RED);
	/**
	 * La compose de <tt>bordeInsistant</tt> et <tt>bordeInitiale</tt>.
	 */
	private Border bordeCompose;

	/**
	 * Construit l'ecouteur.
	 * 
	 * @param vueCarte la face a laquelle celui-ci s'attache
	 */
	public CadreCarteEcouteur(VueCarte vueCarte) {
		super();
		this.vueCarte = vueCarte;
		bordeInitiale = vueCarte.getBorder();
		bordeCompose = BorderFactory.createCompoundBorder(bordeInitiale,
				bordeInsistant);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (vueCarte.isValideAChoisir()) {
			vueCarte.setBorder(bordeCompose);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		vueCarte.setBorder(bordeInitiale);
	}
}