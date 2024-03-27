package com.platydev.calculmental.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.platydev.calculmental.data.score.Score;
import com.platydev.calculmental.db.dao.ScoreDAO;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    private static volatile ScoreDatabase INSTANCE;

    public abstract ScoreDAO scoreDAO();

    public static ScoreDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (ScoreDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                                    ScoreDatabase.class, "MyDatabase.db")

                            .build();

                }

            }

        }

        return INSTANCE;

    }
}
