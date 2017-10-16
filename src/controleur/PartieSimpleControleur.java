package controleur;

import java.util.Iterator;

import javax.swing.JOptionPane;

import modele.CarteIngredient;
import modele.JoueurPhysique;
import modele.Partie;
import modele.PartieSimple;
import vue.FenetrePartie;
import vue.FenetrePrincipale;

public class PartieSimpleControleur extends PartieControleur {

	public void commencerPartie() {
		// TODO Auto-generated method stub
		PartieSimple nouvellePartie = new PartieSimple();
		Partie.setUniquePartie(nouvellePartie);

		fenetrePartie = new FenetrePartie(
				FenetrePrincipale.getFenetrePrincipale());
		fenetrePartie.setTitle("Partie Rapide");
		fenetrePartie.setPartieEnCours(nouvellePartie);
		fenetrePartie.setVisible(true);
		FenetrePrincipale.getFenetrePrincipale().getFrame().add(fenetrePartie);
		setPartieEnCours(nouvellePartie);

		nouvellePartie.addObserver(fenetrePartie);
		JOptionPane.showMessageDialog(null, "C'est la partie Rapide!");
		
		fenetrePartie.setPartieControleur(this);

		demanderInformationInitiale();
		fenetrePartie.presenterJoueurs();
		fenetrePartie.initialiserComposant();
		nouvellePartie.debuterJeu();
	}

	@Override
	public void recommencerPartie() {
		// TODO Auto-generated method stub
		for (Iterator<JoueurPhysique> iterator = getPartieEnCours().getListeJoueurs().iterator(); iterator.hasNext();) {
			JoueurPhysique joueurPhysique = (JoueurPhysique) iterator.next();
			joueurPhysique.getCompteur().setNbGraine(0);
			joueurPhysique.getCompteur().setNbMenhir(0);
			joueurPhysique.getMainDuJoueur().clear();
		}
		for (Iterator<CarteIngredient> iterator = getPartieEnCours().getJeuDeCartesIngredients().iterator(); iterator.hasNext();) {
			CarteIngredient carte = (CarteIngredient) iterator.next();
			carte.setEstValable(true);
			carte.setProprietaire(null);
		}
		fenetrePartie.faireMenage();
		getPartieEnCours().debuterJeu();
	}
}