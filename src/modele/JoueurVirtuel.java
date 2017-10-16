package modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Le joueur cree par l'ordinateur.
 */
public class JoueurVirtuel extends JoueurPhysique {

	/**
	 * Construit de meme facon de <tt>JoueurPhysique</tt>.
	 * 
	 * @param mainDuJoueur
	 *            la main du joueur
	 * @param nomJoueur
	 *            donne par l'ordinateur
	 * @param posJoueur
	 *            l'ordre de jouer
	 * @param age
	 *            donne par l'ordinateur
	 * @param sexe
	 *            donne par l'ordinateur
	 * @param partieEncours
	 *            la partie actuelle
	 * @param compteur
	 *            sa carte de comptage du point
	 */
	public JoueurVirtuel(ArrayList<Carte> mainDuJoueur, String nomJoueur,
			int posJoueur, int age, char sexe, Partie partieEncours,
			Compteur compteur) {
		super(mainDuJoueur, nomJoueur, posJoueur, age, sexe, partieEncours,
				compteur);
	}

	/**
	 * Trouve le joueur dont le nombre de graines est le maximum entre eux.
	 * 
	 * @return <tt>JoueurPhysique</tt>
	 */
	private JoueurPhysique chercherJoueurNombreDeGrainesMax() {
		// TODO Auto-generated method stub
		JoueurPhysique joueurNbreGrainesMax = partieEnCours.getListeJoueurs()
				.get(0);
		for (Iterator<JoueurPhysique> it = partieEnCours.getListeJoueurs()
				.iterator(); it.hasNext();) {
			JoueurPhysique joueur = it.next();
			if (joueur.getCompteur().getNbGraine() > joueurNbreGrainesMax
					.getCompteur().getNbGraine()) {
				joueurNbreGrainesMax = joueur;
			}
		}
		return joueurNbreGrainesMax;
	}

	/**
	 * Demande ce joueur virtuel de reflechir la choix au debut d'une manche.
	 */
	public int choixGrainesOuCarte() {
		int choix = 2;
		float r = (float) Math.random();
		if (r < 0.50001) {
			choix = 1;
		}
		return choix;
	}

