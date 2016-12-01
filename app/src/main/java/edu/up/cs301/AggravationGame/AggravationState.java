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
        toRoll = true; //is this right????

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

        Log.i("changed player num", Integer.toString(playerTurn));
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
    public void setStartArray (int playerNum, int[] newHomeArray)
    {
        playerStart[playerNum] = newHomeArray;
    }

    //for all players
    public void setStartArray(int[][] newStartArray)
    {
        playerStart = newStartArray;
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
        playerHome[playerNum] = newHomeArray;

    }

    public void setHomeArray (int[][] newHomeArray)
    {
        playerHome = newHomeArray;

    }

    public int[] getGameBoard()
    {
        return (gameBoard);

    }

    public void setGameBoard(int[] newGameBoard)
    {
        gameBoard = newGameBoard;
    }

}
 //class AggravationState