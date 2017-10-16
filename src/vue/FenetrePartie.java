package vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;

import modele.Carte;
import modele.CarteAllie;
import modele.CarteIngredient;
import modele.Compteur;
import modele.EvenementCarte;
import modele.EvenementJoueur;
import modele.EvenementPartie;
import modele.JoueurPhysique;
import modele.JoueurVirtuel;
import modele.Manche;
import modele.Partie;
import modele.PartieAvancee;
import modele.TaupeGeante;
import controleur.CadreAfficheur;
import controleur.JournalAfficheur;
import controleur.PartieAvanceeControleur;
import controleur.PartieControleur;
import controleur.PartieSimpleControleur;

/**
 * La fenetre dans laquelle on va jouer.
 * 
 */
public class FenetrePartie extends JInternalFrame implements Observer {

	/**
	 * La fenetre premiere.
	 */
	public static FenetrePrincipale fenetreMere;
	/**
	 * Cette unique fenetre.
	 */
	public static FenetrePartie fenetrePartie;
	private static final long serialVersionUID = 1L;
	/**
	 * La partie dans laquelle les joueurs sont en train de jouer.
	 */
	private Partie partieEnCours;
	/**
	 * La piece a gauche de cette fenetre.
	 */
	private JToolBar barOutils;
	/**
	 * La piece a droite de cette fenetre.
	 */
	private JPanel diver;
	/**
	 * L'endroit ou on va y mettre les cartes jouees.
	 */
	private JPanel pileCartesJouees;
	/**
	 * Le plateau ou les joueurs se situent.
	 */
	private JPanel plateauJeu;
	/**
	 * La champ a deposer la carte choisie.
	 */
	private JPanel champDeposeCartes;
	/**
	 * Ou les joueurs virtuels se situent.
	 */
	private JPanel siegesJoueursVirtuels;
	/**
	 * Ou l'utilisateur se situe.
	 */
	private JPanel siegeJoueurReel;
	/**
	 * Affiche ce que les joueurs font.
	 */
	private JTextPane evenementAfficheur = new JTextPane();
	/**
	 * Affiche le numero de manche actuelle.
	 */
	private CadreAfficheur mancheAfficheur = new CadreAfficheur(
			"Manche En Cours");
	/**
	 * Affiche la saison actuelle.
	 */
	private CadreAfficheur saisonAfficheur = new CadreAfficheur(
			"Saison En Cours");
	/**
	 * Affiche le joueur en tour principal.
	 */
	private CadreAfficheur joueurEnCoursAfficheur = new CadreAfficheur(
			"Joueur En Cours");
	/**
	 * Enregistre tout ce qui se passe.
	 */
	private JTextPane journal = new JTextPane();
	/**
	 * La petite fenetre qui affiche <tt>journal</tt>.
	 */
	private JournalAfficheur journalAfficheur;
	/**
	 * Le controleur de partie actuelle.
	 */
	private PartieControleur partieControleur;

	/**
	 * Creer le frame dans lequel on va jouer.
	 * 
	 * @param fenetrePrincipale
	 *            sa fenetre mere
	 */
	public FenetrePartie(FenetrePrincipale fenetrePrincipale) {
		super();
		setResizable(false);
		fenetreMere = fenetrePrincipale;
		fenetrePartie = this;
	}

	/**
	 * Affiche et enregistre le resultat de l'action Engrais.
	 * 
	 * @param evenement
	 *            contenant le joueur, la force, nombre de Graines a pousser
	 */
	private void affichageActEngrais(EvenementJoueur evenement) {
		JoueurPhysique joueurActuel = evenement.getObjet();
		int force = (Integer) evenement.getObjetSupplementaires()[0];
		int nbGrainesAPousser = (Integer) evenement.getObjetSupplementaires()[1];
		String msg = null;
		msg = joueurActuel.getNomJoueur()
				+ " a choisi de confectionner de l'Engrais." + "\n"
				+ "La force de son action est de: " + force + ".\n"
				+ joueurActuel.getNomJoueur() + " fait donc pousser "
				+ nbGrainesAPousser + " de ses graines en Menhirs." + "\n"
				+ joueurActuel.getNomJoueur() + " a alors "
				+ joueurActuel.getCompteur().getNbMenhir() + " menhirs et "
				+ joueurActuel.getCompteur().getNbGraine() + " graines.";
		sauvegarder(msg);
		if (!(joueurActuel instanceof JoueurVirtuel)) {
			msg = "Vous avez choisi de confectionner de l'Engrais." + "\n"
					+ "La force de votre action est donc de:" + force + "\n"
					+ "Vous faites alors pousser " + nbGrainesAPousser
					+ " de vos graines en Menhirs." + "\n"
					+ "Vous avez desormais "
					+ joueurActuel.getCompteur().getNbMenhir() + " menhirs et "
					+ joueurActuel.getCompteur().getNbGraine() + " graines.";
		}
		afficher(msg);
		attendre();
	}

	/**
	 * Affiche et enregistre le resultat de l'action Geant.
	 * 
	 * @param evenement
	 *            contenant le joueur, la force
	 */
	private void affichageActGeant(EvenementJoueur evenement) {
		JoueurPhysique joueurActuel = evenement.getObjet();
		int force = (Integer) evenement.getObjetSupplementaires()[0];
		String msg = null;
		msg = joueurActuel.getNomJoueur()
				+ " a choisi d'offrir de l'ingredient au Geant." + "\n"
				+ "La force de son action est de: " + force + "\n"
				+ joueurActuel.getNomJoueur() + " a donc desormais "
				+ joueurActuel.getCompteur().getNbMenhir() + " menhirs et "
				+ joueurActuel.getCompteur().getNbGraine() + " graines.";
		sauvegarder(msg);
		if (!(joueurActuel instanceof JoueurVirtuel)) {
			msg = "Vous avez choisi d'offrir de l'ingredient au Geant." + "\n"
					+ "La force de votre action est donc de : " + force + "\n"
					+ "Vous avez donc desormais "
					+ joueurActuel.getCompteur().getNbMenhir() + " menhirs et "
					+ joueurActuel.getCompteur().getNbGraine() + " graines.";
		}
		afficher(msg);
		attendre();
	}

