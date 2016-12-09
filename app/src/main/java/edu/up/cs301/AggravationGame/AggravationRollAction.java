package edu.up.cs301.AggravationGame;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * class to handle roll die action
 *
 * @authors Emily Peterson, Andrew Ripple & Owen Price
 *
 * @version November 2016
 */
public class AggravationRollAction extends GameAction implements Serializable {
    //Allows a player to send a roll action...local game takes care of rolling, so
    //nothing else is needed to be passed in
    private static final long serialVersionUID = -5109179067533136954L;
    public AggravationRollAction(GamePlayer player)
    {
        super(player);
    }

}
 //class AggravationRollAction