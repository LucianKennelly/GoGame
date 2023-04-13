package com.example.goframework;

import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;

public class GoHumanPlayer2 extends GameHumanPlayer implements View.OnClickListener {
    public GoHumanPlayer2(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
