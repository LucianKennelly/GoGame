package com.example.goframework;

import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;

public class GoLocalPlayer extends GameHumanPlayer {
    // the surface view
    private GoSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public GoLocalPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        //surfaceView.setState((GoGameState)info);
        surfaceView.invalidate();
    }

    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);
        surfaceView = new GoSurfaceView(activity);
        surfaceView.init();
        surfaceView.invalidate();

    }
}
