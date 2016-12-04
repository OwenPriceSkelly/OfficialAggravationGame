package edu.up.cs301.AggravationGame;

import android.content.Intent;
import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

import java.io.Serializable;
import java.util.Random;

/**
 * class AggravationLocalGame controls the play of the game
 *
 * @author Owen Price, Emily Peterson, Andrew Ripple
 *
 * @version November 2016
 */
public class AggravationLocalGame extends LocalGame implements Serializable {

    private static final long serialVersionUID = -5109179064333136984L;
    /**
     * This ctor creates a new game state
     */
    private int restart = 0;
    private AggravationState officialGameState;
    private AggravationState copyGameState;
    private int actualRoll = 0;
    public AggravationLocalGame()
    {
        super();
        officialGameState= new AggravationState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerNum)
    {
        for(int i = 0;i < players.length; i++)
        {
            if(players[i] instanceof AggravationHumanPlayer && restart == 1)
            {
                return true;
            }

        }
        return officialGameState.getTurn() == playerNum;
    }

    /**
     * This method is called when a new action arrives from a player
     * if it ever works add a bunch of shit here to explain
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        int playerNum= officialGameState.getTurn();
        int boardCopy[] = officialGameState.getGameBoard();
        int startCopy[]= officialGameState.getStartArray(playerNum);
        int homeCopy[] = officialGameState.getHomeArray(playerNum);
        long x = 50;

        if(action instanceof AggravationRollAction) {
            if(officialGameState.getTurn()!=playerNum) return false; //safety net
            Random dieValue = new Random();//dieValue outside of the conditionals
            int value = dieValue.nextInt(6) + 1;
            officialGameState.setDieValue(value);
            actualRoll = value;

//        for (int i = 0; i < 11; i++) { /possible die animation code
//            int value = dieValue.nextInt(6) + 1;
//            officialGameState.setDieValue(value);
//            actualRoll = value;
//            try {
//                Thread.sleep(x);
//            } catch (InterruptedException e) {
//
//            }
            //Log.i("dieVal is ", Integer.toString(value));
            //System.out.println("Roll = " + value);
            //Log.i("set value LocalGame", Integer.toString(officialGameState.getDieValue()));
            officialGameState.setRoll(false);
            return true;
        }


        else if(action instanceof AggravationMovePieceAction) {
            Log.i("action is ", "type move piece");
            int newIdx = ((AggravationMovePieceAction) action).newIdx;
            int oldIdx=((AggravationMovePieceAction) action).oldIdx;
            String type = ((AggravationMovePieceAction) action).type;
            playerNum = officialGameState.getTurn();

            int endOfTheLine = playerNum*14 -2;//farthest any player should get around the board
            if (playerNum==0) endOfTheLine = 54;

            int otherPlayerNum;
            int otherStart[];

            if (type.equalsIgnoreCase("Start")) {
                Log.i("in", "start");
                newIdx=playerNum*14;//safety net-if a starting move is somehow passed with an index that isn't the start space
                if(boardCopy[newIdx]!= -1){//the new index is occupied

                    //the player occupies their own start spot
                    //not a valid move, they have to move out of the way
                    if (boardCopy[newIdx]==playerNum) return false;

                    else {//aggravating move code - occupied by another player
                        otherPlayerNum = boardCopy[newIdx];//whatever value is in the space
                        otherStart = officialGameState.getStartArray(otherPlayerNum);
                        for (int i=0;i<4;i++){
                            //find first empty space in otherPlayerNum start array
                            if (otherStart[i]==-1){
                                //put their piece "back" in their start array
                                otherStart[i]=otherPlayerNum;
                                //Log.i("otherPlayerNum",""+otherPlayerNum);
                                officialGameState.setStartArray(otherPlayerNum,otherStart);
                                Log.i("at "+newIdx,"Player "+playerNum+" aggravated"+otherPlayerNum);
                                break;
                            }
                        }
                    }
                }

                //out with the old, in with the new
                startCopy[oldIdx]=-1;
                boardCopy[newIdx]=playerNum;
                officialGameState.setStartArray(playerNum,startCopy);
            }

            /* Call your move a "Home" move if you are moving TO the home array
            * from the board or within the home array itself - Owen
            */
            else if (type.equalsIgnoreCase("Home")){

                //"outer space" being the board array
                boolean fromOuterSpace=false;
                Log.i("Old Index", oldIdx+"");
                Log.i("New Index", newIdx+"");
                //should only be needed for CPU players, since there aren't any buttons
                //the move could take you out of bounds of the home array
                if(newIdx>3) return false;

                //the move is coming from the outside
                if(oldIdx>3)fromOuterSpace = true;

                //out with the old, in with the new
                if(fromOuterSpace){
                    //check for a leapfrog in the spaces leading up to the end
                    for(int i = oldIdx+1;i<=endOfTheLine;i++){
                        if (boardCopy[i]==playerNum){
                            return false;
                        }
                    }
                    //
                    //check the home array for leapfrogs
                    for(int i=0;i<=newIdx;i++){
                        if (homeCopy[i]==playerNum){
                            return false;
                        }
                    }
                    //empty oldIdx
                    boardCopy[oldIdx]=-1;
                }
                else{
                    //check for a leapfrog in the home array
                    for(int i = oldIdx+1;i<=newIdx;i++){
                        if (homeCopy[i]==playerNum){
                            return false;
                        }
                    }
                    homeCopy[oldIdx]=-1;
                }

                homeCopy[newIdx]=playerNum;
                officialGameState.setHomeArray(playerNum,homeCopy);
            }

