package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.Serializable;

/**
 * A GUI for a human to play Aggravation.
 *
 *
 * @author Emily Peterson, Andrew Ripple & Owen Price
 * @version November 2016
 */
public class AggravationHumanPlayer extends GameHumanPlayer implements OnClickListener,Serializable {

    private static final long serialVersionUID = -5109179124333136954L;
    /* instance variables */

    // These variables will reference widgets that will be modified during play
    private ImageButton dieImageButton = null;
    private TextView yourTurn;
    private TextView rollView;
    private TextView illegalMove; //say move was illegal if done
    private Button newGameButton; //for a new game
    private MediaPlayer yourTurnSound; //sound when it is your turn
    private AggravationState gameStateInfo;  // holds copy of the game state

    private ImageButton[] gameBoard = new ImageButton[57]; //array of image buttons that is the game board
    private ImageButton[][] playerStart = new ImageButton[4][4];//array of IB but for all start arrays
    private ImageButton[][] playerHome = new ImageButton[4][4];//home arrays
    private int[] gameBoardIDS = {R.id.GameBoard0,    //array of all of the IDs of the gameboard
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
    private int[] playerStartIDS = {R.id.Start00,R.id.Start01,R.id.Start02,R.id.Start03,R.id.Start10,R.id.Start11,R.id.Start12, //ID's of the starts
            R.id.Start13,R.id.Start20,R.id.Start21,R.id.Start22,R.id.Start23,R.id.Start30,R.id.Start31,R.id.Start32,R.id.Start33};
    private int[] playerHomeIDS = {R.id.Home00,R.id.Home01,R.id.Home02,R.id.Home03,R.id.Home10,R.id.Home11,R.id.Home12,
            R.id.Home13,R.id.Home20,R.id.Home21,R.id.Home22,R.id.Home23,R.id.Home30,R.id.Home31,R.id.Home32,R.id.Home33}; //ID's of the homes

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
    public void receiveInfo(GameInfo info) { //Receives updated state from Local Game
        if(info instanceof IllegalMoveInfo) //If it's an illegal move, then tell the user
        {
            illegalMove.setText("Illegal Move.");
        }


        if (info instanceof AggravationState || info instanceof IllegalMoveInfo) { //If it's illegal or if it's a normal thing, update the UI
            illegalMove.setText(""); //no illegal move
            gameStateInfo = (AggravationState) info; //holds game state copy
            int[] temp = gameStateInfo.getGameBoard(); //temporary gameboard
            dieImageButton.setBackgroundColor(Color.WHITE);
            yourTurn.setTextColor(Color.WHITE);


            if(gameStateInfo.getRoll() == true || gameStateInfo.getRoll() == false) //If it is your turn to roll or not your turn to roll, you can make a newGame
            {
                this.newGameButton.setEnabled(true);
            }
            for (int i = 0; i < 57; i++) //setting game board to the pictures
            {

                if (temp[i] == -1) {
                    if (i == 5 || i == 19 || i == 33 || i == 47 || i == 56) {
                        this.gameBoard[i].setBackgroundResource(R.mipmap.shortcut); //makes shortcuts yellow
                        continue;
                    }

                    //if the interger array index is -1
                    this.gameBoard[i].setBackgroundResource(R.mipmap.gamesquare); //black
                } else if (temp[i] == 0) {//if the interger array index is 0
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playerzeropiece);//player zero
                } else if (temp[i] == 1) {//if the interger array index is 1
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playeronepiece);//player one
                } else if (temp[i] == 2) {//if the interger array index is 2
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playertwopiece);//player two
                } else if (temp[i] == 3) { //if the interger array index is 3
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playerthreepiece);//player three
                }
            }
            int tempStart[][] = gameStateInfo.getStartArray(); //temporary array that holds the start array integers of the game state
            for (int i = 0; i < allPlayerNames.length; i++) { //this runs through and looks at the integers in the game state array and changes the image buttons to reflect them
                for (int j = 0; j < 4; j++) {
                    if (tempStart[i][j] == -1) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.gamesquare); //Does the same thing but for the start arrays
                    }
                    else if (tempStart[i][j] == 0) { //if int array is 0, that equals player zero piece...1=1, 2=2...etc
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
                    if (tempHome[i][j] == -1) { //if integer array is -1, then its a basic game square
                        if(i == 0)
                        {   //0 indicates player zero, who is green, so home squares are set to green
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.greensquarehome);
                        }
                        if(i == 1)
                        {   //1 indicates player one, who is pink, so home squares are set to pink
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.pinksquarehome);
                        }
                        if(i == 2)
                        {   //2 indicates player two, who is red, so home squares are set to red
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.redsquarehome);
                        }
                        if(i == 3)
                        {   //3 indicates player three, who is gray, so home squares are set to grey
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.greysquarehome);
                        }
                    }
                    //However, if the integer array is not -1, then it must be occupied by a player's piece
                    else if (tempHome[i][j] == 0) {
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
                //If there are only three players, then make player three's start be blank
                this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
            }
        }
        else if(allPlayerNames.length == 2)
        {
            for (int i = 0; i < 4; i++)
            {//If there are only two players, then make player three's and two's start blank
                this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
                this.playerStart[2][i].setBackgroundResource(R.mipmap.gamesquare);
            }
        }
        else if(allPlayerNames.length == 1)
        {
            for (int i = 0; i < 4; i++)
            {//If there is only one player, then make all but player zero's pieces blank
                this.playerStart[3][i].setBackgroundResource(R.mipmap.gamesquare);
                this.playerStart[2][i].setBackgroundResource(R.mipmap.gamesquare);
                this.playerStart[1][i].setBackgroundResource(R.mipmap.gamesquare);
            }
        }


        int whoseTurn = gameStateInfo.getTurn(); //get the official turn
        if (whoseTurn == playerNum) {
            if (gameStateInfo.getRoll()) { //True only if it is your turn to roll.
                for (int i = 0; i < 57; i++) {
                    this.gameBoard[i].setEnabled(false); //disable all buttons on the gameboard
                }
                for (int i = 0; i < 4; i++)//this runs through and sets start buttons to false since only the roll die should be enabled
                {
                    for (int j = 0; j < 4; j++) {
                        this.playerStart[i][j].setEnabled(false);
                    }
                }
                for (int i = 0; i < 4; i++)//this runs through and sets home buttons to false since only the roll die should be enabled
                    for (int j = 0; j < 4; j++) {
                        this.playerHome[i][j].setEnabled(false);


                    }
                }
                this.dieImageButton.setEnabled(true);

            } else { //if it isn't your turn...do the same thing as above, but don't enable the die button

                this.dieImageButton.setEnabled(false);
                for (int i = 0; i < 57; i++) {
                    this.gameBoard[i].setEnabled(false);
                }
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++) {
                        this.playerStart[i][j].setEnabled(false);
                    }
                }
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++) {
                        this.playerHome[i][j].setEnabled(false);
                    }
                }
            }


        //Always displayed whose turn it is.
        yourTurn.setText("PLAYER " + Integer.toString(whoseTurn) + "!");

        //If it isn't your turn, this is indicated by the text next to the die
        if (whoseTurn != playerNum) {
            rollView.setText("Not Your Turn!");
        }

        if (gameStateInfo.getDieValue() == 0) {
            dieImageButton.setImageResource(R.mipmap.zeroroll); //if somehow the roll is zero
        }
        if (gameStateInfo.getDieValue() == 1) {
            dieImageButton.setImageResource(R.mipmap.face1);//drawable.face1);
        }
        if (gameStateInfo.getDieValue() == 2) {
            dieImageButton.setImageResource(R.mipmap.dietwovalue);//drawable.face2);
        }
        if (gameStateInfo.getDieValue() == 3) {
            dieImageButton.setImageResource(R.mipmap.divevaluethree);//drawable.face3);
        }
        if (gameStateInfo.getDieValue() == 4) {
            dieImageButton.setImageResource(R.mipmap.dievaluefour);//drawable.face4);
        }
        if (gameStateInfo.getDieValue() == 5) {
            dieImageButton.setImageResource(R.mipmap.diefivevalue);//drawable.face5);
        }
        if (gameStateInfo.getDieValue() == 6) {
            dieImageButton.setImageResource(R.mipmap.diesixvalue);//drawable.face6);
        }

        if (gameStateInfo.getRoll() == true && whoseTurn == playerNum)  //If it's your turn and you need to roll
        {
            rollView.setText("Your Turn! \nRoll!"); //Let's your know you need to roll
            if(playerNum == 0) { //if you're player one, set background of die to green, if you're one, to pink..same for players two and three's colors
                dieImageButton.setBackgroundColor(Color.GREEN);
                yourTurn.setTextColor(Color.GREEN);
                playSound(); //player the "Yourturn" sound when it's your turn
            }
            if(playerNum == 1) {
                int color = Color.rgb(255,192,203); //pink
                dieImageButton.setBackgroundColor(color);
                yourTurn.setTextColor(color);
                playSound();
            }
            if(playerNum == 2) {
                dieImageButton.setBackgroundColor(Color.RED);
                yourTurn.setTextColor(Color.RED);
                playSound();
            }
            if(playerNum == 3) {
                dieImageButton.setBackgroundColor(Color.GRAY);
                yourTurn.setTextColor(Color.GRAY);
                playSound();
            }
            //Sets die image to whatever picture corresponds to roll
            if (gameStateInfo.getDieValue() == 0) {dieImageButton.setImageResource(R.mipmap.zeroroll);}
            if (gameStateInfo.getDieValue() == 1) {dieImageButton.setImageResource(R.mipmap.greenface1);}
            if (gameStateInfo.getDieValue() == 2) {dieImageButton.setImageResource(R.mipmap.greenface2);}
            if (gameStateInfo.getDieValue() == 3) {dieImageButton.setImageResource(R.mipmap.greenface3);}
            if (gameStateInfo.getDieValue() == 4) {dieImageButton.setImageResource(R.mipmap.greenface4);}
            if (gameStateInfo.getDieValue() == 5) {dieImageButton.setImageResource(R.mipmap.greenface5);}
            if (gameStateInfo.getDieValue() == 6) {dieImageButton.setImageResource(R.mipmap.greenface6);}
        }
        //If it's your turn and you rolled, then let's you know you need to move a piece
        if (whoseTurn == playerNum && gameStateInfo.getRoll() == false){
            rollView.setText("You Just Rolled! \n Move a Piece!");}

        if (checkPieces){
            possibleMoveChecker(); //sends blank move if no moves are possible
            checkPieces = false;}
    }// End receiveInfo


    public int getPlayerNum()
    {
        return playerNum;
    }
    //variables for onClick
    boolean checkPieces = false;
    int markedButton = -1; //holds the most recently pressed player piece button
    int[] currentPieceLocations = new int[4]; //holds locations of player pieces
    int cpLi; //iterator for current Piece Location


    /**
     * onClick
     * this method gets called when the user clicks the die or a button space. It
     * creates a new AggravationRollAction or AggravationMovePieceAction and sends it to the game,
     * or updates the user's display if is is just showing possible moves.
     *
     * @param button
     *        the button that was clicked
     */
    public void onClick(View button) {
        if (gameStateInfo == null) //if there is nothing to do
        {
            return;
        }
        if(gameStateInfo.getTurn() != playerNum)
        {
            return;
        }

        //holds values for the state of the game board arrays & die value
        int rollVal = gameStateInfo.getDieValue();
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        String boardType = "board"; //default board type

        for (int i = 0; i < 57; i++) { //goes through all pieces & sets highlighted squares back to normal squares
            if (gameBoardCopy[i] == -2) { //highlighted squares have a value of -2 (white squares)
                gameBoard[i].setBackgroundResource(R.mipmap.gamesquare);
                if(i == 5 || i == 19 || i == 33 || i == 47 || i == 56) {
                    gameBoard[i].setBackgroundResource(R.mipmap.shortcut);}//if it's a shortcut square that's white, needs to be yellow
                gameBoardCopy[i] = -1;} //set their values to -1 again
            else if(gameBoardCopy[i] == 10) //10 = an aggravated player zero piece
            {
                gameBoard[i].setBackgroundResource(R.mipmap.playerzeropiece);
                gameBoardCopy[i] = 0;
            }
            else if(gameBoardCopy[i] == 11)//11 = an aggravated player one piece
            {
                gameBoard[i].setBackgroundResource(R.mipmap.playeronepiece);
                gameBoardCopy[i] = 1;
            }
            else if(gameBoardCopy[i] == 12)//12 = an aggravated player two piece
            {
                gameBoard[i].setBackgroundResource(R.mipmap.playertwopiece);
                gameBoardCopy[i] = 2;
            }
            else if(gameBoardCopy[i] == 13)//13= an aggravated player three piece
            {
                gameBoard[i].setBackgroundResource(R.mipmap.playerthreepiece);
                gameBoardCopy[i] = 3;
            }
        }

        //sets home arrays previously highlighted to  correct piece squares
        for (int i = 0; i < allPlayerNames.length; i++) { //Does same thing as for loop above, but for the home arrays
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



        if(button == newGameButton){ //option to start a new game if enabled
            AggravationNewGameAction newGame = new AggravationNewGameAction(this);
            game.sendAction(newGame); //send a new game action
            Log.i("New Game Button", " Clicked");
            return;}

        if (button == dieImageButton) { //roll die if clicked and enabled
            AggravationRollAction roll = new AggravationRollAction(this);
            game.sendAction(roll);
            checkPieces = true;
            return;}


        else{ //(BOARD/HOME/START BUTTONS)
            int clickedIdx = -99;
            String boardTypeCheck = "";

            //=========================Player clicking on board space to move a piece============================
            for (int k = 0; k < 57; k++) { //first checks to see if it is a click to move a piece  (clicking on a blank space != to the player number)
                if (button == this.gameBoard[k] && gameBoardCopy[k] != playerNum) {
                    Log.i("k ", Integer.toString(k));
                    Log.i("marked Button", Integer.toString(markedButton));
                    if (k == playerNum*14) { //starting a piece
                        boardType = "Start";}
                    if (k == 56 || markedButton == 5 || markedButton ==19 || markedButton ==33 ||markedButton ==47) {
                        boardType = "shortcut";} //moving to the middle shortcut
                    if(k!= playerNum*14 && k != 56 && k < 56) {//normal board move
                        boardType = "board";}

                    //sends requested move action with board type, piece clicked, and "marked button" (place piece is moving from)
                    AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, k);
                    Log.i("sending move board", Integer.toString(markedButton));
                    Log.i("move is boardType", boardType);
                    Log.i("from space", Integer.toString(markedButton));
                    Log.i("to space ", Integer.toString(k));
                    game.sendAction(move);
                    return;}}

            for (int l = 0; l <4; l++){ //checks to see if it is a click to move a piece into home array
                if (button == this.playerHome[playerNum][l] && homeCopy[playerNum][l] != playerNum) {
                    boardType = "Home";
                    //sends move action for home
                    AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, l);
                    game.sendAction(move);
                    Log.i("sent action", "home");
                    Log.i("from space", Integer.toString(markedButton));
                    Log.i("to space", Integer.toString(l));
                    return;}}

            //========================Player clicking on own piece===============================================
            //Searches board for clicked button, and disables all non player(starting precaution) (player click on own piece)
            for (int i = 0; i < 57; i++) {
                if (button == this.gameBoard[i] && gameBoardCopy[i] == playerNum) {//finds the button index
                    clickedIdx = i; //marked this button as the most recently clicked in global variable
                    boardTypeCheck = "board";}

                else if (gameBoardCopy[i] != playerNum && this.gameBoard[i] != button){ //if it's not a player button, disable
                    gameBoard[i].setEnabled(false);}}

            //searches start array for clicked button and disables all non piece buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerStart[playerNum][m] ) {
                    if (startCopy[playerNum][m] == playerNum) {
                        clickedIdx = m;
                        boardTypeCheck = "start";}}
                else if (startCopy[playerNum][m] != playerNum){ //disable if it's not the player's piece
                    playerStart[playerNum][m].setEnabled(false);}}

            //checks through home array to find button clicked and disables all non player buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerHome[playerNum][m]) {
                    if (homeCopy[playerNum][m] == playerNum) {
                        clickedIdx = m;
                        boardTypeCheck = "home";}}
                else if (homeCopy[playerNum][m] != playerNum && playerHome[playerNum][m] != button){ //disable if it's not the player's piece &&
                    playerHome[playerNum][m].setEnabled(false);}}

            Moves(boardTypeCheck, clickedIdx, true); //enables possible move spaces
        }
    }


