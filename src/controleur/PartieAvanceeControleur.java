package controleur;

import javax.swing.JOptionPane;

import modele.Partie;
import modele.PartieAvancee;
import vue.FenetrePartie;
import vue.FenetrePrincipale;

public class PartieAvanceeControleur extends PartieControleur {

	public void commencerPartie() {
		// TODO Auto-generated method stub
		PartieAvancee nouvellePartie = new PartieAvancee();
		Partie.setUniquePartie(nouvellePartie);
		fenetrePartie = new FenetrePartie(FenetrePrincipale.getFenetrePrincipale());
		fenetrePartie.setTitle("Partie Avancee");
		fenetrePartie.setVisible(true);
		FenetrePrincipale.getFenetrePrincipale().getFrame().add(fenetrePartie);
		setPartieEnCours(nouvellePartie);
		fenetrePartie.setPartieEnCours(nouvellePartie);
		
		nouvellePartie.addObserver(fenetrePartie);
		JOptionPane.showMessageDialog(null, "C'est la partie Avancee!");
		
		fenetrePartie.setPartieControleur(this);
		
		demanderInformationInitiale();
		fenetrePartie.presenterJoueurs();
		fenetrePartie.initialiserComposant();
		nouvellePartie.debuterJeu();
	}

	@Override
	public void recommencerPartie() {
		// TODO Auto-generated method stub
		fenetrePartie.faireMenage();
		getPartieEnCours().debuterJeu();
	}
}