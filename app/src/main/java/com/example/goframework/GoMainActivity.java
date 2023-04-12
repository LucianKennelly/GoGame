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
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();
        playerTypes.add( new GamePlayerType("Human Player 1") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoHumanPlayer1 player = new GoHumanPlayer1("Human 1", R.layout.go_human_player1);
                return player;
            }

        });
        playerTypes.add( new GamePlayerType("Dumb Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoDumbComputerPlayer player = new GoDumbComputerPlayer("Dumb AI");
                return player;
            }

        });
        GameConfig gameConfig = new GameConfig(playerTypes,1,2,"GoTest", 2345);
        gameConfig.addPlayer("Human Player 1", 0);
        gameConfig.addPlayer("Dumb Computer Player", 1);
        GoSurfaceView surfaceView = new GoSurfaceView(this);
        surfaceView.invalidate();
        return gameConfig;
    }

    public LocalGame createLocalGame(GameState gameState) {
        return new GoLocalGame();
    }

}