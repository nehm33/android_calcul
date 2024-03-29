package com.platydev.calculmental.data.score;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.platydev.calculmental.data.utils.Utils;

import java.time.LocalDateTime;

@Entity(tableName = "scores")
public class Score implements Comparable<Score> {
	
	private String pseudo;
	private int score;
	private int niveau;
	private long temps;
	private String mode;
	@PrimaryKey
	@NonNull
	private String date;
	
	public Score() {
		
	}

	public Score(String pseudo, int score, int niveau, long temps, String mode) {
		this.pseudo = pseudo;
		this.score = score;
		this.niveau = niveau;
		this.temps = temps;
		this.mode = mode;
		this.date = LocalDateTime.now().format(Utils.STANDARD_DATE_TIME_FORMATTER);
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setTemps(long temps) {
		this.temps = temps;
	}

	public long getTemps() {
		return temps;
	}

	@NonNull
	public String getDate() {
		return date;
	}

	public void setDate(@NonNull String date) {
		this.date = date;
	}

	@Override
	public int compareTo(Score o) {
		if (o == null) return 1;
		if (niveau != o.getNiveau()) return Integer.compare(niveau, o.getNiveau());
		if (mode.compareTo(o.getMode()) != 0) return mode.compareTo(o.getMode());
		if (score != o.getScore()) return Integer.compare(score, o.getScore());
		if (temps != o.getTemps()) return -Long.compare(temps, o.getTemps());
		if (!date.equals(o.getDate())) return -LocalDateTime.parse(date, Utils.STANDARD_DATE_TIME_FORMATTER).compareTo(LocalDateTime.parse(o.getDate(), Utils.STANDARD_DATE_TIME_FORMATTER));
		return pseudo.compareTo(o.getPseudo());
	}
}
