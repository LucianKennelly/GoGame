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
    private int playerTurn = 2;

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
        if (playerTurn == 1) {
            playerTurn = 2;
        }
        else {
            playerTurn =1 ;
        }
        if (finalP != null) {
            if (state.getGameBoard()[finalP.x][finalP.y] == 0){
                state.setGameBoard(playerTurn, finalP.x, finalP.y);
            }
        }
        checkCaptures();
        view.invalidate();
        return false;
    }
    public void checkCaptures() {
        int[][] board = state.gameBoard;
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                if(board[row][column] == 1){
                    board[row][column] = 3;
                }
            }
        }

        //part 3
        int loopCounter = 0;
        boolean loopIn = true;
        while (loopIn == true){
            loopIn = false;
            for(int row = 0; row < board.length; row++){
                for(int column = 0; column < board[row].length; column++){
                    if(board[row][column] == 3){
                        if(row>0){
                            if ((board[row-1][column] == 0)||(board[row-1][column] == 1)){
                                board[row][column] = 1;
                                loopIn = true;

                            }
                        }

                        if(row<board.length-1){
                            if((board[row+1][column] == 0)||(board[row+1][column] == 1)){
                                board[row][column] = 1;
                                loopIn = true;
                            }
                        }

                        if(column>0){
                            if ((board[row][column-1] == 0)||(board[row][column-1] == 1)){
                                board[row][column] = 1;
                                loopIn = true;
                            }
                        }

                        if(column<board[row].length -1){
                            if((board[row][column+1] == 0)||(board[row][column+1] == 1)){
                                board[row][column] = 1;
                                loopIn = true;
                            }
                        }

                    }
                }
            }
        }

        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                if(board[row][column] == 3){
                    board[row][column] = 0;
                }
            }
        }



        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                if(board[row][column] == 2){
                    board[row][column] = 4;
                }
            }
        }

        int loopCounter2 = 0;
        boolean loopIn2 = true;
        while (loopIn2 == true){
            loopIn2 = false;
            for(int row = 0; row < board.length; row++){
                for(int column = 0; column < board[row].length; column++){
                    if(board[row][column] == 4){

                        if(row>0){
                            if ((board[row-1][column] == 0)||(board[row-1][column] == 2)){
                                board[row][column] = 2;
                                loopIn2 = true;

                            }
                        }

                        if(row<board.length-1){
                            if((board[row+1][column] == 0)||(board[row+1][column] == 2)){
                                board[row][column] = 2;
                                loopIn2 = true;
                            }
                        }

                        if(column>0){
                            if ((board[row][column-1] == 0)||(board[row][column-1] == 2)){
                                board[row][column] = 2;
                                loopIn2 = true;
                            }
                        }

                        if(column<board[row].length -1){
                            if((board[row][column+1] == 0)||(board[row][column+1] == 2)){
                                board[row][column] = 2;
                                loopIn2 = true;
                            }
                        }

                    }
                }
            }
        }

        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                if(board[row][column] == 4){
                    board[row][column] = 0;
                }
            }
        }
    }

}
