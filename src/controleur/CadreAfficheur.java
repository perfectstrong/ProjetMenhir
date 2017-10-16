package controleur;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Les cades specialises pour <tt>diver</tt> - la piece a droite de la fenetre
 * Partie.
 * 
 */
public class CadreAfficheur extends JPanel {

	private static final long serialVersionUID = -1753093521568882747L;
	/**
	 * Affiche les messages recus dans ce cadre.
	 */
	private JLabel afficheur;

	public CadreAfficheur(String textFixe) {
		super();
		afficheur = new EnTeteSpecial("");
		setBorder(BorderFactory.createTitledBorder(getBorder(), textFixe,
				TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"FormalScript Regular", Font.PLAIN, 12)));
		add(afficheur);
	}

	/**
	 * Met a jour le texte dedans. 
	 * @param text texte a mettre dans ce cadre
	 */
	public void setText(String text) {
		afficheur.setText(text);
	}
}