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
            }

            int xCoor = (int) (9 * Math.random());
            int yCoor = (int) (9 * Math.random());

            while (copyState.getGameBoard(xCoor, yCoor)!= EMPTY) {
                xCoor = (int) (9 * Math.random());
                yCoor = (int) (9 * Math.random());
            }

            sleep(0.001);
            GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
            game.sendAction(toSend);
        }
    }
}
