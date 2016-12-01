package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A GUI for a human to play Aggravation.
 *
 *
 * @author Emily Peterson, Andrew Ripple & Owen Price
 * @version November 2016
 */
public class AggravationHumanPlayer extends GameHumanPlayer implements OnClickListener {

   /* instance variables */

    // These variables will reference widgets that will be modified during play
    private ImageButton dieImageButton = null;
    private TextView yourTurn;
    private TextView rollView;
    private TextView illegalMove;
    private Button newGameButton;
    private AggravationState gameStateInfo;  // holds copy of the game state

    private ImageButton[] gameBoard = new ImageButton[57];
    private ImageButton[][] playerStart = new ImageButton[4][4];
    private ImageButton[][] playerHome = new ImageButton[4][4];
    private int[] gameBoardIDS = {R.id.GameBoard0,
            R.id.GameBoard1, R.id.GameBoard2, R.id.GameBoard3, R.id.GameBoard4,
            R.id.GameBoard5, R.id.GameBoard6, R.id.GameBoard7, R.id.GameBoard8,
            R.id.GameBoard9, R.id.GameBoard10,R.id.GameBoard11,R.id.GameBoard12,
            R.id.GameBoard13,R.id.GameBoard14,R.id.GameBoard15,R.id.GameBoard16,
            R.id.GameBoard17,R.id.GameBoard18,R.id.GameBoard19,R.id.GameBoard20,
            R.id.GameBoard21,R.id.GameBoard22,R.id.GameBoard23,R.id.GameBoard24,
            R.id.GameBoard25,R.id.GameBoard26,R.id.GameBoard27,R.id.GameBoard28,
            R.id.GameBoard29,R.id.GameBoard30,R.id.GameBoard31,R.id.GameBoard32,
            R.id.GameBoard33,R.id.GameBoard34,R.id.GameBoard35,R.id.GameBoard36,
            R.id.GameBoard37,R.id.GameBoard38,R.id.GameBoard39,R.id.GameBoard40,
            R.id.GameBoard41,R.id.GameBoard42,R.id.GameBoard43,R.id.GameBoard44,
            R.id.GameBoard45,R.id.GameBoard46,R.id.GameBoard47,R.id.GameBoard48,
            R.id.GameBoard49,R.id.GameBoard50,R.id.GameBoard51,R.id.GameBoard52,
            R.id.GameBoard53,R.id.GameBoard54,R.id.GameBoard55,R.id.GameBoard56
    };
    private int[] playerStartIDS = {R.id.Start00,R.id.Start01,R.id.Start02,R.id.Start03,R.id.Start10,R.id.Start11,R.id.Start12,
            R.id.Start13,R.id.Start20,R.id.Start21,R.id.Start22,R.id.Start23,R.id.Start30,R.id.Start31,R.id.Start32,R.id.Start33};
    private int[] playerHomeIDS = {R.id.Home00,R.id.Home01,R.id.Home02,R.id.Home03,R.id.Home10,R.id.Home11,R.id.Home12,
            R.id.Home13,R.id.Home20,R.id.Home21,R.id.Home22,R.id.Home23,R.id.Home30,R.id.Home31,R.id.Home32,R.id.Home33};

