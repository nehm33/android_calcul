package com.platydev.calculmental.data.gamelogic;

public class ArcadeGameLogic extends GameLogic {

    private static final int NB_ATTEMPT_PER_EQUATION = 3;
    private static final int SCORE_BONUS_FACTOR = 5;
    private static final int TIME_BONUS_FACTOR = 2;
    private static final int SCORE_MALUS_FACTOR = -3;

    private int nbAttempt;

    public ArcadeGameLogic(int niveau) {
        super(niveau);
        nbAttempt = 0;
    }

    @Override
    protected EquationCheckResult goodAnswer() {
        nbAttempt = 0;
        int difficulte = currentOperation.getOp().getDifficulte();
        return new EquationCheckResult(SCORE_BONUS_FACTOR*difficulte, TIME_BONUS_FACTOR*difficulte, true, false);
    }

    @Override
    protected EquationCheckResult badAnswer() {
        nbAttempt++;
        if (nbAttempt == NB_ATTEMPT_PER_EQUATION) {
            nbAttempt = 0;
            return new EquationCheckResult(SCORE_MALUS_FACTOR*NB_ATTEMPT_PER_EQUATION, 0, true, false);
        } else {
            return new EquationCheckResult(SCORE_MALUS_FACTOR*nbAttempt, 0, false, false);
        }
    }
}
