package com.example.goframework;

import android.util.Log;

import java.util.ArrayList;

public class GoGameState {

    private int whiteScore;
    private int blackScore;
    private int turn;
    private ArrayList<Integer> blackCaptured;
    private ArrayList<Integer> whiteCaptured;
    private int[][] gameBoard;
    private int timer;
    private boolean gameContinue;

    //constructor
    public GoGameState() {

        //initializing all the instance variables
        whiteScore=0;
        blackScore=0;
        turn=0;
        blackCaptured = new ArrayList();
        whiteCaptured = new ArrayList();
        gameBoard = new int[9][9];
        timer = 0;
        gameContinue = true;
    } //constructor

    //copy constructor
    public GoGameState(GoGameState secondObject) {
        this.whiteScore = secondObject.whiteScore;
        this.blackScore = secondObject.blackScore;
        this.turn = secondObject.turn;
        this.blackCaptured = secondObject.blackCaptured;
        this.whiteCaptured = secondObject.whiteCaptured;
        this.gameBoard = secondObject.gameBoard;;
        this.timer = secondObject.timer;
        this.gameContinue = secondObject.gameContinue;
    }


    @Override
    public String toString() {
        Log.d("toString()","White score: " + Integer.toString(whiteScore));
        Log.d("toString()", "Black score: " + Integer.toString(blackScore));
        Log.d("toString()", "Turn (0 white, 1 black): " +Integer.toString(turn));
        for(int i = 0; i < whiteCaptured.size(); i++) {
            Log.d("toString()","White captured: " + Integer.toString(whiteCaptured.get(i)));
        }
        for(int i = 0; i < blackCaptured.size(); i++) {
            Log.d("toString()", "Black captured: " + Integer.toString(blackCaptured.get(i)));
        }
        for (int i=0; i<9;i++) {
            for (int j=0;j<9;j++) {
                Log.d("toString()","Board (0 white, 1 black): " + Integer.toString(gameBoard[i][j]));
            }
        }
        Log.d("toString()", Integer.toString(timer));
        if (gameContinue) {
            Log.d("toString()", "Game in progress");
        }
        else {
            Log.d("toString()", "Game ended");
        }
        return "nothing";
    }
}
