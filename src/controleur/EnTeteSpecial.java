package controleur;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * Les en-tetes specialises pour affichage remarquant.
 *
 */
public class EnTeteSpecial extends JLabel {

	private static final long serialVersionUID = -3716269404573371266L;
	/**
	 * Cree un en-tete avec le style defini par developper.
	 * @param text le texte d'affichage
	 * @param font police d'affichage
	 * @param couleur couleur normal de texte
	 */
	public EnTeteSpecial(String text, Font font, Color couleur) {
		// TODO Auto-generated constructor stub
		super(text, JLabel.CENTER);
		setFont(font);
		setForeground(couleur);
	}
	/**
	 * Cree un en-tete avec le style par defaut.
	 * @param text le texte d'affichage
	 */
	public EnTeteSpecial(String text){
		super(text, JLabel.CENTER);
		setFont(new Font("FormalScript Regular", Font.PLAIN, 12));
		setForeground(Color.BLACK);
	}
}
