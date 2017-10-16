package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import controleur.AideControleur;
import controleur.CouleurChangeur;
import controleur.PartieAvanceeControleur;
import controleur.PartieSimpleControleur;
import controleur.RecordsControleur;

/**
 * Fenetre au debut du jeu
 * 
 * 
 */

public class FenetrePrincipale {

	/**
	 * Le frame d'affichage.
	 */
	private JFrame frame = new JFrame();
	/**
	 * Les boutons de choix.
	 */
	private JPanel panneauBoutons = new JPanel();
	/**
	 * Cette unique fenetre.
	 */
	private static FenetrePrincipale fenetrePrincipale;

	/**
	 * Donne la fenetre premiere.
	 * 
	 * @return fenetrePrincipale la fenetre premiere
	 */
	public static FenetrePrincipale getFenetrePrincipale() {
		return fenetrePrincipale;
	}

	/**
	 * Lance l'application.
	 * 
	 * @param args
	 *            rien a voir
	 */
	public static void main(String[] args) {
		FenetrePrincipale window = new FenetrePrincipale();
		window.frame.setVisible(true);
		fenetrePrincipale = window;
	}

	/**
	 * Cree l'interface de l'application.
	 * 
	 */
	public FenetrePrincipale() {
		initialize();
	}

	/**
	 * Enregistre les polices necessaires pour affichage.
	 */
	private void enregistrerFonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			Font fontFormalScript = Font.createFont(
					Font.TRUETYPE_FONT,
					this.getClass()
							.getClassLoader()
							.getResourceAsStream(
									"FormalScript Regular.ttf"))
					.deriveFont(12f);
			Font fontCharlesworthBold = Font
					.createFont(
							Font.TRUETYPE_FONT,
							this.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"Charlesworth Bold.ttf"))
					.deriveFont(12f);
			Font fontGothicE = Font.createFont(
					Font.TRUETYPE_FONT,
					this.getClass().getClassLoader()
							.getResourceAsStream("GOTHICE_.TTF"))
					.deriveFont(12f);
			ge.registerFont(fontCharlesworthBold);
			ge.registerFont(fontFormalScript);
			ge.registerFont(fontGothicE);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * Donne le frame d'interface.
	 * 
	 * @return l'interface graphique
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() {
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// mettre en plein ecran
		frame.setLocationRelativeTo(null);
		enregistrerFonts();
		Font fontInitialPourTous = new Font("GothicE", Font.BOLD, 48);
		Font fontCommun = new Font("FormalScript Regular", Font.PLAIN, 12);
		Color couleurCommun = Color.WHITE;
		UIManager.put("OptionPane.messageFont", fontCommun);
		UIManager.put("OptionPane.buttonFont", fontCommun);
		UIManager.put("InternalFrame.titleFont", fontCommun);

		GridBagLayout gbl_panneauBoutons = new GridBagLayout();
		panneauBoutons.setLayout(gbl_panneauBoutons);
		panneauBoutons.setOpaque(false);

		JLabel boutonPartieSimple = new JLabel("Partie Rapide ");
		boutonPartieSimple.setFont(fontInitialPourTous);
		boutonPartieSimple.setForeground(couleurCommun);
		GridBagConstraints gbc_boutonPartieSimple = new GridBagConstraints();
		gbc_boutonPartieSimple.anchor = GridBagConstraints.EAST;
		gbc_boutonPartieSimple.fill = GridBagConstraints.VERTICAL;
		gbc_boutonPartieSimple.gridx = 0;
		gbc_boutonPartieSimple.gridy = 0;
		panneauBoutons.add(boutonPartieSimple, gbc_boutonPartieSimple);
		boutonPartieSimple.addMouseListener(new CouleurChangeur(
				boutonPartieSimple));

		// commencer le jeu
		boutonPartieSimple.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				(new PartieSimpleControleur()).commencerPartie();
			}
		});

		JLabel boutonPartieAvancee = new JLabel("Partie Avancee ");
		boutonPartieAvancee.setFont(fontInitialPourTous);
		boutonPartieAvancee.setForeground(couleurCommun);
		GridBagConstraints gbc_boutonPartieAvancee = new GridBagConstraints();
		gbc_boutonPartieAvancee.anchor = GridBagConstraints.EAST;
		gbc_boutonPartieAvancee.fill = GridBagConstraints.VERTICAL;
		gbc_boutonPartieAvancee.gridx = 0;
		gbc_boutonPartieAvancee.gridy = 1;
		panneauBoutons.add(boutonPartieAvancee, gbc_boutonPartieAvancee);
		boutonPartieAvancee.addMouseListener(new CouleurChangeur(
				boutonPartieAvancee));

		// commencer le jeu
		boutonPartieAvancee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				(new PartieAvanceeControleur()).commencerPartie();
			}
		});

		JLabel aide = new JLabel("Aide ");
		aide.setFont(fontInitialPourTous);
		aide.setForeground(couleurCommun);
		GridBagConstraints gbc_aide = new GridBagConstraints();
		gbc_aide.anchor = GridBagConstraints.EAST;
		gbc_aide.fill = GridBagConstraints.VERTICAL;
		gbc_aide.gridx = 0;
		gbc_aide.gridy = 2;
		panneauBoutons.add(aide, gbc_aide);
		aide.addMouseListener(new CouleurChangeur(aide));
		// expliquer la loi
		aide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				(new AideControleur()).expliquer();
			}
		});

		JLabel records = new JLabel("Records ");
		records.setFont(fontInitialPourTous);
		records.setForeground(couleurCommun);
		GridBagConstraints gbc_records = new GridBagConstraints();
		gbc_records.anchor = GridBagConstraints.EAST;
		gbc_records.fill = GridBagConstraints.VERTICAL;
		gbc_records.gridx = 0;
		gbc_records.gridy = 3;
		panneauBoutons.add(records, gbc_records);
		records.addMouseListener(new CouleurChangeur(records));
		records.addMouseListener(new MouseAdapter() {
			// afficher les records
			@Override
			public void mouseClicked(MouseEvent arg0) {
				(new RecordsControleur()).afficherRecords();
			}
		});

		JLabel sortie = new JLabel("Sortie ");
		sortie.setFont(fontInitialPourTous);
		sortie.setForeground(couleurCommun);
		GridBagConstraints gbc_sortie = new GridBagConstraints();
		gbc_sortie.anchor = GridBagConstraints.EAST;
		gbc_sortie.fill = GridBagConstraints.VERTICAL;
		gbc_sortie.gridx = 0;
		gbc_sortie.gridy = 4;
		panneauBoutons.add(sortie, gbc_sortie);
		sortie.addMouseListener(new CouleurChangeur(sortie));
		// sortir du jeu
		sortie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sortir();
			}
		});

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panneauBoutons, BorderLayout.EAST);

		final JLabel lblNom = new JLabel("<HTML>JEU<br>DE<br>MENHIR</HTML>");
		lblNom.setFont(new Font("Charlesworth Bold", Font.TRUETYPE_FONT, 154));
		lblNom.setForeground(Color.GREEN);
		frame.getContentPane().add(lblNom, BorderLayout.WEST);
		frame.getContentPane().setBackground(Color.PINK);
		frame.getContentPane().addContainerListener(new ContainerListener() {

			public void componentAdded(ContainerEvent arg0) {
				// TODO Auto-generated method stub
				panneauBoutons.setVisible(false);
				lblNom.setVisible(false);
			}

			public void componentRemoved(ContainerEvent arg0) {
				// TODO Auto-generated method stub
				panneauBoutons.setVisible(true);
				lblNom.setVisible(true);
			}
		});
	}

	/**
	 * Sort du jeu.
	 */

	private void sortir() {
		frame.dispose();
	}
}