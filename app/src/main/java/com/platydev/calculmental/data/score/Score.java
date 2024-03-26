package com.platydev.calculmental.data.score;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "scores")
public class Score implements Comparable<Score> {
	
	private String pseudo;
	private int score;
	private int niveau;
	private long temps;
	private String mode;
	@PrimaryKey
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

	public long getTemps() {
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

	@Override
	public int compareTo(Score o) {
		if (o == null) return 1;
		if (niveau != o.getNiveau()) return Integer.compare(niveau, o.getNiveau());
		if (mode.compareTo(o.getMode()) != 0) return mode.compareTo(o.getMode());
		if (score != o.getScore()) return Integer.compare(score, o.getScore());
		if (temps != o.getTemps()) return -Long.compare(temps, o.getTemps());
		if (!date.equals(o.getDate())) return date.compareTo(o.getDate());
		return pseudo.compareTo(o.getPseudo());
	}
}
