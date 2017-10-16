package controleur;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * Le role de cette classe est de changer le style d'en-tete lorsque la souris
 * se deplace en-dessus.
 * 
 */
public class CouleurChangeur extends MouseAdapter {

	/**
	 * L'objet observe par ce CouleurChangeur.
	 */
	private JLabel entete;
	/**
	 * Couleur normal.
	 */
	private Color couleurInitial;
	/**
	 * Couleur apres que la souris se deplace en-dessus.
	 */
	private Color couleurAChanger;
	/**
	 * Police normale.
	 */
	private Font fontInitial;
	/**
	 * Police apres que la souris se deplace en-dessus.
	 */
	private Font fontAChanger;

	/**
	 * Cree un ecouteur de ce type.
	 * 
	 * @param entete l'objet a changer
	 */
	public CouleurChangeur(JLabel entete) {
		super();
		this.entete = entete;
		couleurInitial = entete.getForeground();
		couleurAChanger = Color.RED;
		fontInitial = entete.getFont();
		fontAChanger = new Font(entete.getFont().getName(), Font.ITALIC, entete
				.getFont().getSize());
	}

	/**
	 * Change le style de l'objet.
	 */
	public void mouseEntered(MouseEvent e) {
		entete.setFont(fontAChanger);
		entete.setForeground(couleurAChanger);
	}

	/**
	 * Rend le style de l'objet a l'etat initial.
	 */
	public void mouseExited(MouseEvent e) {
		entete.setFont(fontInitial);
		entete.setForeground(couleurInitial);
	}
}