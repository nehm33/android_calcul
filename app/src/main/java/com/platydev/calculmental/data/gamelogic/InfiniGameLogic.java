package com.platydev.calculmental.data.gamelogic;

public class InfiniGameLogic extends GameLogic {

    public InfiniGameLogic(int niveau) {
        super(niveau);
    }

    @Override
    protected GameLogicUpdate goodAnswer() {
        return new GameLogicUpdate(1, 0, true, false);
    }

    @Override
    protected GameLogicUpdate badAnswer() {
        return new GameLogicUpdate(0, 0, false, true);
    }
}
