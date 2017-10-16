package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;

/**
 * La partie dans laquelle on va jouer.
 * 
 */
public abstract class Partie extends Observable {

	/**
	 * Partie unique pour une fois.
	 */
	protected static Partie uniquePartie;

	/**
	 * Donne la partie en cours.
	 * 
	 * @return uniquePartie
	 */
	public static Partie getUniquePartie(){
		return uniquePartie;
	}

	/**
	 * @param partie
	 *            la partie actuelle
	 */
	public static void setUniquePartie(Partie partie) {
		uniquePartie = partie;
	}

	/**
	 * Le joueur qui est en train de jouer une carte Ingredient.
	 */
	protected JoueurPhysique joueurEnTourPrincipal = null;
	/**
	 * La saison actuelle. 0, 1, 2, 3 pour Printemps, Ete, Automne, Hiver
	 * respectivement.
	 */
	protected int saisonEnCours = -1;
	/**
	 * Le nombre de joueurs dans cette partie.
	 */
	protected int nbJoueurs;
	/**
	 * La liste des joueurs participants.
	 */
	private ArrayList<JoueurPhysique> listeJoueurs = new ArrayList<JoueurPhysique>();
	/**
	 * Toutes les cartes Ingredient disponibles.
	 */
	protected ArrayList<CarteIngredient> jeuDeCartesIngredients = new ArrayList<CarteIngredient>();
	/**
	 * Toutes les cartes Allie disponibles (non nulle si cette partie est du
	 * type Avancee).
	 */
	protected ArrayList<CarteAllie> jeuDeCartesAllies = new ArrayList<CarteAllie>();

	/**
	 * Affecte l'ordre de jouer pour chaque joueur.
	 */
	private void affecterPosition() {
		for (int i = 0; i < listeJoueurs.size(); i++) {
			JoueurPhysique joueur = listeJoueurs.get(i);
			joueur.setPosJoueur(i);
		}
	}

	/**
	 * Affect le nom pour les joueuses.
	 */
	private void affecterPrenomsFilles() {
		String[] tabPrenomsFilles = new String[] { "Pauline", "Judith",
				"Helene", "Stephanie", "Paola", "Anna" };
		int i = 0;
		for (Iterator<JoueurPhysique> it = listeJoueurs.iterator(); it
				.hasNext();) {
			JoueurPhysique joueur = it.next();
			if (joueur instanceof JoueurVirtuel && joueur.getSexe() == 'F') {
				joueur.setNomJoueur(tabPrenomsFilles[i]);
			}
			i++;
		}
	}

	/**
	 * Cherche le joueur qui gagne.
	 */
	public void calculGagnant() {
		JoueurPhysique joueurGagant = listeJoueurs.get(0);
		// Assumer que la premiere position est le gagant; alors on parcourt
		// depuis la deuxieme pos
		for (int i = 1; i < uniquePartie.getListeJoueurs().size(); i++) {
			JoueurPhysique joueurActuel = listeJoueurs.get(i);
			if (joueurActuel.getCompteur().getNbMenhir() >= joueurGagant
					.getCompteur().getNbMenhir()) {
				if (joueurActuel.getCompteur().getNbMenhir() == joueurGagant
						.getCompteur().getNbMenhir()) {
					if (joueurActuel.getCompteur().getNbGraine() > joueurGagant
							.getCompteur().getNbGraine()) {
						joueurGagant = joueurActuel;
					}
				} else {
					joueurGagant = joueurActuel;
				}

			}
		}
		setChanged();
		notifyObservers(new EvenementJoueur(EvenementJoueurType.GAGNANT,
				joueurGagant));
	}

	/**
	 * Debut le jeu apres avoir prepare les joueurs, cartes.
	 */
	public abstract void debuterJeu();

	/**
	 * Donne l'age d'un joueur virtuel.
	 * 
	 * @return age
	 */
	private int genererAgeAleatoire() {
		return (int) (8 + Math.random() * (100 - 8 + 1));
	}

