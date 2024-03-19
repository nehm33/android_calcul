package com.platydev.calculmental.data.operation;

public class Operation {
	
	private Operateur op;
	private int opGauche;
	private int opDroite;
	
	public Operation(Operateur op, int opGauche, int opDroite) {
		this.op = op;
		this.opGauche = opGauche;
		this.opDroite = opDroite;
	}

	public Operateur getOp() {
		return op;
	}

	public int getOpGauche() {
		return opGauche;
	}

	public int getOpDroite() {
		return opDroite;
	}
	
	public int getResultat() {
		return op.evaluer(opGauche, opDroite);
	}
	
	public boolean verifResultat(int val) {
		return this.getResultat()==val;
	}

	public void setOpGauche(int opGauche) {
		this.opGauche = opGauche;
	}

	public void setOpDroite(int opDroite) {
		this.opDroite = opDroite;
	}

	@Override
	public String toString() {
		return "" + opGauche + " " + op + " " + opDroite + "  = ";
	}
}
