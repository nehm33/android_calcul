package com.platydev.calculmental.data;

public enum Mode {
	
	Zen("Zen"),
	CLM("CLM"),
	Infini("Infini"),
	Arcade("Arcade");
	
	private String nom; 
	
	Mode(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
	
	

}
