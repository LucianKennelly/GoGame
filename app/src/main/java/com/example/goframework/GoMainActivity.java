/**
 * GO BETA RELEASE
 * This release of GO allows for players to play against a dumb and smart AI.
 * Based on our testing, all of the features are working with no known bugs.
 * These include the remove stones method, place stones action, skip turn action,
 * score keeping, and determining the win for the game. Future steps include adding
 * in extra features such as changing the piece color, designing more interesting pieces,
 * implementing our own version of the game to determine a win, and improving the smart AI.
 *
 */


package com.example.goframework;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;

import java.util.ArrayList;

public class GoMainActivity extends GameMainActivity {
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
        playerTypes.add( new GamePlayerType("Smart Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoSmartComputerPlayer player = new GoSmartComputerPlayer("Smart AI");
                return player;
            }

        });
        GameConfig gameConfig = new GameConfig(playerTypes,1,2,"GoTest", 2345);
        gameConfig.addPlayer("Human Player 1", 0);
        gameConfig.addPlayer("Dumb Computer Player", 1);
        gameConfig.addPlayer("Smart Computer Player", 1);
        return gameConfig;
    }

    public LocalGame createLocalGame(GameState gameState) {
        Spinner spinner = findViewById(R.id.spinner);
        String text = spinner.getSelectedItem().toString();
        int boardSize = 9;
        if (text == "9X9 Board") {
            boardSize =9;
        }
        else if (text == "13X13 Board") {
            boardSize=13;
        }
        else if (text == "19X19 Board") {
            boardSize =19;
        }
        if(gameState == null ) {
            return new GoLocalGame(boardSize);
        }
        return new GoLocalGame((GoGameState) gameState);
    }

}