	@Override
	public void jouerTourPrincipal() {
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.EN_TOUR_PRINCIPAL, this));
		int[] resultat = reflechirAct();
		CarteIngredient carteAJoueur = (CarteIngredient) getMainDuJoueur().get(
				resultat[0]);
		int action = resultat[1];
		carteAJoueur.executer(action);
	}

	/**
	 * Reflechi de choisir quelle carte et de faire quelle action.
	 * 
	 * @return le premier element est l'ordre de la carte choisie dans la main
	 *         de ce joueur<br>
	 *         le deuxieme element est l'action choisie: 0 si Geante, 1 si
	 *         Engrais, 2 si Farfadet
	 */
	private int[] reflechirAct() {
		int saison = partieEnCours.getSaisonEnCours();
		int[] resultat = new int[2];
		ArrayList<CarteIngredient> tabCarte = new ArrayList<CarteIngredient>();
		// ajouter les cartes a la collection
		for (int i = 0; i < 4; i++) {
			tabCarte.add((CarteIngredient) getMainDuJoueur().get(i));
		}
		boolean possibleEngrais = false;
		int posForceEngraisPlusProche = 0;
		int nbGraineRestant = getCompteur().getNbGraine();
		if (nbGraineRestant > 0) {
			// tester s'il existe une carte dont la force d'Engrais > 0
			for (int i = 0; i < 4; i++) {
				if (tabCarte.get(i).getEstValable()
						&& (tabCarte.get(i)).calForce(1, saison) > 0) {
					possibleEngrais = true;
					// trouver ainsi la premiere position dans laquelle la force
					// d'Engrais > 0
					posForceEngraisPlusProche = i;
					break;
				}
			}
		}
		// chercher la force d'Engrais le plus proche (et > si possible) a nb
		// Graine restant
		if (possibleEngrais) {
			int difMin = tabCarte.get(posForceEngraisPlusProche).calForce(1,
					saison)
					- nbGraineRestant;
			if (difMin != 0) {
				for (int i = posForceEngraisPlusProche + 1; i < tabCarte.size(); i++) {
					if (tabCarte.get(i).getEstValable()) {
						int dif = tabCarte.get(i).calForce(1, saison)
								- nbGraineRestant;
						if (((difMin < 0) && (dif > difMin))
								|| ((difMin > 0) && (difMin > Math.abs(dif)))) {
							difMin = dif;
							posForceEngraisPlusProche = i;
						}
					}
				}
			}
			resultat[0] = posForceEngraisPlusProche;// position de la carte
													// choisie
			resultat[1] = 1;// action engrais
		} else {// choisir soit geant soit farfadet
			int geantMax = 0;
			int farfadetMax = 0;
			int posGeantMax = 0, posFarfadetsMax = 0;
			for (int i = 0; i < 4; i++) {// initialiser
				if (tabCarte.get(i).getEstValable()) {
					geantMax = tabCarte.get(i).calForce(0, saison);
					farfadetMax = tabCarte.get(i).calForce(2, saison);
					posFarfadetsMax = i;
					posGeantMax = i;
					break;
				}
			}
			// chercher joueur dont le nombre de Graines est max
			JoueurPhysique joueurNbreGrainesMax1 = chercherJoueurNombreDeGrainesMax();
			for (int i = 0; i < 4; i++) {
				if (tabCarte.get(i).getEstValable()) {
					if (tabCarte.get(i).calForce(0, saison) > geantMax) {// chercher
																			// la
																			// force
																			// Geant
																			// max
						posGeantMax = i;
						geantMax = tabCarte.get(i).calForce(0, saison);
					}
					if (tabCarte.get(i).calForce(2, saison) > farfadetMax) {// chercer
																			// la
																			// force
																			// Farfadet
																			// max
						posFarfadetsMax = i;
						farfadetMax = tabCarte.get(i).calForce(2, saison);
					}
				}
			}
			if (geantMax > 1.5 * farfadetMax) {
				resultat[0] = posGeantMax;
				resultat[1] = 0;
			} else if (geantMax == 1.5 * farfadetMax) {
				if (farfadetMax >= joueurNbreGrainesMax1.getCompteur()
						.getNbGraine()
						&& joueurNbreGrainesMax1.getCompteur().getNbGraine() != 0
						&& joueurNbreGrainesMax1 != this) {
					resultat[0] = posFarfadetsMax;
					resultat[1] = 2;

				} else {
					resultat[0] = posGeantMax;
					resultat[1] = 0;
				}

			} else {// if geantMax < 1.5 * farfadetMax
				// revoir cettre bloc
				if (geantMax <= 1.5 * joueurNbreGrainesMax1.getCompteur()
						.getNbGraine()
						&& joueurNbreGrainesMax1.getCompteur().getNbGraine() != 0
						&& joueurNbreGrainesMax1 != this) {
					resultat[0] = posFarfadetsMax;
					resultat[1] = 2;
				} else {
					resultat[0] = posGeantMax;
					resultat[1] = 0;
				}
			}
		}
		return resultat;
	}

	/**
	 * Reflechi de deposer sa carte Allie ou non.
	 * 
	 * @return <code>true</code> si celui-ci souhait joue.
	 */
	public boolean reflechirCarteAllie() {
		// supposer au debut que l'on la choisira
		int saison = partieEnCours.getSaisonEnCours();
		boolean choisi = true;
		Carte carte = getMainDuJoueur().get(4);
		if (carte.getEstValable()) {
			if (carte instanceof TaupeGeante) {
				// tester si quelqu'un a Menhirs
				choisi = false;
				for (int k = 0; k < partieEnCours.getListeJoueurs().size(); k++) {
					if (partieEnCours.getListeJoueurs().get(k).getCompteur()
							.getNbMenhir() > 0) {
						choisi = true; // existe des Menhirs a detruire
						break;
					}
				}
				int[] tab = ((TaupeGeante) carte).getTabForce();
				for (int i = saison + 1; i < tab.length; i++) {
					if (tab[i] >= tab[saison]) {
						// la force dans cette saison n'est pas la plus forte
						choisi = false;
						break;
					}
				}
			} else if (carte instanceof ChienDeGarde) {
				if (((ChienDeGarde) carte).calForce(saison) == 0) {
					choisi = false;
				}
			}
		} else {
			choisi = false;
		}
		return choisi;
	}

	/**
	 * Joue la carte Chien De Garde lorsque la force dans cette saison est non
	 * nulle.
	 */
	@Override
	public void reflechirChienDeGarde(CarteIngredient carteAttaquer) {
		int saison = partieEnCours.getSaisonEnCours();
		boolean choisi = true;
		ChienDeGarde carte = (ChienDeGarde) getMainDuJoueur().get(4);
		if (carte.calForce(saison) == 0) {
			choisi = false;
		}
		reagirFarfadet(carteAttaquer, choisi);
	}

	@Override
	public void reflechirJoueurAAttaquer(CarteIngredient carteIngredient) {
		// TODO Auto-generated method stub
		int i, j;
		if (getPosJoueur() == 0) {
			j = 1;
		} else {
			j = 0;// initialiser j - la position de joueur que l'on choisit a
					// l'attaquer
		}
		ArrayList<JoueurPhysique> tableau = partieEnCours.getListeJoueurs();
		int nb = partieEnCours.getNbJoueurs();
		JoueurPhysique joueurChoisi = null;
		for (i = 0; i < nb; i++) {
			if (tableau.get(i).getCompteur().getNbGraine() > tableau.get(j)
					.getCompteur().getNbGraine()) {
				if (i != getPosJoueur()) {
					j = i;
				}
			}
		}
		joueurChoisi = tableau.get(j);
		joueurChoisi.setVictime(this, carteIngredient);
	}

	@Override
	public void reflechirJoueurAAttaquer(TaupeGeante taupeGeante) {
		// TODO Auto-generated method stub
		taupeGeante.setChoisie();
		int i, j;
		if (getPosJoueur() == 0) {
			j = 1;
		} else {
			j = 0;// initialiser j - la position de joueur que l'on choisit a
					// l'attaquer
		}
		ArrayList<JoueurPhysique> table = partieEnCours.getListeJoueurs();
		int nb = partieEnCours.getNbJoueurs();
		JoueurPhysique joueurChoisi = null;
		for (i = 0; i < nb; i++) {
			if (table.get(i).getCompteur().getNbMenhir() > table.get(j)
					.getCompteur().getNbMenhir()) {
				if (i != getPosJoueur()) {
					j = i;
				}
			}
			joueurChoisi = table.get(j);
		}
		joueurChoisi.setVictime(this, taupeGeante);
	}

	@Override
	public void reflechirTaupeGeante() {
		int saison = partieEnCours.getSaisonEnCours();
		TaupeGeante taupeGeante = (TaupeGeante) getMainDuJoueur().get(4);
		if (taupeGeante.getEstValable()) {
			boolean choisi = false;
			for (int k = 0; k < partieEnCours.getListeJoueurs().size(); k++) {
				if (partieEnCours.getListeJoueurs().get(k).getCompteur()
						.getNbMenhir() > 0) {
					choisi = true; // existe des Menhirs a detruire
					break;
				}
			}
			int[] tab = taupeGeante.getTabForce();
			for (int i = saison + 1; i < tab.length; i++) {
				if (tab[i] >= tab[saison]) {
					// la force dans cette saison n'est pas la plus forte
					choisi = false;
					break;
				}
			}
			if (choisi) {
				reflechirJoueurAAttaquer(taupeGeante);
			} else {
				((PartieAvancee) partieEnCours).passerTourSecondaire();
			}
		}
	}
}