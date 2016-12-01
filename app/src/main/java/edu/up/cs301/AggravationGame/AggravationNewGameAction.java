package edu.up.cs301.AggravationGame;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by ripple19 on 11/22/2016.
 */

public class AggravationNewGameAction extends GameAction implements Serializable
{

    public AggravationNewGameAction(GamePlayer player)
    {
        super(player);
    }
}
