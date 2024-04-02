package com.platydev.calculmental.data.gamelogic;

public class ArcadeGameLogic extends GameLogic {

    private static final int NB_ATTEMPT_PER_EQUATION = 3;
    private static final int SCORE_BONUS_FACTOR = 10;
    private static final int TIME_BONUS_FACTOR = 1;
    private static final int SCORE_MALUS_FACTOR = -2;
    private static final int TIME_MALUS_FACTOR = -1;
    private static final int DIFFICULTE_MAX = 4;

    private int nbAttempt;

    public ArcadeGameLogic() {
        nbAttempt = 0;
    }

    @Override
    protected GameLogicUpdate goodAnswer() {
        nbAttempt = 0;
        int difficulte = currentOperation.getOp().getDifficulte();
        return new GameLogicUpdate(SCORE_BONUS_FACTOR*difficulte, TIME_BONUS_FACTOR*difficulte, true, false);
    }

    @Override
    protected GameLogicUpdate badAnswer() {
        nbAttempt++;
        int difficulte = currentOperation.getOp().getDifficulte();
        if (nbAttempt == NB_ATTEMPT_PER_EQUATION) {
            nbAttempt = 0;
            return new GameLogicUpdate(SCORE_MALUS_FACTOR*(DIFFICULTE_MAX-difficulte+1), TIME_MALUS_FACTOR*(DIFFICULTE_MAX-difficulte+1), true, false);
        } else {
            return new GameLogicUpdate(SCORE_MALUS_FACTOR*(DIFFICULTE_MAX-difficulte+1), TIME_MALUS_FACTOR*(DIFFICULTE_MAX-difficulte+1), false, false);
        }
    }
}
