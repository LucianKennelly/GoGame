package com.example.goframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.infoMessage.GameState;

public class MainActivity extends GameMainActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
    public GameConfig createDefaultConfig(){
        return null;
    }

    public LocalGame createLocalGame(GameState gameState) {
        return null;
    }
}