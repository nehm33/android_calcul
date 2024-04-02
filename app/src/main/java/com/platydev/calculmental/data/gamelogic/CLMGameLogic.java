package com.platydev.calculmental.data.gamelogic;

public class CLMGameLogic extends GameLogic {

    @Override
    protected GameLogicUpdate goodAnswer() {
        return new GameLogicUpdate(1, 0, true, false);
    }

    @Override
    protected GameLogicUpdate badAnswer() {
        return new GameLogicUpdate(0, 0, false, false);
    }
}
