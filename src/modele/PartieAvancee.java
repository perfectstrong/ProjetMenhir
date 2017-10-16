package modele;

import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import modele.Partie;

public class PartieAvancee extends Partie implements Observer {

	/**
	 * @return la partie actuelle
	 */
	public static Partie getUniquePartie() {
		if (uniquePartie == null) {
			uniquePartie = new PartieAvancee();
		}
		return uniquePartie;
	}
	/**
	 * Le joueur qui est en cours de jouer sa carte Taupe Geante.
	 */
	private JoueurPhysique joueurEnTourSecondaire = null;

	/**
	 * Un morceau de la partie Avancee.
	 */
	private Manche mancheEnCours = null;

	@Override
	public void debuterJeu() {
		// TODO Auto-generated method stub
		Manche.setOrdreManche(-1);
		incrementerManche();
	}

	/**
	 * Marche a la manche suivante.
	 */
	public void incrementerManche() {
		// TODO Auto-generated method stub
		if (Manche.getOrdreManche() + 1 < getNbJoueurs()) {
			saisonEnCours = -1;
			joueurEnTourPrincipal = null;
			joueurEnTourSecondaire = null;
			viderMainJoueur();
			rendreValableToutesLesCartes();
			mancheEnCours = new Manche(this);
			mancheEnCours.addObserver(this);
			mancheEnCours.gererManche();
		} else {
			setChanged();
			notifyObservers(new EvenementPartie(EvenementPartieType.FINIE, this));
		}
	}

	@Override
	public void incrementerSaison() {
		// TODO Auto-generated method stub
		if (saisonEnCours == 3) {
			setChanged();
			notifyObservers(new EvenementPartie(
					EvenementPartieType.MANCHE_FINIE, this));
			incrementerManche();
		} else {
			saisonEnCours++;
			setChanged();
			notifyObservers(new EvenementPartie(
					EvenementPartieType.NOUVELLE_SAISON, this));
			passerTourPrincipal();
		}
	}

	/**
	 * Distribue les cartes Ingredient, Allie et les graines selon la choix de
	 * chaque joueur.
	 * 
	 * @param choixDeJoueurPhysique
	 *            1 si l'utilisateur souhaite une carte Allie; et 2 sinon.
	 */
	public void partagerCartesEtGraines(int choixDeJoueurPhysique) {
		Collections.shuffle(jeuDeCartesIngredients);
		Collections.shuffle(jeuDeCartesAllies);
		partagerCartesIngredients();
		int posCarteAllieADistribuer = -1;
		for (int i = 0; i < getListeJoueurs().size(); i++) {
			JoueurPhysique joueur = getListeJoueurs().get(i);
			int choix = 0;
			if (joueur instanceof JoueurVirtuel) {
				choix = joueur.choixGrainesOuCarte();
			} else {
				choix = choixDeJoueurPhysique;
			}
			EvenementJoueur ev = null;
			if (choix == 1) {// choisir d'une carte alliee
				// trouver la premiere carte alliee valable
				posCarteAllieADistribuer++;
				jeuDeCartesAllies.get(posCarteAllieADistribuer)
						.setProprietaire(joueur);
				joueur.getMainDuJoueur().add(
						jeuDeCartesAllies.get(posCarteAllieADistribuer));
				ev = new EvenementJoueur(
						EvenementJoueurType.CHOISIR_CARTE_ALLIE, joueur);
			} else {// choisir de prendre 2 graines
				joueur.getCompteur().augmenterGraine(2);
				ev = new EvenementJoueur(EvenementJoueurType.CHOISIR_GRAINES,
						joueur);
			}
			setChanged();
			notifyObservers(ev);
		}
	}

	/**
	 * Passe le droite de jouer la carte Taupe Geante au joueur suivant.
	 */
	public void passerTourSecondaire() {
		if (joueurEnTourSecondaire == null) {
			joueurEnTourSecondaire = getListeJoueurs().get(0);// debut de tour
																// secondaire
		} else {
			if (joueurEnTourSecondaire.getPosJoueur() + 1 < getListeJoueurs()
					.size()) {
				joueurEnTourSecondaire = getListeJoueurs().get(
						joueurEnTourSecondaire.getPosJoueur() + 1);
			} else {
				joueurEnTourSecondaire = null;// fin de tour secondaire
			}
		}
		if (joueurEnTourSecondaire == null) {
			passerTourPrincipal();
		} else {
			joueurEnTourSecondaire.jouerTourSecondaire();
		}
	}

	/**
	 * Retourne toutes les cartes au distribueur.
	 */
	public void rendreValableToutesLesCartes() {
		for (Iterator<CarteIngredient> it = jeuDeCartesIngredients.iterator(); it
				.hasNext();) {
			CarteIngredient carte = (CarteIngredient) it.next();
			carte.setEstValable(true);
			carte.setProprietaire(null);
		}
		for (Iterator<CarteAllie> it = jeuDeCartesAllies.iterator(); it
				.hasNext();) {
			CarteAllie carte = (CarteAllie) it.next();
			carte.setEstValable(true);
			carte.setProprietaire(null);
		}
	}

	/**
	 * Observe les manches. Elle transporte les messages recus depuis les
	 * manches vers la fenetre Partie.
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		setChanged();
		notifyObservers(arg1);
	}

	/**
	 * Reprend toutes les cartes dans la main du joueur.
	 */

	public void viderMainJoueur() {
		for (Iterator<JoueurPhysique> it = getListeJoueurs().iterator(); it
				.hasNext();) {
			JoueurPhysique joueur = (JoueurPhysique) it.next();
			for (Iterator<Carte> iterator = joueur.getMainDuJoueur().iterator(); iterator
					.hasNext();) {
				Carte carte = (Carte) iterator.next();
				carte.setProprietaire(null);
			}
			joueur.getMainDuJoueur().clear();
		}
	}
}