package com.example.goframework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;

public class GoLocalPlayer extends GameHumanPlayer implements View.OnTouchListener {
    // the surface view
    private GoSurfaceView surfaceView;
    private float pixelDelta;
    private GoGameState state;

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
    public void setState(GoGameState state) {
        this.state = state;
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
        surfaceView = activity.findViewById(R.id.surfaceView);
        surfaceView.setOnTouchListener(this);
        surfaceView.invalidate();
        this.pixelDelta = surfaceView.pixelDelta;
        surfaceView.setState(state);
        surfaceView.init();

    }
    public void setSurfaceView(GoSurfaceView view) {
        surfaceView = view;
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.d("tag","x:"+x);
        Log.d("tag","y:"+y);
        Point p = new Point(x,y);
        Point finalP = surfaceView.translateToIndex(p);
        if (finalP != null) {
            state.setGameBoard(1,finalP.x,finalP.y);
        }
        view.invalidate();
        return false;
    }

}
