package com.platydev.calculmental.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.platydev.calculmental.data.score.Score;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface ScoreDAO {

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode AND temps = :temps ORDER BY score DESC")
    LiveData<List<Score>> getScores(int niveau, String mode, long temps);

    @Query("SELECT * FROM scores WHERE niveau = :niveau AND mode = :mode ORDER BY score DESC, temps ASC")
    LiveData<List<Score>> getNotFixedTimeModeScores(int niveau, String mode);

    @Insert
    long insertScore(Score score);

    @Delete
    int deleteScore(Score score);
}
