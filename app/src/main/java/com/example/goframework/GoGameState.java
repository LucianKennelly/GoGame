package com.example.goframework;

import android.util.Log;

import com.example.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.ArrayList;

public class GoGameState extends GameState implements Serializable{
    private int playerToMove;
    private int whiteScore;
    private int blackScore;
    private int[][] gameBoard;
    private boolean gameContinueOne;
    private boolean gameContinueTwo;
    public int x;
    public int y;

    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERILL = -4;
    private int BLACK_IN_PERILL = -5;
    private int OUT = -6;


    ////////////////////////////////////////////////////
    private int turn;
    private ArrayList<Integer> blackCaptured;
    private ArrayList<Integer> whiteCaptured;
    private int timer;
    ////////////////////////////////////////////////////




    //constructor
    public GoGameState() {

        //initializing all the instance variables
        playerToMove = 0;
        whiteScore=0;
        blackScore=0;
        gameBoard = new int[9][9];
        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }
        gameContinueOne = true;
        gameContinueTwo = true;
        x=-1;
        y=-1;

        ////////////////////////////////////////////////////
        turn=0;
        blackCaptured = new ArrayList();
        whiteCaptured = new ArrayList();
        timer = 0;
        ////////////////////////////////////////////////////

    } //constructor


    //copy constructor
    public GoGameState(GoGameState original) {
        this.playerToMove= original.playerToMove;
        this.whiteScore = original.whiteScore;
        this.blackScore = original.blackScore;
        gameBoard = new int[9][9];
        for(int i = 0; i < this.gameBoard.length; i++){
            for(int j = 0; j < this.gameBoard[i].length; j++) {
                this.gameBoard[i][j] = original.gameBoard[i][j];
            }
        }
        this.gameContinueOne = original.gameContinueOne;
        this.gameContinueTwo = original.gameContinueTwo;
        this.x = original.x;
        this.y = original.y;


        ////////////////////////////////////////////////////
        this.turn = original.turn;
        this.blackCaptured = original.blackCaptured;
        this.whiteCaptured = original.whiteCaptured;
        this.timer = original.timer;
        ////////////////////////////////////////////////////
    }

    /*Getter Methods for all instance variables*/
    public int getGameBoard(int x, int y){
        if (gameBoard == null){
            return OUT;
        }

        else {
            if(x < 0 || x >= gameBoard.length || y < 0 || y >= gameBoard[x].length) {
                return OUT;
            }
            else {
                int to_return = gameBoard[x][y];
                return to_return;
            }
        }
    }
    public int getPlayerToMove() {
        return playerToMove;
    }
    public int getWhiteScore() {
        return whiteScore;
    }
    public int getBlackScore() {
        return blackScore;
    }
    public boolean getGameContinueOne() {
        return gameContinueOne;
    }
    public boolean getGameContinueTwo() {
        return gameContinueTwo;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }



    /*Setter Methods for all instance variables*/
    public void setGameBoard(int pieceColor, int x, int y) {
        if (gameBoard == null){
            return;
        }

        else {
            if(x < 0 || x >= gameBoard.length || y < 0 || y >= gameBoard[x].length) {
                return;
            }
            else {
                gameBoard[x][y] = pieceColor;
            }
        }

    }
    public void setPlayerToMove (int initPlayerToMove) {
        playerToMove = initPlayerToMove;
    }
    public void setWhiteScore(int initWhiteScore){
        whiteScore = initWhiteScore;
    }
    public void setBlackScore(int initBlackScore) {
        blackScore = initBlackScore;
    }
    public void setGameContinueOne(boolean initGameContinue) {
        gameContinueOne = initGameContinue;
    }
    public void setGameContinueTwo(boolean initGameContinue) {
        gameContinueTwo = initGameContinue;
    }
    public void setX(int initX) {
        x = initX;
    }
    public void setY(int initY) {
        y = initY;
    }


    /*
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
        return "whiteScore: " + whiteScore + "; blackScore: " + blackScore + "; turn: " + turn + "; timer: " + timer;
    }



    //method for player action
    public boolean _placeStone(int playerID, int x, int y, int[][] gameBoard) { // check if the move is legal
        if (x < 0 || x >= gameBoard.length || y < 0 || y >= gameBoard[0].length)
            return false;
        { //check if the space is empty
            if (gameBoard[x][y] != 0) {
                return false;
            }

            //check if the move is legal according to the game's rules
            if (!isLegalMove(playerID, x, y, gameBoard)) {
                return false;
            }

            //update the board with the new stone placement
            gameBoard[x][y] = playerID;
            return true;
        }
    }

    //helper method for player action
    private boolean isLegalMove(int playerID, int x, int y, int[][] gameBoard) { //check if the player is making a valid move
        if (gameBoard[x][y] != playerID) {
            return true;
        }
        return false;
    }

     */


}
