package com.example.goframework;

import android.util.Log;
import android.view.SurfaceView;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;

public class GoLocalGame extends LocalGame{

    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERIL = -4;
    private int BLACK_IN_PERIL = -5;
    private GoGameState goGameState;

    public GoLocalGame() {
        super();
        super.state = new GoGameState();
        goGameState = new GoGameState((GoGameState)super.state);
    }

    public GoLocalGame(GoGameState glg) {
        super();
        super.state = new GoGameState(glg);
    }

    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    }


    @Override
    protected String checkIfGameOver() {
        //GoGameState state = new GoGameState((GoGameState) super.state);
        String win;
        Log.d("tag",goGameState.toString());
        if(goGameState.getGameContinueOne() == false && goGameState.getGameContinueTwo() == false) {
            if(goGameState.getWhiteScore() > goGameState.getBlackScore()) {
                win = "The white piece has won the game!";
                return win;
            }
            else if (goGameState.getWhiteScore() < goGameState.getBlackScore()) {
                win = "The black piece has won the game!";
                return win;
            }
            else if (goGameState.getWhiteScore() == goGameState.getBlackScore()) {
                win = "The game is tied!";
                return win;
            }
        }
        return null;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        removeCapturedStones();
        p.sendInfo(new GoGameState((GoGameState) state));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((GoGameState) state).getPlayerToMove();
    }

    @Override
    protected boolean makeMove(GameAction action) {

        //first checking if the action is to place a piece
        if(action instanceof GoPlacePieceAction) {

            //getting the GoGameState and action
            GoPlacePieceAction gppa = (GoPlacePieceAction) action;
            GoGameState state = (GoGameState) super.state;

            //getting the x and y coordinates
            int x = gppa.getX();
            int y = gppa.getY();

            //getting the current player's id
            int playerId = getPlayerIdx(gppa.getPlayer());

            //return false if the space is not empty
            if(state.getGameBoard(x,y) != EMPTY) {
                return false;
            }

            //else...
            else {

                removeCapturedStones();
                //get
                int playerToMove = state.getPlayerToMove();

                if (playerId == 0) {
                    state.setGameBoard(WHITE, x, y);
                }
                else {
                    state.setGameBoard(BLACK, x, y);
                }
                state.setPlayerToMove(1 - playerToMove);
                return true;
            }

        }
        else if(action instanceof GoSkipTurnAction) {
            GoGameState state = (GoGameState) super.state;
            Log.d("tag",state.toString());
            GoSkipTurnAction gameAction = (GoSkipTurnAction)action;
            if (gameAction.getPlayer() instanceof GoHumanPlayer1) {
                state.setGameContinueOne(false);
                state.setGameContinueTwo(false);
                checkIfGameOver();
                return true;
            }
            if (gameAction.getPlayer() instanceof GoDumbComputerPlayer) {
                state.setGameContinueTwo(false);
                checkIfGameOver();
                return true;
            }
            if (gameAction.getPlayer() instanceof GoSmartComputerPlayer) {
                state.setGameContinueTwo(false);
                checkIfGameOver();
                return true;
            }
            if (state.getGameContinueOne()||state.getGameContinueTwo()) {
                state.setGameContinueOne(true);
                state.setGameContinueTwo(true);
                checkIfGameOver();
                return true;
            }
            return true;
        }
        else {
            return false;
        }
    }

    public void removeCapturedStones() {
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
                    goGameState.deincrementWhiteScore();
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
                    goGameState.deincrementBlackScore();
                    goGameState.incrementWhiteScore();
                    board[row][column] = EMPTY;
                }
            }
        }
    }
}
