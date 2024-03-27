package com.platydev.calculmental.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.platydev.calculmental.data.score.Score;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface ScoreDAO {

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode AND temps = :temps ORDER BY score DESC, date ASC")
    List<Score> getScores(int niveau, String mode, long temps);

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode ORDER BY score DESC, temps ASC, date ASC")
    List<Score> getNotFixedTimeModeScores(int niveau, String mode);

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode AND temps = :temps ORDER BY score ASC, date DESC LIMIT 1")
    Score getWorstScore(int niveau, String mode, long temps);

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode ORDER BY score ASC, temps DESC, date DESC LIMIT 1")
    Score getNotFixedTimeModeWrostScore(int niveau, String mode);

    @Insert
    int insertScore(Score score);

    @Query("DELETE FROM scores WHERE date = :date")
    int deleteScore(LocalDateTime date);
}
