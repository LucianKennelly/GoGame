package com.example.goframework;

import android.graphics.Point;
import android.util.Log;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

public class GoSmartComputerPlayer extends GameComputerPlayer {
    private GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_DANGER = -4;
    private int BLACK_DANGER = -5;

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
        while ((trueState.getGameBoard(bestMove.x,bestMove.y) != EMPTY) && surrounded(bestMove,trueState.getGameBoard(), state)) {
            bestMove = getRandomMove();
        }

        if(trueState.getGameContinueOne() == false || trueState.getGameContinueTwo() == false) {
            GoSkipTurnAction toSend = new GoSkipTurnAction(this);
            game.sendAction(toSend);
        }
        else {
            GoPlacePieceAction action = new GoPlacePieceAction(this, bestMove.x, bestMove.y);
            game.sendAction(action);
        }
    }
    public boolean surrounded(Point bestMove, int[][] board, GoGameState gameState) {
        int OPPONENT;
        if (gameState.getPlayerToMove() == 0) {
            OPPONENT = BLACK;
        }
        else {
            OPPONENT = WHITE;
        }

        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if (bestMove.x-1 > 0) {
            if (board[bestMove.x-1][bestMove.y] == OPPONENT) {
                left = true;
            }
        }
        else {
            left = true;
        }
        if (bestMove.x+1 < board.length) {
            if (board[bestMove.x+1][bestMove.y] == OPPONENT) {
                right = true;
            }
        }
        else {
            right = true;
        }
        if (bestMove.y-1 > 0) {
            if (board[bestMove.x][bestMove.y-1] == OPPONENT) {
                down = true;
            }
        }
        else {
            down = true;
        }
        if (bestMove.y+1 < board.length) {
            if (board[bestMove.x][bestMove.y+1] == OPPONENT) {
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
        int OPPONENT;
        int OPPONENT_DANGER;
        if (gameState.getPlayerToMove() == 0) {
            OPPONENT = BLACK;
            OPPONENT_DANGER = BLACK_DANGER;
        }
        else {
            OPPONENT = WHITE;
            OPPONENT_DANGER = WHITE_DANGER;
        }
        Point bestMove = getRandomMove();
        int[][] board = gameState.getGameBoard();
        boolean loopIn = true;
        while (loopIn) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == OPPONENT) {
                    board[row][column] = OPPONENT_DANGER;
                }
            }
        }
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == OPPONENT_DANGER) {
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
        if (gameState.getPlayerToMove() == 0) {
            gameState.setGameBoard(WHITE,bestMove.x,bestMove.y);
        }
        else {
            gameState.setGameBoard(BLACK,bestMove.x,bestMove.y);
        }
            //gameState.setGameBoard(1,bestMove.x,bestMove.y);
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
                    board[row][column] = WHITE_DANGER;
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
                    if (board[row][column] == WHITE_DANGER) {
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
                if (board[row][column] == WHITE_DANGER) {
                    goGameState.incrementBlackScore();
                    board[row][column] = EMPTY;
                }
            }
        }


        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == BLACK) {
                    board[row][column] = BLACK_DANGER;
                }
            }
        }

        int loopCounter2 = 0;
        boolean loopIn2 = true;
        while (loopIn2 == true) {
            loopIn2 = false;
            for (int row = 0; row < board.length; row++) {
                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] == BLACK_DANGER) {

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
                if (board[row][column] == BLACK_DANGER) {
                    goGameState.incrementWhiteScore();
                    board[row][column] = EMPTY;
                }
            }
        }
    }
}