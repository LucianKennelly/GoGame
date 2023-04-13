package com.example.goframework;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;


public class GoSkipTurnAction extends GameAction {
    private GamePlayer p;
    public GoSkipTurnAction(GamePlayer player) {
        super(player);
        p = player;
    }
    public GamePlayer getPlayer() {
        return p;
    }
}
