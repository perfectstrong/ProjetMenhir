package modele;

/**
 * Les evenements potentiellement emis par une partie.
 * 
 */
public enum EvenementPartieType {
	/**
	 * La partie passe a la saison suivante.
	 */
	NOUVELLE_SAISON,
	/**
	 * La partie passe a la manche suivante.
	 */
	NOUVELLE_MANCHE,
	/**
	 * Toutes les donnes sont preparees.
	 */
	PREPAREE,
	/**
	 * Toutes les donnes pour une manche sont preparees.
	 */
	MANCHE_PREPAREE,
	/**
	 * La partie est finie.
	 */
	FINIE,
	/**
	 * Demande des joueurs de choisir au debut d'une manche.
	 */
	DEMANDER_CHOIX_DE_CARTE_ALLIE,
	/**
	 * La manche actuelle est finie.
	 */
	MANCHE_FINIE
}