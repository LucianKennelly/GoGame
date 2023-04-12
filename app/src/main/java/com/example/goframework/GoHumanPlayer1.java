package com.example.goframework;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;

public class GoHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener {

    private GoSurfaceView surfaceView;
    private int layoutId;

    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERILL = -4;
    private int BLACK_IN_PERILL = -5;

    //constructor
    public GoHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    public void receiveInfo(GameInfo info) {
        surfaceView.setState((GoGameState) info);
        surfaceView.invalidate();

    }

    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);

        surfaceView = (GoSurfaceView)myActivity.findViewById(R.id.goSurfaceViewXML);
        surfaceView.setOnTouchListener(this);
    }


    public View getTopView() {
       return myActivity.findViewById(R.id.top_gui_layout);
    }


    ////////
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if(event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }

        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.d("tag","x:"+x);
        Log.d("tag","y:"+y);
        Point p = new Point(x,y);
        Point finalP = surfaceView.translateToIndex(p);

        GoPlacePieceAction action = new GoPlacePieceAction(this, finalP.x, finalP.y) ;
        game.sendAction(action);
        surfaceView.invalidate();

        return true;
    }
}
