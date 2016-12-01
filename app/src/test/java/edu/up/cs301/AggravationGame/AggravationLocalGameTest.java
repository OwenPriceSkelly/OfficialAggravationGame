package edu.up.cs301.AggravationGame;

import org.junit.Test;

import edu.up.cs301.game.LocalGame;

import static org.junit.Assert.*;

/**
 * Created by priceo19 on 11/9/2016.
 */
public class AggravationLocalGameTest {

    @Test
    public void testCanMove() throws Exception
    {
        AggravationState tempState = new AggravationState();
        AggravationLocalGame tempLocal = new AggravationLocalGame();
        int whoUp = tempState.getTurn();
        AggravationHumanPlayer stickFigure = new AggravationHumanPlayer("Owen");
        assertEquals(whoUp,0);
    }

    @Test
    public void testMakeMove() throws Exception


    {
        AggravationState tempState = new AggravationState();
        AggravationHumanPlayer stickFigure = new AggravationHumanPlayer("Owen");
        AggravationLocalGame testLocal = new AggravationLocalGame();
        tempState.setTurn(stickFigure.getPlayerNum());
        tempState.setDieValue(6);
        int[] tempBoard = tempState.getGameBoard();
        tempBoard[5] = 3;
        tempState.setGameBoard(tempBoard);
        AggravationMovePieceAction testAction = new AggravationMovePieceAction(stickFigure,"board", 5,11);
        assertTrue(testLocal.makeMove(testAction));

        AggravationMovePieceAction testAction2 = new AggravationMovePieceAction(stickFigure, "board", 5,10);
        assertFalse(testLocal.makeMove(testAction2));

        tempBoard[6] = 3;

        AggravationMovePieceAction testAction3 = new AggravationMovePieceAction(stickFigure, "board", 5,11);
        assertFalse(testLocal.makeMove(testAction3));

        int[] tempStartArray= tempState.getStartArray(stickFigure.getPlayerNum());

        AggravationMovePieceAction testAction4 = new AggravationMovePieceAction(
                stickFigure,"start",0,stickFigure.getPlayerNum()*14);
        assertTrue(testLocal.makeMove(testAction4));

        tempStartArray[stickFigure.getPlayerNum()*14]=stickFigure.getPlayerNum();

        AggravationMovePieceAction testAction5 = new AggravationMovePieceAction(
                stickFigure,"start",1,stickFigure.getPlayerNum()*14);
        assertFalse(testLocal.makeMove(testAction5));






    }

    @Test
    public void testSendUpdatedStateTo() throws Exception
    {

    }

    @Test
    public void testCheckIfGameOver() throws Exception
    {

    }
}