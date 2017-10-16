package controleur;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import vue.FenetrePrincipale;

/**
 * Liste tout les records.
 * 
 */
public class RecordsControleur extends JInternalFrame {
	/**
	 * Construit la fenetre de records.
	 */
	public RecordsControleur() {
		setTitle("Records");
		setClosable(true);
		Font fontCommun = new Font("FormalScript Regular", Font.PLAIN, 12);
		setFont(fontCommun);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.01, 1.0 };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblPartieRapide = new JLabel("Partie Rapide");
		lblPartieRapide.setFont(fontCommun);
		GridBagConstraints gbc_lblPartieRapide = new GridBagConstraints();
		gbc_lblPartieRapide.anchor = GridBagConstraints.WEST;
		gbc_lblPartieRapide.gridx = 0;
		gbc_lblPartieRapide.gridy = 0;
		getContentPane().add(lblPartieRapide, gbc_lblPartieRapide);

		JScrollPane scrollPaneRecordSimple = new JScrollPane();
		lblPartieRapide.setLabelFor(scrollPaneRecordSimple);
		scrollPaneRecordSimple
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneRecordSimple
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPaneRecordSimple = new GridBagConstraints();
		gbc_scrollPaneRecordSimple.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneRecordSimple.gridx = 0;
		gbc_scrollPaneRecordSimple.gridy = 1;
		getContentPane()
				.add(scrollPaneRecordSimple, gbc_scrollPaneRecordSimple);

		tableauPartieSimple = new JTable();
		tableauPartieSimple.setFont(fontCommun);
		tableauPartieSimple.getTableHeader().setFont(fontCommun);
		tableauPartieSimple.setModel(new DefaultTableModel(
				loadData("recordSimple.txt"), new String[] { "Date", "Heure",
						"Gagnant", "Menhirs", "Graines" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, Integer.class, Integer.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPaneRecordSimple.setViewportView(tableauPartieSimple);

		JLabel lblPartieAvancee = new JLabel("Partie Avancee");
		lblPartieAvancee.setFont(fontCommun);
		GridBagConstraints gbc_lblPartieAvancee = new GridBagConstraints();
		gbc_lblPartieAvancee.anchor = GridBagConstraints.WEST;
		gbc_lblPartieAvancee.gridx = 1;
		gbc_lblPartieAvancee.gridy = 0;
		getContentPane().add(lblPartieAvancee, gbc_lblPartieAvancee);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		tableauPartieAvancee = new JTable();
		tableauPartieAvancee.setFont(fontCommun);
		tableauPartieAvancee.getTableHeader().setFont(fontCommun);
		tableauPartieAvancee.setModel(new DefaultTableModel(
				loadData("recordAvancee.txt"), new String[] { "Date", "Heure",
						"Gagnant", "Menhirs", "Graines" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, Integer.class, Integer.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tableauPartieAvancee);
	}

	private static final long serialVersionUID = 1L;
	/**
	 * Le tableau a gauche, contenant les resultats de jeu du type partie
	 * rapide.
	 */
	private JTable tableauPartieSimple;
	/**
	 * Le tableau a droite, contenant les resultats de jeu du type partie
	 * avancee.
	 */
	private JTable tableauPartieAvancee;

	/**
	 * Charge les bases de donnee.
	 * 
	 * @param nomFichier
	 *            recordSimple.txt ou recordAvancee.txt
	 * @return les resultats sauvegardes.
	 */
	private Object[][] loadData(String nomFichier) {
		BufferedReader lecture = null;
		Object[][] data = null;
		try {
			lecture = new BufferedReader(new InputStreamReader(this.getClass()
					.getClassLoader()
					.getResourceAsStream(nomFichier),
					"UTF-8"));
			int nbreLignes = 0;
			ArrayList<String> tabStr = new ArrayList<String>();
			String ligneLue;
			while ((ligneLue = lecture.readLine()) != null) {
				nbreLignes++;
				tabStr.add(ligneLue);
			}
			data = new Object[(int) nbreLignes / 5 + 1][5];
			for (int i = 0; i < nbreLignes; i++) {
				data[(int) i / 5][i % 5] = tabStr.get(i);
			}
			lecture.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Fichier " + nomFichier
					+ " introuvable!");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erreur de lecture!");
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur de lecture!");
		}
		return data;
	}

	/**
	 * Affiche cette fenetre.
	 */
	public void afficherRecords() {
		setVisible(true);
		FenetrePrincipale.getFenetrePrincipale().getFrame().getContentPane()
				.add(this);
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