	/**
	 * Affiche et enregistre le debut de l'action Farfadet.
	 * 
	 * @param evenement
	 *            contenant le joueur "voleur", le joueur "vole", la force
	 */
	private void affichageDebutActFarfadets(EvenementJoueur evenement) {
		JoueurPhysique attaqueur = evenement.getObjet();
		JoueurPhysique joueurAAttaquer = (JoueurPhysique) evenement
				.getObjetSupplementaires()[0];
		int force = (Integer) evenement.getObjetSupplementaires()[1];
		String msg = null;
		msg = attaqueur.getNomJoueur()
				+ " a choisi de soudoyer des Farfadets a "
				+ joueurAAttaquer.getNomJoueur() + ".\n"
				+ "La force de son action est donc de: " + force + ".";
		sauvegarder(msg);
		if (attaqueur instanceof JoueurVirtuel
				&& !(joueurAAttaquer instanceof JoueurVirtuel)) {
			msg = attaqueur.getNomJoueur()
					+ " a choisi de soudoyer des Farfadets a vous. \n"
					+ "La force de son action est donc de: " + force + ".";
		} else if (joueurAAttaquer instanceof JoueurVirtuel
				&& !(attaqueur instanceof JoueurVirtuel)) {
			msg = "Vous avez choisi de soudoyer des Farfadets a "
					+ joueurAAttaquer.getNomJoueur()
					+ ".\n La force de votre action est donc de: " + force
					+ ".";
		}
		afficher(msg);
		attendre();
	}

	/**
	 * Affiche et enregistre la fin de l'action Farfadet.
	 * 
	 * @param evenement
	 *            contenant le joueur "voleur", le joueur "vole", le nombre de
	 *            Graines Voles
	 */
	private void affichageFinActFarfadets(EvenementJoueur evenement) {
		JoueurPhysique victime = (JoueurPhysique) evenement
				.getObjetSupplementaires()[0];
		JoueurPhysique attaqueur = evenement.getObjet();
		int nbGrainesVoles = (Integer) evenement.getObjetSupplementaires()[1];
		String msg1 = null;
		String msg2 = null;
		msg1 = victime.getNomJoueur() + " va se voir enlever " + nbGrainesVoles
				+ " graines.";
		msg2 = attaqueur.getNomJoueur() + " a desormais "
				+ attaqueur.getCompteur().getNbMenhir() + " menhirs et "
				+ attaqueur.getCompteur().getNbGraine() + " graines." + "\n"
				+ victime.getNomJoueur() + " a desormais "
				+ victime.getCompteur().getNbMenhir() + " menhirs et "
				+ victime.getCompteur().getNbGraine() + " graines.";
		sauvegarder(msg1 + "\n" + msg2);
		if (!(victime instanceof JoueurVirtuel)) {
			msg1 = "Vous allez donc vous voir enlever " + nbGrainesVoles
					+ " graines.";
			msg2 = attaqueur.getNomJoueur() + " a desormais "
					+ attaqueur.getCompteur().getNbMenhir() + " menhirs et "
					+ attaqueur.getCompteur().getNbGraine() + " graines."
					+ "\n" + "Vous avez donc desormais "
					+ victime.getCompteur().getNbMenhir() + " menhirs et "
					+ victime.getCompteur().getNbGraine() + " graines.";
		} else {
			msg2 = "Vous avez donc desormais "
					+ attaqueur.getCompteur().getNbMenhir() + " menhirs et "
					+ attaqueur.getCompteur().getNbGraine() + " graines."
					+ "\n" + victime.getNomJoueur() + " a desormais "
					+ victime.getCompteur().getNbMenhir() + " menhirs et "
					+ victime.getCompteur().getNbGraine() + " graines.";
		}
		afficher(msg1 + "\n" + msg2);
		attendre();
	}

	/**
	 * Affiche les notifications sur l'afficheur.
	 * 
	 * @param arg
	 *            la notification a afficher
	 */
	public void afficher(String arg) {
		evenementAfficheur.setText(arg);
	}

	/**
	 * Affiche et enregistre l'action de proteger des graines.
	 * 
	 * @param evenement
	 *            contenant le joueur "vole", le nombre de Graines Voles, la
	 *            force de proteger
	 */
	private void afficherProtegeGraines(EvenementJoueur evenement) {

		JoueurPhysique victime = evenement.getObjet();
		int nbGrainesVoles = (Integer) evenement.getObjetSupplementaires()[0];
		int forceProteger = (Integer) evenement.getObjetSupplementaires()[1];
		String msg = null;
		msg = victime.getNomJoueur() + " a choisi de proteger " + forceProteger
				+ " de ses graines.\n" + victime.getNomJoueur()
				+ " va donc se voir faire voler " + nbGrainesVoles
				+ " de ses graines.";
		sauvegarder(msg);
		if (!(victime instanceof JoueurVirtuel)) {
			msg = "Vous a choisi de proteger " + forceProteger
					+ " de vos graines.\n Vous allez donc se voir faire voler "
					+ nbGrainesVoles + " de vos graines.";
		}
		afficher(msg);
		attendre();
	}

