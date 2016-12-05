package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.io.Serializable;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Class which defines the "state" of the game
 * Contains all necessary information to play Aggravation Game
 *
 * @authors Emily Peterson, Andrew Ripple & Owen Price
 *
 * @version November 2016
 *
 */
public class AggravationState extends GameState implements Serializable {

    private static final long serialVersionUID = -5109179065433136954L;
    private int playerTurn;
    private int dieValue;
    private boolean toRoll;
    private int[][] playerStart = new int [4][4];
    private int[][] playerHome = new int [4][4];
    private int[] gameBoard = new int[57];



    public AggravationState()
    {
        super();
        //initializes values
        playerTurn = 0;
        toRoll = true;

        //i is player number
        for (int i = 0; i<4; i++) //assuming 4 players right now
        {
            for (int j= 0; j<4; j++) //the space number
            {
              playerStart[i][j] = i;//start with full start array
              playerHome[i][j] = -1;    //homes is empty
            }
        }
        //sets gameboard to empty
        for (int k = 0; k<57; k++) //ASSUMING 57 SQUARES-- might be wrong
        {
            gameBoard[k] = -1;
        }

    }

    public AggravationState(AggravationState toCopy)
    {
        super();
        //copies all values from gameState
        this.setTurn(toCopy.getTurn());
        this.setDieValue(toCopy.getDieValue());
        this.setRoll(toCopy.getRoll());
        this.setGameBoard(toCopy.getGameBoard());
        this.setHomeArray(toCopy.getHomeArray());
        this.setStartArray(toCopy.getStartArray());





    }

   public void setRoll(boolean canRoll)
   {
       toRoll = canRoll;
   }

    public boolean getRoll()
    {
        return toRoll;
    }

    public void setTurn(int playerNum)
    {
            playerTurn = playerNum;
    }

    public int getTurn()
    {

        return playerTurn;
    }



    public void setDieValue(int value)
    {
        dieValue = value;

    }

    public int getDieValue()
    {
        return dieValue;
    }

    //for specific player
    public int[] getStartArray(int playerNum)
    {

        return (playerStart[playerNum]);

    }

    //for all players
    public int[][] getStartArray()
    {
        return playerStart;
    }

    //for specific player
    public void setStartArray (int playerNum, int[] newStartArray)
    {
        for (int i = 0; i<4; i++){
            playerStart[playerNum][i] = newStartArray[i];}

    }

    //for all players
    public void setStartArray(int[][] newStartArray)
    {
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                playerStart[i][j] = newStartArray[i][j];}}
    }


    public int[] getHomeArray(int playerNum)
    {
        return (playerHome[playerNum]);
    }

    public int[][] getHomeArray()
    {
        return (playerHome);
    }

    public void setHomeArray (int playerNum, int[] newHomeArray)
    {

        for (int i = 0; i<4; i++){
            playerHome[playerNum][i] = newHomeArray[i];}


    }

    public void setHomeArray (int[][] newHomeArray)
    {
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
            playerHome[i][j] = newHomeArray[i][j];}}

    }

    public int[] getGameBoard()
    {
        return (gameBoard);

    }

    public void setGameBoard(int[] newGameBoard)
    {
        for (int i = 0; i<57; i++){
            gameBoard[i] = newGameBoard[i];
        }
    }

}
 //class AggravationState