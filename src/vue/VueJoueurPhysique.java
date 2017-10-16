package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.CarteIngredient;
import modele.JoueurPhysique;

/**
 * L'interface graphique de l'utilisateur.
 * 
 */
public class VueJoueurPhysique extends VueJoueur {

	private static final long serialVersionUID = 6298683972931924590L;
	/**
	 * L'utilisateur.
	 */
	private JoueurPhysique joueur;

	/**
	 * Construit la vue de l'utilisateur.
	 * 
	 * @param joueur
	 *            l'utilisateur
	 */
	public VueJoueurPhysique(JoueurPhysique joueur) {
		super();
		this.joueur = joueur;
		trouverImageRepresentant();
		initialiserComposants();
	}

	/**
	 * Visualise l'interface.
	 */
	private void initialiserComposants() {
		setMinimumSize(new Dimension(150, 150));
		// TODO Auto-generated method stub
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] { 0.1, 0.3, 0.3, 0.3, 0.3, 0.3 };
		gbl.rowWeights = new double[] { 0.1, 0.2, 0.3 };
		setLayout(gbl);

		// pour le nom du joueur
		nomDuJoueur = new JLabel("<HTML>" + joueur.getNomJoueur() + "<br>Pos: "
				+ (joueur.getPosJoueur() + 1) + "</HTML>", JLabel.CENTER);
		nomDuJoueur.setFont(new Font("FormalScript Regular", Font.PLAIN, 12));
		GridBagConstraints gbc_nomDuJoueur = new GridBagConstraints();
		gbc_nomDuJoueur.gridx = 0;
		gbc_nomDuJoueur.gridy = 0;
		add(nomDuJoueur, gbc_nomDuJoueur);

		// imageRepresentant
		visage = new JLabel(new ImageIcon(imageRepresentant.getScaledInstance(
				40, 40, Image.SCALE_DEFAULT)));
		GridBagConstraints gbc_visage = new GridBagConstraints();
		gbc_visage.gridx = 0;
		gbc_visage.gridy = 2;
		add(visage, gbc_visage);

		// pour la main du joueur
		mainDuJoueur = new JPanel();
		mainDuJoueur.setLayout(new FlowLayout());
		GridBagConstraints gbc_mainDuJoueur = new GridBagConstraints();
		gbc_mainDuJoueur.gridx = 1;
		gbc_mainDuJoueur.gridy = 0;
		gbc_mainDuJoueur.gridheight = 3;
		gbc_mainDuJoueur.gridwidth = 5;
		add(mainDuJoueur, gbc_mainDuJoueur);
	}

	/**
	 * Defend l'utilisateur de choisir une carte.
	 */
	public void rendreCartesIngredientInvalidesAChoisir() {
		for (int i = 0; i < mainDuJoueur.getComponentCount(); i++) {
			VueCarte vueCarte = (VueCarte) mainDuJoueur.getComponent(i);
			if (vueCarte.getCarte() instanceof CarteIngredient) {
				vueCarte.setValideAChoisir(false);
			}
		}
	}

	/**
	 * Permet l'utilisateur de choisir une carte.
	 */
	public void rendreCartesIngredientValidesAChoisir() {
		for (int i = 0; i < mainDuJoueur.getComponentCount(); i++) {
			VueCarte vueCarte = (VueCarte) mainDuJoueur.getComponent(i);
			if (vueCarte.getCarte() instanceof CarteIngredient) {
				vueCarte.setValideAChoisir(true);
			}
		}
	}

	/**
	 * Trouve <tt>imageRepresentant</tt>
	 */
	private void trouverImageRepresentant() {
		// trouver imageRepresentant
		String nomStr = null;
		BufferedImage image = null;
		if (joueur.getSexe() == 'M') {
			nomStr = "MASCULIN";
		} else {
			nomStr = "FEMININ";
		}
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(nomStr + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageRepresentant = image;
	}
}