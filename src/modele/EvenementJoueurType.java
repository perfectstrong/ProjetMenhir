package modele;

/**
 * Les evenements potentiellement emis par un joueur.
 * 
 */
public enum EvenementJoueurType {
	/**
	 * Ce joueur a droit de jouer une carte Ingredient.
	 */
	EN_TOUR_PRINCIPAL,
	/**
	 * Ce joueur a droit de jouer une carte Allie.
	 */
	EN_TOUR_SECONDAIRE,
	/**
	 * Ce joueur a fini de jouer sa carte Ingredient.
	 */
	FIN_TOUR_PRINCIPAL,
	/**
	 * Ce joueur a fini de jouer sa carte Allie.
	 */
	FIN_TOUR_SECONDAIRE,
	/**
	 * Ce joueur a decide de faire l'action Geant.
	 */
	FAIRE_GEANT,
	/**
	 * Ce joueur a decide de faire l'action Engrais.
	 */
	FAIRE_ENGRAIS,
	/**
	 * Ce joueur a decide de faire l'action Farfadet et il choit une victime.
	 */
	FAIRE_FARFADET_CHOISIR_VICTIME,
	/**
	 * Ce joueur commence a voler. La victime peut choisir de se defendre si
	 * elle possede une care Chien De Garde.
	 */
	FAIRE_FARFADET_ATTAQUER,
	/**
	 * L'action Farfadet est finie.
	 */
	FAIRE_FARFADER_FINIR,
	/**
	 * Ce joueur a decide de faire l'action Taupe Geante. Il choit une victime.
	 */
	FAIRE_TAUPE_GEANTE_CHOISIR_VICTIME,
	/**
	 * Ce joueur detruit de Menhirs d'un joueur.
	 */
	FAIRE_TAUPE_GEANTE_DETRUIRE_DE_MENHIRS,
	/**
	 * Ce joueur a gagne cette partie.
	 */
	GAGNANT,
	/**
	 * Ce joueur est choisi comme une victime de l'action Farfadet ou Taupe
	 * Geante.
	 */
	VICTIME,
	/**
	 * Ce joueur a choisit de prendre une carte Allie au debut d'une manche.
	 */
	CHOISIR_CARTE_ALLIE,
	/**
	 * Ce joueur a choisit de prendre 2 graines au debut d'une manche.
	 */
	CHOISIR_GRAINES,
	/**
	 * Ce joueur a choisit de proteger ses graines.
	 */
	PROTEGER_DE_GRAINES,
	/**
	 * Ce joueur est demande a selectionner jouer sa carte Chien De Garde ou
	 * non.
	 */
	REFLECHIR_CHIEN_DE_GARDE,
	/**
	 * Ce joueur est demande si il souhaite detruire de Menhirs d'un autre
	 * joueur.
	 */
	REFLECHIR_TAUPE_GEANTE
}