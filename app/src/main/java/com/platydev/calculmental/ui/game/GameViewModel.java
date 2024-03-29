package com.platydev.calculmental.ui.game;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.platydev.calculmental.data.gamelogic.ArcadeGameLogic;
import com.platydev.calculmental.data.gamelogic.CLMGameLogic;
import com.platydev.calculmental.data.gamelogic.EquationCheckResult;
import com.platydev.calculmental.data.gamelogic.GameLogic;
import com.platydev.calculmental.data.gamelogic.GameLogicUpdate;
import com.platydev.calculmental.data.gamelogic.InfiniGameLogic;
import com.platydev.calculmental.data.gamelogic.ZenGameLogic;
import com.platydev.calculmental.data.operation.GenAleatoireOperation;
import com.platydev.calculmental.data.operation.Operation;
import com.platydev.calculmental.data.options.Mode;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.data.score.Score;
import com.platydev.calculmental.db.ScoreDatabase;

import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {

    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<Operation> currentOperation = new MutableLiveData<>();
    MutableLiveData<Boolean> isFinished = new MutableLiveData<>(false);
    MutableLiveData<Boolean> isHighScore = new MutableLiveData<>(false);

    private long timerTime;
    private boolean timerNeeded = true;
    private boolean timelessTimer = false;
    private boolean chronometer = false;
    private GameLogic logic;
    private Options options;
    private Score newHighScore;
    private Score oldHighScore;

    public GameViewModel() {
    }

    public EquationCheckResult checkEquation(int result) {
        EquationCheckResult equationCheckResult = logic.checkResult(currentOperation.getValue(), result);
        GameLogicUpdate gameLogicUpdate = equationCheckResult.getGameLogicUpdate();
        if (gameLogicUpdate.isNewEquationNeeded()) {
            currentOperation.postValue(getNewEquation());
        }
        isFinished.postValue(gameLogicUpdate.isEndGame());
        score.postValue(score.getValue()+ gameLogicUpdate.getScoreVariation());
        timerTime += gameLogicUpdate.getTimeVariation();
        return equationCheckResult;
    }

    public Options getOptions() {
        return options;
    }

    public void init(Options options) {
        this.options = options;
        newHighScore = null;
        oldHighScore = null;

        currentOperation.postValue(getNewEquation());
        score.postValue(0);
        isHighScore.postValue(false);

        Mode mode = options.getMode();
        switch (mode) {
            case CLM:
                setParameters(false, false,true, options.getTemps());
                logic = new CLMGameLogic(options.getNiveau());
                break;
            case Infini:
                setParameters(true, true, true, 0);
                logic = new InfiniGameLogic(options.getNiveau());
                break;
            case Arcade:
                setParameters(false, true, true, options.getTemps());
                logic = new ArcadeGameLogic(options.getNiveau());
                break;
            default:
                setParameters(false, false, false, 0);
                logic = new ZenGameLogic(options.getNiveau());
                break;
        }
    }

    public long getTimerTime() {
        return timerTime;
    }

    public boolean isChronometer() {
        return chronometer;
    }

    public boolean isTimerNeeded() {
        return timerNeeded;
    }

    public boolean isTimelessTimer() {
        return timelessTimer;
    }

    public void incrementTimerTime() {
        timerTime++;
    }

    public void decrementTimerTime() {
        timerTime--;
        if (timerTime == 0) {
            isFinished.postValue(true);
        }
    }

    public void reset() {
        isFinished.postValue(false);
    }

    public void isScoreHighScore(ScoreDatabase database, LifecycleOwner lifecycleOwner) {
        if (options.getMode() != Mode.Zen) {
            Score finalScore = new Score("", score.getValue(), options.getNiveau(),
                            options.getMode().isTimeLimitedMode() ? options.getTemps() : timerTime,
                                    options.getMode().getNom());
            Observer<List<Score>> observer = new Observer<List<Score>>() {
                @Override
                public void onChanged(List<Score> scores) {
                    Collections.sort(scores);
                    if (scores.isEmpty() || scores.size() < 10) {
                        newHighScore = finalScore;
                        isHighScore.postValue(true);
                    } else if (finalScore.compareTo(scores.get(0)) > 0) {
                        newHighScore = finalScore;
                        isHighScore.postValue(true);
                        oldHighScore = scores.get(0);
                    }
                }
            };
            if (options.getMode().isTimeLimitedMode()) {
                database.scoreDAO()
                        .getScores(options.getNiveau(), options.getMode().getNom(), options.getTemps())
                        .observe(lifecycleOwner, observer);
            } else {
                database.scoreDAO()
                        .getNotFixedTimeModeScores(options.getNiveau(), options.getMode().getNom())
                        .observe(lifecycleOwner, observer);

            }
        }

    }

    public boolean saveScore(String pseudo, ScoreDatabase database) {
        if (isHighScore.getValue()) {
            if (!pseudo.isEmpty() && pseudo.length() <= 15) {
                newHighScore.setPseudo(pseudo);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (oldHighScore != null) {
                            database.scoreDAO().deleteScore(oldHighScore);
                        }
                        database.scoreDAO().insertScore(newHighScore);
                    }
                }).start();
                return true;
            }
            return false;
        }
        return true;
    }

    private void setParameters(boolean chronometer, boolean timelessTimer, boolean timerNeeded, long timerTime) {
        this.chronometer = chronometer;
        this.timelessTimer = timelessTimer;
        this.timerNeeded = timerNeeded;
        this.timerTime = timerTime;
    }

    private Operation getNewEquation() {
        return GenAleatoireOperation.nouvelleOperation(options.getNiveau());
    }
}
