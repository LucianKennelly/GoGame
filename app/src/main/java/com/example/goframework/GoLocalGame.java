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
        this.state = new GoGameState();
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
        GoPlacePieceAction gppa = (GoPlacePieceAction) action;
        GoGameState state = (GoGameState) super.state;

        int x = gppa.getX();
        int y = gppa.getY();

        int playerId = getPlayerIdx(gppa.getPlayer());

        if(state.getGameBoard(x,y) != EMPTY) {
            return false;
        }

        int playerToMove = state.getPlayerToMove();

        state.setGameBoard(WHITE, x, y);

        state.setPlayerToMove(1-playerToMove);
        return true;
    }
}
