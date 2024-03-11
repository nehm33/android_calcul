package com.platydev.calculmental.data;

public class GenAleatoireOperation {
	
	public GenAleatoireOperation() {
		
	}

	public static Operation nouvelleOperation(int niveau) {
		Operateur op = nouvelOperateur(niveau);
		Operation o = new Operation(op, 0, 0);
		setOperation(o, niveau);
		return o;
	}
	
	private static Operateur nouvelOperateur(int niveau) {
		if (niveau <= 5) {
			return Operateur.opAleatoire(true, false, false, false);
		} else if (niveau <= 10) {
			return Operateur.opAleatoire(true, true, false, false);
		} else if (niveau <= 15) {
			return Operateur.opAleatoire(true, true, true, false);
		} else {
			return Operateur.opAleatoire(true, true, true, true);
		}
	}
	
	private static void setOperation(Operation o, int niveau) {
		switch (o.getOp().getOp()) {
			case '+':
				o.setOpGauche((int) (Math.random()*niveau*10));
				o.setOpDroite((int) (Math.random()*niveau*10));
				break;
			case '-':
				int number = (int) (Math.random()*(niveau-5)*10);
				o.setOpDroite(number);
				o.setOpGauche((int) (Math.random()*(niveau-5)*10)+number);
				break;
			case 'x':
				o.setOpGauche((int) (Math.random()*(niveau-10)*5));
				o.setOpDroite((int) (Math.random()*(niveau-10)*5));
				break;
			case '/':
				int quot; 
				do {
					quot = (int) (Math.random()*(niveau-15)*5);
				} while (quot==0);
				o.setOpDroite(quot);
				o.setOpGauche((int) (Math.random()*(niveau-15)*5)*quot);
				break;
			default:
				break;
		}
	}

}
