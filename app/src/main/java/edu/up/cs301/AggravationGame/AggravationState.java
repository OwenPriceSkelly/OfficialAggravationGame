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


    private static final long serialVersionUID = -5109179065433136954L; //network play
    private int playerTurn;
    private int dieValue;
    private boolean toRoll;
    private int[][] playerStart = new int [4][4];
    private int[][] playerHome = new int [4][4];
    private int[] gameBoard = new int[57];

/*
    constructor for state which sets an empty gameboard with all pieces in their home arrays
 */

    public AggravationState()
    {
        super();
        //initializes values
        playerTurn = 0;
        toRoll = true;
        dieValue = 6;

        //i is player number
        for (int i = 0; i<4; i++)
        {
            for (int j= 0; j<4; j++) //the space number
            {
              playerStart[i][j] = i;//start with full start array
              playerHome[i][j] = -1;    //homes is empty
            }
        }
        //sets gameboard to empty
        for (int k = 0; k<57; k++)
        {
            gameBoard[k] = -1;
        }

    }

    /*
        AggravationState
        Will take in a state copy from local game, and update values in the official game based on those new values
     */
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

    /*
        setRoll -- sets the toRoll boolean value to the given canRoll input arg
     */
   public void setRoll(boolean canRoll)
   {
       toRoll = canRoll;
   }

    /*
        getRoll -- returns toRoll boolean from official game state (this)
     */
    public boolean getRoll()
    {
        return toRoll;
    }

    /*
        setTurn -- sets the official state playerTurn to the given playerNum
     */
    public void setTurn(int playerNum)
    {
            playerTurn = playerNum;
    }

    /*
        getTurn -- returns the playerTurn
     */
    public int getTurn() { return playerTurn;}

    /*
       setDieValue -- sets the official state dieVale to given int
     */
    public void setDieValue(int value) {dieValue = value;}

    /*
       getDieValue -- returns the dieValue int
     */
    public int getDieValue()
    {
        return dieValue;
    }

    /*
      getStartArray -- returns start array for given player
      !This method takes in the playerNum and returns a 1-d start array for that player!
    */
    public int[] getStartArray(int playerNum) { return (playerStart[playerNum]);}

    /*
      getStartArray -- returns start array for ALL players (overloaded)
      !This method returns the 2-d start array for ALL players!
    */
    public int[][] getStartArray()
    {
        return playerStart;
    }

    /*
        setStartArray -- sets the official start array of given player to the given array
        !This method takes in the playerNum & array and sets the new array ONLY for that player!

     */
    public void setStartArray (int playerNum, int[] newStartArray)
    {
        for (int i = 0; i<4; i++){
            playerStart[playerNum][i] = newStartArray[i];}

    }

    /*
        setStartArray -- sets the official start arrays to the given 2-d start array
        !This method takes one 2-d array and sets for ALL players!
     */
    public void setStartArray(int[][] newStartArray)
    {
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                playerStart[i][j] = newStartArray[i][j];}}
    }

    /*
      getHomeArray -- returns home array for given player
      !This method takes in the playerNum and returns a 1-d home array for that player!
     */
    public int[] getHomeArray(int playerNum)
    {
        return (playerHome[playerNum]);
    }

    /*
      getHomeArray -- returns home array for ALL players (overloaded)
      !This method returns the 2-d home array for ALL players!
    */
    public int[][] getHomeArray()
    {
        return (playerHome);
    }

   /*
      setHomeArray -- sets the official home array of given player to the given array
      !This method takes in the playerNum & array and sets the new array ONLY for that player!
    */
   public void setHomeArray (int playerNum, int[] newHomeArray)
    {
        for (int i = 0; i<4; i++){
            playerHome[playerNum][i] = newHomeArray[i];}
    }

    /*
       setHomeArray -- sets the official start arrays to the given 2-d home array
       !This method takes one 2-d array and sets for ALL players!
    */
    public void setHomeArray (int[][] newHomeArray)
    {
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
            playerHome[i][j] = newHomeArray[i][j];}}
    }

    /*
        getGameBoard -- returns the official state game board int array
     */
    public int[] getGameBoard() { return (gameBoard);}

    /*
       setGameBoard -- takes in a game board array and sets the official state to that array
    */
    public void setGameBoard(int[] newGameBoard)
    {
        for (int i = 0; i<57; i++){
            gameBoard[i] = newGameBoard[i];
        }
    }

}
 //class AggravationState