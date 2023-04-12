package com.example.goframework;

import android.view.SurfaceView;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class GoPlacePieceAction extends GameAction{

    private int x;
    private int y;
    public GoPlacePieceAction(GamePlayer player, int initX, int initY) {
        super(player);
        x = Math.max(0, Math.min(8, initX));
        y = Math.max(0, Math.min(8, initY));
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

}
