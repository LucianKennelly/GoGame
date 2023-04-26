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
    public int boardSize;

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
    public GoGameState(int boardSize) {

        //initializing all the instance variables
        this.boardSize = boardSize;
        playerToMove = 0;
        whiteScore=0;
        blackScore=0;
        gameBoard = new int[boardSize][boardSize];
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
        this.boardSize = original.boardSize;
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
    public int[][] getGameBoard(){
        if (gameBoard == null){
            return null;
        }
        else {
            return gameBoard;
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
    public void incrementWhiteScore(){
        whiteScore++;
    }
    public void incrementBlackScore() {
        blackScore++;
    }
    public void deincrementWhiteScore(){
        whiteScore--;
    }
    public void deincrementBlackScore() {
        blackScore--;
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



    @Override
    public String toString() {
        return "whiteScore: " + whiteScore + "; blackScore: " + blackScore + "; turn: " + turn + "; timer: " + timer;
    }
}
