package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An smart AI for Aggravation
 *
 * @author Emily Peterson & Andrew Ripple, & Owen Price
 * @version Nov 2016
 */
public class AggravationComputerPlayerSmart extends GameComputerPlayer {

    private AggravationState gameStateInfo; //the copy of the game state
    private int officialRoll = 0;


    /* *
     * ctor does nothing extra
     */
    public AggravationComputerPlayerSmart(String name) {
        super(name);
    }

    private int previousMoveFrom;
    /**
     * callback method--game's state has changed
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof AggravationState) {

            gameStateInfo = (AggravationState) info;
            officialRoll = gameStateInfo.getDieValue();
            int startCopy[] = gameStateInfo.getStartArray(this.playerNum);
            int boardCopy[] = gameStateInfo.getGameBoard();
            int homeCopy[] = gameStateInfo.getHomeArray(this.playerNum);
            int startIdx = this.playerNum * 14;
            int toMoveFrom = -9;
            int toMoveTo = -9;
            String moveType = "Board";
            int middleMove = playerNum * 14 - 9;
            if (middleMove == -9) {
                middleMove = 47;
            }

            if (gameStateInfo.getTurn() == this.playerNum) {
                sleep(500); //CHANGED FROM 2550
                Log.i("my turn player", Integer.toString(this.playerNum));

                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) {
                    AggravationRollAction rollAct = new AggravationRollAction(this);
                    game.sendAction(rollAct);
                    sleep(500); //CHANGED FROM 500
                    System.out.println("I rolled!");
                    return;
                }


                //don't have to roll, so move a piece
                else {
                    if (boardCopy[56] == playerNum && officialRoll == 1) {

                        if (boardCopy[middleMove] != playerNum) {
                            AggravationMovePieceAction movePieceGetOutTheWay;
                            movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", 56, middleMove);
                            game.sendAction(movePieceGetOutTheWay);
                            return;
                        }
                    }
                    if(officialRoll == 1 && boardCopy[middleMove+7] != playerNum)
                    {
                        for (int k = 0; k < 4; k++) {
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                    //check to see if there even is a piece on the board the CPU can look to move

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
                    } else if (homeCopy[3] == playerNum && homeCopy[2] != playerNum) {
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
                    int count = 0;
                    for (int i = 0; i < 4; i++) //check to see if there is only one in home array
                    {
                        if (homeCopy[i] == playerNum) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        if (homeCopy[0] == playerNum) {
                            if (officialRoll == 1) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 1);
                                game.sendAction(homeMove);
                                return;
                            } else if (officialRoll == 2) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 2);
                                game.sendAction(homeMove);
                                return;
                            } else if (officialRoll == 3) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 3);
                                game.sendAction(homeMove);
                                return;
                            }
                        }
                        if (homeCopy[1] == playerNum) {
                            if (officialRoll == 1) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 1, 2);
                                game.sendAction(homeMove);
                                return;
                            } else if (officialRoll == 2) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 1, 3);
                                game.sendAction(homeMove);
                                return;
                            }

                        }
                        if (homeCopy[2] == playerNum) {
                            if (officialRoll == 1) {
                                AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 2, 3);
                                game.sendAction(homeMove);
                                return;
                            }

                        }
                    }

                    for (int i = 0; i < 56; i++) {
                        int j = i + startIdx;
                        if (j > 55) j -= 56;
                        if (boardCopy[j] == playerNum) {
                                if (toMoveFrom != -9) {
                                    previousMoveFrom = toMoveFrom;}
                                toMoveFrom = j;
                                if(toMoveFrom+officialRoll == 5 || toMoveFrom+officialRoll == 19 || toMoveFrom+officialRoll == 33 || toMoveFrom+officialRoll == 47)
                                {
                                    break;
                                }
                                continue;

                            }
                        }
                    if(boardCopy[5] == playerNum)
                    {
                        toMoveFrom = 5;
                    }
                    if(boardCopy[19] == playerNum)
                    {
                        toMoveFrom = 19;
                    }
                    if(boardCopy[33] == playerNum)
                    {
                        toMoveFrom = 33;
                    }
                    if(boardCopy[47] == playerNum)
                    {
                        toMoveFrom = 47;
                    }







                    int scaleComputer = playerNum * 14 - 2;
                    if (playerNum == 0) {
                        scaleComputer = 54;
                    }

                    if (toMoveFrom <= scaleComputer && toMoveFrom > scaleComputer -3 && officialRoll == 6) {
                        for (int k = 0; k < 4; k++) {
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                    Log.i("toMoveFrom is", " " + toMoveFrom);

                    //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                    //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                    //if toMoveFrom!=-9, that means the previous loop found a space with playerNum as a officialRoll and is trying to move it
                    /*This is where a Board move comes from*/
                    if ((toMoveFrom + officialRoll == 6 || toMoveFrom + officialRoll == 20 || toMoveFrom + officialRoll == 34
                            || toMoveFrom + officialRoll == 48) && boardCopy[56] != playerNum && (toMoveFrom + officialRoll) != playerNum * 14 - 8
                            && officialRoll != 1) {
                        AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, 56);
                        game.sendAction(movePieceGetOutTheWay);
                        return;
                    }

                    if (toMoveFrom == 5 || toMoveFrom == 19 || toMoveFrom == 33 || toMoveFrom == 47 && (toMoveFrom + officialRoll) != (middleMove + officialRoll)) {
                        if (officialRoll == 1) {
                            int shortCutMove = toMoveFrom + 14;
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                        }
                        if (officialRoll == 2) {
                            int shortCutMove = toMoveFrom + (14 * 2);
                            int cutBefore = toMoveFrom + 14;
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (cutBefore > 55) {
                                cutBefore -= 56;
                            }
                            if (cutBefore == middleMove && boardCopy[cutBefore + 1] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + 1);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != playerNum * 14 - 9 && (cutBefore) != middleMove) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                        }
                        if (officialRoll == 3) {
                            int shortCutMove = toMoveFrom + (14 * 3);
                            int cutBefore = toMoveFrom + 14;
                            int cutBefore2 = toMoveFrom + 28;
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (cutBefore > 55) {
                                cutBefore -= 56;
                            }
                            if (cutBefore2 > 55) {
                                cutBefore2 -= 56;
                            }
                            if (cutBefore == middleMove && boardCopy[cutBefore + 1] != playerNum && boardCopy[cutBefore + 2] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + 2);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (cutBefore2 == middleMove && boardCopy[cutBefore] != playerNum && boardCopy[cutBefore2 + 1] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore2 + 1);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove && (cutBefore) != middleMove &&
                                    (cutBefore2) != middleMove) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }

                        }
                        if (officialRoll > 3) {
                            int shortCutMove = toMoveFrom + (14 * 3) + (officialRoll - 3);
                            int cutBefore = toMoveFrom + 14;
                            int cutBefore2 = toMoveFrom + 28;
                            int cutBefore3 = toMoveFrom + 42;
                            if (cutBefore > 55) {
                                cutBefore -= 56;
                            }
                            if (cutBefore2 > 55) {
                                cutBefore2 -= 56;
                            }
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (cutBefore3 > 55) {
                                cutBefore3 -= 56;
                            }
                            if (cutBefore == middleMove && boardCopy[cutBefore] != playerNum && boardCopy[cutBefore + 1] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + (officialRoll - 1));
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (cutBefore2 == middleMove && boardCopy[cutBefore2] != playerNum && boardCopy[cutBefore2 + 1] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore2 + (officialRoll - 2));
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (cutBefore3 == middleMove && boardCopy[cutBefore3] != playerNum && boardCopy[cutBefore3 + 1] != playerNum) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore3 + (officialRoll - 3));
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove && (cutBefore) != middleMove &&
                                    (cutBefore2) != middleMove) {
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                game.sendAction(movePieceGetOutTheWay);
                                return;
                            }
                        }

                    }

                    if (toMoveFrom != -9) {//if toMoveFrom found a piece to move on the boardCopy[] / is not its default value

                        int j = 0;
                        int maybeMoveFrom = toMoveFrom;
                        //looks for pieces blocking the desired more, and moves the pieces in a "daisy chain"
                        //if the cpu is giving you problems it's not here, this loop is good
                        while (j < officialRoll) {
                            maybeMoveFrom = maybeMoveFrom + 1; //"can I move from here? How about here? etc"
                            if (maybeMoveFrom > 55) maybeMoveFrom -= 56;
                            Log.i("maybeMoveFrom =", " " + maybeMoveFrom);
                            if (boardCopy[maybeMoveFrom] == this.playerNum) {
                                Log.i("There was a block", "");
                                toMoveFrom = maybeMoveFrom;
                                j = 0;
                            } else j++;
                        }

                        Log.i("toMoveFrom is", " " + toMoveFrom);
                        toMoveTo = toMoveFrom + officialRoll;
                        int endOfTheLine = startIdx - 2;
                        if (toMoveTo > 55) toMoveTo -= 56;
                        if (playerNum == 0 && toMoveTo >54){
                            toMoveTo -=55;}
                        if (this.playerNum == 0) endOfTheLine = 54;
                        Log.i("endOfTheLine is", " " + endOfTheLine);

                        //if the move from would roll across the end of the line, chance toMoveTo to reflect that
                        //since it is now a home move
                        //if this works I'm never changing it because it fits my aesthetic perfectly
                        for (int i = toMoveFrom; i < toMoveFrom + officialRoll; i++) {
                            if (i == endOfTheLine) {
                                moveType = "Home";
                                if (playerNum == 0) {
                                    toMoveTo = toMoveTo +1;}
                                else{
                                    toMoveTo = toMoveTo - endOfTheLine - 1; //PROBLEM HERE
                                }
                                break;
                            }
                        }
                        if (moveType.equalsIgnoreCase("Home")) {
                            if (toMoveTo > 3)
                            {
                                moveType = "Skip";
                                AggravationMovePieceAction movePieceGetOutTheWay;
                                movePieceGetOutTheWay = new AggravationMovePieceAction(this, "board", previousMoveFrom, toMoveTo+officialRoll);
                                game.sendAction(movePieceGetOutTheWay);

                            }
                            else {
                                for (int i = 0; i <= toMoveTo; i++) {
                                    if (homeCopy[i] == playerNum)
                                    {
                                        moveType = "Skip";
                                        AggravationMovePieceAction movePieceGetOutTheWay;
                                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "board", previousMoveFrom, toMoveTo+officialRoll);
                                        game.sendAction(movePieceGetOutTheWay);
                                    }
                                }
                            }

                        }

                            AggravationMovePieceAction movePieceGetOutTheWay;
                            movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                            game.sendAction(movePieceGetOutTheWay);
                            return;

                        //THIS IS WHERE THE PROBLEMS LIVE - Owen
                        //if your move takes you over theEndOfTheLine, and from a point before/= to it,
                        //you're making a move into your home array
                        /*if (toMoveTo>endOfTheLine && toMoveFrom<=endOfTheLine) {
                            toMoveTo=toMoveTo-endOfTheLine-1;//note to self: why -1? -Owen  }*/

                        /*AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "Board", toMoveFrom, toMoveTo);
                        game.sendAction(movePieceGetOutTheWay);*/
                    }
                      /*This is where a start move comes from*/

                    if (officialRoll == 6 || officialRoll == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                        for (int k = 0; k < 4; k++) {
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                }
                if (toMoveFrom == -9) {
                    moveType = "Skip";
                    AggravationMovePieceAction movePieceGetOutTheWay;
                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                    game.sendAction(movePieceGetOutTheWay);
                    return;

                }


                //no pieces to move, so send a skip turn
                //move a piece
            }
        }//receiveInfo
    }
}



//class AggravationComputerPlayerSmart