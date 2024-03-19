package com.platydev.calculmental.data.gamelogic;

public class ZenGameLogic extends GameLogic {

    private static final int NB_EQUATIONS_TO_RESOLVE = 20;

    private int nbEquationsPlayed;

    public ZenGameLogic(int niveau) {
        super(niveau);
        this.nbEquationsPlayed = 0;
    }

    @Override
    protected EquationCheckResult goodAnswer() {
        nbEquationsPlayed++;
        if (nbEquationsPlayed == NB_EQUATIONS_TO_RESOLVE) {
            return new EquationCheckResult(1, 0, false, true);
        } else {
            return new EquationCheckResult(1, 0, true, false);
        }
    }

    @Override
    protected EquationCheckResult badAnswer() {
        nbEquationsPlayed++;
        if (nbEquationsPlayed == NB_EQUATIONS_TO_RESOLVE) {
            return new EquationCheckResult(0, 0, false, true);
        } else {
            return new EquationCheckResult(0, 0, true, false);
        }
    }
}
