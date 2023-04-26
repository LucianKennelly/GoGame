package com.example.goframework;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.GameFramework.players.GameHumanPlayer;

public class GoHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERIL = -4;
    private int BLACK_IN_PERIL = -5;
    private GoSurfaceView surfaceView;
    private Button skipButton = null;
    private int layoutId;
    public TextView playerOneScoreTextView;
    public TextView playerTwoScoreTextView;
    private GoGameState state;

    //constructor
    public GoHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    public void receiveInfo(GameInfo info) {
        if(surfaceView == null) {
            return;
        }
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            surfaceView.flash(Color.RED, 50);
        }
        else {
            state = (GoGameState) info;
            surfaceView.setState((GoGameState) info);
            playerOneScoreTextView.setText("Player One Score: " + state.getWhiteScore());
            playerTwoScoreTextView.setText(("Player Two Score: " + state.getBlackScore()));
        }

    }

    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);
        this.skipButton = (Button)activity.findViewById(R.id.button);
        surfaceView = (GoSurfaceView)myActivity.findViewById(R.id.goSurfaceViewXML);

        playerOneScoreTextView = (TextView)activity.findViewById(R.id.textView6);
        playerTwoScoreTextView = (TextView)activity.findViewById(R.id.textView7);


        surfaceView.setOnTouchListener(this);
        skipButton.setOnClickListener(this);
    }

@Override
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
        Point finalP = surfaceView.translateToIndex(p, view);

        if(finalP == null) {
            surfaceView.flash(Color.RED, 50);
        }
        else if (state.getPlayerToMove() != 0) {
            surfaceView.flash(Color.RED, 50);
        }
        else {
            GoPlacePieceAction action = new GoPlacePieceAction(this, finalP.x, finalP.y);

            game.sendAction(action);
            surfaceView.invalidate();
        }
        return true;
    }

    public void onClick(View view) {
        if (view != null) {
            GoSkipTurnAction action = new GoSkipTurnAction(this);
            game.sendAction(action);
        }
        surfaceView.invalidate();
    }
}
