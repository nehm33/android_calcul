package com.platydev.calculmental.data.score;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score {
	
	private String pseudo;
	private int score;
	private int niveau;
	private int temps;
	private String mode;
	private LocalDateTime date;
	
	public Score() {
		
	}

	public Score(String pseudo, int score, int niveau, int temps, String mode) {
		this.pseudo = pseudo;
		this.score = score;
		this.niveau = niveau;
		this.temps = temps;
		this.mode = mode;
		this.date = LocalDateTime.now();
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getTempsString() {
		return temps/60  + " : " + temps%60;
	}
	
	public String getDateString() {
		return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
}