            /* Call your move a "Board" move if you are moving TO somewhere on the board, unless it's a start
            * the shortcut spaces count as "Board" spaces, so unless you're trying to land on the actual center
            * and are actually TAKING a shortcut / cutting something short, call it a "Board" move
            - Owen */
            else if (type.equalsIgnoreCase("Board")) {

                //return false if you would be "leapfrogging" one of your own
                //Should never happen with CPU
                Log.i("Making a board", " action");

                boolean skipNext = false;
                if(oldIdx == 5 || oldIdx == 19 || oldIdx == 33 || oldIdx == 47 || newIdx == 56)
                {
                    if(newIdx != playerNum)
                    {
                        skipNext = true;
                    }
                }
                if(!skipNext) {
                    if (oldIdx < 56) {
                        for (int i = oldIdx + 1; i <= newIdx; i++) {
                            if (boardCopy[i] == playerNum) return false;
                        }
                    }
                }

                /*If the desired spot is not empty, it is another player*/
                if (boardCopy[newIdx]!=-1){

                    //aggravating move code
                    otherPlayerNum = boardCopy[newIdx];//whatever value is in the space
                    otherStart = officialGameState.getStartArray(otherPlayerNum);

                    for (int i=0;i<4;i++){

                        //find first empty space in otherPlayerNum start array
                        if (otherStart[i]==-1){

                            //put their piece "back" in their start array
                            otherStart[i]=otherPlayerNum;

                            officialGameState.setStartArray(otherPlayerNum,otherStart);
                            Log.i("at "+newIdx,"Player "+playerNum+" aggravated"+otherPlayerNum);
                            break;
                        }
                    }
                }

                //out with the old, in with the new
                Log.i("Computer got ", "here");
                Log.i("Old Index", oldIdx+"");
                Log.i("New Index", newIdx+"");
                boardCopy[oldIdx]=-1;
                boardCopy[newIdx]=playerNum;
            }

                /* the 4 shortcut spaces are 5, 19, 33, and 47
                * if oldIdx = any of these, then enable shortcut moves?
                * a valid shortcut move is based exclusively on the space
                * you can loop all the way around if you want, which means it doesn't have to be
                * based on the player at all valid newIdx's:
                * i from 0 to the die value - there are as many valid new indexes as the die value
                * newIdx == oldIdx + (14*i) + value - i
                * 14*i is b/c you move a full quarter of the 56 piece board jumping shortcut spot to shortcut spot
                * value -i is b/c each jump from cut to cut is 1 less you can move after
                * newIdx == oldIdx+value + 13*i
                * - Owen
                */

