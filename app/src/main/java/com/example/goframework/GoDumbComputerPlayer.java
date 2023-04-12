package com.example.goframework;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

public class GoDumbComputerPlayer extends GameComputerPlayer {


    public GoDumbComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        int xCoor = (int)(9*Math.random());
        int yCoor = (int)(9*Math.random());

        GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
        game.sendAction(toSend);

    }


}
