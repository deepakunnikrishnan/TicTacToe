package com.androidnerds.tictactoe.game;

import com.androidnerds.tictactoe.game.model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class GameEngineTest {

    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";

    private GameEngine gameEngine;
    @Mock
    private GameEngine.GameStatusListener gameStatusListener;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.gameEngine = new GameEngine(gameStatusListener);
    }

    @After
    public void tearDown() throws Exception {
        this.gameEngine = null;
    }

    @Test
    public void testInit() {
        assertFalse(this.gameEngine.isGameOver());
        assertNull(this.gameEngine.getCurrentPlayer());
    }

    @Test
    public void testStartGame() throws Exception {
        this.gameEngine.init();
        this.gameEngine.startGame();
        Player currentPlayer = this.gameEngine.getCurrentPlayer();
        assertNotNull(currentPlayer);
        assertEquals(PLAYER_1, currentPlayer.getPlayer());

        ArgumentCaptor<GameEngine.GameStatus> gameStatusArgumentCaptor = ArgumentCaptor.forClass(GameEngine.GameStatus.class);
        Mockito.verify(this.gameStatusListener,Mockito.times(1)).onGameStatusChanged(gameStatusArgumentCaptor.capture());

        GameEngine.GameStatus gameStatus = gameStatusArgumentCaptor.getValue();
        assertEquals(GameEngine.GameStatus.NEXT_PLAYER_PLAYS, gameStatus);
    }

    @Test(expected = Exception.class)
    public void testStartGameWithoutInitShouldThrowException() throws Exception {
        this.gameEngine.startGame();
    }

    @Test
    public void testGetBoardSize() {
        assertEquals(4, this.gameEngine.getBoardSize());
    }

    @Test
    public void testGetCurrentPlayer() throws Exception {
        this.gameEngine.init();
        assertNull(this.gameEngine.getCurrentPlayer());

        this.gameEngine.startGame();
        assertNotNull(this.gameEngine.getCurrentPlayer());
        assertEquals(PLAYER_1,this.gameEngine.getCurrentPlayer().getPlayer());

    }

    @Test
    public void testOnPlayerSelected() throws Exception {
        this.gameEngine.init();
        this.gameEngine.startGame();
        this.gameEngine.onPlayerSelected(0,0);

        ArgumentCaptor<GameEngine.GameStatus> gameStatusArgumentCaptor = ArgumentCaptor.forClass(GameEngine.GameStatus.class);
        Mockito.verify(this.gameStatusListener,Mockito.times(2)).onGameStatusChanged(gameStatusArgumentCaptor.capture());

        GameEngine.GameStatus gameStatus = gameStatusArgumentCaptor.getValue();
        assertEquals(GameEngine.GameStatus.NEXT_PLAYER_PLAYS, gameStatus);
    }

    @Test(expected = Exception.class)
    public void testOnPlayerSelectedWithoutInitShouldThrowException() throws Exception {
        this.gameEngine.onPlayerSelected(0,0);
    }

    @Test
    public void testIsGameOver() throws Exception {
        this.gameEngine.init();
        this.gameEngine.startGame();
        assertFalse(this.gameEngine.isGameOver());

        //Player1-Move-1
        this.gameEngine.onPlayerSelected(0,0);
        //Player2-Move-1
        this.gameEngine.onPlayerSelected(0,1);

        //Player1-Move-2
        this.gameEngine.onPlayerSelected(1,0);
        //Player2-Move-2
        this.gameEngine.onPlayerSelected(0,2);

        //Player1-Move-3
        this.gameEngine.onPlayerSelected(2,0);
        //Player2-Move-3
        this.gameEngine.onPlayerSelected(0,3);

        //Player1-Move-4
        this.gameEngine.onPlayerSelected(3,0);

        ArgumentCaptor<GameEngine.GameStatus> gameStatusArgumentCaptor = ArgumentCaptor.forClass(GameEngine.GameStatus.class);
        Mockito.verify(this.gameStatusListener,Mockito.times(8)).onGameStatusChanged(gameStatusArgumentCaptor.capture());

        GameEngine.GameStatus gameStatus = gameStatusArgumentCaptor.getValue();
        assertEquals(GameEngine.GameStatus.PLAYER_1_WON, gameStatus);

        assertTrue(this.gameEngine.isGameOver());

    }
}