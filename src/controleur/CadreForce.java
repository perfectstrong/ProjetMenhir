package controleur;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modele.Carte;
import modele.CarteIngredient;
import modele.Partie;
import vue.VueCarte;

/**
 * Les en-tetes specialises pour afficher les nombres de force sur une carte.
 * 
 */
public class CadreForce extends EnTeteSpecial {

	private static final long serialVersionUID = -5007639828600108817L;
	/**
	 * L'action correspondant. 0: Geante, 1: Engrais, 2: Farfadet pour une carte
	 * Ingredient; et nul pour une carte Allie.
	 */
	private int act;
	/**
	 * La saison correspondante. 0: Printemps, 1: Ete, 2: Automne, 3: Hiver.
	 */
	private int saison;
	/**
	 * La face dans laquelle ce cadre s'affiche.
	 */
	private VueCarte vueCarte;

	public CadreForce(int act, int saison, int force, VueCarte vueCarte) {
		super(Integer.toString(force));
		this.act = act;
		this.saison = saison;
		this.vueCarte = vueCarte;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (testPosition()) {
					choisirCetteCarte();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (testPosition()) {
					setForeground(Color.RED);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setForeground(Color.BLACK);
			}
		});
	}

	/**
	 * Teste si ce cadre est necessaire de se changer.
	 * 
	 * @return <code>true</code> si sa vueCarte est valide a choisir et sa
	 *         saison est bien celle d'actuelle
	 */
	private boolean testPosition() {
		return vueCarte.isValideAChoisir()
				&& Partie.getUniquePartie().getSaisonEnCours() == saison;
	}

	/**
	 * Execute l'action correspondant lorsque l'utilisateur clique sur ce cadre.
	 */
	private void choisirCetteCarte() {
		// TODO Auto-generated method stub
		Carte carte = vueCarte.getCarte();
		((CarteIngredient) carte).executer(act);
	}
}