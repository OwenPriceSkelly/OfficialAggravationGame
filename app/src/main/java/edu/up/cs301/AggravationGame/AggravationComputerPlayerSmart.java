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

    private int previousMoveFrom = -9;
    /**
     * callback method--game's state has changed
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof AggravationState) { //never makes an illegal move

            gameStateInfo = (AggravationState) info; //copy of the game state
            officialRoll = gameStateInfo.getDieValue(); //copy of the roll value
            //The arrays are copies of the player's start and home, and the game board
            int startCopy[] = gameStateInfo.getStartArray(this.playerNum);
            int boardCopy[] = gameStateInfo.getGameBoard();
            int homeCopy[] = gameStateInfo.getHomeArray(this.playerNum);
            int startIdx = this.playerNum * 14; //Where the computer places their first piece out of start
            int toMoveFrom = -9; //Starts at -9 - explained later
            int toMoveTo = -9;//Starts at -9 - explained later
            String moveType = "Board"; //Used when sending Move actions
            int middleMove = playerNum * 14 - 9; //This is the move that corresponds to the shortcut closest to a given player's home
            if (middleMove == -9) { //if computer is player 0, then their middle move is -9, which is impossible on our board, so it is 47
                middleMove = 47;
            }

            if (gameStateInfo.getTurn() == this.playerNum) { //If it's the AI's turn
                sleep(500); //pause for half a second

                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) { //If AI needs to roll
                    AggravationRollAction rollAct = new AggravationRollAction(this); //Makes a roll action
                    game.sendAction(rollAct); //sends it
                    sleep(500); //Sleeps for half a second
                    return;
                }


                //AI doesn't have to roll, so moves a piece
                else {
                    if (boardCopy[56] == playerNum && officialRoll == 1) { //If it's in the middle shortcut and rolls a 1, then it moves to the "MiddleMove" spot
                        //i.e. "closest shortcut spot to its home"
                        if (boardCopy[middleMove] != playerNum) { //as long as middle move isn't its own piece
                            AggravationMovePieceAction movePieceGetOutTheWay;
                            movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", 56, middleMove);
                            game.sendAction(movePieceGetOutTheWay);
                            return;
                        }
                    }
                    int counts = 0; //Counts how many pieces the AI has on the board
                    for (int i = 0; i < 56; i++) {
                        int j = i + startIdx;
                        if (j > 55) j -= 56;
                        if (boardCopy[j] == playerNum) {
                            counts++;
                        }
                    }
                    //If counts is less than two, then it still makes start moves.
                    //Start moves means you move one piece from the start to the board
                    //As long as the AI does not have a piece right below its home when it rolls a 1, it starts a new piece
                    if(officialRoll == 1 && boardCopy[middleMove+7] != playerNum && boardCopy[startIdx] != playerNum && counts < 2)
                    {
                        for (int k = 0; k < 4; k++) {
                            //Makes sure its start index isn't filled by another piece of its own
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }

                    //NEXT FEW LINES OF CODE ARE ALL FOR THE HOME ARRAY! AI SMART PRIORITIZES MAKING HOME MOVES NEXT
                    if (homeCopy[3] != playerNum) { //If the last square is not filled, then checks to see if a move is possible to make it filled
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
                    } else if (homeCopy[3] == playerNum && homeCopy[2] != playerNum) { //If the last square is  filled, then checks to see if a move is possible to fill third
                        if (officialRoll == 1 && homeCopy[1] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 1, 2);
                            game.sendAction(homeMove);
                            return;
                        } else if (officialRoll == 2 && homeCopy[1] != playerNum && homeCopy[0] == playerNum) {
                            AggravationMovePieceAction homeMove = new AggravationMovePieceAction(this, "Home", 0, 2);
                            game.sendAction(homeMove);
                            return;
                        }
                        //If the last and third is  filled, then checks to see if a move is possible to fill second
                    } else if (homeCopy[3] == playerNum && homeCopy[2] == playerNum && homeCopy[1] != playerNum){
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
                    //Basically, if there is one piece in the AI's home array, it always makes a move to get to the ending part
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



                    previousMoveFrom = -9; //Keeps track of the last piece to be found on the board of the AI's pieces
                    for (int i = 0; i < 56; i++) {
                        int breaks = 0;
                        int j = i + startIdx; //Starts searching the board at the players starting index
                        if (j > 55) j -= 56; //if that j gets over 55, then subtract 56 from it to keep it in line with array values
                        if (boardCopy[j] == playerNum) { //if it finds a piece on the board that is the AI
                            if (toMoveFrom != -9) //If it's the first piece, the previousMove is still -9, only changes if this gets hit twice
                            {
                                previousMoveFrom = toMoveFrom;
                            }
                            toMoveFrom = j; //the piece to move on the board is at the index of j
                            //If that piece plus the roll is going to be a short cut, then cut out of the loop and make that piece the piece the AI moves
                            if(toMoveFrom+officialRoll == 5 || toMoveFrom+officialRoll == 19 || toMoveFrom+officialRoll == 33 || toMoveFrom+officialRoll == 47)
                            {
                                for(int z = toMoveFrom+officialRoll;z > toMoveFrom;z--)//First checks to make sure it doesn't jump over its own piece
                                {
                                    if(boardCopy[z] == playerNum)
                                    {
                                        break;
                                    }
                                    else if(z-1 == toMoveFrom && boardCopy[z] != playerNum)
                                    {
                                        breaks = 1;
                                        break;
                                    }
                                }
                            }
                            if(breaks == 1)
                            {
                                break;
                            }

                        }
                    }
                    //If the board index below its home array is its own piece, change the place to move from to that piece
                    if(boardCopy[middleMove+7] == playerNum)
                    {
                        toMoveFrom = middleMove+7;
                    }
                    //This checks to see if there is a piece on a shortcut the AI can move
                    //It prioritizes moving those over anything else, besides the moves above
                    if(playerNum == 0) {
                        if (boardCopy[5] == playerNum) {
                            toMoveFrom = 5;
                        }
                        if (boardCopy[19] == playerNum) {
                            toMoveFrom = 19;
                        }
                        if (boardCopy[33] == playerNum) {
                            toMoveFrom = 33;
                        }
                        if (boardCopy[47] == playerNum) {
                            toMoveFrom = 47;
                        }
                    }
                    if(playerNum == 1) {
                        if (boardCopy[19] == playerNum) {
                            toMoveFrom = 19;
                        }
                        if (boardCopy[33] == playerNum) {
                            toMoveFrom = 33;
                        }
                        if (boardCopy[47] == playerNum) {
                            toMoveFrom = 47;
                        }
                        if (boardCopy[5] == playerNum) {
                            toMoveFrom = 5;
                        }
                    }
                    if(playerNum == 2) {
                        if (boardCopy[33] == playerNum) {
                            toMoveFrom = 33;
                        }
                        if (boardCopy[47] == playerNum) {
                            toMoveFrom = 47;
                        }
                        if (boardCopy[19] == playerNum) {
                            toMoveFrom = 19;
                        }
                        if (boardCopy[5] == playerNum) {
                            toMoveFrom = 5;
                        }
                    }
                    if(playerNum == 3) {
                        if (boardCopy[47] == playerNum) {
                            toMoveFrom = 47;
                        }
                        if (boardCopy[5] == playerNum) {
                            toMoveFrom = 5;
                        }
                        if (boardCopy[19] == playerNum) {
                            toMoveFrom = 19;
                        }
                        if (boardCopy[33] == playerNum) {
                            toMoveFrom = 33;
                        }
                    }



                    //if AI is player zero, then sets where the ending spot it can move to is to a positive number in line with arrays
                    int scaleComputer = playerNum * 14 - 2;
                    if (playerNum == 0) {
                        scaleComputer = 54;
                    }
                    //If the AI rolls a 6, which means it can go again, and the piece it wishes to move is close to its home
                    //Then it will instead make a start move, if it has less than 2 pieces on the board. Since it can roll again.
                    if (toMoveFrom <= scaleComputer && toMoveFrom > scaleComputer -3 && officialRoll == 6 && boardCopy[startIdx] != playerNum
                            && counts < 2) {
                        for (int k = 0; k < 4; k++) {
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                    Log.i("toMoveFrom is", " " + toMoveFrom);

                    //If the computer can move into the middle, and it is less than half way across board, move into the middle, make sure its not occupied
                    //by its own piece
                    if ((toMoveFrom + officialRoll == 6 || toMoveFrom + officialRoll == 20 || toMoveFrom + officialRoll == 34
                            || toMoveFrom + officialRoll == 48) && boardCopy[56] != playerNum && (toMoveFrom + officialRoll) != playerNum * 14 - 8
                            &&(toMoveFrom + officialRoll) != playerNum * 14 - 22 && officialRoll != 1) {
                        AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, 56); //It's a shortcut move
                        game.sendAction(movePieceGetOutTheWay);
                        return;
                    }
                    //If its a shortcut it is moving from, then there are 6 possible rolls the AI rolled.
                    if (toMoveFrom == 5 || toMoveFrom == 19 || toMoveFrom == 33 || toMoveFrom == 47 && (toMoveFrom + officialRoll) != (middleMove + officialRoll)) {
                        if (officialRoll == 1) { //If it rolled a one...then it has one possible move on the shortcut
                            boolean go = true;
                            int shortCutMove = toMoveFrom + 14; //which is 14 plus where the AI's piece is now on the board
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            //Will only make that move if the shortcut move it's on is not the one closest to its home
                            //If it can move, then makes sure it isn't moving onto a space occupied by its own piece
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove) {

                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                        }
                        //Rolled a two...then it has two possible moves on the shortcut
                        if (officialRoll == 2) {
                            boolean go = true;
                            int shortCutMove = toMoveFrom + (14 * 2); //2nd one it jumps to
                            int cutBefore = toMoveFrom + 14; //first one it jumps to
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (cutBefore > 55) {
                                cutBefore -= 56;
                            }
                            //Makes sure it is not making a move that would make it go past the shortcut that is closest to its home array
                            if (cutBefore == middleMove && boardCopy[cutBefore] != playerNum) {
                                //THIS FOR LOOP IS IN ALL OF NEXT FEW IF STATEMENTS FOR "OFFICIAL ROLL"
                                //Checks to make sure that the spot on the board it is moving to does not have
                                //any other AI pieces before it, as well as on top of it.
                                for (int k = cutBefore + (officialRoll - 1); k > cutBefore; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false; //if it catches another piece where it wants to move...then it doesn't make that move
                                    }
                                }
                                if (go)
                                {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + (officialRoll - 1));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove && (cutBefore) != middleMove) {
                                for (int k = shortCutMove; k > cutBefore; k--)
                                {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                        }
                        //Rolled a three..then it has three possible moves
                        if (officialRoll == 3) {
                            boolean go = true;
                            int shortCutMove = toMoveFrom + (14 * 3); //third shortcut it jumps to
                            int cutBefore = toMoveFrom + 14; //first shortcut it jumps to
                            int cutBefore2 = toMoveFrom + 28; //second one
                            if (shortCutMove > 55) {
                                shortCutMove -= 56;
                            }
                            if (cutBefore > 55) {
                                cutBefore -= 56;
                            }
                            if (cutBefore2 > 55) {
                                cutBefore2 -= 56;
                            }
                            if (cutBefore == middleMove && boardCopy[cutBefore] != playerNum) {
                                for (int k = cutBefore + (officialRoll - 1); k > cutBefore; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go)
                                {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + (officialRoll - 1));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (cutBefore2 == middleMove && boardCopy[cutBefore] != playerNum && boardCopy[cutBefore2] != playerNum) {
                                for (int k = cutBefore2 + (officialRoll - 2); k > cutBefore2; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore2 + (officialRoll - 2));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove && (cutBefore) != middleMove &&
                                    (cutBefore2) != middleMove) {
                                for (int k = shortCutMove; k > cutBefore2; k--)
                                {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }

                        }
                        //Rolled greater than a three...then it has four possible moves to make.
                        if (officialRoll > 3) {
                            boolean go = true;
                            int shortCutMove = toMoveFrom + (14 * 3) + (officialRoll - 3); //board move
                            int cutBefore = toMoveFrom + 14; //first shortcut
                            int cutBefore2 = toMoveFrom + 28;//second
                            int cutBefore3 = toMoveFrom + 42;//third
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
                            //IF ANY OF THE SHORTCUTS = THE SPACE CLOSEST TO THE HOME ARRAY, THEN AI JUMPS ONTO THE BOARD
                            //THE FOR LOOP CHECKS TO MAKE SURE IT DOESN'T LAND ON ANOTHER PIECE OF ITSELF AND THAT
                            //IT DOES NOT JUMP OVER ANY
                            if (cutBefore == middleMove && boardCopy[cutBefore] != playerNum) {
                                for (int k = cutBefore + (officialRoll - 1); k > cutBefore; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go)
                                {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore + (officialRoll - 1));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (cutBefore2 == middleMove && boardCopy[cutBefore] != playerNum && boardCopy[cutBefore2] != playerNum) {
                                for (int k = cutBefore2 + (officialRoll - 2); k > cutBefore2; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore2 + (officialRoll - 2));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (cutBefore3 == middleMove && boardCopy[cutBefore3] != playerNum && boardCopy[cutBefore2] != playerNum
                                    && boardCopy[cutBefore] != playerNum){
                                for (int k = cutBefore3 + (officialRoll - 3); k > cutBefore3; k--) {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, cutBefore3 + (officialRoll - 3));
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                            if (boardCopy[shortCutMove] != playerNum && toMoveFrom != middleMove && (cutBefore) != middleMove &&
                                    (cutBefore2) != middleMove && cutBefore3!=playerNum) {
                                for (int k = shortCutMove; k > cutBefore3; k--)
                                {
                                    if (boardCopy[k] == playerNum) {
                                        go = false;
                                    }
                                }
                                if (go) {
                                    AggravationMovePieceAction movePieceGetOutTheWay;
                                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, "shortcut", toMoveFrom, shortCutMove);
                                    game.sendAction(movePieceGetOutTheWay);
                                    return;
                                }
                            }
                        }

                    }

                    if (toMoveFrom != -9) {//if toMoveFrom found a piece to move on the boardCopy[] / is not its default value

                        int j = 0;
                        int maybeMoveFrom = toMoveFrom;
                        //looks for pieces blocking the desired more, and moves the pieces in a "daisy chain"
                        while (j < officialRoll) {
                            maybeMoveFrom = maybeMoveFrom + 1; //"can I move from here? How about here? etc"
                            if(maybeMoveFrom > middleMove+7)
                            {
                                break; //since that would be over the AI's spot it could reach on the board
                            }
                            if (maybeMoveFrom > 55) maybeMoveFrom -= 56; //For the home arrays- make index 0-4 for a possible move
                            if(boardCopy[maybeMoveFrom] == this.playerNum) {
                                toMoveFrom = maybeMoveFrom; //If there is a block, then that means there is another piece in the way, so try to move that one
                                j = 0;
                            } else j++;
                        }

                        Log.i("toMoveFrom is", " " + toMoveFrom);
                        toMoveTo = toMoveFrom + officialRoll;
                        int endOfTheLine = startIdx - 2; //Furthest part AI can get to on the board
                        if(playerNum !=0) { //If it is not player zero, then if it is trying to move over 55, subtract 56 from the move. 56-56 = 0 on board
                            if (toMoveTo > 55) toMoveTo -= 56;
                        }
                        if (playerNum == 0 && toMoveTo >54){ //If the player is zero, then they are moving into the home array...so can't go over 54
                            toMoveTo -=55;}
                        if (this.playerNum == 0) endOfTheLine = 54; //end of the line for 0 must be set to 54 since starting index - 2 = -2. = Array error
                        Log.i("endOfTheLine is", " " + endOfTheLine);
                        Log.i("toMoveto", " " + toMoveTo);

                        //if the move from would roll across the end of the line, chance toMoveTo to reflect that
                        //since it is now a home move
                        //if this works I'm never changing it because it fits my aesthetic perfectly
                        for (int i = toMoveFrom; i < toMoveFrom + officialRoll; i++) {
                            if (i == endOfTheLine) { //if the player is going over their max spot
                                moveType = "Home"; //It's a home action
                                if (playerNum != 0) {
                                    toMoveTo = toMoveTo - endOfTheLine - 1; //only if not player zero, reset to 0-4 for home move
                                }
                                break;
                            }
                        }
                        if (moveType.equalsIgnoreCase("Home")) {
                            int breaks3 = 0;
                            if (toMoveTo > 3) //if the move would go over the home array
                            {
                                moveType = "Skip"; //its a skip move
                                if(previousMoveFrom != -9) { //if there was more than one piece on the board
                                    int prev = previousMoveFrom + officialRoll; //prev is where the next piece could move to
                                    if (prev > 55) {
                                        prev -= 56;
                                    }
                                    if (boardCopy[prev] != playerNum) { //make sure where its moving to is not itself
                                        if (boardCopy[prev] != playerNum) {
                                            for (int z = prev; z > previousMoveFrom; z--) {
                                                if (boardCopy[z] == playerNum) {
                                                    breaks3 = 1; //can't leapfrog, change to 1
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (breaks3 == 0) { //if the second piece can move...breaks3 is 0, since it changes to 1 if it would leapfrog
                                        if(boardCopy[prev] != playerNum)
                                        {
                                            Log.i("Got into","Else breaks 3 " + previousMoveFrom);
                                            AggravationMovePieceAction movePieceGetOutTheWay;
                                            movePieceGetOutTheWay = new AggravationMovePieceAction(this, "board", previousMoveFrom, prev);
                                            game.sendAction(movePieceGetOutTheWay);
                                            return;
                                        }
                                    }
                                }

                            }
                            else {
                                //Its a valid home action
                                int breaks2 = 0;
                                for (int i = 0; i <= toMoveTo; i++) {
                                    if (homeCopy[i] == playerNum) { //can't leapfrog own piece
                                        moveType = "Skip";
                                        if (previousMoveFrom != -9) { //Same as the code above. Tries to Move second piece on the board
                                            int prev = previousMoveFrom + officialRoll;
                                            if (prev > 55) {
                                                prev -= 56;
                                            }
                                            if (boardCopy[prev] != playerNum) {
                                                for (int z = prev; z > previousMoveFrom; z--) {
                                                    if (boardCopy[z] == playerNum) {
                                                        breaks2 = 1;
                                                        break;
                                                    }
                                                }
                                                if (breaks2 == 0) { //Only moves if the second piece doesn't leapfrog either
                                                    if(boardCopy[prev] != playerNum) {
                                                        Log.i("Got into", "Else breaks 2 " + previousMoveFrom);

                                                        AggravationMovePieceAction movePieceGetOutTheWay;
                                                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "board", previousMoveFrom, prev);
                                                        game.sendAction(movePieceGetOutTheWay);
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        previousMoveFrom = -9;
                        Log.i("Move Type is", ""+moveType);
                        AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                        game.sendAction(movePieceGetOutTheWay);
                        //Sends a skip move if it gets here, and sets the previousMoveFrom back to -9
                        return;

                    }
                      /*This is where a start move comes from*/
                    if (officialRoll == 6 || officialRoll == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                        for (int k = 0; k < 4; k++) {
                            previousMoveFrom = -9;
                            if (startCopy[k] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece = new AggravationMovePieceAction(this, "Start", k, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                }
                if (toMoveFrom == -9) { //If no move was found...then skip
                    previousMoveFrom = -9;
                    moveType = "Skip";
                    AggravationMovePieceAction movePieceGetOutTheWay;
                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                    game.sendAction(movePieceGetOutTheWay);
                    return;

                }
                //no pieces to move, so send a skip turn
            }
        }//receiveInfo
    }
}



//class AggravationComputerPlayerSmart