	/**
	 * Affiche et enregistre le resultat de l'action Taupe Geante.
	 * 
	 * @param evenement
	 *            contenant le joueur "attaqueur", le joueur "victime", la carte
	 *            Taupe Geante utilise pour attaquer
	 */
	private void afficherTaupeGeante(EvenementJoueur evenement) {

		JoueurPhysique attaqueur = (JoueurPhysique) evenement
				.getObjetSupplementaires()[0];
		JoueurPhysique victime = evenement.getObjet();
		Carte carte = (Carte) evenement.getObjetSupplementaires()[1];
		String msg1, msg2 = null;
		msg1 = attaqueur.getNomJoueur()
				+ " a decide d'utiliser sa carte Taupe Geante suivante contre "
				+ victime.getNomJoueur() + ".";
		msg2 = victime.getNomJoueur() + " a donc desormais "
				+ victime.getCompteur().getNbMenhir() + " Menhirs.";
		sauvegarder(msg1);
		sauvegarder(carte);
		if (!(attaqueur instanceof JoueurVirtuel)) {
			msg1 = "Vous avez decide d'utiliser votre carte Taupe Geante contre "
					+ victime.getNomJoueur() + ".";
			msg2 = victime.getNomJoueur() + " a donc desormais "
					+ victime.getCompteur().getNbMenhir() + " Menhirs.";
		} else if (!(victime instanceof JoueurVirtuel)) {
			msg1 = attaqueur.getNomJoueur()
					+ " a decide d'utiliser sa carte Taupe Geante suivante contre vous.";
			msg2 = "Vous avez donc desormais "
					+ victime.getCompteur().getNbMenhir() + " Menhirs.";
		}
		afficher(msg1 + "\n" + msg2);
		attendre();
	}

	/**
	 * Affiche et enregistre le debut d'un tour principal.
	 * 
	 * @param joueur
	 *            le joueur a afficher
	 */
	public void afficherTourPrincipal(JoueurPhysique joueur) {

		joueurEnCoursAfficheur.setText(joueur.getNomJoueur());
		sauvegarder("\n\nC'est le tour de " + joueur.getNomJoueur() + ".");
		if (!(joueur instanceof JoueurVirtuel)) {
			((VueJoueurPhysique) siegeJoueurReel.getComponent(0))
					.rendreCartesIngredientValidesAChoisir();// permet joueur
																// physique de
																// choisir une
																// carte
																// ingredient
			afficher("C ' est votre tour.");
		} else {
			afficher("C ' est le tour de " + joueur.getNomJoueur() + ".");
		}
		attendre();
	}

	/**
	 * Annonce et enregistre le joueur qui gagne le jeu actuel.
	 * 
	 * @param joueur
	 *            le gagnant
	 */
	private void annoncerGagant(JoueurPhysique joueur) {

		String msg = null;
		msg = "Le gagnant est donc: " + joueur.getNomJoueur() + "!!!!!!!";
		sauvegarder("\n\n" + msg);
		if (!(joueur instanceof JoueurVirtuel)) {
			msg = "Vous avez gagne!!!!!!!!!!!";
		}
		journalAfficheur.sauvegarderRecord(joueur, partieEnCours);
		afficher(msg);
	}

	/**
	 * Met le fil de procede en attente jusqu'a l'utilisateur decide de
	 * continuer.
	 */
	private synchronized void attendre() {
		JOptionPane.showMessageDialog(diver, "Tapez pour continuer");
	}

	/**
	 * Demande l'age de l'utilisateur.
	 * 
	 * @return age de l'utilisateur
	 */
	public int demanderAgeDeJoueurPhysique() {
		String ageStr = null;
		int age = 0;
		boolean mauvaisChoix = true;
		do {
			try {
				ageStr = JOptionPane.showInputDialog(this,
						"Entrez votre age s'il vous plait:", "Votre age",
						JOptionPane.QUESTION_MESSAGE);
				age = Integer.parseInt(ageStr);
				if (age < 8) {
					JOptionPane.showMessageDialog(this,
							"Vous devez avoir au moins 8 ans!", "Votre age",
							JOptionPane.WARNING_MESSAGE);
				} else if (age > 100) {
					JOptionPane.showMessageDialog(this,
							"Vous etes trop age! Veuillez bien rester!",
							"Votre age", JOptionPane.WARNING_MESSAGE);
				} else {
					mauvaisChoix = false;
				}
			} catch (NumberFormatException e) {

				JOptionPane.showMessageDialog(this,
						"Veuillez entrer un entier!", "Votre age",
						JOptionPane.QUESTION_MESSAGE);
			}
		} while (mauvaisChoix);
		return age;
	}

	/**
	 * Demande la choix au debut d'un manche de l'utilisateur.
	 * 
	 * @return <tt>1</tt> si l'utilisateur choisit de prendre une carte Allie <br>
	 *         <tt>2</tt> s'il choisit de prendre 2 graines <br>
	 *         <tt>-1</tt> s'il veut quitter
	 */
	public int demanderChoixDeCarteAllie() {

		int choix = -1;
		boolean mauvaisChoix = true;
		String[] tabChoix = { "Une carte Allie", "Deux graines" };
		while (mauvaisChoix) {
			choix = JOptionPane.showOptionDialog(this, "Vous voulez...",
					"Choix au debut de la nouvelle manche",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, tabChoix, null);
			if (choix == JOptionPane.CLOSED_OPTION) {
				if (sortir()) {
					mauvaisChoix = false;
				}
			} else {
				if (choix == JOptionPane.YES_OPTION) {
					choix = 1;// choisir d'une carte allie
				} else {
					choix = 2;// choisir de 2 graines
				}
				mauvaisChoix = false;
			}
		}
		return choix;
	}

