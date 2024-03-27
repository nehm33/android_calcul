package com.platydev.calculmental.data.options;

public enum Mode {
	
	Zen("Zen", false),
	CLM("CLM", true),
	Infini("Infini", false),
	Arcade("Arcade", true);
	
	private final String nom;
	private final boolean timeLimitedMode;
	
	Mode(String nom, boolean timeMode) {
		this.nom = nom;
		this.timeLimitedMode = timeMode;
	}

	public String getNom() {
		return nom;
	}

	public boolean isTimeLimitedMode() {
		return timeLimitedMode;
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
