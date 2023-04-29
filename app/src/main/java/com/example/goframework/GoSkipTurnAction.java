package com.example.goframework;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

/**
 * class GoSkipTurnAction
 *
 * This class defines the skip turn action when a player skips their turn
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

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
