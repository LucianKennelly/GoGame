package com.example.goframework;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GamePlayer;

public class GoLocalPlayer implements GamePlayer {

    @Override
    public void gameSetAsGui(GameMainActivity activity) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }

    @Override
    public void sendInfo(GameInfo info) {

    }

    @Override
    public void start() {

    }

    @Override
    public boolean requiresGui() {
        return false;
    }

    @Override
    public boolean supportsGui() {
        return false;
    }

    @Override
    public GameMainActivity getActivity() {
        return null;
    }
}
