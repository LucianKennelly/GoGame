package com.example.goframework;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;

import java.util.ArrayList;

public class GoMainActivity extends GameMainActivity {
/*
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the runTestTextView
        TextView runTestTextView = findViewById(R.id.runTestTextView);

        //finding the runTestButton and setting an onClickListener for it
        runButton = findViewById(R.id.runTestButton);

    }*/
    public GameConfig createDefaultConfig(){
        ArrayList<GamePlayerType> array = new ArrayList<>();
        array.add( new GamePlayerType("Local Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoLocalPlayer player = new GoLocalPlayer("Player 1",R.layout.activity_main);
                return player;
            }

        });
        GameConfig gameConfig = new GameConfig(array,1,2,"GoTest", 2345);
        gameConfig.addPlayer("Human Player 1", 0);
        GoSurfaceView surfaceView = new GoSurfaceView(this);
        surfaceView.invalidate();
        return gameConfig;
    }

    public LocalGame createLocalGame(GameState gameState) {
        return new GoLocalGame();
    }

}