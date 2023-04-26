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

    @Override
    protected void receiveInfo(GameInfo info) {

        if (!(info instanceof GoGameState)) {
            return;
        }
        else {

            GoGameState copyState = (GoGameState) info;

            if(copyState.getGameContinueOne() == false) {
                GoSkipTurnAction toSend = new GoSkipTurnAction(this);
                game.sendAction(toSend);
                return;
            }
            else {

                int xCoor = (int) (copyState.boardSize * Math.random());
                int yCoor = (int) (copyState.boardSize * Math.random());

                while (copyState.getGameBoard(xCoor, yCoor) != EMPTY) {
                    xCoor = (int) (copyState.boardSize * Math.random());
                    yCoor = (int) (copyState.boardSize * Math.random());
                }

                //sleep(0.001);
                GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
                game.sendAction(toSend);
            }
        }
    }
}
