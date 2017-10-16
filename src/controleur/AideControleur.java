package controleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import vue.FenetrePrincipale;

/**
 * La fenetre expliquant la loi du jeu de Menhir et comment jouer sur
 * l'ordinateur.
 * 
 */
public class AideControleur extends JInternalFrame {
	private static final long serialVersionUID = -1189615613920312190L;

	/**
	 * Lance cette fenetre.
	 */
	public void expliquer() {
		// TODO Auto-generated method stub
		setVisible(true);
		FenetrePrincipale.getFenetrePrincipale().getFrame().getContentPane()
				.add(this);
	}

	/**
	 * Charge le fichier regle.txt, qui contient la loi du jeu. Leve une
	 * exception du type FileNotFoundException si le fichier reglex.txt n'est
	 * trouve.
	 * 
	 * @return le contenu du fichier regle.txt
	 */
	private String[] loadData() {
		BufferedReader lecture = null;
		ArrayList<String> listeDataLu = new ArrayList<String>();
		try {
			lecture = new BufferedReader(
					new InputStreamReader(this.getClass().getClassLoader()
							.getResourceAsStream("regle.txt"), "UTF-8"));
			String dataLu = null;
			while ((dataLu = lecture.readLine()) != null) {
				listeDataLu.add(dataLu);
			}
			lecture.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"Fichier regle.txt introuvable!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"Erreur de lecture: incloturable!");
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,
					e.getMessage());
		}
		String[] data = listeDataLu.toArray(new String[listeDataLu.size()]);
		return data;
	}

	/**
	 * Construit cette fenetre, inclus la police d'affichage, charger la
	 * contenu.
	 */
	public AideControleur() {
		setTitle("Regle du Jeu de Menhir");
		setClosable(true);
		getContentPane().setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(textPane);
		Document doc = textPane.getDocument();

		String[] texte = loadData();
		String nouvelleLigne = System.getProperty("line.separator");
		for (int i = 0; i < texte.length; i++) {
			String string = texte[i];
			try {
				if (string.length() > 0) {
					doc.insertString(doc.getLength(), nouvelleLigne
							+ realTextOf(string), styleOf(string));
				} else {
					doc.insertString(doc.getLength(), nouvelleLigne, null);
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Trouve l'"exact" texte (sans code de marque).
	 * 
	 * @param string
	 *            le texte a determiner le contenu reel
	 * @return l'"exact" texte
	 */
	private String realTextOf(String string) {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder(string);
		str.delete(0, levelOfText(string));
		return str.toString();
	}

	/**
	 * Donne le niveau du texte lu en fonction de code de marque.
	 * 
	 * @param string
	 *            le texte a determiner le niveau
	 * @return 0 si seule du texte<br>
	 *         1 si le titre premier<br>
	 *         2 si le titre de partie<br>
	 *         3 si le titre d'un paragraphe
	 */
	private int levelOfText(String string) {
		StringBuilder str = new StringBuilder(string);
		int i = 0;
		while (str.charAt(i) == '=') {
			i++;
		}
		return i;
	}

	/**
	 * Donne la police d'affichage par defaut pour la ligne du texte lu.
	 * 
	 * @param string
	 *            le texte a determiner le style approprie
	 * @return la police d'affichage
	 */
	private SimpleAttributeSet styleOf(String string) {
		// TODO Auto-generated method stub
		SimpleAttributeSet set = new SimpleAttributeSet();
		Font fontThirdHeader = new Font("Charlesworth Bold", Font.BOLD, 28);
		Font fontSecondHeader = new Font("Charlesworth Bold", Font.BOLD, 32);
		Font fontFirstHeader = new Font("GothicE", Font.PLAIN, 48);
		Font fontPlain = new Font("FormalScript Regular", Font.PLAIN, 24);
		switch (levelOfText(string)) {
		case 0:
			StyleConstants.setFontFamily(set, fontPlain.getFamily());
			StyleConstants.setFontSize(set, fontPlain.getSize());
			StyleConstants.setForeground(set, Color.WHITE);
			break;
		case 1:
			StyleConstants.setFontFamily(set, fontFirstHeader.getFamily());
			StyleConstants.setFontSize(set, fontFirstHeader.getSize());
			StyleConstants.setForeground(set, Color.RED);
			StyleConstants.setBold(set, true);
			StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
			break;
		case 2:
			StyleConstants.setFontFamily(set, fontSecondHeader.getFamily());
			StyleConstants.setFontSize(set, fontSecondHeader.getSize());
			StyleConstants.setForeground(set, Color.RED);
			StyleConstants.setBold(set, true);
			break;
		case 3:
			StyleConstants.setFontFamily(set, fontThirdHeader.getFamily());
			StyleConstants.setFontSize(set, fontThirdHeader.getSize());
			StyleConstants.setForeground(set, Color.RED);
			break;
		default:
			break;
		}
		return set;
	}

	/**
	 * Eteint cette fenetre et re-affiche <tt>panneauBoutons</tt> dans la
	 * fenetre principale.
	 */
	@Override
	public void doDefaultCloseAction() {
		// TODO Auto-generated method stub
		super.doDefaultCloseAction();
		FenetrePrincipale.getFenetrePrincipale().getFrame().getContentPane()
				.remove(this);
	}
}