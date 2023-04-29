package com.example.goframework;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

/**
 * class GoDumbComputerPlayer
 *
 * This class controls the dumb AI player which makes a move based on a random x and y coordinate.
 * If the human player presses the skip button then the dumb AI player also sends a skip action
 * which causes the game to stop.
 *
 * @author Lucian Kennelly, Connor Sisourath, Malissa Chen, Colin Miller
 * @date 28 April 2023
 */

public class GoDumbComputerPlayer extends GameComputerPlayer {
    private GoGameState state;
    private int EMPTY = -1;
    private int WHITE = -2;
    private int BLACK = -3;
    private int WHITE_DANGER = -4;
    private int BLACK_DANGER = -5;

    public GoDumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * receiveInfo
     * @param: GameInfo info
     * @return: void
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // if the info is not a GoGameState, return
        if (!(info instanceof GoGameState)) {
            return;
        }
        else {
            sleep(0.5);
            GoGameState copyState = (GoGameState) info;

            // if the game is over for player 1, skip turn
            if(copyState.getGameContinueOne() == false && copyState.getGameContinueTwo() == true ) {
                GoSkipTurnAction toSend = new GoSkipTurnAction(this);
                game.sendAction(toSend);
                return;
            }
            else if (copyState.getGameContinueOne() == true && copyState.getGameContinueTwo() == false ) {
                GoSkipTurnAction toSend = new GoSkipTurnAction(this);
                game.sendAction(toSend);
                return;
            }
            // otherwise, make a random move
            else {
                int xCoor = (int) (copyState.boardSize * Math.random());
                int yCoor = (int) (copyState.boardSize * Math.random());

                // keep generating random moves until one is found that is legal
                while (copyState.getGameBoard(xCoor, yCoor) != EMPTY) {
                    xCoor = (int) (copyState.boardSize * Math.random());
                    yCoor = (int) (copyState.boardSize * Math.random());
                }

                // sleep(0.001);
                // create and send a GoPlacePieceAction for the random move
                GoPlacePieceAction toSend = new GoPlacePieceAction(this, xCoor, yCoor);
                game.sendAction(toSend);
            }
        }
    }
}
