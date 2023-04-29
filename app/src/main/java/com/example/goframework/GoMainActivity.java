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

/**
 * class GoMainActivity
 *
 * This class initializes the game with the players and the player settings.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

public class GoMainActivity extends GameMainActivity {

    int localBoardSize = 0;


    /**
     * createDefaultCong
     * @return: GameConfig
     * This method returns the config of the game with all the players.
     */
    public GameConfig createDefaultConfig(){

        //creating an arrayList of players
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        //adding the human player
        playerTypes.add( new GamePlayerType("Human Player 1") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoHumanPlayer1 player = new GoHumanPlayer1("Human 1", R.layout.go_human_player1);
                return player;
            }

        });

        //adding the dumb AI
        playerTypes.add( new GamePlayerType("Dumb Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoDumbComputerPlayer player = new GoDumbComputerPlayer("Dumb AI");
                return player;
            }

        });

        //adding the smart AI
        playerTypes.add( new GamePlayerType("Smart Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                GoSmartComputerPlayer player = new GoSmartComputerPlayer("Smart AI");
                return player;
            }

        });

        //lastly adding all of them to the gameconfig and setting the default calues
        GameConfig gameConfig = new GameConfig(playerTypes,1,2,"GoTest", 2345);
        gameConfig.addPlayer("Human Player 1", 0);
        gameConfig.addPlayer("Dumb Computer Player", 1);
        gameConfig.addPlayer("Smart Computer Player", 1);
        return gameConfig;
    }


    /**
     * createLocalGame
     * @param: GameState gameState
     * This method initializes the board size to the one set by the user in the settings tab.
     */
    public LocalGame createLocalGame(GameState gameState) {
        Spinner spinner = findViewById(R.id.spinner);

        //checking if the size was initialized yet
        if(localBoardSize == 0) {
            localBoardSize = 9;
        }

        //when the spinner is not null then set the board size according to the options
        if (spinner != null) {
            String text = spinner.getSelectedItem().toString();

            if (text == "9X9 Board") {
                localBoardSize = 9;
            } else if (text == "13X13 Board") {
                localBoardSize = 13;
            } else if (text == "19X19 Board") {
                localBoardSize = 19;
            }
        }

        //checking if the gameState is null
        if(gameState == null ) {
            return new GoLocalGame(localBoardSize);
        }

        return new GoLocalGame((GoGameState) gameState);
    }

}