    //didn't put in playerIdx bc playerNum is defined in Game Human Player
    // .... so i think we should follow the pig lead on this one.

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public AggravationHumanPlayer(String name) {
        super(name);
        gameStateInfo = new AggravationState();
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     *        the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     *        the message
     */


    @Override
    public void receiveInfo(GameInfo info) {
        Log.i("here", "recieveInfo");
        for (int z = 0; z<4; z++) {
            if (playerHome[0][z].isEnabled()) {
                Log.i("clickable spacerI", Integer.toString(z));
            }
        }

        if(info instanceof IllegalMoveInfo)
        {
            illegalMove.setText("Illegal Move.");
        }


            if (info instanceof AggravationState) {
                illegalMove.setText("");
                gameStateInfo = (AggravationState) info;
                Log.i("rollVal Receive Info", Integer.toString(gameStateInfo.getDieValue()));
                int[] temp = gameStateInfo.getGameBoard();


                    if(gameStateInfo.getRoll() == true || gameStateInfo.getRoll() == false)
                    {
                        this.newGameButton.setEnabled(true);
                    }
                    for (int i = 0; i < 57; i++) //setting game board to the pictures
                    {

                        if (temp[i] == -1) {
                            if (i == 5 || i == 19 || i == 33 || i == 47 || i == 56) {
                                this.gameBoard[i].setBackgroundResource(R.mipmap.shortcut);
                                continue;
                            }
                            this.gameBoard[i].setBackgroundResource(R.mipmap.gamesquare);
                        } else if (temp[i] == 0) {
                            this.gameBoard[i].setBackgroundResource(R.mipmap.playerzeropiece);
                        } else if (temp[i] == 1) {
                            this.gameBoard[i].setBackgroundResource(R.mipmap.playeronepiece);
                        } else if (temp[i] == 2) {
                            this.gameBoard[i].setBackgroundResource(R.mipmap.playertwopiece);
                        } else if (temp[i] == 3) {
                            this.gameBoard[i].setBackgroundResource(R.mipmap.playerthreepiece);
                        }
                    }
                    int tempStart[][] = gameStateInfo.getStartArray(); //temporary array that holds the start array integers of the game state
                    for (int i = 0; i < allPlayerNames.length; i++) { //this runs through and looks at the integers in the game state array and changes the image buttons to reflect them
                        for (int j = 0; j < 4; j++) {
                            if (tempStart[i][j] == -1) {
                                this.playerStart[i][j].setBackgroundResource(R.mipmap.gamesquare);
                            }
                            else if (tempStart[i][j] == 0) {
                                this.playerStart[i][j].setBackgroundResource(R.mipmap.playerzeropiece);
                            } else if (tempStart[i][j] == 1) {
                                this.playerStart[i][j].setBackgroundResource(R.mipmap.playeronepiece);
                            } else if (tempStart[i][j] == 2) {
                                this.playerStart[i][j].setBackgroundResource(R.mipmap.playertwopiece);
                            } else if (tempStart[i][j] == 3) {
                                this.playerStart[i][j].setBackgroundResource(R.mipmap.playerthreepiece);
                            }
                        }
                    }
                    int tempHome[][] = gameStateInfo.getHomeArray();//temporary array that holds the home array integers of the game state
                    for (int i = 0; i < 4; i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
                    {
                        for (int j = 0; j < 4; j++) {
                            if (tempHome[i][j] == -1) {
                                if(i == 0)
                                {
                                    this.playerHome[i][j].setBackgroundResource(R.mipmap.greensquarehome);
                                }
                                if(i == 1)
                                {
                                    this.playerHome[i][j].setBackgroundResource(R.mipmap.pinksquarehome);
                                }
                                if(i == 2)
                                {
                                    this.playerHome[i][j].setBackgroundResource(R.mipmap.redsquarehome);
                                }
                                if(i == 3)
                                {
                                    this.playerHome[i][j].setBackgroundResource(R.mipmap.greysquarehome);
                                }
                            } else if (tempHome[i][j] == 0) {
                                this.playerHome[i][j].setBackgroundResource(R.mipmap.playerzeropiece);
                            } else if (tempHome[i][j] == 1) {
                                this.playerHome[i][j].setBackgroundResource(R.mipmap.playeronepiece);
                            } else if (tempHome[i][j] == 2) {
                                this.playerHome[i][j].setBackgroundResource(R.mipmap.playertwopiece);
                            } else if (tempHome[i][j] == 3) {
                                this.playerHome[i][j].setBackgroundResource(R.mipmap.playerthreepiece);
                            }

                        }
                    }
                }
                if(allPlayerNames.length == 3)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
                    }
                }
                if(allPlayerNames.length == 2)
                {
                    for (int i = 0; i < 4; i++)
                {
                     this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
                     this.playerStart[2][i].setBackgroundResource(R.mipmap.gamesquare);
                    }
                }
                if(allPlayerNames.length == 1)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
                        this.playerStart[2][i].setBackgroundResource(R.mipmap.gamesquare);
                        this.playerStart[1][i].setBackgroundResource(R.mipmap.gamesquare);
                }
                }


                int whoseTurn = gameStateInfo.getTurn();
                if (whoseTurn == playerNum) {
                    if (gameStateInfo.getRoll()) {
                        for (int i = 0; i < 57; i++) {
                            this.gameBoard[i].setEnabled(false);
                        }
                        for (int i = 0; i < 4; i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
                        {
                            for (int j = 0; j < 4; j++) {
                                this.playerStart[i][j].setEnabled(false);
                            }
                        }
                        for (int i = 0; i < 4; i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
                        {
                            for (int j = 0; j < 4; j++) {
                                this.playerHome[i][j].setEnabled(false);


                                                            }
                        }
                        this.dieImageButton.setEnabled(true);

                    } else {

                        this.dieImageButton.setEnabled(false);
                    }
                }


                yourTurn.setText("PLAYER " + Integer.toString(whoseTurn) + "!");


                if (whoseTurn != playerNum) {
                    rollView.setText("Not Your Turn To Roll.");
                }
                if (gameStateInfo.getRoll() == true && whoseTurn == playerNum) {
                    rollView.setText("Roll!");
                }
                if (whoseTurn == playerNum && gameStateInfo.getRoll() == false) {
                    rollView.setText("You Just Rolled! \n Move a Piece!");
                }



            // else
            // {
            // flash(Color.RED,100);
            //}
            if (gameStateInfo.getDieValue() == 0) {
                dieImageButton.setImageResource(R.mipmap.zeroroll);
             }
            if (gameStateInfo.getDieValue() == 1) {
                dieImageButton.setImageResource(R.mipmap.face1);
            }
            if (gameStateInfo.getDieValue() == 2) {
                dieImageButton.setImageResource(R.drawable.face2);
            }
            if (gameStateInfo.getDieValue() == 3) {
                dieImageButton.setImageResource(R.drawable.face3);
            }
            if (gameStateInfo.getDieValue() == 4) {
                dieImageButton.setImageResource(R.drawable.face4);
            }
            if (gameStateInfo.getDieValue() == 5) {
                dieImageButton.setImageResource(R.drawable.face5);
            }
            if (gameStateInfo.getDieValue() == 6) {
                dieImageButton.setImageResource(R.drawable.face6);
            }

            if (checkPieces) {
                Log.i("does this work", Integer.toString(gameStateInfo.getDieValue()));
                possibleMoveChecker();
                checkPieces = false;
            }

        for (int z = 0; z<4; z++) {
            if (playerHome[0][z].isEnabled()) {
                Log.i("clickable spaceri2", Integer.toString(z));
            }
        }

    }//receiveInfo


    public int getPlayerNum()
    {
        return playerNum;
    }
/**
 * possibleMoveChecker -- holds code to call moves but has the correct die value. Called after a player rolls the die
 * and checks if there are possible moves. Code would be in onClick but the die value needs to be updated first
 */
public void possibleMoveChecker()
{
    int[] gameBoardCopy = gameStateInfo.getGameBoard();
    int[][] homeCopy = gameStateInfo.getHomeArray();
    int[][] startCopy = gameStateInfo.getStartArray();
    int rollVal = gameStateInfo.getDieValue();
    cpLi = -1;
    boolean canImove = false;
    for (int i = 0; i < 57; i++) {
        if (gameBoardCopy[i] == playerNum) {
                    cpLi++;
                    currentPieceLocations[cpLi] = i;
                    this.gameBoard[i].setEnabled(true); //enables player's buttons in game board
                    Log.i("enabled", Integer.toString(i));
                    if (!canImove) {
                        canImove = Moves("board", i, false);
                    }
                    //callNewMethod which checks the piece & make it's response = some boolean

                }
            }
    if (cpLi <3)
    {

        Log.i("cpLi is ", Integer.toString(cpLi));
        for (int i= cpLi; i<3; i++)
        {
            cpLi++;
            currentPieceLocations[cpLi] = -999;
        }
    }
            for (int i = 0; i < 4; i++) //checks buttons in start and home array
            {
                if (startCopy[playerNum][i] == playerNum) {
                    this.playerStart[playerNum][i].setEnabled(true);
                    if (!canImove) {
                        canImove = Moves("start", i, false);
                        Log.i("checked", "moves");
                        }
                    if (!canImove)
                    {
                        Log.i("canIMove is", "false");
                    }

                }
                if (homeCopy[playerNum][i] == playerNum) {
                    this.playerHome[playerNum][i].setEnabled(true);
                    if (!canImove) {
                        canImove = Moves("home", i, false);
                    }
                    if (!canImove){Log.i("homesays", "move poss");}
                }
            }
            if (!canImove)
            {
                AggravationMovePieceAction move = new AggravationMovePieceAction(this, "skip", -1, -1); //sends empty action
                game.sendAction(move);
                Log.i("sent", "blank move");
            }
            else
            {
                Log.i("possible moves", "exist");
            }

}


/**
 * Moves checks and or enables possible move locations for the given button
 */
public boolean Moves(String board, int pieceLoc, boolean enable) {
    Log.i("clicked", "click");
    int rollVal = gameStateInfo.getDieValue();
    int myNum = playerNum;
    int[] gameBoardCopy = gameStateInfo.getGameBoard();
    int[][] homeCopy = gameStateInfo.getHomeArray();
    int[][] startCopy = gameStateInfo.getStartArray();
    String boardType = "board";
    boolean possibleMove = false;

    //if it is start value piece, checks or enables the possible move spot 0
    if (board.equals("start")) {
        Log.i("in helper1roll Val", Integer.toString(rollVal));
        if (rollVal == 1 || rollVal == 6) //if a one or a 6 & there are pieces to move from start array, enable space
        {
            Log.i("in helper, roll val", Integer.toString(rollVal));
            if (enable) {
                this.gameBoard[playerNum * 14].setEnabled(true);
                if(gameBoardCopy[playerNum*14] == -1) {
                    this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.whitesquare);
                    gameBoardCopy[playerNum*14] = -2;
                }
            }
            possibleMove = true;
            Log.i("in helper", "PM is true");
            Log.i("enabled", Integer.toString(playerNum * 14));
            markedButton = pieceLoc; //button the move will be send from
            Log.i("markedButton", Integer.toString(pieceLoc));
            boardType = "start";

        }

    }

    //if checking a value in the home area, checks or enables possible move spot(s)
    if (board.equals("home")) {
        int i = pieceLoc;
        Log.i("pieceLoc is ", Integer.toString(pieceLoc));
            if (i + rollVal < 4 && homeCopy[playerNum][i + rollVal] != playerNum) {//something is up here
                if(rollVal == 1)
                {
                    if (enable) {
                        this.playerHome[playerNum][rollVal + i].setEnabled(true);
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;
                        }
                        Log.i("enabledhomearray", Integer.toString(rollVal + i));
                    }
                }
                if(rollVal == 2 && i < 2 && homeCopy[playerNum][i + rollVal-1] != playerNum)
                {
                    if (enable) {
                        this.playerHome[playerNum][rollVal + i].setEnabled(true);
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;
                        }
                        Log.i("enabledhomearray", Integer.toString(rollVal + i));
                    }
                }
                if(rollVal == 3 && i < 1 && homeCopy[playerNum][i + rollVal-1] != playerNum && homeCopy[playerNum][i + rollVal-2] != playerNum)
                {
                    if (enable) {
                        this.playerHome[playerNum][rollVal + i].setEnabled(true);
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;
                        }
                        this.playerHome[playerNum][rollVal+i].setBackgroundResource(R.mipmap.whitesquare);
                        Log.i("enabledhomearray", Integer.toString(rollVal + i));
                    }
                }


            possibleMove = true;
            Log.i("return possibleMove", "true");
        }
        markedButton = pieceLoc;
        return possibleMove; //returning here because if it's in the home array that is all we need to check
    }


    //if checking a value in the board area, checks or enables possible move spot(s)
    if (board.equals("board")) {
        int i = pieceLoc;
        Log.i("button clicked is", Integer.toString(i));
        if (gameBoardCopy[i] == playerNum) //if the player clicked on its own button
        {
            markedButton = i;

            //CASE: roll val on the board //WILL NEED TO CHANGE FOR NETWORK PLAY>>>> address 56
            if (((i + rollVal) > (55 - (playerNum * 14) + 4))) //if there is a viable button for player button + roll
            {
                Log.i(".", "got inside :(....");
                if ((i + rollVal) > 55 && ((rollVal + i) - 14 * playerNum) < 54) { //player other than 0 is moving "over the top" legally
                    Log.i("inside loop non player", "one");
                    int correctedSpace = rollVal + i - 55; //"over the top space"
                    if (gameBoardCopy[correctedSpace] != playerNum)//"over the top"
                    {
                        if (checkPieceOrder(currentPieceLocations, playerNum, i, correctedSpace)) { //right here is where it needs to be fixed
                            if (enable) {
                                this.gameBoard[correctedSpace].setEnabled(true);
                                if(gameBoardCopy[correctedSpace] == -1) {
                                    gameBoard[correctedSpace].setBackgroundResource(R.mipmap.whitesquare);
                                    gameBoardCopy[correctedSpace] = -2;
                                }
                                Log.i("enabled CS1", Integer.toString(correctedSpace));
                            }
                            possibleMove = true;
                        }
                    }
                }
            }
                else if (checkPieceOrder(currentPieceLocations, playerNum, i, (i + rollVal))) {
                    if (i + rollVal < 55 && gameBoardCopy[i + rollVal] != playerNum) {
                        Log.i("got here","e");
                        if (enable) {
                            this.gameBoard[i + rollVal].setEnabled(true); //enables that button
                            if(gameBoardCopy[i+rollVal] == -1) {
                                gameBoard[i+rollVal].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[i+rollVal] = -2;
                            }
                            Log.i("enabled cso", Integer.toString(i + rollVal));
                        }
                        possibleMove = true;
                    }
                }
            }




            //CASE: Potential home array move
            //checks the Home Arrays

                if ((i + rollVal) > 54 && (i + rollVal) < (54 + 5) && i != 56) {
                    Log.i("trying to enable", "home");
                    if (homeCopy[playerNum][(i+rollVal)-55] != playerNum) {
                        boolean canDoThis = true;
                        int iterator = i + rollVal - 55;
                        for (int j = 0; j < 4; j++) //checks to make sure it's not leapfrogging a piece already there
                        {
                            if (j < iterator) //only the spaces before the possible move space
                            {
                                if (homeCopy[playerNum][j] == playerNum) {
                                    canDoThis = false;
                                    Log.i("not enable", "can't skip ownpiece");
                                }
                            }

                        }
                        if (canDoThis) {
                            if (enable) {

                                playerHome[playerNum][iterator].setEnabled(true);
                                if(homeCopy[playerNum][iterator] == -1) {
                                    playerHome[playerNum][iterator].setBackgroundResource(R.mipmap.whitesquare);
                                    homeCopy[playerNum][iterator] = -2;
                                }
                                Log.i("enabledstm", Integer.toString(rollVal - (54 - i)-1));
                            }
                            possibleMove = true;
                        }
                    }

                }


                if ((i + rollVal) == 6 || (i + rollVal) == 20 || (i + rollVal) == 34 || (i + rollVal) == 48) //if the player can directly land on middle shortcut
                {
                    if (gameBoardCopy[56] != playerNum) {
                        if (enable) {
                            this.gameBoard[56].setEnabled(true); //enable middle
                            if(gameBoardCopy[56] == -1) {
                                this.gameBoard[56].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[56] = -2;
                            }
                            Log.i("enabledrv", Integer.toString(56));
                        }
                        possibleMove = true;
                    }
                }

                if (i == 5 || i == 19 || i == 33 || i == 47) //if the player is on a corner shortcut
                {
                    int moveSpace;
                    int moveSpace2;
                    int moveSpace3;
                    int moveSpace4;


                    if (rollVal == 1) //1 shortcut
                    {
                        moveSpace = i +1;
                        moveSpace2 = i + 14;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                            if (enable) {
                                this.gameBoard[moveSpace].setEnabled(true);
                                if(gameBoardCopy[moveSpace] == -1) {
                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                    gameBoardCopy[moveSpace] = -2;
                                }
                                Log.i("enabledmovespace", Integer.toString(moveSpace));
                            }
                            possibleMove = true;
                        }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                                if (enable) {
                                    this.gameBoard[moveSpace2].setEnabled(true);
                                    if(gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace2));
                                }
                                possibleMove = true;
                            }
                        }


                    }
                    if (rollVal == 2) //1 shortcut
                    {
                        moveSpace = i + 2;
                        moveSpace2 = i + 14 + 1;
                        moveSpace3 = i + 14*rollVal;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    if(gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                        if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                            if (enable) {
                                this.gameBoard[moveSpace2].setEnabled(true);
                                if(gameBoardCopy[moveSpace2] == -1) {
                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                    gameBoardCopy[moveSpace2] = -2;
                                }
                                Log.i("enabledmovespace", Integer.toString(moveSpace2));
                            }
                            possibleMove = true;
                        }
                         }
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)){

                                if (enable) {
                                    this.gameBoard[moveSpace3].setEnabled(true);
                                    if(gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace3));
                                }
                                possibleMove = true;
                            }
                        }



                    }
                    if (rollVal == 3) //1 shortcut
                    {
                        moveSpace = i + 3;
                        moveSpace2 = i + 14 + 2;
                        moveSpace3 = i + 14*2 + 1;
                        moveSpace4 = i + 14*rollVal;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;
                        }
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    if(gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                                if (enable) {
                                    this.gameBoard[moveSpace2].setEnabled(true);
                                    if(gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace2));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)){

                                if (enable) {
                                    this.gameBoard[moveSpace3].setEnabled(true);
                                    if(gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace3));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)){

                                if (enable) {
                                    this.gameBoard[moveSpace4].setEnabled(true);
                                    if(gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace4));
                                }
                                possibleMove = true;
                            }
                        }

                    }
                    if (rollVal == 4) //1 shortcut
                    {
                        moveSpace = i + 4;
                        moveSpace2 = i + 14 + 3;
                        moveSpace3 = i + 14*2 + 2;
                        moveSpace4 = i + 14*3 + 1;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;
                        }
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    if(gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                                if (enable) {
                                    this.gameBoard[moveSpace2].setEnabled(true);
                                    if(gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace2));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)){

                                if (enable) {
                                    this.gameBoard[moveSpace3].setEnabled(true);
                                    if(gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace3));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)){

                                if (enable) {
                                    this.gameBoard[moveSpace4].setEnabled(true);
                                    if(gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace4));
                                }
                                possibleMove = true;
                            }
                        }

                    }
                    if (rollVal == 5) //1 shortcut
                    {
                        moveSpace = i + 5;
                        moveSpace2 = i + 14 + 4;
                        moveSpace3 = i + 14*2 + 3;
                        moveSpace4 = i + 14*3 + 2;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;
                        }
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    if(gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                                if (enable) {
                                    this.gameBoard[moveSpace2].setEnabled(true);
                                    if(gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace2));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)){

                                if (enable) {
                                    this.gameBoard[moveSpace3].setEnabled(true);
                                    if(gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace3));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)){

                                if (enable) {
                                    this.gameBoard[moveSpace4].setEnabled(true);
                                    if(gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace4));
                                }
                                possibleMove = true;
                            }
                        }
                    }
                    if (rollVal == 6) //1 shortcut
                    {
                        moveSpace = i + 6;
                        moveSpace2 = i + 14 + 5;
                        moveSpace3 = i + 14*2 + 4;
                        moveSpace4 = i + 14*3 + 3;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;
                        }
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;
                        }
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;
                        }
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;
                        }

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)){

                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    if(gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)){

                                if (enable) {
                                    this.gameBoard[moveSpace2].setEnabled(true);
                                    if(gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace2));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)){

                                if (enable) {
                                    this.gameBoard[moveSpace3].setEnabled(true);
                                    if(gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace3));
                                }
                                possibleMove = true;
                            }
                        }
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)){

                                if (enable) {
                                    this.gameBoard[moveSpace4].setEnabled(true);
                                    if(gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;
                                    }
                                    Log.i("enabledmovespace", Integer.toString(moveSpace4));
                                }
                                possibleMove = true;
                            }
                        }
                    }
                }
                if (i == 56 && rollVal == 1) //if the player is in the middle space
                {
                    if (gameBoardCopy[5] != playerNum) {
                        if (enable) {
                            this.gameBoard[5].setEnabled(true);
                            if(gameBoardCopy[5] == -1) {
                                this.gameBoard[5].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[5] = -2;
                            }
                            Log.i("enabled", Integer.toString(5));
                        }
                        possibleMove = true;
                    }
                    if (gameBoardCopy[19] != playerNum) {
                        if (enable) {
                            this.gameBoard[19].setEnabled(true);
                            if(gameBoardCopy[19] == -1) {
                                this.gameBoard[19].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[19] = -2;
                            }
                            Log.i("enabled", Integer.toString(19));
                        }
                        possibleMove = true;
                    }
                    if (gameBoardCopy[33] != playerNum) {
                        if (enable) {
                            this.gameBoard[33].setEnabled(true);
                            if(gameBoardCopy[33] == -1) {
                                this.gameBoard[33].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[33] = -2;
                            }
                            Log.i("enabled", Integer.toString(33));
                        }
                        possibleMove = true;
                    }
                    if (gameBoardCopy[47] != playerNum) {
                        if (enable) {
                            this.gameBoard[47].setEnabled(true);
                            if(gameBoardCopy[47] == -1) {
                                this.gameBoard[47].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[47] = -2;
                            }
                            Log.i("enabled", Integer.toString(47));
                        }
                        possibleMove = true;
                    }

                }



        }


    return possibleMove;
}
     /**
     * this method checks whether a possible move would move one player's piece ahead of one of his/her
     * pieces. If it would, returns false (illegal move). Otherwise returns true.
     *
     * Helper Method!
     *
     * @param pieceLocations locations of the player's pieces
     * @param playerNum player number
     * @param startMove space on board the piece currently is
     * @param endMove space of the proposed move
     * @return true is move is legal. False otherwise
     */
    public boolean checkPieceOrder(int[] pieceLocations, int playerNum, int startMove, int endMove)
    {
        //Resets everything on a scale of 0-56 to check any player Num
        for (int i = 0; i<4;i++)
        {
            pieceLocations[i] = pieceLocations[i] + playerNum*14;
        }
        int startMove1 = startMove + playerNum*14;
        int endMove1 = endMove + playerNum*14;
        for (int j = 0; j<4; j++) //something in here is wrong
        {
            Log.i("start is ", Integer.toString(startMove1));
            Log.i("endMOve is", Integer.toString(endMove1));
            Log.i("checking on piece", Integer.toString(pieceLocations[j])); //Somehow this value is -999 when on a shortcut, Emily

            Log.i("inside", "checkPieceOrder");
            if (pieceLocations[j]!= startMove1 && pieceLocations[j] > startMove1 && pieceLocations[j] <endMove1)
            { Log.i("no move", "here");
                return false;}
        }
        Log.i("true","move away!");
        return true;
    }

    //makes sure a piece can't skip another shortcut piece (checklocations but for shorctuts)
    public boolean checkPieceOrderShortcut(int []pieceLocations, int playerNum, int startMove, int endMove)
    {
        int [] pieceLocNew = new int[4];
        for (int i = 0; i<4;i++)
        {
            pieceLocNew[i] = pieceLocations[i] + playerNum*14;
        }
        int startMove1 = startMove + playerNum*14;
        int endMove1 = endMove + playerNum*14;

        for (int i = 0; i<4; i++) {
            if (pieceLocations[i] == 5 || pieceLocations[i] == 19 ||
                    pieceLocations[i] == 33 || pieceLocations[i] == 47)
            {
                if (pieceLocations[i] != startMove)
                {
                    if (startMove1 < pieceLocNew[i] && endMove1 > pieceLocNew[i])
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**
     * this method gets called when the user clicks the die or a button space. It
     * creates a new AggravationRollAction or AggravationMovePieceAction and sends it to the game,
     * or updates the user's display if is is just showing possible moves.
     *
     * @param button
     *        the button that was clicked
     */
    boolean checkPieces = false;
    int markedButton = -1; //holds the most recently pressed player piece button
    int[] currentPieceLocations = new int[4]; //holds locations of player pieces
    int cpLi; //iterator for current Piece Location

    public void onClick(View button) {
         if (gameStateInfo == null)
         {
             return;
         }

        Log.i("Start", "onClick");

        int rollVal = gameStateInfo.getDieValue();
        int myNum = playerNum;
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        String boardType = "board";

            for (int i = 0; i < 57; i++) {
                if (gameBoardCopy[i] == -2) {
                    gameBoard[i].setBackgroundResource(R.mipmap.gamesquare);
                    if(i == 5 || i == 19 || i == 33 || i == 47 || i == 56)
                    {
                        gameBoard[i].setBackgroundResource(R.mipmap.shortcut);
                    }
                    gameBoardCopy[i] = -1;
                }
            }
            for (int i = 0; i < allPlayerNames.length; i++) {
                for (int j = 0; j < 4; j++) {
                    if (homeCopy[i][j] == -2) {
                        if (playerNum == 0) {
                            playerHome[i][j].setBackgroundResource(R.mipmap.greensquarehome);
                            homeCopy[i][j] = -1;
                        }
                        if (playerNum == 1) {
                            playerHome[i][j].setBackgroundResource(R.mipmap.pinksquarehome);
                            homeCopy[i][j] = -1;
                        }
                        if (playerNum == 2) {
                            playerHome[i][j].setBackgroundResource(R.mipmap.redsquarehome);
                            homeCopy[i][j] = -1;
                        }
                        if (playerNum == 3) {
                            playerHome[i][j].setBackgroundResource(R.mipmap.greysquarehome);
                            homeCopy[i][j] = -1;
                        }
                    }
                }
            }


        if(button == newGameButton)
        {
            AggravationNewGameAction newGame = new AggravationNewGameAction(this);
            game.sendAction(newGame);
            Log.i("New Game Button", " Clicked");
            return;
        }
        Log.i("clicked", "click");


        if (button == dieImageButton) { //if the die has been rolled, enable player's buttons
            AggravationRollAction roll = new AggravationRollAction(this);
            game.sendAction(roll);
            checkPieces = true;
            return;

        }


        else //(NORMAL BUTTONS)
        {
            Log.i("roll val is ", Integer.toString(rollVal));
            Log.i("PlayerNum", Integer.toString(playerNum));
            boolean enabled = false;
            int clickedIdx = -99;
            String boardTypeCheck = "";

            for (int k = 0; k < 57; k++) { //first checks to see if it is a click to move a piece -->clicking on a blank space
                if (button == this.gameBoard[k] && gameBoardCopy[k] != playerNum) {
                    Log.i("k ", Integer.toString(k));
                    Log.i("marked Button", Integer.toString(markedButton));
                    if (k == playerNum*14)
                    {
                        boardType = "Start"; //
                    }
                    if (k == 56 || markedButton == 5 || markedButton ==19 || markedButton ==33 ||markedButton ==47)
                    {
                        boardType = "shortcut";
                    }
                    if(k!= playerNum*14 && k != 56 && k < 56)
                    {
                        boardType = "board";
                    }
                    //if ()Owen wants to make every shortcut move say shortcut
                    AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, k);
                    Log.i("sending move board", Integer.toString(markedButton));
                    Log.i("move is boardType", boardType);
                    Log.i("from space", Integer.toString(markedButton));
                    Log.i("to space ", Integer.toString(k));
                    game.sendAction(move);
                    return;
                }
                //conditions
            }

            for (int l = 0; l <4; l++)
            {
                Log.i("got into l ", "loop");
                if (button == this.playerHome[playerNum][l] && homeCopy[playerNum][l] != playerNum)
                {
                    boardType = "Home";
                    AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, l);
                    game.sendAction(move);
                    Log.i("sent action", "home");
                    Log.i("from space", Integer.toString(markedButton));
                    Log.i("to space", Integer.toString(l));
                    return;
                }
            }

            //Searches board for clicked button, and disables all non player(starting precaution) -->click on own piece
            for (int i = 0; i < 57; i++) {
                if (button == this.gameBoard[i] && gameBoardCopy[i] == playerNum) //finds the button index
                {
                    clickedIdx = i;
                    boardTypeCheck = "board";

                }

                else if (gameBoardCopy[i] != playerNum && this.gameBoard[i] != button) //if it's not a player button, disable
                {
                    gameBoard[i].setEnabled(false);
                }
            }

            //searches start array for clicked button and disables all non piece buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerStart[playerNum][m] )
                {
                        if (startCopy[playerNum][m] == playerNum) {
                            clickedIdx = m;
                            boardTypeCheck = "start";
                        }
                }
                else if (startCopy[playerNum][m] != playerNum){ //disable if it's not the player's piece

                    playerStart[playerNum][m].setEnabled(false);
                }
            }

            //checks through home array to find button clicked and disables all non player buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerHome[playerNum][m]) {
                    if (homeCopy[playerNum][m] == playerNum) {
                        clickedIdx = m;
                        boardTypeCheck = "home";
                    }
                }
                else if (homeCopy[playerNum][m] != playerNum && playerHome[playerNum][m] != button) //disable if it's not the player's piece &&
                {
                    playerHome[playerNum][m].setEnabled(false);
                    Log.i("disabled", Integer.toString(m));
                }
            }

            if (clickedIdx >-1) //if the player clicked on its own piece --> own piece enabing moves
            {

                Log.i("enabling board type",boardTypeCheck);
                Log.i("for position", Integer.toString(clickedIdx));
                if (Moves(boardTypeCheck, clickedIdx, true))
                {

                        //AggravationMovePieceAction move = new AggravationMovePieceAction(this,boardTypeCheck,clickedIdx, rollVal+clickedIdx);
                        //game.sendAction(move); THIS MAKES MOVES AUTOMATIC
                    }
                    Log.i("move is ", "true");} //enable possible moves for that clicked piece

            }

                //When the player clicks on an available space to make a move
        for (int z = 0; z<4; z++) {
            if (playerHome[0][z].isEnabled()) {
                Log.i("clickable space2", Integer.toString(z));
            }
        }


            Log.i("end of ", "on click");
        }




