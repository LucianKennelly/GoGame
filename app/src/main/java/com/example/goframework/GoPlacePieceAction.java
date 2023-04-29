package com.example.goframework;

import android.view.SurfaceView;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
/**
 * class GoPlacePieceAction
 *
 * This class defines all the information needed for a place piece action with its
 * x and y coordinates.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

public class GoPlacePieceAction extends GameAction {

    private int x;
    private int y;
    public GoPlacePieceAction(GamePlayer player, int initX, int initY) {
        super(player);
        x = initX;
        y = initY;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

}
