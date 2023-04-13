package com.example.goframework;

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
    private int WHITE_IN_PERILL = -4;
    private int BLACK_IN_PERILL = -5;

    public GoLocalGame() {
        super();
        super.state = new GoGameState();
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
        GoGameState state = (GoGameState) super.state;
        String win;
        if(state.getGameContinueOne() == false && state.getGameContinueTwo() == false) {
            if(state.getWhiteScore() > state.getBlackScore()) {
                win = "The white piece has won the game!";
                return win;
            }
            else if (state.getWhiteScore() < state.getBlackScore()) {
                win = "The black piece has won the game!";
                return win;
            }
            else if (state.getWhiteScore() == state.getBlackScore()) {
                win = "The game is tied!";
                return win;
            }
        }
        return null;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
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

//        else if(action instanceof GoSkipTurnAction) {
//
//            return true;
//        }
//        else {
//            return false;
//        }
        return true;
    }
}
