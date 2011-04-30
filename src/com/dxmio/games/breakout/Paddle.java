package com.dxmio.games.breakout;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;

import com.dxmio.games.breakout.Constants.*;

/**
 * @author byte
 *
 * Represents the paddle.
 */
public class Paddle
{
    private Point2D.Double _position;
    
    private int _pixelWidth;
    private int _pixelHeight;
    
    private Rectangle2D.Double _collisionRectangle;
    
    private PlayField _parentPlayField;
    
    /**
     * @param parentPlayField
     * 
     * Instantiates a paddle.
     */
    public Paddle(PlayField parentPlayField)
    {
        _parentPlayField = parentPlayField;
        _pixelWidth = (int)((getGameOptions().get("fieldPixelWidth") / playFieldSettings.maxBricksHorizontal) * 1.8);
        _pixelHeight = (int)((getGameOptions().get("fieldPixelHeight") / playFieldSettings.maxBricksVertical) / 3);
       
        double x = (((getGameOptions().get("fieldPixelWidth")) / 2) - (_pixelWidth / 2));
        double y = getGameOptions().get("fieldPixelHeight") - (_pixelHeight * 2);
        
        _position = new Point2D.Double(x,y);
        
        _collisionRectangle = new Rectangle2D.Double(_position.x-2, _position.y-2, _pixelWidth+2, _pixelHeight+2);
    }
    
    protected void paddleContacted()
    {

        double angles[] = 
        {
          Math.toRadians(157.5),
          Math.toRadians(142.5),
          Math.toRadians(127.5),
          Math.toRadians(112.5),
          Math.toRadians(97.5),
          Math.toRadians(82.5),
          Math.toRadians(67.5),
          Math.toRadians(52.5),
          Math.toRadians(37.5),
          Math.toRadians(22.5)
        };

        int hitPos = (int)(getBall().getPosition().x - _position.x + _pixelWidth/2); 

        int angle = 0;
        for ( angle = angles.length-1; angle > 0; angle--)
            if ( hitPos >= _pixelWidth/angles.length * angle )
                break;

        double movX = getCanvas().getCurrentMovementX();
        double movY = getCanvas().getCurrentMovementY();
        double speed = Math.sqrt( movX*movX + movY*movY );
        double newMovX =  speed * Math.cos(angles[angle]);
        double newMovY = -speed * Math.sin(angles[angle]);

        getCanvas().setCurrentMovementX(newMovX);
        getCanvas().setCurrentMovementY(newMovY);

    }
   
    private Ball getBall()
    {
        return getPlayField().getBall();
    }
    
    private BasicCanvas getCanvas()
    {
        return getPlayField().getCanvas();
    }
    
    private PlayField getPlayField()
    {
        return _parentPlayField;
    }
    
    protected Point2D.Double getPosition()
    {
        return _position;
    }
    
    protected Rectangle2D.Double getCollisionRectangle()
    {
        return _collisionRectangle;
    }
    
    protected boolean isPointOnSurface(Point testPoint)
    {

        double upperLeftX = _position.x - (_pixelWidth/2);
        double upperRightX = _position.x + (_pixelWidth/2);
        
        if((testPoint.y == _position.y) && (testPoint.x < upperRightX) && (testPoint.x > upperLeftX))
            return true;
        else
            return false;
        
    }

    protected Hashtable<String, Integer> getGameOptions()
    {
        return _parentPlayField.getGameOptions();
    }
    
    protected void updatePosition(int x)
    {
        if(isValidPosition(x) == true)
            _position.x = x;
    }
    
    private boolean isValidPosition(int x)
    {
        if((x <= getGameOptions().get("fieldPixelWidth")) && (x >= 0))
            return true;
        else
            return false;
    }
    
    /**
     * Draws the paddle.
     * 
     * @param paddleGraphic The graphics object to draw with.
     */
    public void draw(Graphics2D paddleGraphic)
    {

        _pixelWidth = (int)((getGameOptions().get("fieldPixelWidth") / playFieldSettings.maxBricksHorizontal) * 1.8);
        _pixelHeight = (int)((getGameOptions().get("fieldPixelHeight") / playFieldSettings.maxBricksVertical) / 3);
        
        _position.y = getGameOptions().get("fieldPixelHeight") - (_pixelHeight * 2);
        
        double upperLeftX = _position.x - (_pixelWidth/2);
        double upperLeftY = _position.y - (_pixelHeight/2);
        
        //inside fill
        GradientPaint paddleGradient = new GradientPaint((float)_position.x, 
                                                        (float)_position.y, 
                                                        Color.BLACK, 
                                                        (float)_position.x, 
                                                        (float)_position.y+_pixelHeight, 
                                                        Color.LIGHT_GRAY);
        paddleGraphic.setPaint(paddleGradient);
        paddleGraphic.fillRect((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);
        //border
        paddleGraphic.setStroke(new BasicStroke(1));
        paddleGraphic.setPaint(Color.GRAY);
        paddleGraphic.drawRect((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);
        
        updateCollisionRectangle(upperLeftX, upperLeftY, _pixelWidth, _pixelHeight);

    }

    /**
     * Erases the paddle.
     * 
     * @param paddleGraphic The graphics object to draw with.
     */
    public void erase(Graphics2D paddleGraphic)
    {
        double upperLeftX = _position.x - (_pixelWidth/2);
        double upperLeftY = _position.y - (_pixelHeight/2);

        paddleGraphic.setPaint(Color.WHITE);
        paddleGraphic.fillRect((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);
        //border
        paddleGraphic.setStroke(new BasicStroke(1));
        paddleGraphic.setPaint(Color.WHITE);
        paddleGraphic.drawRect((int)upperLeftX, (int)upperLeftY, _pixelWidth, _pixelHeight);

    }
    
    private void updateCollisionRectangle(double x, double y, int width, int height)
    {
        _collisionRectangle.x = x-2;
        _collisionRectangle.y = y-2;
        _collisionRectangle.width = width+2;
        _collisionRectangle.height = height+2;
    }
    
    
}
