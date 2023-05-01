package com.example.goframework;

import static java.lang.Thread.sleep;

import android.graphics.Point;
import android.util.Log;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.GameFramework.utilities.Logger;

import java.util.ArrayList;
/**
 * class GoLocalGame
 *
 * This class makes changes to the gamestate depending on what info it receives and acts a bit
 * as a controller.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

public class GoLocalGame extends LocalGame{

    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_DANGER = -4;
    private int BLACK_DANGER = -5;



    /**
     * Constructor
     * @param: int boardSize
     * This method creates a new game state object.
     */
    public GoLocalGame(int boardSize) {
        super();
        super.state = new GoGameState(boardSize);
    }


    /**
     * Constructor
     * @param: int boardSize
     * This method creates a new game state object using the copy constructor.
     */
    public GoLocalGame(GoGameState glg) {
        super();
        super.state = new GoGameState(glg);
    }


    /**
     * start
     * @param: GamePlayer[]
     * This method initializes the game with the given players
     */
    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    }



    /**
     * checkIfGameOver
     * @return : String
     * This checks if the winning conditions of both players pressing the skip button is met and
     * calculates the winner.
     */
    @Override
    protected String checkIfGameOver() {
        String win;
        GoGameState state = (GoGameState) super.state;

        Log.d("tag",state.toString());

        if (!state.getGameContinueOne() && !state.getGameContinueTwo() ) {
            if(state.getWhiteScore() > state.getBlackScore()) {
                win = " The white piece has won the game! ";
                return win;
            }
            else if (state.getWhiteScore() < state.getBlackScore()) {
                win = " The black piece has won the game! ";
                return win;
            }
            else if (state.getWhiteScore() == state.getBlackScore()) {
                win = " The game is tied! ";
                return win;
            }
        }
        return null;
    }


    /**
     * sendUpdatedStateTo
     * @param: GamePlayer p
     * This method sends the updated state.
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //removeCapturedStones(); //no longer needed

        p.sendInfo(new GoGameState((GoGameState) state));
    }


    /**
     * canMove
     * @param: int playerIdx
     * This method checks if the current playerIdx is equal to the same player to move in
     * the game state.
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((GoGameState) state).getPlayerToMove();
    }


    /**
     * checkValidMove
     * @param: GoGameState goGameState
     * @param: int xToPlace
     * @param: int yToPlace
     * @param: int playerIdNum
     * This method creates a new game state object.
    */
    public boolean checkValidMove(GoGameState goGameState, int xToPlace, int yToPlace, int playerIdNum) {

        //initializing a array list of points and coppying the board
        ArrayList<Point> invalidMoves = new ArrayList<>();
        int[][] copyBoard = new int[goGameState.boardSize][goGameState.boardSize];

        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[i].length; j++) {
                copyBoard[i][j] = goGameState.getGameBoard(i, j);
            }
        }

        //if the coordinates are out of bound then return false
        if(xToPlace < 0 || xToPlace >= goGameState.boardSize || yToPlace < 0 || yToPlace >= goGameState.boardSize) {
            return false;
        }

        //return false if the spot is not empty
        if (copyBoard[xToPlace][yToPlace] != EMPTY) {
            return false;
        }

        //if its player 0...
        if (playerIdNum == 0) {
            copyBoard[xToPlace][yToPlace] = WHITE;

            //setting all the WHITE pieces to WHITE_DANGER
            for (int row = 0; row < copyBoard.length; row++) {
                for (int column = 0; column < copyBoard[row].length; column++) {
                    if (copyBoard[row][column] == WHITE) {
                        copyBoard[row][column] = WHITE_DANGER;
                    }
                }
            }

            //REMOVING ALL WHITE CAPTURED PIECES
            boolean continueLoop = true;
            while (continueLoop == true) {
                continueLoop = false;

                //traversing the board...
                for (int xCoor = 0; xCoor < copyBoard.length; xCoor++) {
                    for (int yCoor = 0; yCoor < copyBoard[xCoor].length; yCoor++) {

                        //if the position is WHITE_DANGER...
                        if (copyBoard[xCoor][yCoor] == WHITE_DANGER) {

                            //the remaining if statements now check to see if an adjacent cell EMPTY or WHITE
                            //if it is EMPTY or WHITE then set the current position back to white and
                            //continue through the loop
                            if (xCoor > 0) {
                                if ((copyBoard[xCoor - 1][yCoor] == EMPTY) || (copyBoard[xCoor - 1][yCoor] == WHITE)) {
                                    copyBoard[xCoor][yCoor] = WHITE;
                                    continueLoop = true;

                                }
                            }

                            if (xCoor < copyBoard.length - 1) {
                                if ((copyBoard[xCoor + 1][yCoor] == EMPTY) || (copyBoard[xCoor + 1][yCoor] == WHITE)) {
                                    copyBoard[xCoor][yCoor] = WHITE;
                                    continueLoop = true;
                                }
                            }

                            if (yCoor > 0) {
                                if ((copyBoard[xCoor][yCoor - 1] == EMPTY) || (copyBoard[xCoor][yCoor - 1] == WHITE)) {
                                    copyBoard[xCoor][yCoor] = WHITE;
                                    continueLoop = true;
                                }
                            }

                            if (yCoor < copyBoard[xCoor].length - 1) {
                                if ((copyBoard[xCoor][yCoor + 1] == EMPTY) || (copyBoard[xCoor][yCoor + 1] == WHITE)) {
                                    copyBoard[xCoor][yCoor] = WHITE;
                                    continueLoop = true;
                                }
                            }
                        }
                    }
                }
            }

            //traversing the board to find WHITE_DANGER pieces that still remain and setting them to empty
            for (int xCoor = 0; xCoor < copyBoard.length; xCoor++) {
                for (int yCoor = 0; yCoor < copyBoard[xCoor].length; yCoor++) {
                    if (copyBoard[xCoor][yCoor] == WHITE_DANGER) {
                        invalidMoves.add(new Point(xCoor, yCoor));
                    }
                }
            }
            boolean flag = true;
            for (int i = 0; i < invalidMoves.size(); i ++) {
                Point temp = invalidMoves.get(i);
                int tempX = temp.x;
                int tempY = temp.y;

                if(tempX == xToPlace && tempY == yToPlace) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        else if (playerIdNum == 1) {
            copyBoard[xToPlace][yToPlace] = BLACK;
            //repeating the same process with BLACK pieces
            //setting all BLACK pieces to BLACK_DANGER
            for (int xCoor = 0; xCoor < copyBoard.length; xCoor++) {
                for (int yCoor = 0; yCoor < copyBoard[xCoor].length; yCoor++) {
                    if (copyBoard[xCoor][yCoor] == BLACK) {
                        copyBoard[xCoor][yCoor] = BLACK_DANGER;
                    }
                }
            }

            //REMOVING ALL WHITE CAPTURED PIECES
            boolean loopIn2 = true;
            while (loopIn2 == true) {
                loopIn2 = false;

                //traversing the board...
                for (int xCoor = 0; xCoor < copyBoard.length; xCoor++) {
                    for (int yCoor = 0; yCoor < copyBoard[xCoor].length; yCoor++) {

                        //if the position is BLACK_DANGER...
                        if (copyBoard[xCoor][yCoor] == BLACK_DANGER) {

                            //the remaining if statements now check to see if an adjacent cell EMPTY or BLACK
                            //if it is EMPTY or BLACK then set the current position back to BLACK and
                            //continue through the loop
                            if (xCoor > 0) {
                                if ((copyBoard[xCoor - 1][yCoor] == EMPTY) || (copyBoard[xCoor - 1][yCoor] == BLACK)) {
                                    copyBoard[xCoor][yCoor] = BLACK;
                                    loopIn2 = true;

                                }
                            }

                            if (xCoor < copyBoard.length - 1) {
                                if ((copyBoard[xCoor + 1][yCoor] == EMPTY) || (copyBoard[xCoor + 1][yCoor] == BLACK)) {
                                    copyBoard[xCoor][yCoor] = BLACK;
                                    loopIn2 = true;
                                }
                            }

                            if (yCoor > 0) {
                                if ((copyBoard[xCoor][yCoor - 1] == EMPTY) || (copyBoard[xCoor][yCoor - 1] == BLACK)) {
                                    copyBoard[xCoor][yCoor] = BLACK;
                                    loopIn2 = true;
                                }
                            }

                            if (yCoor < copyBoard[xCoor].length - 1) {
                                if ((copyBoard[xCoor][yCoor + 1] == EMPTY) || (copyBoard[xCoor][yCoor + 1] == BLACK)) {
                                    copyBoard[xCoor][yCoor] = BLACK;
                                    loopIn2 = true;
                                }
                            }

                        }
                    }
                }
            }

            //traversing the board to find BLACK_DANGER pieces that still remain and setting them to empty
            for (int xCoor = 0; xCoor < copyBoard.length; xCoor++) {
                for (int yCoor = 0; yCoor < copyBoard[xCoor].length; yCoor++) {
                    if (copyBoard[xCoor][yCoor] == BLACK_DANGER) {
                        invalidMoves.add(new Point(xCoor, yCoor));
                    }
                }
            }
            boolean flag = true;
            for (int i = 0; i < invalidMoves.size(); i ++) {
                Point temp = invalidMoves.get(i);
                int tempX = temp.x;
                int tempY = temp.y;

                if(tempX == xToPlace && tempY == yToPlace) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }
        return false;
    }



    /**
     * makeMove
     * @param: GameAction action
     * This method checks alters the game state depending on which action if detected.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        //first checking if the action is to place a piece
        if(action instanceof GoPlacePieceAction) {

            //getting the GoGameState and action
            GoPlacePieceAction gppa = (GoPlacePieceAction) action;
            GoGameState state = (GoGameState) super.state;

            //setting both the continue game variables to true
            state.setGameContinueOne(true);
            state.setGameContinueTwo(true);

            //getting the x and y coordinates
            int x = gppa.getX();
            int y = gppa.getY();

            //getting the current player's id
            int playerId = getPlayerIdx(gppa.getPlayer());

            //return false if the space is not empty
            if(checkValidMove(state, x, y, playerId) == false) {
             //   return false;
            }

            //else...
            else if (state.getGameBoard(x,y) == EMPTY) {

                //get
                int playerToMove = state.getPlayerToMove();

                if (playerId == 0) {
                    state.setGameBoard(WHITE, x, y);
                }
                else {
                    state.setGameBoard(BLACK, x, y);
                }
                removeCapturedStones(state);

                state.setPlayerToMove(1 - playerToMove);
                return true;
            }

        }
        else if(action instanceof GoSkipTurnAction) {
            GoGameState state = (GoGameState) super.state;
            Log.d("tag",state.toString());
            GoSkipTurnAction gsta = (GoSkipTurnAction)action;

            //getting the current player's id
            int playerId = getPlayerIdx(gsta.getPlayer());
            int playerToMove = state.getPlayerToMove();

            if (state.getGameContinueOne() == true) {
                state.setGameContinueOne(false);
            }
            else if (state.getGameContinueTwo() == true){
                state.setGameContinueTwo(false);
            }

            Logger.log("GoSkipTurnAction", "gameContinueOne "
                    + state.getGameContinueOne() + " gameContinueTwo "  + state.getGameContinueTwo());
            state.setPlayerToMove(1 - playerToMove);
            return true;
        }
        return false;
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
