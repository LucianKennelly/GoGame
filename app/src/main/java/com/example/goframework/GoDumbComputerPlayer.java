package com.example.goframework;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

public class GoDumbComputerPlayer extends GameComputerPlayer {
    private GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_IN_PERIL = -4;
    private int BLACK_IN_PERIL = -5;

    public GoDumbComputerPlayer(String name) {
        super(name);
    }

    // method to receive updates from the game
    @Override
    protected void receiveInfo(GameInfo info) {

        // if the info is not a GoGameState, return
        if (!(info instanceof GoGameState)) {
            return;
        }
        else {

            GoGameState copyState = (GoGameState) info;

            // if the game is over for player 1, skip turn
            if(copyState.getGameContinueOne() == false) {
                GoSkipTurnAction toSend = new GoSkipTurnAction(this);
                game.sendAction(toSend);
                return;
            }
            // otherwise, make a random move
            else {
                int xCoor = (int) (copyState.boardSize * Math.random());
                int yCoor = (int) (copyState.boardSize * Math.random());

                // keep generating random moves until one is found that is legal
                while (copyState.getGameBoard(xCoor, yCoor) != EMPTY) {
                    xCoor = (int) (copyState.boardSize * Math.random());
                    yCoor = (int) (copyState.boardSize * Math.random());
                }

                // sleep(0.001);
                // create and send a GoPlacePieceAction for the random move
                GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
                game.sendAction(toSend);
            }
        }
    }
}
