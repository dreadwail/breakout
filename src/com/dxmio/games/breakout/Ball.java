package com.dxmio.games.breakout;

import java.awt.*;
import java.awt.geom.*;
import java.util.Hashtable;

import com.dxmio.games.breakout.Constants.*;

/**
 * @author byte
 * 
 * Represents the ball.
 */
public class Ball
{
    
    private Point2D.Double _position;
    
    private int _pixelWidth;
    private int _pixelHeight;
    
    private Rectangle2D.Double _collisionRectangle;
    
    private PlayField _parentPlayField;
    
    /**
     * Instantiates a new ball.
     * 
     * @param parentPlayField The playfield object.
     */
    public Ball(PlayField parentPlayField)
    {
        _parentPlayField = parentPlayField;
       
        _pixelWidth = playFieldSettings.ballSize;
        _pixelHeight = playFieldSettings.ballSize;
        double x = (getGameOptions().get("fieldPixelWidth") / 2);
        double y = (getGameOptions().get("fieldPixelHeight") / 1.5);
        _position = new Point2D.Double(x, y);
        _collisionRectangle = new Rectangle2D.Double(_position.x-2, _position.y-2, _pixelWidth+2, _pixelHeight+2);
    }

    protected Hashtable<String, Integer> getGameOptions()
    {
        return _parentPlayField.getGameOptions();
    }
    
    protected Point2D.Double getPosition()
    {
        return _position;
    }
    
    protected Rectangle2D.Double getCollisionRectangle()
    {
        return _collisionRectangle;
    }
    
    protected void resetPosition()
    {
        double x = (getGameOptions().get("fieldPixelWidth") / 2);
        double y = (getGameOptions().get("fieldPixelHeight") / 1.5);
        updatePosition(x, y);
    }
    
    protected void updatePosition(double x, double y)
    {
        if(isValidPosition(x, y) == true)
        {
            _position.x = x;
            _position.y = y;
        }
    }
    
    protected void movePosition(double movementX, double movementY)
    {
        double newPositionX = _position.x + movementX;
        double newPositionY = _position.y + movementY;
        updatePosition(newPositionX, newPositionY);
    }
    
    private boolean isValidPosition(double x, double y)
    {
        if((x <= getGameOptions().get("fieldPixelWidth")) && (x >= 0) && (y <= getGameOptions().get("fieldPixelHeight")) && (y >= 0))
            return true;
        else
            return false;
    }
    
    /**
     * Draws the ball.
     * 
     * @param ballGraphic The graphics object to use.
     */
    public void draw(Graphics2D ballGraphic)
    {
        double upperLeftX = _position.x - (_pixelWidth/2);
        double upperLeftY = _position.y - (_pixelHeight/2);
        
        ballGraphic.setPaint(Color.BLACK);
        ballGraphic.fillOval((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);
        
        updateCollisionRectangle(upperLeftX, upperLeftY, _pixelWidth, _pixelHeight);
    }

    /**
     * Erases the ball.
     * 
     * @param ballGraphic The graphics object to use.
     */
    public void erase(Graphics2D ballGraphic)
    {
        double upperLeftX = _position.x - (_pixelWidth/2);
        double upperLeftY = _position.y - (_pixelHeight/2);
        
        ballGraphic.setPaint(Color.WHITE);
        ballGraphic.fillOval((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);
    }
    
    private void updateCollisionRectangle(double x, double y, int width, int height)
    {
        _collisionRectangle.x = x-2;
        _collisionRectangle.y = y-2;
        _collisionRectangle.width = width+2;
        _collisionRectangle.height = height+2;
    }
    

}