// onClick


    /**
 * callback method--our game has been chosen/rechosen to be the GUI,
 * called from the GUI thread
 *
 * @param activity
 *        the activity under which we are running
 */
    public void setAsGui(GameMainActivity activity) {



        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Listen for button presses
        this.yourTurn = (TextView)activity.findViewById(R.id.yourTurn);
        this.rollView = (TextView)activity.findViewById(R.id.rollView);
        this.dieImageButton = (ImageButton)activity.findViewById(R.id.RollButton);
        this.newGameButton = (Button)activity.findViewById(R.id.newGameButton);
        this.illegalMove=(TextView) activity.findViewById(R.id.illegalMove);
        newGameButton.setOnClickListener(this);
        Log.i("HERE","HERE");
        dieImageButton.setOnClickListener(this);
        Log.i("die image button", "created");
        //Initialize the widget reference member variables

        //ALL THOSE BUTTONS GO HERE
        for (int i = 0; i<57; i++) {
            this.gameBoard[i] = (ImageButton) activity.findViewById(gameBoardIDS[i]);
            this.gameBoard[i].setOnClickListener(this);
        }
        int count = 0;
        for(int i = 0; i < 4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                this.playerStart[i][j] = (ImageButton) activity.findViewById(playerStartIDS[count]);
                this.playerStart[i][j].setOnClickListener(this);
                Log.i("count is", Integer.toString(count));
                count++;
            }
        }

        int count2 = 0;
        for(int i = 0; i < 4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                this.playerHome[i][j] = (ImageButton) activity.findViewById(playerHomeIDS[count2]);
                this.playerHome[i][j].setOnClickListener(this);
                Log.i("count2 is ", Integer.toString(count2));
                count2++;
            }
        }

    }//setAsGui

}// class AggravationHumanPlayer
