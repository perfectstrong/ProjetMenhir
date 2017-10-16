package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Carte;
import modele.CarteAllie;
import modele.CarteIngredient;
import modele.EvenementCarte;
import modele.EvenementCarteType;
import modele.JoueurVirtuel;
import controleur.CadreCarteEcouteur;
import controleur.CadreForce;
import controleur.EnTeteSpecial;

/**
 * L'interface graphique d'une carte.
 * 
 */
public class VueCarte extends JPanel implements Observer {
	private static final long serialVersionUID = -1128362701344192488L;

	/**
	 * La carte a afficher.
	 */
	private Carte carte;
	/**
	 * Permet de voir la carte ou non.
	 */
	private boolean couvert;
	/**
	 * Nom de la carte.
	 */
	private JLabel nom;
	/**
	 * Image au fond.
	 */
	private Image imageRepresentant;
	/**
	 * Image en verso.
	 */
	private Image imageVerso;
	/**
	 * Tableau de la force de la carte.
	 */
	private JPanel tabForce;
	/**
	 * Permet de choisir la carte si c'est au tour de l'utilisateur.
	 */
	private boolean valideAChoisir = false;

	/**
	 * Construit la "face" d'une carte.
	 * 
	 * @param carte
	 *            la carte que l'on veut afficher.
	 */
	public VueCarte(Carte carte) {
		super();
		this.carte = carte;
		couvert = carte.getProprietaire() instanceof JoueurVirtuel;
		// trouver image representant
		BufferedImage image = null;
		String nomStr = carte.getNomCarte();
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(nomStr + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageRepresentant = image;
		instancierVueCarte();
		if (!this.couvert) {
			this.addMouseListener(new CadreCarteEcouteur(this)); // des ecouters
																	// nous
																	// permettant
																	// de les
																	// choisir
		}
		image = null;
		String nomVerso = null;
		if (carte instanceof CarteIngredient) {
			nomVerso = "GEANT";
		} else {
			nomVerso = "FARFADET";
		}
		try {
			image = ImageIO.read(this.getClass().getClassLoader()
					.getResourceAsStream(nomVerso + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageVerso = image;
	}

	/**
	 * Affiche le recto.
	 */
	public void affichageRelle() {
		setPreferredSize(new Dimension(170, 170));
		couvert = false;
		repaint();
		String nomStr = carte.getNomCarte();
		nom = new EnTeteSpecial(nomStr);
		add(nom, BorderLayout.NORTH);
		// ajouter tableau de force
		if (this.carte instanceof CarteIngredient) {
			instancierVueTabForceIngredient();
		} else {
			instancierVueTabForceAllie();
		}
		add(tabForce, BorderLayout.SOUTH);
	}

	/**
	 * Donne la carte.
	 * 
	 * @return la carte
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Initilise la face initiale.
	 */
	private void instancierVueCarte() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		if (couvert) {// pour les cartes non jouees de joueur virtuel
			setPreferredSize(new Dimension(24, 24));
			repaint();
		} else {// pour les carte de joueur physique
			affichageRelle();
		}
	}

	/**
	 * Visualise <tt>tabForce</tt> d'une carte Allie
	 */
	private void instancierVueTabForceAllie() {
		// TODO Auto-generated method stub
		JPanel panneau = new JPanel();
		panneau.setOpaque(false);
		panneau.setLayout(new GridLayout(2, 4));
		panneau.add(new EnTeteSpecial("P"));
		panneau.add(new EnTeteSpecial("E"));
		panneau.add(new EnTeteSpecial("A"));
		panneau.add(new EnTeteSpecial("H"));
		int[] tableauForce = ((CarteAllie) carte).getTabForce();
		for (int i = 0; i < tableauForce.length; i++) {
			panneau.add(new EnTeteSpecial(Integer.toString(tableauForce[i])));
		}
		tabForce = panneau;
	}

	/**
	 * Visualise <tt>tabForce</tt> d'une carte Ingredient
	 */
	private void instancierVueTabForceIngredient() {
		JPanel panneau = new JPanel();
		panneau.setOpaque(false);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] { 1, 0.5, 0.5, 0.5, 0.5 };
		gbl.rowWeights = new double[] { 1, 1, 1, 1 };
		panneau.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panneau.add(new JLabel(), gbc);
		String[] saison = new String[] { "P", "E", "A", "H" };
		String[] act = new String[] { "Geant", "Engrais", "Farfadet" };
		for (int i = 0; i < 4; i++) {
			gbc.gridy = 0;
			gbc.gridx = i + 1;
			panneau.add(new EnTeteSpecial(saison[i]), gbc);
		}
		for (int i = 0; i < 3; i++) {
			gbc.gridx = 0;
			gbc.gridy = i + 1;
			panneau.add(new EnTeteSpecial(act[i]), gbc);
		}
		int[][] tableauForce = ((CarteIngredient) carte).getTabForce();
		for (int i = 0; i < tableauForce.length; i++) {
			for (int j = 0; j < tableauForce[i].length; j++) {
				gbc.gridx = j + 1;
				gbc.gridy = i + 1;
				CadreForce cadreForce = new CadreForce(i, j,
						tableauForce[i][j], this);
				panneau.add(cadreForce, gbc);
			}
		}
		tabForce = panneau;
	}

	/**
	 * @return <tt>valideAChoisir</tt>
	 */
	public boolean isValideAChoisir() {
		return valideAChoisir;
	}

	@Override
	public void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		if (arg0 != null) {
			if (!couvert) {
				arg0.drawImage(imageRepresentant.getScaledInstance(getWidth(),
						getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
			} else {
				arg0.drawImage(imageVerso.getScaledInstance(getWidth(),
						getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
			}
		}
	}

	/**
	 * @param valideAChoisir
	 *            valeur boolean a affecter
	 */
	public void setValideAChoisir(boolean valideAChoisir) {
		this.valideAChoisir = valideAChoisir;
	}

	/**
	 * Regle les messages recus.
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 != null) {
			if (arg1 instanceof EvenementCarte) {
				EvenementCarte ev = (EvenementCarte) arg1;
				if (ev.getEvenement() == EvenementCarteType.EST_CHOISIE) {
					valideAChoisir = false;
					getParent().remove(this);
				}
			}
		}
	}
}