            /* Call your move a "shortcut" move if you are moving IN "shortcut space"
             * "Am I taking a shortcut?" is the key question here, so any moves
             * TO the center, or FROM a regular shortcut space
             * are moves you should label "Shortcut."
             * Any moves to just a regular shortcut space should be labeled Board
             * if this sounds unintuitive and backwards I get it but I have my reasons - Owen
             * */
            else if (type.equalsIgnoreCase("Shortcut")) {

                if (oldIdx == 56 && actualRoll != 1)
                    return false; //need to roll a 1 to leave the center
                Log.i("Old Index", oldIdx + "");
                Log.i("New Index", newIdx + "");
                //canCutThere -"Can you take a shortcut there?" for validity of move,
                // last shortcut is an idx for taking counter-leapfrog measures
                boolean canCutThere = false;
                int lastShortcut = 0;

                /*basically, if you're trying to move to the middle spot, it makes sure that
                * you land on a shortcut space 1 count before hitting the middle spot,
                * so you see that it's landing on the center by an exact count, then makes sure neither that spot nor the center spot
                * is the player's piece (leapfrog check), then it aggravates if someone's there - Owen */
                boolean skipNext = false;
                if (newIdx == 56) {
                    //special case for center square move
                    //oneOff is the shortcut spot right before the middle, reached by exact count
                    int oneOff = oldIdx + actualRoll - 1;
                    Log.i("Oneoff = ", oneOff + "");
                    //landing by exact count, and not overcrowding

                    if ((oneOff == 5 || oneOff == 19 || oneOff == 33 || oneOff == 47) && boardCopy[newIdx] != playerNum) {
                        Log.i("okay", playerNum + "");
                        Log.i("Getting in if statement", " hello");
                        //not leapfrogging the oneOff space
                        if (boardCopy[oneOff] == playerNum && actualRoll != 1) {
                            return false;
                        }

                        //aggravation copypasta
                        if (boardCopy[56] != -1) {
                            otherPlayerNum = boardCopy[newIdx];
                            otherStart = officialGameState.getStartArray(otherPlayerNum);
                            for (int i = 0; i < 4; i++) {
                                if (otherStart[i] == -1) {
                                    otherStart[i] = otherPlayerNum;
                                    officialGameState.setStartArray(otherPlayerNum, otherStart);
                                    Log.i("at " + newIdx, "Player " + playerNum + " aggravated" + otherPlayerNum);
                                    break;
                                }
                            }
                        }
                    }
                    boardCopy[oldIdx] = -1;
                    boardCopy[newIdx] = playerNum;
                    skipNext = true;


                }

                /*moving from any of the 4 possible shortcut spaces,
                * check the given newIdx with every possible valid point you can move to
                * you're allowed to loop around as many times as you want on the shortcut spaces, apparently,
                * and if this works like it does in my head that should be totally fine - Owen
                * changed to else-if because center square code should be handled before this*/
                if (skipNext == false) {
                    if (oldIdx == 5 || oldIdx == 19 || oldIdx == 33 || oldIdx == 47) {
                        for (int i = 0; i <= actualRoll; i++) {

                            //valid is a valid new index - if you aren't sitting on any other
                            // shortcut squares, there is a unique valid move per the value of the roll,
                            // plus a move where you don't leave the shortcut spaces
                            //yes I did mean 13 not 14 check my work the i distributes - Owen
                            int valid = oldIdx + actualRoll + (13 * i);

                            //keep it on the board
                            //while loop since we want it to stay relative to the 56 square board
                            while (valid > 55) valid -= 56;

                            //special leapfrog check, breaks the loop before validating
                            //a newIdx if you have another piece on that shortcut space
                            //don't need to keep it relative to the 56, since it would break before
                            //i got greater than 3, if it was going to break
                            //if(i>0 && i<3&& boardCopy[oldIdx+14*i]==playerNum) break;
                            //Owen the above code breaks if the old index is 47 since you always get greater than 56.

                            //oldIdx+14*i = the index of the furthest shortcut reached before
                            //leaving shortcut space for the main board
                            //so lastShortcut = oldIdx + 14*i = valid - actualRoll + i
                            //since valid is decremented above, I put in terms of valid - Owen

                            //if the newIdx matches with a valid spot, you can take the
                            // short cut you asked for and break the loop without checking the rest
                            if (valid == newIdx) {
                                lastShortcut = valid - actualRoll + i;
                                canCutThere = true;
                                break;
                            }
                        }
                    }

                    //don't make a shortcut move if you aren't moving from a shortcut space
                    //else return false;

                    //if you can, check for leapfrog and aggravations from the shortcut you left from
                    //ideally, since every move has such similar leapfrog/aggravation code, I would be able to
                    //move this bit outside of the "type" cases. Not important for functionality yet though - Owen
                    if (canCutThere) {

                        //leapfrog check
                        for (int i = lastShortcut; i <= newIdx; i++) {
                            if (boardCopy[i] == playerNum) return false;
                        }

                        //aggravation copypasta
                        if (boardCopy[newIdx] != -1) {
                            otherPlayerNum = boardCopy[newIdx];
                            otherStart = officialGameState.getStartArray(otherPlayerNum);
                            for (int i = 0; i < 4; i++) {
                                if (otherStart[i] == -1) {
                                    otherStart[i] = otherPlayerNum;
                                    officialGameState.setStartArray(otherPlayerNum, otherStart);
                                    Log.i("at " + newIdx, "Player " + playerNum + " aggravated" + otherPlayerNum);
                                    break;
                                }
                            }
                        }


                    } //else return false;
                    //out with the old, in with the new

                    //if you don't match with any of the possible valid moves
                    if(oldIdx == 56)
                    {
                        if (boardCopy[newIdx] != -1) {
                            otherPlayerNum = boardCopy[newIdx];
                            otherStart = officialGameState.getStartArray(otherPlayerNum);
                            for (int i = 0; i < 4; i++) {
                                if (otherStart[i] == -1) {
                                    otherStart[i] = otherPlayerNum;
                                    officialGameState.setStartArray(otherPlayerNum, otherStart);
                                    Log.i("at " + newIdx, "Player " + playerNum + " aggravated" + otherPlayerNum);
                                    break;
                                }
                            }
                        }

                    }
                    boardCopy[oldIdx] = -1;
                    boardCopy[newIdx] = playerNum;
                }//shortcut
            }

