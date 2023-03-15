package com.example.goframework;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;

import java.util.ArrayList;

public class GoMainActivity extends GameMainActivity {

    /*@Override
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
        GamePlayerType playerType = new GamePlayerType("Local Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoLocalPlayer player = new GoLocalPlayer();
                return player;
            }
        };
        array.add(playerType);
        GameConfig gameConfig = new GameConfig(array,1,2,"GoTest", 2345);
        return gameConfig;
    }

    public LocalGame createLocalGame(GameState gameState) {
        return new GoLocalGame();
    }
}