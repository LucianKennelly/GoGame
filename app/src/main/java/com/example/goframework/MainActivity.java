package com.example.goframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.infoMessage.GameState;

public class MainActivity extends GameMainActivity {

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the runTestTextView
        TextView runTestTextView = findViewById(R.id.runTestTextView);

        //finding the runTestButton and setting an onClickListener for it
        runButton = findViewById(R.id.runTestButton);

    }
    public GameConfig createDefaultConfig(){
        return null;
    }

    public LocalGame createLocalGame(GameState gameState) {
        return null;
    }
}