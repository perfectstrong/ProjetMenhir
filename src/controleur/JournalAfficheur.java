package controleur;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;

import modele.JoueurPhysique;
import modele.Partie;
import modele.PartieSimple;

/**
 * Enregistre tout ce qui se passe dans la partie.
 * 
 */
public class JournalAfficheur extends JInternalFrame {

	/**
	 * Le contenu.
	 */
	private JTextPane journal;
	private static final long serialVersionUID = 1L;

	/**
	 * Construit le logger.
	 * 
	 * @param journal le contenu
	 */
	public JournalAfficheur(JTextPane journal) {
		super();
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setPreferredSize(new Dimension(500, 500));
		this.journal = journal;
		this.journal.setFont(new Font("FormalScript Regular", Font.PLAIN, 12));
		setTitle("Journal");
		setClosable(true);
		JScrollPane afficheur = new JScrollPane(journal);
		afficheur
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		afficheur
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(afficheur);
	}

	/**
	 * Sauvegarde le journal dans un texte fichier.
	 */
	public void sauvegarderDonneesEcrites() {
		String nomFichier = new SimpleDateFormat("ddMMyyyy_HHmmss")
				.format(Calendar.getInstance().getTime());
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar
				.getInstance().getTime());
		String heure = new SimpleDateFormat("HH : mm : ss").format(Calendar
				.getInstance().getTime());
		try {
			journal.getStyledDocument().insertString(
					journal.getStyledDocument().getLength(),
					"\n" + heure + "\n" + date, null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedWriter ecrivain = null;
		try {
			ecrivain = new BufferedWriter(new FileWriter(new File("journals/"
					+ nomFichier + ".txt")));
			journal.write(ecrivain);
			ecrivain.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sauvegarde le gagnant et son record dans le ficher recordSimple.txt (pour
	 * la partie Rapide) ou recordAvancee.txt (pour la partie Avancee).
	 * 
	 * @param gagnant le joueur qui gagne le jeu
	 * @param partieJouee partie qui vient de terminer
	 */
	public void sauvegarderRecord(JoueurPhysique gagnant, Partie partieJouee) {
		String typePartie = null;
		if (partieJouee instanceof PartieSimple) {
			typePartie = "recordSimple";
		} else {
			typePartie = "recordAvancee";
		}
		File fichierRecord = new File("journals/" + typePartie + ".txt");
		String nouvelleLigne = System.getProperty("line.separator");
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar
				.getInstance().getTime());
		String heure = new SimpleDateFormat("HH : mm : ss").format(Calendar
				.getInstance().getTime());
		String record = date + nouvelleLigne + heure + nouvelleLigne
				+ gagnant.getNomJoueur() + nouvelleLigne
				+ gagnant.getCompteur().getNbMenhir() + nouvelleLigne // Menhirs
				+ gagnant.getCompteur().getNbGraine();// Graines
		BufferedWriter ecrivain = null;
		try {
			FileWriter fstream = new FileWriter(fichierRecord, true);
			ecrivain = new BufferedWriter(fstream);
			ecrivain.append(record + nouvelleLigne);
			ecrivain.close();
		} catch (Exception e) {

		}
	}
}