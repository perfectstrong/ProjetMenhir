package modele;

import java.util.Collections;

public class PartieSimple extends Partie {

	/**
	 * @return la partie actuelle
	 */
	public static Partie getUniquePartie() {
		if (uniquePartie == null) {
			uniquePartie = new PartieSimple();
		}
		return uniquePartie;
	}

	public void debuterJeu() {
		joueurEnTourPrincipal = null;
		saisonEnCours = -1;
		partagerCartesEtGraines();
		setChanged();
		notifyObservers(new EvenementPartie(EvenementPartieType.PREPAREE, this));
		incrementerSaison();
	}

	/**
	 * Distribue les cartes Ingredient et les Graines au debut de la partie.
	 */
	public void partagerCartesEtGraines() {
		Collections.shuffle(jeuDeCartesIngredients);
		partagerCartesIngredients();// partager cartes ingredients
		for (int i = 0; i < getNbJoueurs(); i++) {
			getListeJoueurs().get(i).getCompteur().augmenterGraine(2);
		}
	}
}