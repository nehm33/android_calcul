package com.platydev.calculmental.data.gamelogic;

public class ZenGameLogic extends GameLogic {

    private static final int NB_EQUATIONS_TO_RESOLVE = 20;

    private int nbEquationsPlayed;

    public ZenGameLogic() {
        this.nbEquationsPlayed = 0;
    }

    @Override
    protected GameLogicUpdate goodAnswer() {
        nbEquationsPlayed++;
        if (nbEquationsPlayed == NB_EQUATIONS_TO_RESOLVE) {
            return new GameLogicUpdate(1, 0, false, true);
        } else {
            return new GameLogicUpdate(1, 0, true, false);
        }
    }

    @Override
    protected GameLogicUpdate badAnswer() {
        nbEquationsPlayed++;
        if (nbEquationsPlayed == NB_EQUATIONS_TO_RESOLVE) {
            return new GameLogicUpdate(0, 0, false, true);
        } else {
            return new GameLogicUpdate(0, 0, true, false);
        }
    }
}