// onClick

    /**
     * possibleMoveChecker --  checks if there are possible moves. Code would be in onClick but the die value needs to be updated first
     * Purpose: to automatically end the player's turn if there are no moves he or she can make
     * */
    public void possibleMoveChecker()
    {
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        cpLi = -1; //current piece locations index
        boolean canImove = false;

        //looks through normal board to find player's pieces and record their locations
        for (int i = 0; i < 57; i++) {
            if (gameBoardCopy[i] == playerNum) {
                cpLi++; //increments index holder in cPL array
                currentPieceLocations[cpLi] = i; //adds that piece to the array{
                if(gameStateInfo.getTurn() == playerNum) {
                    this.gameBoard[i].setEnabled(true); }//enables player's buttons in game board
                if ( i == 56) { //if it is the middle space, prevents it from interfereing with order checking
                    currentPieceLocations[cpLi] = -999;}}}
        if (cpLi <3){ //if the array of player pieces is not full, it means there are pieces in the home and start arrays
            for (int i= cpLi; i<3; i++) { //sets the remaining values in the array to arbitrary Large negative value
                cpLi++;
                currentPieceLocations[cpLi] = -999;}} //this is done to prevent these home/start values from interfering

        //=============checking moves for all pieces======================
        //GAME BOARD
        for (int i = 0; i < 57; i++) {
            if (gameBoardCopy[i] == playerNum) {
                if (!canImove) { //if no possible moves have been recorded yet
                    canImove = Moves("board", i, false); //looks for possible moves (without enabling board)
                    //^^makes canImove true if there are possible options and false otherwise
                }}}
        //START & HOME ARRAYS
        for (int i = 0; i < 4; i++) {//checks buttons in start and home array
            if (startCopy[playerNum][i] == playerNum) {
                if(gameStateInfo.getTurn() == playerNum) {
                    this.playerStart[playerNum][i].setEnabled(true);}

                if (!canImove) {
                    canImove = Moves("start", i, false);}}
            if (homeCopy[playerNum][i] == playerNum) {
                if(gameStateInfo.getTurn() == playerNum) {
                    this.playerHome[playerNum][i].setEnabled(true);}
                if (!canImove) {
                    canImove = Moves("home", i, false);}}}
        //==================no moves are possible===============================
        if (!canImove) {
            AggravationMovePieceAction move = new AggravationMovePieceAction(this, "skip", -1, -1); //sends empty action
            game.sendAction(move);
            Log.i("sending", "empty move");}
    }