            /*Label a move "skip" if you want to make a move,
            * but you're afraid of the commitment */
            else if (type.equalsIgnoreCase("Skip")){
                actualRoll=0;
            }

            //setStart and setGameBoard were redundant, being the same in every case so I moved them here
            //makeMove doesn't set anything official before this point, it just modifies copies

            //(only)after any actual move is made, someone has to roll
            //increment the turn whenever the roll isn't a 6
            if (actualRoll !=6)
            {
                if(officialGameState.getTurn() == playerNames.length-1)
                {
                    officialGameState.setTurn(0);
                }
                else {
                    officialGameState.setTurn(officialGameState.getTurn() + 1);
                }
               // Random ran = new Random();
                //int x = ran.nextInt(2);
                //if(x == 0) {
                  //  officialGameState.setTurn(3);
                //}
                //else if(x == 1)
                //{
                 //   officialGameState.setTurn(2);
                //}
            }
            officialGameState.setRoll(true);
            officialGameState.setGameBoard(boardCopy);
        }
        else if(action instanceof AggravationNewGameAction)
        {
            officialGameState.setTurn(0);
            officialGameState.setRoll(true);
            officialGameState.setDieValue(0);
            int[][] playerStart = new int [4][4];
            int[][] playerHome = new int [4][4];
            int[] gameBoard = new int[57];
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
            officialGameState.setStartArray(playerStart);
            officialGameState.setHomeArray(playerHome);
            officialGameState.setGameBoard(gameBoard);
        }

        return true;
        /* Shortened this and moved it up for scope reasons - we had it setting roll to true outside the scope of
        the roll, where it set it to false. So following through the code, we had set roll to
        false after getting a roll, checked to see if it was a 6, then set it true either way and returned.
        If I'm misunderstanding something feel free to change it back
        if(actualRoll == 6)
        {
            officialGameState.setTurn(officialGameState.getTurn());
            officialGameState.setRoll(true);
            return true;
        }
        else
        {
            officialGameState.setTurn(officialGameState.getTurn() + 1);
            officialGameState.setRoll(true);
            return true;
        }*/
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //Log.i("player run in sendSta", Integer.toString(officialGameState.getTurn()));
        copyGameState = new AggravationState(officialGameState);
        p.sendInfo(copyGameState);
    }//sendUpdatedState

    /**
     * Check if the game is over
     *
     * @return
     *        a message that tells who has won the game, or null if the
     *        game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int[] counts = new int[4];
        int[][] homeCopy = officialGameState.getHomeArray();
        String winMessage=null;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
                if (homeCopy[i][j]==i) counts[i]++;
            if (counts[i]==4) {
                restart = 1;
                officialGameState.setTurn(0);
                winMessage = "Player " + i + " Wins!";
                return winMessage;
            }
            else counts[i] = 0;
        }

        return null;
    }

}// class AggravationLocalGame
