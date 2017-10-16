package modele;

/**
 * Detruire des Menhirs d'un autre joueur. Vous pouvez la jouer meme pendans le
 * tour d'un autre joueur. Utilisalble uniquement une fois.
 */
public class TaupeGeante extends CarteAllie {

	public TaupeGeante(int id, String nom, int[] tab) {
		super(id, nom, tab);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Detruit des Menhirs d'autre personne.
	 * @param force la force de cette carte dans la saison actuelle
	 * @param joueurAAttaquer la victime choisie
	 */
	public void detruireMenhir(int force, JoueurPhysique joueurAAttaquer) {
		// Compteur compteurAAttaquer = joueurAAttaquer.getCompteur();
		joueurAAttaquer.getCompteur().diminuerMenhir(force);
	}
}
