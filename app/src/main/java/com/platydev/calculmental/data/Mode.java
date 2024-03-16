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
	
	public static Mode fromString(String mode) {
		switch (mode) {
            case "CLM":
				return Mode.CLM;
			case "Infini":
				return Mode.Infini;
			case "Arcade":
				return Mode.Arcade;
			default:
				return Mode.Zen;
		}
	}

}
