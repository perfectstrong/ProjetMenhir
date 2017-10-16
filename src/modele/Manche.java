package modele;

import java.util.Observable;

/**
 * 
 * Une piece de la partie Avancee. Elle deroule comme une partie Simple, sauf
 * que les cartes Allies sont distribuees au debut si le joueur souhaite.
 * 
 */
public class Manche extends Observable {
	/**
	 * La partie mere.
	 */
	private PartieAvancee superPartie;
	/**
	 * Le nombre de manche deja jouee.
	 */
	private static int ordreManche;

	/**
	 * @param ordreManche l'ordre
	 */
	public static void setOrdreManche(int ordreManche) {
		Manche.ordreManche = ordreManche;
	}

	/**
	 * @return ordre de Manche actuelle.
	 */
	public static int getOrdreManche() {
		return ordreManche;
	}

	/**
	 * @param superPartie la partie a laquelle cette manche appartient
	 */
	public Manche(PartieAvancee superPartie) {
		super();
		this.superPartie = superPartie;
		ordreManche++;
	}

	/**
	 * Lance cette manche: demander les joueurs de choisir soit une carte Allie
	 * soit 2 graines au debut de cette manche, et puis commencer a jouer.
	 */
	public void gererManche() {
		setChanged();
		notifyObservers(new EvenementPartie(
				EvenementPartieType.DEMANDER_CHOIX_DE_CARTE_ALLIE, superPartie));
		setChanged();
		notifyObservers(new EvenementPartie(
				EvenementPartieType.NOUVELLE_MANCHE, superPartie));
		superPartie.incrementerSaison();
	}
}