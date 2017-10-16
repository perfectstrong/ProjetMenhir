package controleur;

import vue.FenetrePartie;
import modele.Partie;

/**
 * Le controleur du jeu. Il initialise les marches premiers.
 * 
 */
public abstract class PartieControleur {
	/**
	 * La partie que celui-ci controle.
	 */
	private Partie partieEnCours;
	/**
	 * La fenetre que celui-ci controle.
	 */
	protected FenetrePartie fenetrePartie;

	/**
	 * Commence le jeu.
	 */
	public abstract void commencerPartie();

	/**
	 * Recommence le jeu avec les memes joueurs.
	 */
	public abstract void recommencerPartie();

	/**
	 * @return partieEnCours
	 */
	public Partie getPartieEnCours() {
		return partieEnCours;
	}

	/**
	 * @param partieEnCours
	 *            la partie actuelle
	 */
	public void setPartieEnCours(Partie partieEnCours) {
		this.partieEnCours = partieEnCours;
	}

	/**
	 * Demande l'utilisateur a entrer le nombre de joueurs voulu, son nom, son
	 * age, son sexe.
	 */
	public void demanderInformationInitiale() {
		// TODO Auto-generated method stub
		int nombreDeJoueur = fenetrePartie.demanderNombreDeJoueur();
		String nomDeJoueurPhysique = fenetrePartie
				.demanderNomDeJoueurPhysique();
		int ageDeJoueurPhysique = fenetrePartie.demanderAgeDeJoueurPhysique();
		char sexeDeJoueurPhysique = fenetrePartie
				.demanderSexeDeJoueurPhysique();
		getPartieEnCours().instancierJoueursEtCartes(nombreDeJoueur,
				nomDeJoueurPhysique, ageDeJoueurPhysique, sexeDeJoueurPhysique);
	}
}