package com.example.goframework;

import android.graphics.Point;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
/**
 * class GoSmartComputerPlayer
 *
 * This class contains all the function for a computer player to make a more informed move. It tries
 * to capture the other player's pieces
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

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

    /**
     * receiveInfo
     * @param: GameInfo info
     * This method receives the game state in the form of a GameInfo
     * then makes moves based on it.
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        //if info is not an instance of the gamestate then return
        if (!(info instanceof GoGameState)) return;

        //casting to a gamestate and creating a copy
        GoGameState trueState = (GoGameState)info;
        state = new GoGameState(trueState);


        // sleep for a second to make any observers think that we're thinking
        sleep(0.000000001);
        //getting the best move
        Point bestMove = selectBestMove(state);

        //if the best move is not empty or its surrounded then get a random one
        while ((trueState.getGameBoard(bestMove.x,bestMove.y) != EMPTY) && surrounded(bestMove,trueState.getGameBoard())) {
            bestMove = getRandomMove();
        }

        //if the player has selected to skip then the Ai also skips
        if(!trueState.getGameContinueOne()) {
            GoSkipTurnAction toSend = new GoSkipTurnAction(this);
            game.sendAction(toSend);
        }

        //else send the move action
        else if (state.getPlayerToMove() == 1) {
            GoPlacePieceAction action = new GoPlacePieceAction(this, bestMove.x, bestMove.y);
            game.sendAction(action);
        }
    }

    /**
     * surrounded
     * @param: Point bestMove
     * @param: int[][] board
     * This method checks if the current point is surrounded by the opponents piece.
     */
    public boolean surrounded(Point bestMove, int[][] board) {

        //setting all to false
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;

        //checking to the left
        if (bestMove.x-1 > 0) {
            if (board[bestMove.x-1][bestMove.y] == WHITE) {
                left = true;
            }
        }
        else {
            left = true;
        }

        //checking to the right
        if (bestMove.x+1 < board.length) {
            if (board[bestMove.x+1][bestMove.y] == WHITE) {
                right = true;
            }
        }
        else {
            right = true;
        }

        //checking down
        if (bestMove.y-1 > 0) {
            if (board[bestMove.x][bestMove.y-1] == WHITE) {
                down = true;
            }
        }
        else {
            down = true;
        }

        //checking up
        if (bestMove.y+1 < board.length) {
            if (board[bestMove.x][bestMove.y+1] == WHITE) {
                up = true;
            }
        }
        else {
            up = true;
        }

        //return true only when the piece is not surrounded in all directions
        return left && right && down && up;
    }

    /**
     * getRandomMove
     * @return: Point
     * This method returns a random x and y coordinate pair
     */
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

    /**
     * selectBestMove
     * @param: GoGameState gameState
     * @return: Point
     * This method returns the best possible move. It attempts to get close to the player's pieces
     * and surround them to capture it.
     */
    public Point selectBestMove(GoGameState gameState) {
        Point bestMove = getRandomMove();
        int[][] board = gameState.getGameBoard();
        boolean loopIn = true;
        while (loopIn) {

            //setting all the white pieces to white_danger
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == WHITE) {
                    board[row][column] = WHITE_DANGER;
                }
            }
        }

        //now traversing the board to find adjacent cells
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == WHITE_DANGER) {

                    //best move is to the left
                    if (row > 0) {
                            if (board[row-1][column] == EMPTY) {
                                bestMove = new Point(row-1, column);
                            }
                    }

                    //best move is to the right
                    if (row < board.length - 1) {
                            if (board[row+1][column] == EMPTY) {
                                bestMove = new Point(row+1, column);
                            }
                    }

                    //best move is down
                    if (column > 0) {
                            if (board[row][column - 1] == EMPTY) {
                                bestMove = new Point(row, column-1);
                            }
                    }

                    //best move is above
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

        //continuing through the loop when scores change
        loopIn = (gameState.getBlackScore() < blackScore) || (gameState.getWhiteScore() > whiteScore);
        }

        //returning the best move
        return bestMove;
    }
    /**
     * removeCapturedStones
     * @param: GoGameState goGameState
     * This method removes all the captured stones from the 2d array board. The checkValidMove
     * and this method both use similar logic
     */
    public void removeCapturedStones(GoGameState goGameState) {

        //setting all the WHITE stones to WHITE_DANGER
        int[][] board = goGameState.getGameBoard();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == WHITE) {
                    board[row][column] = WHITE_DANGER;
                }
            }
        }

        //REMOVING ALL WHITE CAPTURED PIECES
        int loopCounter = 0;
        boolean loopIn = true;
        while (loopIn == true) {
            loopIn = false;

            //traversing the board...
            for (int row = 0; row < board.length; row++) {
                for (int column = 0; column < board[row].length; column++) {

                    //if the piece is WHITE...
                    if (board[row][column] == WHITE_DANGER) {

                        //the remaining if statements now check to see if an adjacent cell EMPTY or WHITE
                        //if it is EMPTY or WHITE then set the current position back to white and
                        //continue through the loop
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

        //traversing the board to find WHITE_DANGER pieces that still remain and setting them to empty
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

        //now repeating the same process with BLACK pieces
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