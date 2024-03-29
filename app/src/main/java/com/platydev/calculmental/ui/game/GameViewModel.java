package com.platydev.calculmental.ui.game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platydev.calculmental.data.gamelogic.ArcadeGameLogic;
import com.platydev.calculmental.data.gamelogic.CLMGameLogic;
import com.platydev.calculmental.data.gamelogic.EquationCheckResult;
import com.platydev.calculmental.data.gamelogic.GameLogicUpdate;
import com.platydev.calculmental.data.gamelogic.GameLogic;
import com.platydev.calculmental.data.gamelogic.InfiniGameLogic;
import com.platydev.calculmental.data.gamelogic.ZenGameLogic;
import com.platydev.calculmental.data.operation.GenAleatoireOperation;
import com.platydev.calculmental.data.options.Mode;
import com.platydev.calculmental.data.operation.Operation;
import com.platydev.calculmental.data.options.Options;

public class GameViewModel extends ViewModel {

    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<Operation> currentOperation = new MutableLiveData<>();
    MutableLiveData<Boolean> isFinished = new MutableLiveData<>(false);

    private long timerTime;
    private boolean timerNeeded = true;
    private boolean timelessTimer = false;
    private boolean chronometer = false;
    private GameLogic logic;
    private Options options;

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

        currentOperation.postValue(getNewEquation());
        score.postValue(0);

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
