package edu.up.cs301.AggravationGame;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * this is the primary activity for Aggravation
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 *          modified by Emily Peterson, Andrew Ripple, & Owen Price
 *
 * @version November 2016
 */
public class AggravationMainActivity extends GameMainActivity {

    // the port number that this game will use when playing over the network
    private static final int PORT_NUMBER = 2278;

    /**
     * Create the default configuration for this game:
     * - one human player vs. one dumb computer player vs one smart computer player
     * - minimum of 2 player, maximum of 4
     *
     * @return
     * 		the new configuration object, representing the default configuration
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // Aggravation has 3 player types: human, smart cpu, and dumb cpu
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new AggravationHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new AggravationComputerPlayerSmart(name);
            }});
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new AggravationComputerPlayerDumb(name);
            }});

        // Create a game configuration class for Pig:
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Aggravation", PORT_NUMBER);
        defaultConfig.addPlayer("Human Player", 0); // player 1: a human player
        defaultConfig.addPlayer("Dumb Computer Player", 1); // player 2: a computer player
        defaultConfig.addPlayer("Smart Computer Player",2); //smart computer player
        defaultConfig.setRemoteData("Remote Human Player", "", 0);
        //defaultConfig.addPlayer("Dumb Computer Player", 3);

        return defaultConfig;
    }//createDefaultConfig

    /**
     * create a local game
     *
     * @return
     * 		the local game, a pig game
     */
    @Override
    public LocalGame createLocalGame() {
        return new AggravationLocalGame();
    }

}
 //class AggravationMainActivity