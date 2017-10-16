package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import modele.Carte;
import modele.CarteIngredient;
import modele.JoueurVirtuel;
import modele.TaupeGeante;

/**
 * L'interface graphique d'un joueur virtuel.
 * 
 */
public class VueJoueurVirtuel extends VueJoueur {

	private static final long serialVersionUID = 7532174720368934071L;
	/**
	 * Le joueur virtuel.
	 */
	private JoueurVirtuel joueur;
	/**
	 * Permet l'utilisateur de choisir a attaquer si <code>true</code>; et
	 * defend si <code>false</code>.
	 */
	private boolean valideAChoisir = false;
	/**
	 * La borde qui s'apparait lors de l'utilisateur deplace la souris
	 * en-dessus.
	 */
	private Border bordeInsistant = BorderFactory.createLineBorder(Color.RED);

	/**
	 * Cree l'interface.
	 * 
	 * @param joueur
	 *            celui que cette face s'attache
	 */
	public VueJoueurVirtuel(JoueurVirtuel joueur) {
		super();
		this.joueur = joueur;
		trouverImageRepresentant();
		initialiserComposants();
	}

	/**
	 * Visualise l'interface.
	 */
	private void initialiserComposants() {
		// TODO Auto-generated method stub
		setPreferredSize(new Dimension(150, 150));
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] { 0.1, 0.1 };
		gbl.rowWeights = new double[] { 0.1, 0.2, 0.2 };
		setLayout(gbl);

		// nomDuJoueur
		nomDuJoueur = new JLabel("<HTML>" + joueur.getNomJoueur() + "<br>Pos: "
				+ (joueur.getPosJoueur() + 1) + "</HTML>", JLabel.CENTER);
		nomDuJoueur.setFont(new Font("FormalScript Regular", Font.PLAIN, 12));
		GridBagConstraints gbc_nomDuJoueur = new GridBagConstraints();
		gbc_nomDuJoueur.gridx = 1;
		gbc_nomDuJoueur.gridy = 0;
		add(nomDuJoueur, gbc_nomDuJoueur);

		// imageRepresentant
		visage = new JLabel(new ImageIcon(imageRepresentant.getScaledInstance(
				40, 40, Image.SCALE_DEFAULT)));
		GridBagConstraints gbc_visage = new GridBagConstraints();
		gbc_visage.gridx = 1;
		gbc_visage.gridy = 1;
		add(visage, gbc_visage);

		// main du joueur
		mainDuJoueur = new JPanel();
		mainDuJoueur.setLayout(new FlowLayout());
		GridBagConstraints gbc_mainDuJoueur = new GridBagConstraints();
		gbc_mainDuJoueur.gridx = 0;
		gbc_mainDuJoueur.gridy = 2;
		gbc_mainDuJoueur.gridwidth = 2;
		gbc_mainDuJoueur.fill = GridBagConstraints.BOTH;
		add(mainDuJoueur, gbc_mainDuJoueur);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (isValideAChoisir()) {
					try {
						Carte carte = ((VueCarte) FenetrePartie.fenetrePartie
								.getChampDeposeCartes().getComponent(0))
								.getCarte();
						if (carte instanceof TaupeGeante) {
							joueur.setVictime(carte.proprietaire,
									(TaupeGeante) carte);
						} else if (carte instanceof CarteIngredient) {
							joueur.setVictime(carte.proprietaire,
									(CarteIngredient) carte);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						valideAChoisir = false;
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (isValideAChoisir()) {
					setBorder(bordeInsistant);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setBorder(null);
			}
		});
	}

	/**
	 * @return <tt>valideAChoisir</tt>
	 */
	public boolean isValideAChoisir() {
		return valideAChoisir;
	}

	/**
	 * @param valideAChoisir
	 *            la valeur boolean a affecter.
	 */
	public void setValideAChoisir(boolean valideAChoisir) {
		this.valideAChoisir = valideAChoisir;
	}

	/**
	 * Trouve <tt>imageRepresentant</tt>.
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