	/**
	 * Donne le sexe d'un joueur virtuel.
	 * 
	 * @return <tt>M</tt> si masculin<br>
	 *         <tt>F</tt> si feminin
	 */
	private char genererSexeAleatoire() {
		if (Math.random() <= 0.5) {
			return 'F';
		} else {
			return 'M';
		}
	}

	/**
	 * Donne les cartes Allie du jeu.
	 * 
	 * @return jeuDeCartesAllies
	 */
	public ArrayList<CarteAllie> getJeuDeCartesAllies() {
		return jeuDeCartesAllies;
	}

	/**
	 * Donne les cartes Ingredient du jeu.
	 * 
	 * @return jeuDeCartesIngredients
	 */
	public ArrayList<CarteIngredient> getJeuDeCartesIngredients() {
		return jeuDeCartesIngredients;
	}

	/**
	 * Donne le joueur en tour de jouer sa carte Ingredient.
	 * 
	 * @return joueurEnTourPrincipal
	 */
	public JoueurPhysique getJoueurEnTourPrincipal() {
		return joueurEnTourPrincipal;
	}

	/**
	 * @return listeJoueurs
	 */
	public ArrayList<JoueurPhysique> getListeJoueurs() {
		return listeJoueurs;
	}

	/**
	 * @return nbJoueurs
	 */
	public int getNbJoueurs() {
		return nbJoueurs;
	}

	/**
	 * @return saisonEnCours
	 */
	public int getSaisonEnCours() {
		return saisonEnCours;
	}

	/**
	 * Marche a la saison suivante.
	 */
	public void incrementerSaison() {
		// TODO Auto-generated method stub
		if (saisonEnCours == 3) {
			setChanged();
			notifyObservers(new EvenementPartie(EvenementPartieType.FINIE, this));
		} else {
			saisonEnCours++;
			setChanged();
			notifyObservers(new EvenementPartie(
					EvenementPartieType.NOUVELLE_SAISON, this));
			passerTourPrincipal();
		}
	}

