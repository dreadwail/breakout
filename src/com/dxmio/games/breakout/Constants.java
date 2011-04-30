package com.dxmio.games.breakout;

import java.awt.Color;

/**
 * @author byte
 *
 * Game constants.
 */
public class Constants
{

    /**
     * @author byte
     * 
     * State of the game.
     */
    public enum appStates { 
        /**
         * Initialized
         */
        Initialized, 
        /**
         * Paused
         */
        Paused, 
        /**
         * GameOver
         */
        GameOver 
    }
    
    /**
     * @author byte
     *
     * Result type of dialogs.
     */
    public enum dialogResult { 
        /**
         * OK
         */
        OK, 
        /**
         * None
         */
        None, 
        /**
         * Cancel
         */
        Cancel 
    }
    
    /**
     * @author byte
     *
     * Option constants.
     */
    public static final class initialOptions
    {
        static final int brickCount = 10;
        static final int hitsToRemoveBrick = 2;
        static final int ballsPerLevel = 3;
        static final int nextLevelSpeedJump = 25;
        static final int fieldPixelWidth = 512;
        static final int fieldPixelHeight = 384;
    }

    /**
     * @author byte
     *
     * Playfield settings.
     */
    public static final class playFieldSettings
    {
        static final int minBricksHorizontal = 0;
        static final int minBricksVertical = 0;
        static final int maxBricksHorizontal = 10;
        static final int maxBricksVertical = 10;
        static final int ballSize = 14;
    }
    
    /**
     * @author byte
     *
     * Status of bricks.
     */
    public static final class brickStatus
    {
        static final int hard = 4;
        static final int mediumHard = 3;
        static final int medium = 2;
        static final int mediumBroken = 1;
        static final int broken = 0;
    }
    
    /**
     * @author byte
     *
     * Color of bricks.
     */
    public static final class brickColor
    {
        static final Color normal = Color.BLUE;
        static final Color cracked = Color.RED;
    }
    
    /**
     * @author byte
     *
     * Movement of ball.
     */
    public static final class ballMovement
    {
        static final double initialXmovement = 2;
        static final double initialYmovement = 2; //negative is up, positive is down
        static final int ballSpeed = 20; //in milis
    }
    
}
