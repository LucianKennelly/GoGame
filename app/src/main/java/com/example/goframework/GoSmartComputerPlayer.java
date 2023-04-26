package com.example.goframework;

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
        sleep(0.001);
        Point bestMove = selectBestMove(state);
        if (trueState.getGameBoard(bestMove.x,bestMove.y) != EMPTY) {
            game.sendAction(new GoSkipTurnAction(this));
        }
        else {
            game.sendAction(new GoPlacePieceAction(this, bestMove.x, bestMove.y));
        }
        return;
    }
    private Point getRandomMove() {
        // Implement a method that returns a random legal move for the current player
        Random r = new Random();
        int x = (int)(9*Math.random());
        int y = (int)(9*Math.random());
        return new Point(x,y);
    }

    //    /**
//     * Selects the best move for the AI player using Monte Carlo Tree Search.
//     *
//     * @param gameState The current game state
//     * @return The best move for the AI player
//     */
    public Point selectBestMove(GoGameState gameState) {
        Point bestMove = getRandomMove();
        int[][] board = gameState.getGameBoard();
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
                                bestMove = new Point(row-1,column);
                                board[row][column] = WHITE;
                                loopIn = true;

                            }
                        }

                        if (row < board.length - 1) {
                            if ((board[row + 1][column] == EMPTY) || (board[row + 1][column] == WHITE)) {
                                bestMove = new Point(row+1,column);
                                board[row][column] = WHITE;
                                loopIn = true;
                            }
                        }

                        if (column > 0) {
                            if ((board[row][column - 1] == EMPTY) || (board[row][column - 1] == WHITE)) {
                                bestMove = new Point(row,column-1);
                                board[row][column] = WHITE;
                                loopIn = true;
                            }
                        }

                        if (column < board[row].length - 1) {
                            if ((board[row][column + 1] == EMPTY) || (board[row][column + 1] == WHITE)) {
                                bestMove = new Point(row,column+1);
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
                    state.incrementBlackScore();
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
                    state.incrementWhiteScore();
                    board[row][column] = EMPTY;
                }
            }
        }
        return bestMove;
    }
}