package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An dumb AI for Aggravation
 *
 * @author Owen Price, Emily Peterson, Andrew Ripple
 * @version Nov 2016
 */
public class AggravationComputerPlayerDumb extends GameComputerPlayer {

    private int officialRoll=0;

    private AggravationState gameStateInfo; //the copy of the game state

    /* *
       * ctor does nothing extra
       */
    public AggravationComputerPlayerDumb(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     * dumbAI: start a piece -> move the piece closest to the start -> move the piece blocking that piece
     * does not use shortcuts at all or aggravate intentionally, but should not miss a possible move otherwise
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof AggravationState) {
            //Smart AI has better comments on what all of these mean if you are confused
            //Default values of array copies and other variables used in methods
            gameStateInfo = (AggravationState) info;
            officialRoll = gameStateInfo.getDieValue();
            int startCopy[] = gameStateInfo.getStartArray(this.playerNum);
            int boardCopy[] = gameStateInfo.getGameBoard();
            int homeCopy[] = gameStateInfo.getHomeArray(this.playerNum);
            int startIdx = this.playerNum * 14; //where player usually starts
            int toMoveFrom=-9;
            int toMoveTo=-9;
            String moveType ="Board";


            if(gameStateInfo.getTurn()==this.playerNum) {
                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) {
                    AggravationRollAction rollAct = new AggravationRollAction(this);
                    game.sendAction(rollAct);
                    sleep(500);
                    return;
                }

                //don't have to roll, so move a piece
                else{
                    //Checks home arrays for a possible move...Smart computer player does the same thing with same code and is commented better.
                    if (homeCopy[3] != playerNum) {
                        if (officialRoll == 1 && homeCopy[2] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 2, 3);
                            game.sendAction(homeMove);
                            return;
                        } else if (officialRoll == 2 && homeCopy[2] != playerNum && homeCopy[1] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 1, 3);
                            game.sendAction(homeMove);
                            return;
                        } else if (officialRoll == 3 && homeCopy[2] != playerNum && homeCopy[1] != playerNum && homeCopy[0] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 3);
                            game.sendAction(homeMove);
                            return;
                        }
                    }
                    else if (homeCopy[3] == playerNum && homeCopy[2] != playerNum) {
                        if (officialRoll == 1 && homeCopy[1] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 1, 2);
                            game.sendAction(homeMove);
                            return;
                        } else if (officialRoll == 2 && homeCopy[1] != playerNum && homeCopy[0] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 2);
                            game.sendAction(homeMove);
                            return;
                        }
                    } else if (homeCopy[3] == playerNum && homeCopy[2] == playerNum && homeCopy[1] != playerNum) {
                        if (officialRoll == 1 && homeCopy[0] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 1);
                            game.sendAction(homeMove);
                            return;
                        }
                    }

                    /*This is where a start move comes from*/
                    if (officialRoll == 6 || officialRoll == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                        for (int j = 0; j < 4; j++) {
                            if (startCopy[j] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece =
                                        new AggravationMovePieceAction(this, "Start", j, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }}}

                    //check to see if there even is a piece on the board the CPU can look to move
                    for (int i = 0; i < 56; i++) {
                        int j = i + startIdx;
                        if (j > 55) j -= 56;
                        if (boardCopy[j] == playerNum) {
                            toMoveFrom = j; //index to move from - finds first one
                            break;
                        }}

                    //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                    //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                    //if toMoveFrom!=-9, that means the previous loop found a space with playerNum as a officialRoll and is trying to move it
                    /*This is where a Board move comes from*/


                    if (toMoveFrom != -9) {//if toMoveFrom found a piece to move on the boardCopy[] / is not its default value
                        int j = 0;
                        int maybeMoveFrom = toMoveFrom;
                        //looks for pieces blocking the desired more, and moves the pieces in a "daisy chain"
                        //if the cpu is giving you problems it's not here, this loop is good
                        while (j < officialRoll) {
                            maybeMoveFrom = maybeMoveFrom + 1; //"can I move from here? How about here? etc"
                            if (maybeMoveFrom > 55) maybeMoveFrom -= 56;
                            if (boardCopy[maybeMoveFrom] == this.playerNum) {
                                toMoveFrom = maybeMoveFrom;
                                j = 0;
                            } else j++;}

                        toMoveTo = toMoveFrom + officialRoll; //new index to move to
                        int endOfTheLine = startIdx - 2; //where the AI cannot move over (-2 for player 0, must be 54)
                        if(playerNum !=0) {
                            if (toMoveTo > 55) toMoveTo -= 56; //reset position to keep in line with arrays
                        }
                        if (playerNum == 0 && toMoveTo >54){ //reset position to keep in line with arrays
                            toMoveTo -=55;}
                        if (this.playerNum == 0) endOfTheLine = 54; //54...since -2 is out of bounds

                        //if the move from would roll across the end of the line, chance toMoveTo to reflect that
                        //since it is now a home move
                        for (int i = toMoveFrom; i < toMoveFrom + officialRoll; i++) {
                            if (i == endOfTheLine)
                            {
                                moveType = "Home";
                                if (playerNum != 0)
                                {
                                    toMoveTo = toMoveTo - endOfTheLine - 1; //PROBLEM HERE
                                }
                                break;
                            }}
                        if (moveType.equalsIgnoreCase("Home")) {
                            if (toMoveTo > 3) moveType = "Skip";
                            else {
                                for (int i = 0; i <= toMoveTo; i++) {
                                    if (homeCopy[i] == playerNum) moveType = "Skip";
                                }
                            }
                        }
                    }
                    //no pieces to move, so send a skip turn
                    if (toMoveFrom == -9) moveType = "Skip";
                    AggravationMovePieceAction movePieceGetOutTheWay;
                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                    game.sendAction(movePieceGetOutTheWay);
                }//move a piece
            }
        }
    }//receiveInfo
}

