package com.platydev.calculmental.data;

// Zen(nb fixe), Infini(erreur fatale), CLM(1min), Burger(1min+bonus et malus)
public class Modele {
	
	private Operation currOp;
	private int score = 0;

	public Operation getCurrOp() {
		return currOp;
	}

	public int getScore() {
		return score;
	}

	/*public void nouvelleOperation() {
		currOp = GenAleatoireOperation.nouvelleOperation(currNiveau);
	}
	
	public boolean verifier(int resultat) {
		return currOp.verifResultat(resultat);
	}
	
	public void commenceJeu() {
		nouvelleOperation();
		score = 0;
	}
	
	public void augmenteScore(int points) {
		score += points;
	}
	
	public void diminueScore(int points) {
		score -= points;
	}*/

}
