package modele;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Joueur: soit physique (l'utilisateur), soit virtuel.
 */
public class JoueurPhysique extends Observable {

	/**
	 * Les cartes distribuees a ce joueur dans une partie rapide ou une manche.
	 */
	private ArrayList<Carte> mainDuJoueur = new ArrayList<Carte>();
	/**
	 * Nom de ce joueur,
	 */
	private String nomJoueur;

	/**
	 * Position a determiner l'ordre a jouer de ce joueur.
	 */
	private int posJoueur;
	/**
	 * L'age de ce joueur.
	 */
	private int age;
	/**
	 * Le sexe de ce joueur.
	 */
	private char sexe;

	/**
	 * Partie dans laquelle ce joueur joue.
	 */
	protected Partie partieEnCours = null;

	/**
	 * Le comptage du point de ce joueur.
	 */
	private Compteur compteur;

	/**
	 * @param mainDuJoueur
	 *            <tt>mainDuJoueur</tt>
	 * @param nomJoueur
	 *            <tt>nomJoueur</tt>
	 * @param posJoueur
	 *            <tt>posJoueur</tt>
	 * @param age
	 *            <tt>age</tt>
	 * @param sexe
	 *            <tt>sexe</tt>
	 * @param partieEnCours
	 *            <tt>partieEnCours</tt>
	 * @param compteur
	 *            <tt>compteur</tt>
	 */
	public JoueurPhysique(ArrayList<Carte> mainDuJoueur, String nomJoueur,
			int posJoueur, int age, char sexe, Partie partieEnCours,
			Compteur compteur) {
		super();
		this.mainDuJoueur = mainDuJoueur;
		this.nomJoueur = nomJoueur;
		this.posJoueur = posJoueur;
		this.age = age;
		this.sexe = sexe;
		this.partieEnCours = partieEnCours;
		this.compteur = compteur;
	}

	/**
	 * Demande ce joueur a choisir de prendre 2 graines ou une carte Allie au
	 * debut d'une Manche.
	 * @return rien
	 */

	public int choixGrainesOuCarte() {
		// le modele ne fait rien
		return 0;
	}

	/**
	 * @return <tt>age</tt>
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return <tt>compteur</tt>
	 */
	public Compteur getCompteur() {
		return compteur;
	}

	/**
	 * @return <tt>mainDuJoueur</tt>
	 */
	public ArrayList<Carte> getMainDuJoueur() {
		return mainDuJoueur;
	}

	/**
	 * @return <tt>nomJoueur</tt>
	 */

	public String getNomJoueur() {
		return nomJoueur;
	}

	/**
	 * @return <tt>partieEnCours</tt>
	 */
	public Partie getPartieEnCours() {
		return partieEnCours;
	}

	/**
	 * @return <tt>posJoueur</tt>
	 */

	public int getPosJoueur() {
		return posJoueur;
	}

	/**
	 * @return <tt>sexe</tt>
	 */
	public char getSexe() {
		return sexe;
	}

	/**
	 * Joue la carte Chien De Garde.
	 * 
	 * @param carteAttaquer
	 *            la carte jouee par le voleur
	 * @return nombre de graines voles
	 */
	public int jouerChienDeGarde(CarteIngredient carteAttaquer) {
		int forceAttaque = carteAttaquer.calForce(2,
				partieEnCours.saisonEnCours);
		ChienDeGarde chienDeGarde = (ChienDeGarde) mainDuJoueur.get(4);
		chienDeGarde.setChoisie();
		int forceProteger = chienDeGarde.calForce(partieEnCours.saisonEnCours);
		int nbGrainesVoles = chienDeGarde.protegerGraine(forceAttaque,
				partieEnCours.saisonEnCours);
		carteAttaquer.actFarfadet(nbGrainesVoles, this);
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.PROTEGER_DE_GRAINES, this, new Object[] {
						nbGrainesVoles, forceProteger }));
		chienDeGarde.setFinie();
		return nbGrainesVoles;
	}

	/**
	 * Joue une carte Ingredient. Et puis, passe le tour principal au joueur
	 * suivant.
	 */
	public void jouerTourPrincipal() {
		// TODO Auto-generated method stub
		// le modele fait rien
		// la vueJoueurPhysique se met a jour et attend la reponse de
		// l'utilisateur
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.EN_TOUR_PRINCIPAL, this));
	}

	/**
	 * Joue la carte Taupe Geante si ce joueur en possede une. Et puis, passe le
	 * tour secondaire au joueur suivant.
	 */
	public void jouerTourSecondaire() {
		// TODO Auto-generated method stub
		if (mainDuJoueur.size() == 5
				&& mainDuJoueur.get(4) instanceof TaupeGeante
				&& mainDuJoueur.get(4).getEstValable()) {
			reflechirTaupeGeante();
		} else {
			((PartieAvancee) partieEnCours).passerTourSecondaire();
		}
	}

	/**
	 * Reagit avant le voleur.
	 * 
	 * @param carteAttaquer
	 *            la carte choisie par le voleur
	 * @param seProteger
	 *            <code>true</code> si ce joueur accepte de jouer sa carte Chien
	 *            De Garde; <code>false</code> si il n'a pas de choix.
	 */
	public void reagirFarfadet(CarteIngredient carteAttaquer, boolean seProteger) {
		int forceAttaque = carteAttaquer.calForce(2,
				partieEnCours.saisonEnCours);
		int nbGrainesVoles = 0;
		if (seProteger == false) {// choisir de ne pas utiliser chien de garde
			// ou bien ne pas avoir chien de garde (inclus le cas de partie
			// rapide)
			nbGrainesVoles = carteAttaquer.actFarfadet(forceAttaque, this);
		} else {
			nbGrainesVoles = jouerChienDeGarde(carteAttaquer);
		}
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.FAIRE_FARFADER_FINIR,
				carteAttaquer.proprietaire,
				new Object[] { this, nbGrainesVoles }));
		if (partieEnCours instanceof PartieSimple) {
			partieEnCours.passerTourPrincipal();
		} else {
			((PartieAvancee) partieEnCours).passerTourSecondaire();
		}
	}

	/**
	 * Reflechit de jouer la carte Chien De Garde ou non.
	 * 
	 * @param carteAttaquer
	 *            la carte choisie par le voleur
	 */
	public void reflechirChienDeGarde(CarteIngredient carteAttaquer) {
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.REFLECHIR_CHIEN_DE_GARDE, this,
				new Object[] { carteAttaquer }));
	}

	/**
	 * Choisit une victime pour voler des Graines.
	 * 
	 * @param carteIngredient
	 *            la carte que ce joueur a deposee
	 */
	public void reflechirJoueurAAttaquer(CarteIngredient carteIngredient) {
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.FAIRE_FARFADET_CHOISIR_VICTIME, this));
	}

	/**
	 * Choisit une victime pour detruire ses Menhirs.
	 * 
	 * @param taupeGeante
	 *            la carte que ce joueur a deposee
	 */
	public void reflechirJoueurAAttaquer(TaupeGeante taupeGeante) {
		taupeGeante.setChoisie();
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.FAIRE_TAUPE_GEANTE_CHOISIR_VICTIME, this));
	}

	/**
	 * Se demande d'attaquer ou non.
	 */
	public void reflechirTaupeGeante() {
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.REFLECHIR_TAUPE_GEANTE, this));
	}

	public void setMainDuJoueur(ArrayList<Carte> mainDuJoueur) {
		this.mainDuJoueur = mainDuJoueur;
	}

	/**
	 * Utile pour changer le nom a celui des filles.
	 * 
	 * @param nomJoueur
	 *            le nom a affecter
	 */
	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

	/**
	 * @param posJoueur
	 *            la position a affecter.
	 */
	public void setPosJoueur(int posJoueur) {
		this.posJoueur = posJoueur;
	}

	/**
	 * Designe ce joueur comme une victime de l'action Farfadet.
	 * @param attaqueur celui qui vole des Graines de ce joueur.
	 * @param carte la carte Ingredient choisie par <tt>attaqueur</tt>
	 */
	public void setVictime(JoueurPhysique attaqueur, CarteIngredient carte) {
		setChanged();
		notifyObservers(new EvenementJoueur(EvenementJoueurType.VICTIME, this));
		int forceAttaque = carte.calForce(2, partieEnCours.saisonEnCours);
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.FAIRE_FARFADET_ATTAQUER, attaqueur,
				new Object[] { this, forceAttaque }));
		carte.setFinie();
		if (partieEnCours instanceof PartieAvancee) {
			// joueur chien de garde si possible
			if (mainDuJoueur.size() == 5
					&& mainDuJoueur.get(4) instanceof ChienDeGarde
					&& mainDuJoueur.get(4).getEstValable()) {
				reflechirChienDeGarde(carte);
			} else {
				reagirFarfadet(carte, false);
			}
		} else {
			reagirFarfadet(carte, false);
		}
	}

	/**
	 * Designe ce joueur comme une victime de l'action Taupe Geante.
	 * @param attaqueur celui qui detruit des Menhirs de ce joueur.
	 * @param carte la carte Taupe Geante choisie par <tt>attaqueur</tt>
	 */
	public void setVictime(JoueurPhysique attaqueur, TaupeGeante carte) {
		setChanged();
		notifyObservers(new EvenementJoueur(EvenementJoueurType.VICTIME, this));
		int forceAttaque = carte.calForce(partieEnCours.saisonEnCours);
		carte.detruireMenhir(forceAttaque, this);
		setChanged();
		notifyObservers(new EvenementJoueur(
				EvenementJoueurType.FAIRE_TAUPE_GEANTE_DETRUIRE_DE_MENHIRS,
				this, new Object[] { attaqueur, carte }));
		carte.setFinie();
		((PartieAvancee) partieEnCours).passerTourSecondaire();
	}
}