	/**
	 * Demande le nombre de joueurs a jouer ensemble
	 * 
	 * @return le nombre voulu de joueurs.
	 */
	public int demanderNombreDeJoueur() {
		String nbreJoueurStr = null;
		int nbreJoueur = 0;
		boolean mauvaisChoix = true;
		do {
			try {
				nbreJoueurStr = (JOptionPane.showInputDialog(this,
						"A combien de joueurs voulez-vous jouer?:",
						"Nombre de joueurs", JOptionPane.QUESTION_MESSAGE));
				nbreJoueur = Integer.parseInt(nbreJoueurStr);
				if (nbreJoueur < 2 || nbreJoueur > 6) {
					JOptionPane.showMessageDialog(this,
							"Veuillez entrer un entier entre 2 et 6!",
							"Nombre de joueurs", JOptionPane.WARNING_MESSAGE);
				} else {
					mauvaisChoix = false;
				}
			} catch (NumberFormatException e) {

				JOptionPane.showMessageDialog(this,
						"Veuillez entrer un entier!", "Nombre de joueurs",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (mauvaisChoix);
		return nbreJoueur;
	}

	/**
	 * Demande le nom de l'utilisateur.
	 * 
	 * @return le nom de l'utilisateur
	 */
	public String demanderNomDeJoueurPhysique() {
		String nomJoueurPhysique = null;
		do {
			nomJoueurPhysique = JOptionPane.showInputDialog(this,
					"Saisissez votre nom s'il vous plait:", "Votre nom",
					JOptionPane.QUESTION_MESSAGE);
			if (nomJoueurPhysique == null) {
				JOptionPane.showMessageDialog(this,
						"Veuillez entrer votre nom!");
			}
		} while (nomJoueurPhysique == null);
		return nomJoueurPhysique;
	}

	/**
	 * Demande le sexe de l'utilisateur.
	 * 
	 * @return <tt>M</tt> si masculin<br>
	 *         <tt>F</tt> si feminin
	 */
	public char demanderSexeDeJoueurPhysique() {
		String sexe = null;
		boolean mauvaisChoix = true;
		String[] sexePossible = { "Masculin", "Feminin" };
		do {
			sexe = (String) JOptionPane
					.showInputDialog(this,
							"Selectez votre sexe s'il vous plait",
							"Votre sexe", JOptionPane.QUESTION_MESSAGE, null,
							sexePossible, "Maculin");
			if (sexe != null && sexe.length() > 0) {
				mauvaisChoix = false;
			} else {
				JOptionPane.showMessageDialog(this,
						"Veuillez choisir votre sexe!");
			}
		} while (mauvaisChoix);
		return sexe.charAt(0);
	}

	/**
	 * Depose la carte choisie par joueur actuel a la champ de depose.
	 * 
	 * @param evenement
	 *            contenant la carte choisie
	 */
	private void deposerCarteAChampDeposeCarte(EvenementCarte evenement) {

		Carte objet = evenement.getObjet();
		VueCarte vueCarte = new VueCarte(objet);
		vueCarte.affichageRelle();
		champDeposeCartes.add(vueCarte);
	}

	/**
	 * Eteint cette fenetre et re-affiche <tt>panneauBoutons</tt> dans la
	 * fenetre principale.
	 */
	public void doDefaultCloseAction() {
		dispose();
		FenetrePartie.fenetreMere.getFrame().getContentPane().remove(this);
	}

	/**
	 * Affiche et enregistre la choix de joueur au debut d'un manche.
	 * 
	 * @param evenement
	 *            contenant le joueur et son choix
	 */
	private void enregistrerChoix(EvenementJoueur evenement) {
		String msg = null;
		switch (evenement.getEvenement()) {
		case CHOISIR_CARTE_ALLIE:
			msg = evenement.getObjet().getNomJoueur()
					+ " a choisi de prendre une carte allie en ce debut de manche.";
			break;
		case CHOISIR_GRAINES:
			msg = evenement.getObjet().getNomJoueur()
					+ " a choisi de prendre 2 graines en ce debut de manche.";
		default:
			break;
		}
		afficher(msg);
		sauvegarder("\n" + msg);
		attendre();
	}

	/**
	 * Enregistre les donnes initiales, inclus le nombre de joueurs, information
	 * de chaque joueur (nom, age, sexe, position).
	 */
	private void enregistrerDonneesInitiales() {
		if (partieEnCours instanceof PartieAvancee) {
			sauvegarder("PARTIE AVANCEE\n=========================");
		}
		sauvegarder(Integer.toString(partieEnCours.hashCode()));
		sauvegarder("Le nombre de joueurs:" + partieEnCours.getNbJoueurs());
		sauvegarder("Nom \tAge \tSexe \tPosition");
		for (int i = 0; i < partieEnCours.getNbJoueurs(); i++) {
			JoueurPhysique joueur = partieEnCours.getListeJoueurs().get(i);
			sauvegarder(joueur.getNomJoueur() + "\t" + joueur.getAge() + "\t"
					+ joueur.getSexe() + "\t" + (joueur.getPosJoueur() + 1));
		}
		sauvegarder("==============================");
	}

	/**
	 * Vide <tt>plateauJeu</tt> et <tt>pileCartesJouees</tt>.
	 */
	public void faireMenage() {
		pileCartesJouees.removeAll();
		champDeposeCartes.removeAll();
		siegeJoueurReel.removeAll();
		siegesJoueursVirtuels.removeAll();
		for (Iterator<JoueurPhysique> iterator = partieEnCours
				.getListeJoueurs().iterator(); iterator.hasNext();) {
			JoueurPhysique joueur = (JoueurPhysique) iterator.next();
			joueur.getCompteur().deleteObservers();
			joueur.deleteObservers();
		}
		for (Iterator<CarteIngredient> iterator = partieEnCours
				.getJeuDeCartesIngredients().iterator(); iterator.hasNext();) {
			CarteIngredient carte = (CarteIngredient) iterator.next();
			carte.deleteObservers();
		}
		for (Iterator<CarteAllie> iterator = partieEnCours
				.getJeuDeCartesAllies().iterator(); iterator.hasNext();) {
			CarteAllie carte = (CarteAllie) iterator.next();
			carte.deleteObservers();
		}
	}

	/**
	 * Donne la champ de deposer des cartes a jouer.
	 * 
	 * @return champDeposeCartes
	 */
	public JPanel getChampDeposeCartes() {
		return champDeposeCartes;
	}

	/**
	 * Donne le controleur de <tt>partieEnCours</tt>.
	 * 
	 * @return celui qui controle le deroulement du jeu
	 */
	public PartieControleur getPartieControleur() {
		return partieControleur;
	}

	/**
	 * Donne la partie actuelle.
	 * 
	 * @return partieEnCours
	 */
	public Partie getPartieEnCours() {
		return partieEnCours;
	}

	/**
	 * Initialise la piece a gauche de cette fenetre.
	 * 
	 * @param fontCommun
	 *            la police d'affichage
	 */
	private void initialiserBarOutils(Font fontCommun) {

		barOutils = new JToolBar();
		barOutils.setOrientation(SwingConstants.VERTICAL);
		barOutils.setFloatable(false);
		getContentPane().add(barOutils, BorderLayout.WEST);

		JButton sortie = new JButton("Sortie");
		sortie.setFont(fontCommun);
		sortie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sortir();
			}
		});

		journal.setEditable(false);
		journalAfficheur = new JournalAfficheur(journal);

		JButton journalBouton = new JButton("Journal");
		journalBouton.setFont(fontCommun);
		journalBouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				journalAfficheur.setVisible(true);
				journalAfficheur.setPreferredSize(new Dimension(500, 500));
				journalAfficheur.setMaximumSize(getPreferredSize());
				fenetreMere.getFrame().add(journalAfficheur, 0);
			}
		});

		JButton boutonNouvellePartie = new JButton("Nouvelle Partie");
		boutonNouvellePartie.setFont(fontCommun);
		boutonNouvellePartie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Object[] options = { "Partie Rapide", "Partie Avancee",
						"Non, je veux continuer!" };
				int choix = JOptionPane.showOptionDialog(fenetrePartie,
						"Voulez-vous une nouvelle...", null,
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, null);
				if (choix == JOptionPane.YES_OPTION) {
					fenetrePartie.dispose();
					(new PartieSimpleControleur()).commencerPartie();
				} else if (choix == JOptionPane.NO_OPTION) {
					fenetrePartie.dispose();
					(new PartieAvanceeControleur()).commencerPartie();
				}
			}
		});

		JButton boutonRecommencer = new JButton("Recommencer");
		boutonRecommencer.setFont(fontCommun);
		boutonRecommencer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				partieControleur.recommencerPartie();
			}
		});

		barOutils.add(journalBouton);
		barOutils.add(boutonNouvellePartie);
		barOutils.add(boutonRecommencer);
		barOutils.add(sortie);
	}

	/**
	 * Initialise les composants d'affichage.
	 */
	public void initialiserComposant() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(false);
		Font fontCommun = new Font("FormalScript Regular", Font.PLAIN, 12);

		initialiserBarOutils(fontCommun);

		initialiserPlateauJeu(fontCommun);

		initialiserDiver(fontCommun);
	}

	/**
	 * Initialise la piece a droite de cette fenetre.
	 * 
	 * @param fontCommun
	 *            la police d'affichage
	 */
	private void initialiserDiver(Font fontCommun) {
		diver = new JPanel();
		diver.setPreferredSize(new Dimension(180, this.getHeight()));
		getContentPane().add(diver, BorderLayout.EAST);

		GridBagLayout gbl_diver = new GridBagLayout();
		gbl_diver.columnWeights = new double[] { 1.0 };
		diver.setLayout(gbl_diver);

		if (Partie.getUniquePartie() instanceof PartieAvancee) {
			GridBagConstraints gbc_mancheAfficheur = new GridBagConstraints();
			gbc_mancheAfficheur.fill = GridBagConstraints.HORIZONTAL;
			gbc_mancheAfficheur.weighty = 0.1;
			gbc_mancheAfficheur.gridx = 0;
			gbc_mancheAfficheur.gridy = 0;
			diver.add(mancheAfficheur, gbc_mancheAfficheur);
		}

		GridBagConstraints gbc_saisonAfficheur = new GridBagConstraints();
		gbc_saisonAfficheur.fill = GridBagConstraints.HORIZONTAL;
		gbc_saisonAfficheur.weighty = 0.1;
		gbc_saisonAfficheur.gridx = 0;
		gbc_saisonAfficheur.gridy = 1;
		diver.add(saisonAfficheur, gbc_saisonAfficheur);

		GridBagConstraints gbc_joueurEnCoursAfficheur = new GridBagConstraints();
		gbc_joueurEnCoursAfficheur.fill = GridBagConstraints.HORIZONTAL;
		gbc_joueurEnCoursAfficheur.weighty = 0.1;
		gbc_joueurEnCoursAfficheur.gridx = 0;
		gbc_joueurEnCoursAfficheur.gridy = 2;
		diver.add(joueurEnCoursAfficheur, gbc_joueurEnCoursAfficheur);

		evenementAfficheur.setEditable(false);
		evenementAfficheur.setFont(fontCommun);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.weighty = 0.7;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 3;
		JScrollPane evenementAfficheurScrollable = new JScrollPane(
				evenementAfficheur);
		evenementAfficheurScrollable
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		diver.add(evenementAfficheurScrollable, gbc_textPane);

		pileCartesJouees = new JPanel();
		pileCartesJouees.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		pileCartesJouees.setPreferredSize(new Dimension(170, 170));
		pileCartesJouees.setName("Pile des Cartes Jouees");
		CardLayout cl_pileCartesJouees = new CardLayout();
		pileCartesJouees.setLayout(cl_pileCartesJouees);
		pileCartesJouees.setBackground(Color.WHITE);
		GridBagConstraints gbc_pileCartesJouees = new GridBagConstraints();
		gbc_pileCartesJouees.gridx = 0;
		gbc_pileCartesJouees.gridy = 4;
		gbc_pileCartesJouees.weighty = 0.5;
		diver.add(pileCartesJouees, gbc_pileCartesJouees);
	}

	/**
	 * Initialise la partie centrale de cette fenetre.
	 * 
	 * @param fontCommun
	 *            la police d'affichage
	 */
	private void initialiserPlateauJeu(Font fontCommun) {
		plateauJeu = new JPanel();
		getContentPane().add(plateauJeu, BorderLayout.CENTER);

		GridBagLayout gbl_plateauJeu = new GridBagLayout();
		gbl_plateauJeu.rowWeights = new double[] { 0.5, 0.7, 0.5 };
		gbl_plateauJeu.columnWeights = new double[] { 0.5, 0.5, 0.5, 0.5, 0.5 };
		plateauJeu.setLayout(gbl_plateauJeu);

		siegesJoueursVirtuels = new JPanel();
		siegesJoueursVirtuels.setLayout(new FlowLayout());
		siegesJoueursVirtuels.setBorder(BorderFactory
				.createLoweredSoftBevelBorder());
		GridBagConstraints gbc_siegesJoueursVirtuels = new GridBagConstraints();
		gbc_siegesJoueursVirtuels.gridx = 0;
		gbc_siegesJoueursVirtuels.gridy = 0;
		gbc_siegesJoueursVirtuels.gridwidth = 5;
		plateauJeu.add(siegesJoueursVirtuels, gbc_siegesJoueursVirtuels);

		champDeposeCartes = new JPanel();
		champDeposeCartes.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		champDeposeCartes.setPreferredSize(new Dimension(170, 170));
		champDeposeCartes.setLayout(new BorderLayout());
		GridBagConstraints gbc_cadreDeposeCartes = new GridBagConstraints();
		gbc_cadreDeposeCartes.gridx = 2;
		gbc_cadreDeposeCartes.gridy = 1;
		plateauJeu.add(champDeposeCartes, gbc_cadreDeposeCartes);

		siegeJoueurReel = new JPanel();
		siegeJoueurReel.setLayout(new BorderLayout());
		siegeJoueurReel.setBorder(BorderFactory.createRaisedBevelBorder());
		GridBagConstraints gbc_siegeJoueurReel = new GridBagConstraints();
		gbc_siegeJoueurReel.gridx = 0;
		gbc_siegeJoueurReel.gridy = 2;
		gbc_siegeJoueurReel.gridwidth = 5;
		plateauJeu.add(siegeJoueurReel, gbc_siegeJoueurReel);
	}

	/**
	 * Met a jour <tt>saisonAfficheur</tt>, affiche et enregistre la saison en
	 * cours.
	 */
	private void metteSaisonAfficheurAJour() {

		String saisonStr = null;
		switch (partieEnCours.getSaisonEnCours()) {
		case 0:
			saisonStr = "Printemps";
			break;
		case 1:
			saisonStr = "Ete";
			break;
		case 2:
			saisonStr = "Automne";
			break;
		case 3:
			saisonStr = "Hiver";
			break;
		default:
			break;
		}
		saisonAfficheur.setText(saisonStr);
		String msg = "C'est " + saisonStr + ".";
		afficher(msg);
		sauvegarder("\n\n" + msg);
	}

	/**
	 * Met la fin du jeu et sauvegarde son journal.
	 */
	private void mettreLaFin() {

		String msg = "C'est fini.";
		afficher(msg);
		sauvegarder("\n===================================\n" + msg);
		partieEnCours.calculGagnant();
		journalAfficheur.sauvegarderDonneesEcrites();
	}

	/**
	 * Met a jour <tt>mancheAfficheur</tt>, affiche et enregistre la manche en
	 * cours.
	 */
	private void mettreMancheAfficheurAJour() {

		mancheAfficheur.setText(Integer.toString(Manche.getOrdreManche() + 1));
		afficher("C' est la manche No" + Manche.getOrdreManche());
		sauvegarder("======================\nManche No"
				+ Manche.getOrdreManche());
	}

	/**
	 * Prepare la vue de tous les joueurs, les compteurs, les carte et y ajoute
	 * les observateurs.
	 */
	private void preparer() {
		// instancier tous les vues (Compteur, Carte, Joueur) et ajouter les
		// observateurs
		partieEnCours = Partie.getUniquePartie();
		ArrayList<JoueurPhysique> listeJoueurs = partieEnCours
				.getListeJoueurs();
		for (int i = 0; i < listeJoueurs.size(); i++) {
			JoueurPhysique joueur = listeJoueurs.get(i);
			// instancier la vue de joueur
			VueJoueur vueJoueur;
			if (joueur instanceof JoueurVirtuel) {
				vueJoueur = new VueJoueurVirtuel((JoueurVirtuel) joueur);
			} else {
				vueJoueur = new VueJoueurPhysique(joueur);
			}
			joueur.addObserver(this);

			// instancier la vue de compteur et le soumettre a son observateur
			Compteur compteur = joueur.getCompteur();
			VueCompteur vueCompteur = new VueCompteur(compteur);
			compteur.addObserver(vueCompteur);
			GridBagConstraints gbc_vueCompteur = new GridBagConstraints();
			gbc_vueCompteur.gridx = 0;
			gbc_vueCompteur.gridy = 1;
			vueJoueur.add(vueCompteur, gbc_vueCompteur);
			vueJoueur.setVueCompteur(vueCompteur);

			// instancier la vue de carte dans la main et ajouter les
			// observateurs de la carte
			for (Iterator<Carte> iterator2 = joueur.getMainDuJoueur()
					.iterator(); iterator2.hasNext();) {
				Carte carte = (Carte) iterator2.next();
				VueCarte vueCarte = new VueCarte(carte);
				carte.addObserver(vueCarte);
				carte.addObserver(this);
				vueJoueur.mainDuJoueur.add(vueCarte);
			}

			// dresser le plateau de jeu
			if (joueur instanceof JoueurVirtuel) {
				siegesJoueursVirtuels.add(vueJoueur);
			} else {
				siegeJoueurReel.add(vueJoueur);
			}
		}
	}

	/**
	 * Presente les joueurs participant et les enregistre.
	 */
	public void presenterJoueurs() {
		ArrayList<JoueurPhysique> listeJoueurs = partieEnCours
				.getListeJoueurs();
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < listeJoueurs.size(); i++) {
			JoueurPhysique joueur = listeJoueurs.get(i);
			if (joueur instanceof JoueurVirtuel) {
				msg.append("\nEn position " + (joueur.getPosJoueur() + 1)
						+ " vous avez " + joueur.getNomJoueur() + " qui a "
						+ joueur.getAge() + " ans");
			} else {
				msg.append("\nVous, " + joueur.getNomJoueur()
						+ "  etes en position " + (joueur.getPosJoueur() + 1)
						+ " qui a " + joueur.getAge() + " ans");
			}
		}
		JOptionPane.showMessageDialog(this, msg.toString());
		enregistrerDonneesInitiales();
	}

	/**
	 * Demande l'utilisateur de reflechir jouer la carte Chien De Garde.
	 * 
	 * @param evenement
	 *            contenant le joueur demande
	 */
	private void reflechirChienDeGarde(EvenementJoueur evenement) {
		if (!(evenement.getObjet() instanceof JoueurVirtuel)) {
			boolean choix = false;
			int choixInt = JOptionPane.showOptionDialog(this,
					"Voulez-vous utiliser votre Chien de Garde?",
					"Chien De Garde", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (choixInt == JOptionPane.YES_OPTION) {
				choix = true;
			}
			evenement.getObjet().reagirFarfadet(
					(CarteIngredient) evenement.getObjetSupplementaires()[0],
					choix);
		}
	}

	/**
	 * Demande l'utilisateur de reflechir jouer la carte Taupe Geante.
	 * 
	 * @param evenement
	 *            contenant le joueur demande
	 */
	private void reflechirTaupeGeante(EvenementJoueur evenement) {

		JoueurPhysique joueur = evenement.getObjet();
		if (!(joueur instanceof JoueurVirtuel)) {
			int choixInt = JOptionPane.showOptionDialog(this,
					"Voulez-vous utiliser votre Taupe Geante?", "Taupe Geante",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null);
			if (choixInt == JOptionPane.YES_OPTION) {
				sauvegarder(joueur.getMainDuJoueur().get(4));
				joueur.reflechirJoueurAAttaquer((TaupeGeante) joueur
						.getMainDuJoueur().get(4));
			} else {
				((PartieAvancee) partieEnCours).passerTourSecondaire();
			}
		}
	}

	/**
	 * Rend la vue des joueur virtuels invalables a choisir pour leur attaquer.
	 */
	private void rendreJoueursVirtuelsInvalidesAChoisir() {

		for (int i = 0; i < siegesJoueursVirtuels.getComponentCount(); i++) {
			((VueJoueurVirtuel) siegesJoueursVirtuels.getComponent(i))
					.setValideAChoisir(false);
		}
	}

	/**
	 * Rend la vue des joueur virtuels valables a choisir pour leur attaquer.
	 */
	public void rendreJoueursVirtuelsValidesAChoisir() {
		for (int i = 0; i < siegesJoueursVirtuels.getComponentCount(); i++) {
			((VueJoueurVirtuel) siegesJoueursVirtuels.getComponent(i))
					.setValideAChoisir(true);
		}
	}

	/**
	 * Sauvegarde une carte.
	 * 
	 * @param carte
	 *            la carte a sauvegarder
	 */
	private void sauvegarder(Carte carte) {
		if (carte instanceof CarteIngredient) {
			sauvegarder((CarteIngredient) carte);
		} else {
			sauvegarder((CarteAllie) carte);
		}
	}

	/**
	 * Sauvegarde une carte Allie.
	 * 
	 * @param carte
	 *            la carte a sauvegarder
	 */
	private void sauvegarder(CarteAllie carte) {
		String msg = carte.getProprietaire().getNomJoueur()
				+ " a decide de jouer cette carte:";
		sauvegarder("\n" + msg);
		int[] tabForce = carte.getTabForce();
		sauvegarder("Nom de carte:" + carte.getNomCarte() + "  Id de carte:"
				+ carte.getIdCarte());
		sauvegarder("Printemps\tEte\tAutomne\tHiver");
		sauvegarder(tabForce[0] + "\t" + tabForce[1] + "\t" + tabForce[2]
				+ "\t" + tabForce[3]);
	}

	/**
	 * Sauvegarde une carte Ingredient.
	 * 
	 * @param carte
	 *            la carte a sauvegarder
	 */
	private void sauvegarder(CarteIngredient carte) {
		String msg = carte.getProprietaire().getNomJoueur()
				+ " a decide de jouer cette carte:";
		int[][] tabForce = carte.getTabForce();
		sauvegarder("\n" + msg);
		sauvegarder("Nom de carte:" + carte.getNomCarte() + "  Id de carte:"
				+ carte.getIdCarte());
		sauvegarder("\tPrintemps\t\tEte\t\tAutomne\t\tHiver");
		sauvegarder("Geant\t" + tabForce[0][0] + "\t\t" + tabForce[0][1]
				+ "\t\t" + tabForce[0][2] + "\t\t" + tabForce[0][3]);
		sauvegarder("Engrais\t" + tabForce[1][0] + "\t\t" + tabForce[1][1]
				+ "\t\t" + tabForce[1][2] + "\t\t" + tabForce[1][3]);
		sauvegarder("Farfadets\t" + tabForce[2][0] + "\t\t" + tabForce[2][1]
				+ "\t\t" + tabForce[2][2] + "\t\t" + tabForce[2][3]);
	}

	/**
	 * Sauvegarde une chaine de caractere au journal.
	 * 
	 * @param arg
	 *            une chaine de caractere
	 */
	private void sauvegarder(String arg) {
		try {
			journal.getStyledDocument().insertString(
					journal.getStyledDocument().getLength(), "\n" + arg, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met a place le controleur.
	 * 
	 * @param partieControleur
	 *            celui qui controle le deroulement du jeu
	 */
	public void setPartieControleur(PartieControleur partieControleur) {
		this.partieControleur = partieControleur;
	}

	/**
	 * Affecte l'attribut <tt>partieEnCours</tt>.
	 * 
	 * @param partieEnCours partie actuelle
	 */
	public void setPartieEnCours(Partie partieEnCours) {
		this.partieEnCours = partieEnCours;
	}

	/**
	 * Sort de cette fenetre.
	 * 
	 * @return <code>true</code> si l'utilisateur veut quitter et <code>false</code>
	 *         sinon
	 */
	private synchronized boolean sortir() {
		int option = JOptionPane.showConfirmDialog(null,
				"Voulez-vous quitter?", "Sortie", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			doDefaultCloseAction();
			return true;
		}
		return false;
	}

	/**
	 * Regler les messages recus.
	 */
	public void update(Observable arg0, Object arg1) {
		// regler les message concernant partie
		if (arg1 != null) {
			if (arg1 instanceof EvenementPartie) {
				EvenementPartie evenement = (EvenementPartie) arg1;
				switch (evenement.getEvenement()) {
				case PREPAREE:
					preparer();
					break;
				case DEMANDER_CHOIX_DE_CARTE_ALLIE:
					((PartieAvancee) partieEnCours)
							.partagerCartesEtGraines(demanderChoixDeCarteAllie());
					break;
				case NOUVELLE_SAISON:
					metteSaisonAfficheurAJour();
					break;
				case FINIE:
					mettreLaFin();
					break;
				case NOUVELLE_MANCHE:
					mettreMancheAfficheurAJour();
					preparer();
					break;
				case MANCHE_FINIE:
					sauvegarder("Manche No" + (Manche.getOrdreManche() + 1)
							+ " est finie.");
					faireMenage();
					break;
				default:
					break;
				}
			} else if (arg1 instanceof EvenementCarte) {
				EvenementCarte evenement = (EvenementCarte) arg1;
				switch (evenement.getEvenement()) {
				case EST_CHOISIE:
					sauvegarder(evenement.getObjet());
					if (!(evenement.getObjet().getProprietaire() instanceof JoueurVirtuel)) {
						((VueJoueurPhysique) siegeJoueurReel.getComponent(0))
								.rendreCartesIngredientInvalidesAChoisir();
					}
					deposerCarteAChampDeposeCarte(evenement);
					break;
				case FINIE:
					viderCadreDeposeCarte();
					break;
				default:
					break;
				}
			} else if (arg1 instanceof EvenementJoueur) {
				EvenementJoueur evenement = (EvenementJoueur) arg1;
				switch (evenement.getEvenement()) {
				case EN_TOUR_PRINCIPAL:
					afficherTourPrincipal(evenement.getObjet());
					break;
				case FAIRE_GEANT:
					affichageActGeant(evenement);
					break;
				case FAIRE_ENGRAIS:
					affichageActEngrais(evenement);
					break;
				case FAIRE_FARFADET_CHOISIR_VICTIME:
					afficher("Veuillez-vous choissir un joueur que vous souhaiter attaquer.");
					rendreJoueursVirtuelsValidesAChoisir();
					break;
				case VICTIME:
					rendreJoueursVirtuelsInvalidesAChoisir();
					break;
				case FAIRE_FARFADET_ATTAQUER:
					affichageDebutActFarfadets(evenement);
					break;
				case FAIRE_FARFADER_FINIR:
					affichageFinActFarfadets(evenement);
					break;
				case FAIRE_TAUPE_GEANTE_CHOISIR_VICTIME:
					afficher("Veuillez-vous choissir un joueur que vous souhaiter attaquer.");
					rendreJoueursVirtuelsValidesAChoisir();
					break;
				case FAIRE_TAUPE_GEANTE_DETRUIRE_DE_MENHIRS:
					afficherTaupeGeante(evenement);
					break;
				case GAGNANT:
					annoncerGagant(evenement.getObjet());
					break;
				case CHOISIR_CARTE_ALLIE:
					enregistrerChoix(evenement);
					break;
				case CHOISIR_GRAINES:
					enregistrerChoix(evenement);
					break;
				case PROTEGER_DE_GRAINES:
					afficherProtegeGraines(evenement);
					break;
				case REFLECHIR_CHIEN_DE_GARDE:
					reflechirChienDeGarde(evenement);
					break;
				case REFLECHIR_TAUPE_GEANTE:
					reflechirTaupeGeante(evenement);
				default:
					break;
				}
			}
		}
	}

	/**
	 * Vide <tt>champDeposeCarte</tt>.
	 */
	private void viderCadreDeposeCarte() {
		if (champDeposeCartes.getComponentCount() != 0) {
			Component[] tabCompsant = champDeposeCartes.getComponents();
			for (int i = 0; i < tabCompsant.length; i++) {
				Component component = tabCompsant[i];
				champDeposeCartes.remove(component);
				pileCartesJouees.add(component);
			}
		}
	}
}