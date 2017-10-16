package modele;

/**
 * Les cartes basiques. Distribue 4 cartes de ce type a chaque joueurs au debut
 * chaque manche (dans Partie Avancee) ou Partie Rapide.
 */
public class CarteIngredient extends Carte {

	/**
	 * Le tableau de la force. 3 lignes correspondent aux Geant (1), Engrais (2)
	 * et Farfadet (3). 4 colonnes correspondent aux Printemps (1), Ete (2),
	 * Autumn (3) et Hiver (4).
	 */
	private int[][] tabForce = new int[3][4];

	/**
	 * Construit une carte Ingredient en general.
	 * 
	 * @param id
	 *            <tt>idCarte</tt>
	 * @param nom
	 *            <tt>nomCarte</tt>
	 * @param tab
	 *            <tt>tabForce</tt>
	 */

	public CarteIngredient(int id, String nom, int[][] tab) {
		super(id, nom);
		tabForce = tab;
	}

	/**
	 * Fait pousser des Graines aux Menhirs.
	 * 
	 * @param force
	 *            la force appliquee
	 * @return nbGrainesAPousser le nombre de Graines possibles a pousser
	 */
	public int actEngrais(int force) {
		Compteur compteur = this.getProprietaire().getCompteur();
		int nbGrainesAPousser = Math.min(compteur.getNbGraine(), force);
		compteur.diminuerGraine(nbGrainesAPousser);
		compteur.augmenterMenhir(nbGrainesAPousser);
		return nbGrainesAPousser;
	}

	/**
	 * Vole des Graines d'un joueur. Ne pas pouvoir voler ceux qui sont proteges
	 * par Chien De Garde.
	 * 
	 * @param force
	 *            le nombre de Graines possibles a voler (appliquee a la fois
	 *            Partie Simple et Partie Avancee)
	 * @param joueurAAttaquer
	 *            la victime
	 * @return le nombre de graines voles.
	 */
	public int actFarfadet(int force, JoueurPhysique joueurAAttaquer) {
		JoueurPhysique voleur = getProprietaire();
		Compteur compteurDeVoleur = voleur.getCompteur();
		Compteur compteurDeVictime = joueurAAttaquer.getCompteur();
		force = Math.min(force, compteurDeVictime.getNbGraine());
		compteurDeVictime.diminuerGraine(force);
		compteurDeVoleur.augmenterGraine(force);
		return force;
	}

	/**
	 * Recoit des Graines.
	 * 
	 * @param force
	 *            la force appliquee
	 */
	public void actGeant(int force) {
		proprietaire.getCompteur().augmenterGraine(force);

	}

	/**
	 * Calcule la force d'une action dans une saison donnee.
	 * 
	 * @param typeDeAct
	 *            0 si Geant, 1 si Engrais, 2 si Farfadet
	 * @param saison
	 *            la saison actuelle
	 * @return force
	 */
	public int calForce(int typeDeAct, int saison) {
		return this.getTabForce()[typeDeAct][saison];
	}

	/**
	 * Execute une action choisie
	 * 
	 * @param act
	 *            0 si Geant, 1 si Engrais, 2 si Farfadet
	 */
	public void executer(int act) {
		// TODO Auto-generated method stub
		setChoisie();
		int force = calForce(act, Partie.getUniquePartie().saisonEnCours);
		switch (act) {
		case 0:
			Object[] t = { force, this };
			actGeant(force);
			setChanged();
			notifyObservers(new EvenementJoueur(
					EvenementJoueurType.FAIRE_GEANT, proprietaire, t));
			setFinie();
			if (proprietaire.partieEnCours instanceof PartieSimple) {
				proprietaire.partieEnCours.passerTourPrincipal();
			} else {
				// commencer le tour secondaire
				((PartieAvancee) proprietaire.partieEnCours)
						.passerTourSecondaire();
			}
			break;
		case 1:
			int nbGraineAPousser = actEngrais(force);
			Object[] t2 = { force, nbGraineAPousser, this };
			setChanged();
			notifyObservers(new EvenementJoueur(
					EvenementJoueurType.FAIRE_ENGRAIS, proprietaire, t2));
			setFinie();
			if (proprietaire.partieEnCours instanceof PartieSimple) {
				proprietaire.partieEnCours.passerTourPrincipal();
			} else {
				// commencer le tour secondaire
				((PartieAvancee) proprietaire.partieEnCours)
						.passerTourSecondaire();
			}
			break;
		case 2:
			proprietaire.reflechirJoueurAAttaquer(this);
		default:
			break;
		}
	}

	/**
	 * @return <tt>tabForce</tt>
	 */
	public int[][] getTabForce() {
		return tabForce;
	}
}