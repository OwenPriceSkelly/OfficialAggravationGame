package edu.up.cs301.AggravationGame;


import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * class to handle move piece actions
 *
 * @author Emily Peterson, Andrew Ripple, & Owen Price
 *
 * @version November 2016
 */
public class AggravationMovePieceAction extends GameAction implements Serializable {

    private static final long serialVersionUID = -5109179064390136954L;
    int oldIdx; //index where piece is moving from
    int newIdx; //index where piece wants to move to
    String type;//Start, Board, or Home
    //Local game looks at old index and new and makes sure it is a valid move
    public AggravationMovePieceAction(GamePlayer player, String type, int oldIdx, int newIdx)
    {
        super(player);
        this.oldIdx=oldIdx;
        this.newIdx=newIdx;
        this.type=type;
    }
}


//class AggravationMovePieceAction