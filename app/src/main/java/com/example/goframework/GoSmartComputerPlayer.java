package com.example.goframework;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class GoSmartComputerPlayer extends GameComputerPlayer {
    private GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERIL = -4;
    private int BLACK_IN_PERIL = -5;
    public GoSmartComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof GoGameState)) return;
        GoGameState trueState = (GoGameState)info;
        state = new GoGameState(trueState);


        // sleep for a second to make any observers think that we're thinking
        sleep(0.000000001);
        Point bestMove = selectBestMove(state);
        while ((trueState.getGameBoard(bestMove.x,bestMove.y) != EMPTY) && surrounded(bestMove,trueState.getGameBoard())) {
            bestMove = getRandomMove();
        }
        if(!trueState.getGameContinueOne()) {
            GoSkipTurnAction toSend = new GoSkipTurnAction(this);
            game.sendAction(toSend);
        }
        else if (state.getPlayerToMove() == 1) {
            GoPlacePieceAction action = new GoPlacePieceAction(this, bestMove.x, bestMove.y);
            game.sendAction(action);
        }
    }
    public boolean surrounded(Point bestMove, int[][] board) {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if (bestMove.x-1 > 0) {
            if (board[bestMove.x-1][bestMove.y] == WHITE) {
                left = true;
            }
        }
        else {
            left = true;
        }
        if (bestMove.x+1 < board.length) {
            if (board[bestMove.x+1][bestMove.y] == WHITE) {
                right = true;
            }
        }
        else {
            right = true;
        }
        if (bestMove.y-1 > 0) {
            if (board[bestMove.x][bestMove.y-1] == WHITE) {
                down = true;
            }
        }
        else {
            down = true;
        }
        if (bestMove.y+1 < board.length) {
            if (board[bestMove.x][bestMove.y+1] == WHITE) {
                up = true;
            }
        }
        else {
            up = true;
        }
        return left && right && down && up;
    }
    private Point getRandomMove() {
        // Implement a method that returns a random legal move for the current player
        int x = (int)(state.boardSize*Math.random());
        int y = (int)(state.boardSize*Math.random());
        return new Point(x,y);
    }

    //    /**
//     * Selects the best move for the AI player using the current board
//     *
//     * @param gameState The current game state
//     * @return The best move for the AI player
//     */
    public Point selectBestMove(GoGameState gameState) {
        Point bestMove = getRandomMove();
        int[][] board = gameState.getGameBoard();
        boolean loopIn = true;
        while (loopIn) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == WHITE) {
                    board[row][column] = WHITE_IN_PERIL;
                }
            }
        }
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == WHITE_IN_PERIL) {
                    if (row > 0) {
                            if (board[row-1][column] == EMPTY) {
                                bestMove = new Point(row-1, column);
                            }
                    }

                    if (row < board.length - 1) {
                            if (board[row+1][column] == EMPTY) {
                                bestMove = new Point(row+1, column);
                            }
                    }

                    if (column > 0) {
                            if (board[row][column - 1] == EMPTY) {
                                bestMove = new Point(row, column-1);
                            }
                    }

                    if (column < board[row].length - 1) {
                            if (board[row][column + 1] == EMPTY) {
                                bestMove = new Point(row, column+ 1);
                            }
                    }

                }
            }
        }
        gameState.setGameBoard(1,bestMove.x,bestMove.y);
        int blackScore = gameState.getBlackScore();
        int whiteScore = gameState.getWhiteScore();
        removeCapturedStones(gameState);
        loopIn = (gameState.getBlackScore() < blackScore) || (gameState.getWhiteScore() > whiteScore);
        }
        return bestMove;
    }
    public void removeCapturedStones(GoGameState goGameState) {
        int[][] board = goGameState.getGameBoard();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == WHITE) {
                    board[row][column] = WHITE_IN_PERIL;
                }
            }
        }

        //part 3
        int loopCounter = 0;
        boolean loopIn = true;
        while (loopIn == true) {
            loopIn = false;
            for (int row = 0; row < board.length; row++) {
                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] == WHITE_IN_PERIL) {
                        if (row > 0) {
                            if ((board[row - 1][column] == EMPTY) || (board[row - 1][column] == WHITE)) {
                                board[row][column] = WHITE;
                                loopIn = true;

                            }
                        }

                        if (row < board.length - 1) {
                            if ((board[row + 1][column] == EMPTY) || (board[row + 1][column] == WHITE)) {
                                board[row][column] = WHITE;
                                loopIn = true;
                            }
                        }

                        if (column > 0) {
                            if ((board[row][column - 1] == EMPTY) || (board[row][column - 1] == WHITE)) {
                                board[row][column] = WHITE;
                                loopIn = true;
                            }
                        }

                        if (column < board[row].length - 1) {
                            if ((board[row][column + 1] == EMPTY) || (board[row][column + 1] == WHITE)) {
                                board[row][column] = WHITE;
                                loopIn = true;
                            }
                        }
                    }
                }
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == WHITE_IN_PERIL) {
                    goGameState.incrementBlackScore();
                    board[row][column] = EMPTY;
                }
            }
        }


        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == BLACK) {
                    board[row][column] = BLACK_IN_PERIL;
                }
            }
        }

        int loopCounter2 = 0;
        boolean loopIn2 = true;
        while (loopIn2 == true) {
            loopIn2 = false;
            for (int row = 0; row < board.length; row++) {
                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] == BLACK_IN_PERIL) {

                        if (row > 0) {
                            if ((board[row - 1][column] == EMPTY) || (board[row - 1][column] == BLACK)) {
                                board[row][column] = BLACK;
                                loopIn2 = true;

                            }
                        }

                        if (row < board.length - 1) {
                            if ((board[row + 1][column] == EMPTY) || (board[row + 1][column] == BLACK)) {
                                board[row][column] = BLACK;
                                loopIn2 = true;
                            }
                        }

                        if (column > 0) {
                            if ((board[row][column - 1] == EMPTY) || (board[row][column - 1] == BLACK)) {
                                board[row][column] = BLACK;
                                loopIn2 = true;
                            }
                        }

                        if (column < board[row].length - 1) {
                            if ((board[row][column + 1] == EMPTY) || (board[row][column + 1] == BLACK)) {
                                board[row][column] = BLACK;
                                loopIn2 = true;
                            }
                        }

                    }
                }
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == BLACK_IN_PERIL) {
                    goGameState.incrementWhiteScore();
                    board[row][column] = EMPTY;
                }
            }
        }
    }
}