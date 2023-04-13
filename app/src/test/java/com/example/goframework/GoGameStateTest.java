package com.example.goframework;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GoGameStateTest {
    GoGameState goGameState;

    @Before
    public void setUp() {
        goGameState = new GoGameState();
    }

    @Test
    public void setGameBoard() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                goGameState.setGameBoard(1, x, y);
                assertEquals(1, goGameState.getGameBoard()[x][y]);
            }
        }
    }

    @Test
    public void getGameBoard() {
        int[][] gameBoard = goGameState.getGameBoard();
        assertNotNull(gameBoard);
    }

    @Test
    public void _placeStone() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                boolean success = goGameState._placeStone(1, x, y, goGameState.getGameBoard());
                assertTrue(success);
            }
        }
    }
}