//possibleMoveChecker

    /**
     * Moves: checks and/or enables possible move locations for the given button
     */
    public boolean Moves(String board, int pieceLoc, boolean enable) {
        int rollVal = gameStateInfo.getDieValue();
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        String boardType = "board";
        boolean possibleMove = false;

        int topSpace = playerNum*14-2;
        if (playerNum == 0){
            topSpace = 54;}

        //if it is start value piece, checks or enables the possible move spot 0
        if (board.equals("start")) {
            if (rollVal == 1 || rollVal == 6){ //if a one or a 6 & there are pieces to move from start array, enable space
                if (enable) {
                    this.gameBoard[playerNum * 14].setEnabled(true);
                    //APPEARS ALL THE TIME IN THE CODE:
                    //IF THE BUTTON IS ENABLED, AND ITS INTERGER
                    //VALUE IS -1 == GAME SQUARE
                    //THEN SET IT TO -2, TO RECORD IT HAS BEEN
                    //CHANGED, AND THEN SET IT TO BE A WHITE SQUARE
                    if(gameBoardCopy[playerNum*14] == -1) {
                        this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.whitesquare);
                        gameBoardCopy[playerNum*14] = -2;}
                    else
                    {
                        //THIS FOR LOOP APPEARS MULTIPLE TIMES THROUGHOUT THE CODE
                        //EVERYTIME IT RUNS THROUGH THE POSSIBLE ENABLED MOVES
                        //IF THOSE POSSIBLE ENABLED MOVES ARE OTHER PLAYERS
                        //IT SETS THAT IMAGE BUTTON TO BE AN "AGGRAVATED" PIECE
                        //AND SETS THE COPY OF THE BOARD TO A NUMBER USED TO
                        //LATER FIGURE OUT IF IT WAS CHANGED
                        for(int iter = 0; iter < 4;iter++)
                        {
                            if(gameBoardCopy[playerNum*14] == iter)
                            {
                                if(iter == 0&& iter != playerNum)
                                {
                                    this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                    gameBoardCopy[playerNum*14] = 10;
                                }
                                else if(iter == 1&& iter != playerNum)
                                {
                                    this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                    gameBoardCopy[playerNum*14] = 11;
                                }
                                else if(iter == 2&& iter != playerNum)
                                {
                                    this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                    gameBoardCopy[playerNum*14] = 12;
                                }
                                else if(iter == 3&& iter != playerNum)
                                {
                                    this.gameBoard[playerNum*14].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                    gameBoardCopy[playerNum*14] = 13;
                                }
                            }
                        }
                    }}
                possibleMove = true;
                markedButton = pieceLoc; //button the move will be send from
                boardType = "start";}}

        //if checking a value in the home area, checks or enables possible move spot(s)
        if (board.equals("home")) {
            int i = pieceLoc;
            if (i + rollVal < 4 && homeCopy[playerNum][i + rollVal] != playerNum) {
                if(rollVal == 1) {
                    possibleMove = true;
                    if (enable) {
                        if(gameStateInfo.getTurn() == playerNum) {
                            this.playerHome[playerNum][rollVal + i].setEnabled(true);}
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;} }}
                if(rollVal == 2 && i < 2 && homeCopy[playerNum][i + rollVal-1] != playerNum) {
                    possibleMove = true;
                    if (enable) {
                        if(gameStateInfo.getTurn() == playerNum) {
                            this.playerHome[playerNum][rollVal + i].setEnabled(true);}
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;} }}
                if(rollVal == 3 && i < 1 && homeCopy[playerNum][i + rollVal-1] != playerNum && homeCopy[playerNum][i + rollVal-2] != playerNum) {
                    possibleMove = true;
                    if (enable) {
                        if(gameStateInfo.getTurn() == playerNum) {
                            this.playerHome[playerNum][rollVal + i].setEnabled(true);}
                        if(homeCopy[playerNum][rollVal + i] == -1) {
                            playerHome[playerNum][rollVal + i].setBackgroundResource(R.mipmap.whitesquare);
                            homeCopy[playerNum][rollVal + i] = -2;
                        }}}
            }
            markedButton = pieceLoc;
            return possibleMove; //returning here because if it's in the home array that is all we need to check
        }


        //if checking a value in the board area, checks or enables possible move spot(s)
        if (board.equals("board")) {
            int i = pieceLoc;

            if (gameBoardCopy[i] == playerNum) { //if the player clicked on its own button
                markedButton = i;
                //===================CASE: moving from middle==========================
                if (i == 56 )
                { if (rollVal != 1){
                    Log.i("56","no moves possible");
                    possibleMove = false;
                    return possibleMove;}
                    //if the player is in the middle space
                    if (gameBoardCopy[5] != playerNum) {
                        if (enable) {
                            if(gameStateInfo.getTurn() == playerNum) {
                                this.gameBoard[5].setEnabled(true);}
                            if (gameBoardCopy[5] == -1) {
                                this.gameBoard[5].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[5] = -2;} else
                            {
                                for(int iter = 0; iter < 4;iter++)
                                {
                                    if(gameBoardCopy[5] == iter)
                                    {
                                        if(iter == 0&& iter != playerNum)
                                        {
                                            this.gameBoard[5].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                            gameBoardCopy[5] = 10;
                                        }
                                        else if(iter == 1&& iter != playerNum)
                                        {
                                            this.gameBoard[5].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                            gameBoardCopy[5] = 11;
                                        }
                                        else if(iter == 2&& iter != playerNum)
                                        {
                                            this.gameBoard[5].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                            gameBoardCopy[5] = 12;
                                        }
                                        else if(iter == 3&& iter != playerNum)
                                        {
                                            this.gameBoard[5].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                            gameBoardCopy[5] = 13;
                                        }
                                    }
                                }
                            }}
                        possibleMove = true;}
                    if (gameBoardCopy[19] != playerNum) {
                        if (enable) {
                            if(gameStateInfo.getTurn() == playerNum) {
                                this.gameBoard[19].setEnabled(true);}
                            if (gameBoardCopy[19] == -1) {
                                this.gameBoard[19].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[19] = -2;} else
                            {
                                for(int iter = 0; iter < 4;iter++)
                                {
                                    if(gameBoardCopy[19] == iter)
                                    {
                                        if(iter == 0&& iter != playerNum)
                                        {
                                            this.gameBoard[19].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                            gameBoardCopy[19] = 10;
                                        }
                                        else if(iter == 1&& iter != playerNum)
                                        {
                                            this.gameBoard[19].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                            gameBoardCopy[19] = 11;
                                        }
                                        else if(iter == 2&& iter != playerNum)
                                        {
                                            this.gameBoard[19].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                            gameBoardCopy[19] = 12;
                                        }
                                        else if(iter == 3&& iter != playerNum)
                                        {
                                            this.gameBoard[19].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                            gameBoardCopy[19] = 13;
                                        }
                                    }
                                }
                            }}
                        possibleMove = true;}
                    if (gameBoardCopy[33] != playerNum) {
                        if (enable) {
                            if(gameStateInfo.getTurn() == playerNum) {
                                this.gameBoard[33].setEnabled(true);}
                            if (gameBoardCopy[33] == -1) {
                                this.gameBoard[33].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[33] = -2;}
                            else
                            {
                                for(int iter = 0; iter < 4;iter++)
                                {
                                    if(gameBoardCopy[33] == iter)
                                    {
                                        if(iter == 0&& iter != playerNum)
                                        {
                                            this.gameBoard[33].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                            gameBoardCopy[33] = 10;
                                        }
                                        else if(iter == 1&& iter != playerNum)
                                        {
                                            this.gameBoard[33].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                            gameBoardCopy[33] = 11;
                                        }
                                        else if(iter == 2&& iter != playerNum)
                                        {
                                            this.gameBoard[33].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                            gameBoardCopy[33] = 12;
                                        }
                                        else if(iter == 3&& iter != playerNum)
                                        {
                                            this.gameBoard[33].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                            gameBoardCopy[33] = 13;
                                        }
                                    }
                                }
                            }}
                        possibleMove = true;}
                    if (gameBoardCopy[47] != playerNum) {
                        if (enable)
                        {
                            if(gameStateInfo.getTurn() == playerNum) {
                                this.gameBoard[47].setEnabled(true);}
                            if (gameBoardCopy[47] == -1)
                            {
                                this.gameBoard[47].setBackgroundResource(R.mipmap.whitesquare);
                                gameBoardCopy[47] = -2;
                            }
                            else
                            {
                                for(int iter = 0; iter < 4;iter++)
                                {
                                    if(gameBoardCopy[47] == iter)
                                    {
                                        if(iter == 0&& iter != playerNum)
                                        {
                                            this.gameBoard[47].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                            gameBoardCopy[47] = 10;
                                        }
                                        else if(iter == 1&& iter != playerNum)
                                        {
                                            this.gameBoard[47].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                            gameBoardCopy[47] = 11;
                                        }
                                        else if(iter == 2&& iter != playerNum)
                                        {
                                            this.gameBoard[47].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                            gameBoardCopy[47] = 12;
                                        }
                                        else if(iter == 3&& iter != playerNum)
                                        {
                                            this.gameBoard[47].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                            gameBoardCopy[47] = 13;
                                        }
                                    }
                                }
                            }
                        }
                        possibleMove = true;
                        return possibleMove;}}

                //==================CASE: roll val "Over the Top" for player 1,2,3=====================

                if ((i + rollVal) > 55 && ((rollVal + i) - 14 * playerNum) < 54) { //player (other than 0) is moving "over the top" legally
                    int correctedSpace = rollVal + i - 56; //"over the top space"
                    if (gameBoardCopy[correctedSpace] != playerNum) {//"over the top"
                        if (checkPieceOrder(currentPieceLocations, playerNum, i, correctedSpace)) {
                            possibleMove = true;
                            if (enable) {
                                if(gameStateInfo.getTurn() == playerNum) {
                                    this.gameBoard[correctedSpace].setEnabled(true);
                                }
                                if (gameBoardCopy[correctedSpace] == -1) {
                                    gameBoard[correctedSpace].setBackgroundResource(R.mipmap.whitesquare);
                                    gameBoardCopy[correctedSpace] = -2;} else
                                {
                                    for(int iter = 0; iter < 4;iter++)
                                    {
                                        if(gameBoardCopy[correctedSpace] == iter)
                                        {
                                            if(iter == 0&& iter != playerNum)
                                            {
                                                this.gameBoard[correctedSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                gameBoardCopy[correctedSpace] = 10;
                                            }
                                            else if(iter == 1&& iter != playerNum)
                                            {
                                                this.gameBoard[correctedSpace].setBackgroundResource(R.mipmap.aggravate1);//inksquarehome);
                                                gameBoardCopy[correctedSpace] = 11;
                                            }
                                            else if(iter == 2&& iter != playerNum)
                                            {
                                                this.gameBoard[correctedSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                gameBoardCopy[correctedSpace] = 12;
                                            }
                                            else if(iter == 3&& iter != playerNum)
                                            {
                                                this.gameBoard[correctedSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                gameBoardCopy[correctedSpace] = 13;
                                            }
                                        }
                                    }
                                }}}}}

                //==================CASE: valid move for board value i + roll val (most basic move)==========

                else if (checkPieceOrder(currentPieceLocations, playerNum, i, (i + rollVal)))  {
                    possibleMove = true;
                    if (enable) {
                        if(gameStateInfo.getTurn() == playerNum) {
                            this.gameBoard[i + rollVal].setEnabled(true);} //enables that button
                        if (gameBoardCopy[i + rollVal] == -1) {
                            gameBoard[i + rollVal].setBackgroundResource(R.mipmap.whitesquare);
                            gameBoardCopy[i + rollVal] = -2;} else
                        {
                            for(int iter = 0; iter < 4;iter++)
                            {
                                if(gameBoardCopy[i + rollVal] == iter)
                                {
                                    if(iter == 0&& iter != playerNum)
                                    {
                                        this.gameBoard[i + rollVal].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                        gameBoardCopy[i + rollVal] = 10;
                                    }
                                    else if(iter == 1&& iter != playerNum)
                                    {
                                        this.gameBoard[i + rollVal].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                        gameBoardCopy[i + rollVal] = 11;
                                    }
                                    else if(iter == 2&& iter != playerNum)
                                    {
                                        this.gameBoard[i + rollVal].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                        gameBoardCopy[i + rollVal] = 12;
                                    }
                                    else if(iter == 3&& iter != playerNum)
                                    {
                                        this.gameBoard[i + rollVal].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                        gameBoardCopy[i + rollVal] = 13;
                                    }
                                }
                            }
                        }}}

                //===============CASE: Potential home array move=====
                //checks the Home Arrays
                int overLine = playerNum*14 -2;
                if(overLine == -2) {
                    overLine = 54;}
                if ( i != playerNum*14 && i != playerNum*14+1 && i != playerNum*14+2 && i != playerNum*14+3 ){
                    if ((i + rollVal) > overLine && (i + rollVal) < overLine+5 && i != 56 && i<= overLine) {
                        boolean canDoThis = true;
                        int prev = overLine;
                        for (int z = prev; z > i; z--) {
                            if (gameBoardCopy[z] == playerNum) {
                                canDoThis = false;
                                break;
                            }
                        }
                        //checkhere
                        int leftOver = (i+rollVal) - overLine - 1;
                        if (homeCopy[playerNum][leftOver] != playerNum) {
                            int iterator = leftOver;
                            for (int j = 0; j < 4; j++){ //checks to make sure it's not leapfrogging a piece already there
                                if (j <= iterator){ //only the spaces before the possible move space
                                    if (homeCopy[playerNum][j] == playerNum) {
                                        canDoThis = false;}}}
                            if (!checkPieceOrder(currentPieceLocations, playerNum, i, topSpace) && gameBoardCopy[topSpace] == playerNum)  {
                                canDoThis = false;}
                            if (canDoThis) {
                                if (enable) {
                                    playerHome[playerNum][iterator].setEnabled(true);
                                    if (homeCopy[playerNum][iterator] == -1) {
                                        playerHome[playerNum][iterator].setBackgroundResource(R.mipmap.whitesquare);
                                        homeCopy[playerNum][iterator] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[iterator] == iter)
                                            {
                                                if(iter == 0&& iter != playerNum)
                                                {
                                                    this.gameBoard[iterator].setBackgroundResource(R.mipmap.aggravate0);//reensquarehome);
                                                    gameBoardCopy[iterator] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[iterator].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[iterator] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[iterator].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[iterator] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[iterator].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[iterator] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}}

                //===================CASE: Shortcut move==========================\\
                if ((i + rollVal) == 6 || (i + rollVal) == 20 || (i + rollVal) == 34 || (i + rollVal) == 48){ //if the player can directly land on middle shortcut
                    if (gameBoardCopy[56] != playerNum)  {
                        if (checkPieceOrder(currentPieceLocations, playerNum, i, (i + rollVal -1)))  {
                            if (gameBoardCopy[i+ rollVal -1] != playerNum || rollVal == 1){
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[56].setEnabled(true);} //enable middle
                                    if (gameBoardCopy[56] == -1) {
                                        this.gameBoard[56].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[56] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[56] == iter)
                                            {
                                                if(iter == 0&& iter != playerNum)
                                                {
                                                    this.gameBoard[56].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[56] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[56].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[56] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[56].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[56] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[56].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[56] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}}

                //===================CASE: moving from a shortcut==========================\\
                if (i == 5 || i == 19 || i == 33 || i == 47){ //if the player is on a corner shortcut
                    int moveSpace;
                    int moveSpace2;
                    int moveSpace3;
                    int moveSpace4;
                    if (rollVal == 1){ //1 shortcut
                        moveSpace = i + 1;
                        moveSpace2 = i + 14;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {if(iter == 0 && iter != playerNum)
                                        {
                                            this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                            gameBoardCopy[moveSpace] = 10;
                                        }
                                        else if(iter == 1&& iter != playerNum)
                                        {
                                            this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                            gameBoardCopy[moveSpace] = 11;
                                        }
                                        else if(iter == 2&& iter != playerNum)
                                        {
                                            this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                            gameBoardCopy[moveSpace] = 12;
                                        }
                                        else if(iter == 3&& iter != playerNum)
                                        {
                                            this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                            gameBoardCopy[moveSpace] = 13;
                                        }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);
                                    }
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}
                    if (rollVal == 2){ //1 shortcut
                        moveSpace = i + 2;
                        moveSpace2 = i + 14 + 1;
                        moveSpace3 = i + 14 * rollVal;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;}
                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace3].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace3] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace3] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace3] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace3] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace3] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}
                    if (rollVal == 3){ //1 shortcut
                        moveSpace = i + 3;
                        moveSpace2 = i + 14 + 2;
                        moveSpace3 = i + 14 * 2 + 1;
                        moveSpace4 = i + 14 * rollVal;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;}
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;}

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace3].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace3] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace3] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace3] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace3] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace3] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)) {
                                if (enable) {
                                    Log.i("enablingMS4", Integer.toString(moveSpace4));
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace4].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace4] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace4] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace4] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace4] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace4] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}
                    if (rollVal == 4) {
                        moveSpace = i + 4;
                        moveSpace2 = i + 14 + 3;
                        moveSpace3 = i + 14 * 2 + 2;
                        moveSpace4 = i + 14 * 3 + 1;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;}
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;}

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {

                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace3].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace3] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace3] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace3] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace3] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace3] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace4].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace4] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace4] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace4] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace4] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace4] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}
                    if (rollVal == 5) //1 shortcut
                    {
                        moveSpace = i + 5;
                        moveSpace2 = i + 14 + 4;
                        moveSpace3 = i + 14 * 2 + 3;
                        moveSpace4 = i + 14 * 3 + 2;
                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;}
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;}

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace3].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace3] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace3] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace3] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace3] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace3] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace4].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace4] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace4] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace4] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace4] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace4] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}
                    if (rollVal == 6) //1 shortcut
                    {
                        moveSpace = i + 6;
                        moveSpace2 = i + 14 + 5;
                        moveSpace3 = i + 14 * 2 + 4;
                        moveSpace4 = i + 14 * 3 + 3;

                        if (moveSpace > 55) {
                            moveSpace = moveSpace - 56;}
                        if (moveSpace2 > 55) {
                            moveSpace2 = moveSpace2 - 56;}
                        if (moveSpace3 > 55) {
                            moveSpace3 = moveSpace3 - 56;}
                        if (moveSpace4 > 55) {
                            moveSpace4 = moveSpace4 - 56;}

                        if (gameBoardCopy[moveSpace] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace] == -1) {
                                        this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace2] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace2)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace2].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace2] == -1) {
                                        this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace2] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace2] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace2] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace2] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace2] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace2].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace2] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace3] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace3)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace3].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace3] == -1) {
                                        this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace3] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace3] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace3] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace3] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace3] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace3].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace3] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}
                        if (gameBoardCopy[moveSpace4] != playerNum) {
                            if (checkPieceOrderShortcut(currentPieceLocations, playerNum, i, moveSpace4)) {
                                if (enable) {
                                    if(gameStateInfo.getTurn() == playerNum) {
                                        this.gameBoard[moveSpace4].setEnabled(true);}
                                    if (gameBoardCopy[moveSpace4] == -1) {
                                        this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.whitesquare);
                                        gameBoardCopy[moveSpace4] = -2;} else
                                    {
                                        for(int iter = 0; iter < 4;iter++)
                                        {
                                            if(gameBoardCopy[moveSpace4] == iter)
                                            {
                                                if(iter == 0 && iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate0);//greensquarehome);
                                                    gameBoardCopy[moveSpace4] = 10;
                                                }
                                                else if(iter == 1&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate1);//pinksquarehome);
                                                    gameBoardCopy[moveSpace4] = 11;
                                                }
                                                else if(iter == 2&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate2);//(R.mipmap.redsquarehome);
                                                    gameBoardCopy[moveSpace4] = 12;
                                                }
                                                else if(iter == 3&& iter != playerNum)
                                                {
                                                    this.gameBoard[moveSpace4].setBackgroundResource(R.mipmap.aggravate3);//greysquarehome);
                                                    gameBoardCopy[moveSpace4] = 13;
                                                }
                                            }
                                        }
                                    }}
                                possibleMove = true;}}}}

            }}

        return possibleMove;
    }
    /**
     * checkPieceOrder
     * this helper method checks whether a possible move would move one player's piece ahead of one of his/her
     * pieces. If it would, returns false (illegal move). Otherwise returns true.
     *
     *
     * @param pieceLocations locations of the player's pieces
     * @param playerNum player number
     * @param startMove space on board the piece currently is
     * @param endMove space of the proposed move
     * @return true is move is legal. False otherwise
     */
    public boolean checkPieceOrder(int[] pieceLocations, int playerNum, int startMove, int endMove) {
        int startMoveNew = startMove;
        int endMoveNew = endMove;
        int pieceLocNew[] = new int[4];
        //if any pieces are "over the top" for a specific player, resets them to 55 + piece location in order to check for leapfrogging
        for (int i = 0; i<4;i++) {
            if (playerNum != 0 && pieceLocations[i] <playerNum*14) {
                pieceLocNew[i] = pieceLocations[i] + 56;}
            else{
                pieceLocNew[i] = pieceLocations[i];}}
        if (startMove < playerNum*14) { //resets startMove to scaled value
            startMoveNew = startMove +56;}
        if (endMove < playerNum*14){
            endMoveNew = endMove + 56;}
        if (startMoveNew > (playerNum*14 +54)-(endMove - startMove)){
            return false;}
        for (int j = 0; j<4; j++) { //checks leapfrogging
            if (pieceLocNew[j]!= startMoveNew && pieceLocNew[j] > startMoveNew && pieceLocNew[j] <endMoveNew) {
                return false;}}
        return true;}
    /**
     * checkPieceOrderShortcut
     * this helper method checks whether a possible move would move one player's piece ahead of one of his/her
     * pieces. If it would, returns false (illegal move). Otherwise returns true.
     *
     * for a startMove on a shorcut space
     * @param pieceLocations locations of the player's pieces
     * @param playerNum player number
     * @param startMove space on board the piece currently is
     * @param endMove space of the proposed move
     * @return true is move is legal. False otherwise
     */
    public boolean checkPieceOrderShortcut(int []pieceLocations, int playerNum, int startMove, int endMove) {
        for (int i = 0; i<4; i++) { //checks to make sure you are not leapfrogging your own piece in a shortcut
            if (pieceLocations[i] != startMove && (pieceLocations[i] == 5 || pieceLocations[i] == 19 || pieceLocations[i] ==33 || pieceLocations[i] ==47)){
                if (endMove> pieceLocations[i] && endMove < pieceLocations[i] +6){ //end piece location on straighaway after another piece
                    return false;}
                if (pieceLocations[i] == 5 && (startMove ==33 ||startMove == 47) && endMove >=19 && endMove < 19+6) {return false;}
                if (pieceLocations[i] == 5 &&  startMove == 47 && endMove>=33 && endMove <33+6) {return false;}
                if (pieceLocations[i] == 19 && (startMove == 47|| startMove == 5) && endMove >=33 && endMove < 33+6){ return false;}
                if (pieceLocations[i] == 19 && startMove == 5 && endMove>=47 && endMove < 47+6){return false;}
                if (pieceLocations[i] == 33 && (startMove == 5 || startMove == 19) && endMove >=47 && endMove < 47 +6){ return false;}
                if (pieceLocations[i] == 33 && startMove == 19 && endMove >=5 && endMove <5+6){return false;}
                if (pieceLocations[i] == 47 && (startMove == 19 || startMove == 33) && endMove >= 5 && endMove <5+6 ){return false;}
                if (pieceLocations[i] == 47 && startMove == 33 && endMove >=19 && endMove < 19+6){return false;}}}

        if (endMove >4 && endMove <12) //end move on straightaway from 4 to 11
        {for (int i = 0; i<4; i++){
            if (pieceLocations[i] >4 && pieceLocations[i] <12){ //there is a piece on that straigtaway
                if (pieceLocations[i] < endMove){return false;
                }}}}
        if (endMove >18 && endMove <26) //end move on straightaway from 18 to 25
        {for (int i = 0; i<4; i++){
            if (pieceLocations[i] >18 && pieceLocations[i] <26){ //there is a piece on that straigtaway
                if (pieceLocations[i] < endMove){return false;
                }}}}
        if (endMove >32 && endMove <40) //end move on straightaway from 32 to 39
        {for (int i = 0; i<4; i++){
            if (pieceLocations[i] >32 && pieceLocations[i] <40){ //there is a piece on that straigtaway
                if (pieceLocations[i] < endMove){return false;
                }}}}
        if (endMove >46 && endMove <54) //end move on straightaway from 46 to 53
        {for (int i = 0; i<4; i++){
            if (pieceLocations[i] >46 && pieceLocations[i] <54){ //there is a piece on that straigtaway
                if (pieceLocations[i] < endMove){return false;
                }}}}
        Log.i("pieceChecker","return true");
        return true;
    }


    public void playSound()
    {
        yourTurnSound.start();
    }



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
        //Set up the sound for your turn
        yourTurnSound = MediaPlayer.create(myActivity,R.raw.song);
        //Listen for button presses
        //Below are text views and listeners for buttons
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