	/**
	 * Instancie toutes les cartes Ingredient et Allie.
	 */
	private void instancierCartes() {
		jeuDeCartesIngredients
				.add(new CarteIngredient(0, "POUDRE D'OR", new int[][] {
						{ 2, 2, 3, 1 }, { 2, 3, 0, 3 }, { 1, 1, 3, 3 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(1, "POUDRE D'OR", new int[][] {
						{ 2, 2, 2, 2 }, { 0, 4, 4, 0 }, { 1, 3, 2, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(2, "POUDRE D'OR", new int[][] {
						{ 3, 1, 3, 1 }, { 1, 4, 2, 1 }, { 2, 4, 1, 1 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(3,
				"RACINES D'ARC EN CIEL", new int[][] { { 4, 1, 1, 1 },
						{ 1, 2, 1, 3 }, { 1, 2, 2, 2 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(4,
				"RACINES D'ARC EN CIEL", new int[][] { { 2, 3, 2, 0 },
						{ 0, 4, 3, 0 }, { 2, 1, 1, 3 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(5,
				"RACINES D'ARC EN CIEL", new int[][] { { 2, 2, 3, 0 },
						{ 1, 1, 1, 4 }, { 2, 0, 3, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(6, "ESPRIT DE DOLMEN", new int[][] {
						{ 3, 1, 4, 1 }, { 2, 1, 3, 3 }, { 2, 3, 2, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(7, "ESPRIT DE DOLMEN", new int[][] {
						{ 2, 4, 1, 2 }, { 2, 2, 2, 3 }, { 1, 4, 3, 1 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(8, "ESPRIT DE DOLMEN", new int[][] {
						{ 3, 3, 3, 0 }, { 1, 3, 3, 2 }, { 2, 3, 1, 3 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(9, "RIRES DE FEES", new int[][] {
						{ 1, 2, 2, 1 }, { 1, 2, 3, 0 }, { 0, 2, 2, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(10, "RIRES DE FEES", new int[][] {
						{ 4, 0, 1, 1 }, { 1, 1, 3, 1 }, { 0, 0, 3, 3 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(11, "RIRES DE FEES", new int[][] {
						{ 2, 0, 1, 3 }, { 0, 3, 0, 3 }, { 1, 2, 2, 1 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(12, "RAYON DE LUNE", new int[][] {
						{ 1, 1, 1, 1 }, { 2, 0, 1, 1 }, { 2, 0, 2, 0 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(13, "RAYON DE LUNE", new int[][] {
						{ 2, 0, 1, 1 }, { 1, 3, 0, 0 }, { 0, 1, 2, 1 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(14, "RAYON DE LUNE", new int[][] {
						{ 0, 0, 4, 0 }, { 0, 2, 2, 0 }, { 0, 0, 1, 3 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(15, "CHANT DE SIRENE", new int[][] {
						{ 1, 3, 1, 0 }, { 1, 2, 1, 1 }, { 0, 1, 4, 0 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(16, "CHANT DE SIRENE", new int[][] {
						{ 2, 1, 1, 1 }, { 1, 0, 2, 2 }, { 3, 0, 2, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(17, "CHANT DE SIRENE", new int[][] {
						{ 1, 2, 2, 0 }, { 1, 1, 2, 1 }, { 2, 0, 1, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(18, "LARMES DE DRYADE", new int[][] {
						{ 2, 1, 1, 2 }, { 1, 1, 1, 3 }, { 2, 0, 2, 2 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(19, "LARMES DE DRYADE", new int[][] {
						{ 0, 3, 0, 3 }, { 2, 1, 3, 0 }, { 1, 1, 3, 1 } }));
		jeuDeCartesIngredients
				.add(new CarteIngredient(20, "LARMES DE DRYADE", new int[][] {
						{ 1, 2, 1, 2 }, { 1, 0, 1, 4 }, { 2, 4, 0, 0 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(21,
				"FONTAINES D'EAU PURE", new int[][] { { 1, 3, 1, 2 },
						{ 2, 1, 2, 2 }, { 0, 0, 3, 4 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(22,
				"FONTAINES D'EAU PURE", new int[][] { { 2, 2, 0, 3 },
						{ 1, 1, 4, 1 }, { 1, 2, 1, 3 } }));
		jeuDeCartesIngredients.add(new CarteIngredient(23,
				"FONTAINES D'EAU PURE", new int[][] { { 2, 2, 3, 1 },
						{ 2, 2, 0, 3 }, { 1, 1, 3, 3 } }));
		jeuDeCartesAllies.add(new TaupeGeante(24, "TAUPE GEANTE", new int[] {
				1, 1, 1, 1 }));
		jeuDeCartesAllies.add(new TaupeGeante(25, "TAUPE GEANTE", new int[] {
				0, 2, 2, 0 }));
		jeuDeCartesAllies.add(new TaupeGeante(26, "TAUPE GEANTE", new int[] {
				0, 1, 2, 1 }));
		jeuDeCartesAllies.add(new ChienDeGarde(27, "CHIEN DE GARDE", new int[] {
				2, 0, 2, 0 }));
		jeuDeCartesAllies.add(new ChienDeGarde(28, "CHIEN DE GARDE", new int[] {
				1, 2, 0, 1 }));
		jeuDeCartesAllies.add(new ChienDeGarde(29, "CHIEN DE GARDE", new int[] {
				0, 1, 3, 0 }));

	}

	/**
	 * Construit les joueurs participants. Les met dans l'ordre.
	 * 
	 * @param nbreJoueur
	 *            nombre de jouers voulu par l'utilisateur
	 * @param nomJoueurPhysique
	 *            le nom de l'utilisateur
	 * @param ageJoueurPhysique
	 *            l'age de l'utilisateur
	 * @param sexeJoueurPhysique
	 *            le sexe de l'utilisateur
	 */
	public void instancierJoueursEtCartes(int nbreJoueur,
			String nomJoueurPhysique, int ageJoueurPhysique,
			char sexeJoueurPhysique) {
		String[] tabPrenomsGarcons = new String[] { "Paul", "James", "Harris",
				"Quentin", "Nelson", "Harrison" };
		uniquePartie.nbJoueurs = nbreJoueur;
		uniquePartie.instancierCartes();
		listeJoueurs.add(new JoueurPhysique(new ArrayList<Carte>(),
				nomJoueurPhysique, listeJoueurs.size() + 1, ageJoueurPhysique,
				sexeJoueurPhysique, this, new Compteur()));
		for (int i = 0; i < this.nbJoueurs - 1; i++) {
			listeJoueurs.add(new JoueurVirtuel(new ArrayList<Carte>(),
					tabPrenomsGarcons[i], listeJoueurs.size() + 1,
					genererAgeAleatoire(), genererSexeAleatoire(), this,
					new Compteur()));
		}
		affecterPrenomsFilles();
		Collections.shuffle(listeJoueurs);
		mettreEnPremierLaPlusJeuneJoueuse();
		affecterPosition();
		setChanged();
	}

	/**
	 * Donne le premier tout a la plus jeune jouese. Si il n'y a pas de joueuse,
	 * le plus jeune joueur commence premierement.
	 */
	private void mettreEnPremierLaPlusJeuneJoueuse() {
		JoueurPhysique premierJoueur = null;
		for (int i = 1; i < listeJoueurs.size(); i++) {
			if (listeJoueurs.get(i).getSexe() == 'F') {
				premierJoueur = listeJoueurs.get(i);
				break;
			}
		}
		if (premierJoueur == null) {// pas de joueuse
			// donc choisir le plus jeune
			premierJoueur = listeJoueurs.get(0);
			for (int i = 1; i < listeJoueurs.size(); i++) {
				JoueurPhysique joueur = listeJoueurs.get(i);
				if (joueur.getAge() < premierJoueur.getAge()) {
					Collections.swap(listeJoueurs, 0, i);
				}
			}
		} else {
			for (int i = 1; i < listeJoueurs.size(); i++) {
				if (listeJoueurs.get(i).getSexe() == 'F'
						&& listeJoueurs.get(i).getAge() < premierJoueur
								.getAge()) {
					Collections.swap(listeJoueurs, 0, i);
				}
			}
		}
	}

	/**
	 * Partage les cartes Ingredient. 4 pour chaqu'un et chaqu'une.
	 */
	public void partagerCartesIngredients() {
		int j = 0;
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < getListeJoueurs().size(); k++) {
				CarteIngredient carteADistribuer = jeuDeCartesIngredients
						.get(j);
				listeJoueurs.get(k).getMainDuJoueur().add(carteADistribuer);
				carteADistribuer.setProprietaire(listeJoueurs.get(k));
				j++;
			}
		}
	}

	/**
	 * Passe le droit de jouer principalement au jouer suivant.
	 */
	public void passerTourPrincipal() {
		if (joueurEnTourPrincipal == null) {
			joueurEnTourPrincipal = listeJoueurs.get(0);// debut d'une
														// saison
		} else {
			if (joueurEnTourPrincipal.getPosJoueur() + 1 < listeJoueurs.size()) {
				joueurEnTourPrincipal = listeJoueurs.get(joueurEnTourPrincipal
						.getPosJoueur() + 1);
			} else {
				joueurEnTourPrincipal = null;// fin d'une saison
			}
		}
		if (joueurEnTourPrincipal == null) {
			incrementerSaison();
		} else {
			joueurEnTourPrincipal.jouerTourPrincipal();
		}
	}

	/**
	 * Donne un joueur le droit a jouer principalement.
	 * 
	 * @param joueurEnTourPrincipal
	 *            le joueur qui a le droite de jouer une carte Ingredient
	 */
	public void setJoueurEnTourPrincipal(JoueurPhysique joueurEnTourPrincipal) {
		this.joueurEnTourPrincipal = joueurEnTourPrincipal;
	}
}