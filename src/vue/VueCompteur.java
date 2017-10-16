package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;

import modele.Compteur;

/**
 * L'interface graphique d'un compteur.
 */
public class VueCompteur extends JTextPane implements Observer {

	private static final long serialVersionUID = 895892544611804868L;

	/**
	 * Le compteur que l'on va donner une interface graphique.
	 */
	private Compteur compteur;

	/**
	 * Construit la face.
	 * 
	 * @param compteur celui que cette face s'attache
	 */
	public VueCompteur(Compteur compteur) {
		super();
		setForeground(SystemColor.textHighlight);
		setFont(new Font("FormalScript Regular", Font.PLAIN, 12));
		setEditable(false);
		this.compteur = compteur;
		initialiserComposants();
	}

	/**
	 * @return le compteur
	 */
	public Compteur getCompteur() {
		return compteur;
	}

	/**
	 * Visualise la face.
	 */
	private void initialiserComposants() {
		// TODO Auto-generated method stub
		setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		setText("Graine: " + compteur.getNbGraine() + "\nMenhir: "
				+ compteur.getNbMenhir());
	}

	/**
	 * Met a jour le nombre de graines et de menhirs.
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		setText("Graine: " + compteur.getNbGraine() + "\nMenhir: "
				+ compteur.getNbMenhir());
	}
}