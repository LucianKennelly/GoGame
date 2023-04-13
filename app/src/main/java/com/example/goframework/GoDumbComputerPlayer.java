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
        if (info instanceof GoGameState) {
            GoGameState state = (GoGameState) info;

            int xCoor = (int) (9 * Math.random());
            int yCoor = (int) (9 * Math.random());
            if (state.getGameBoard(xCoor, yCoor) != EMPTY) {
                GoSkipTurnAction toSend = new GoSkipTurnAction(this);
                game.sendAction(toSend);
            } else {
                sleep(0.001);
                GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
                game.sendAction(toSend);
            }
        }
        else {
            int xCoor = (int) (9 * Math.random());
            int yCoor = (int) (9 * Math.random());
            sleep(0.001);
            GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
            game.sendAction(toSend);
        }
    }
}
