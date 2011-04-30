package com.dxmio.games.breakout;

import java.awt.BasicStroke;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;

import com.dxmio.games.breakout.Constants.*;

/**
 * @author byte
 *
 * Represents the brick.
 */
public class Brick
{
    private Color _color;
    private int _status;
    private PlayField _parentPlayField;
    private boolean _needsRedraw;
    
    private Point2D.Double _position;
    
    private int _pixelWidth;
    private int _pixelHeight;
    
    private Rectangle2D.Double _collisionRectangle;
    
    /**
     * Instantiates a new brick.
     * 
     * @param parentPlayField The playfield to use.
     * @param x Horizontal coordinate.
     * @param y Vertical coordinate.
     */
    public Brick(PlayField parentPlayField, int x, int y)
    {
        
        _parentPlayField = parentPlayField;
        _status = brickStatus.hard;
        _color = brickColor.normal;
        init();
        _needsRedraw = true;
        
        _pixelWidth = getGameOptions().get("fieldPixelWidth") / playFieldSettings.maxBricksHorizontal;
        _pixelHeight = (getGameOptions().get("fieldPixelHeight") / playFieldSettings.maxBricksVertical) / 2;
        
        _position = new Point2D.Double((x * _pixelWidth), (y * _pixelHeight));
        
        _collisionRectangle = new Rectangle2D.Double(_position.x-2, _position.y-2, _pixelWidth+2, _pixelHeight+2);
    }
    
    /**
     * Collision detection.
     * 
     * @param myGraphics The graphics object to use.
     * @return Whether a hit occurred.
     */
    public boolean hit(Graphics2D myGraphics)
    {
        Point2D.Double ballPosition = getBall().getPosition();

        if((ballPosition.y < _position.y) || (ballPosition.y > (_position.y + _pixelHeight)))
        {
            getCanvas().flipMovementY();
        }
        else if((ballPosition.x < _position.x) || (ballPosition.x > (_position.x + _pixelWidth)))
        {
            getCanvas().flipMovementX();
        }
        
        if(_status > 0)
        {
            _status--;
            if(_status == 0)
                erase(myGraphics);
            else
                updateColor();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private BasicCanvas getCanvas()
    {
        return getPlayField().getCanvas();
    }
    
    protected void setPosition(int x, int y)
    {
        _position = new Point2D.Double((x * _pixelWidth), (y * _pixelHeight));
    }

    protected Point2D.Double getPosition()
    {
        return _position;
    }
    
    protected Rectangle2D.Double getCollisionRectangle()
    {
        return _collisionRectangle;
    }
    
    private PlayField getPlayField()
    {
        return _parentPlayField;
    }
    
    private Ball getBall()
    {
        return getPlayField().getBall();
    }
    
    private void init()
    {
        setStatus(getGameOptions().get("hitsToRemoveBrick"));
    }
    
    protected Hashtable<String, Integer> getGameOptions()
    {
        return _parentPlayField.getGameOptions();
    } 
    
    protected boolean getNeedsRedraw()
    {
        return _needsRedraw;
    }
    
    /**
     * Sets the status.
     * 
     * @param sts The status.
     */
    public void setStatus(int sts)
    {
        _status = sts;
        updateColor();
        _needsRedraw = true;
    }
    
    /**
     * @return The status.
     */
    public int getStatus()
    {
        return _status;
    }
    
    /**
     * @return The color.
     */
    public Color getColor()
    {
        return _color;
    }
    

    
    private void updateColor()
    {
        if(_status > brickStatus.mediumBroken)
            _color = brickColor.normal;
        else if(_status == brickStatus.mediumBroken)
            _color = brickColor.cracked;
        
    }
    
    /**
     * Draws the brick.
     * 
     * @param brickGraphic The graphics object to draw with.
     */
    public void draw(Graphics2D brickGraphic)
    {
        _pixelWidth = getGameOptions().get("fieldPixelWidth") / playFieldSettings.maxBricksHorizontal;
        _pixelHeight = (getGameOptions().get("fieldPixelHeight") / playFieldSettings.maxBricksVertical) / 2;

        if(_status > brickStatus.broken)
        {
            //inside fill
            GradientPaint brickGradient = new GradientPaint((float)_position.x+2, 
                                                            (float)_position.y+2, 
                                                            _color, 
                                                            (float)_position.x+2, 
                                                            (float)_position.y+2+_pixelHeight+20, 
                                                            Color.BLACK);

            brickGraphic.setPaint(brickGradient);
            brickGraphic.fillRect((int)_position.x+2, (int)_position.y+2, _pixelWidth-2, _pixelHeight-2);

            //border
            brickGraphic.setStroke(new BasicStroke(1));
            brickGraphic.setPaint(Color.GRAY);
            brickGraphic.drawRect((int)_position.x, (int)_position.y, _pixelWidth, _pixelHeight);
            
            updateCollisionRectangle(_position.x, _position.y, _pixelWidth, _pixelHeight);
        }

        _needsRedraw = false;
    }
    
    /**
     * Erases the brick.
     * 
     * @param brickGraphic The graphics object to draw with.
     */
    public void erase(Graphics2D brickGraphic)
    {
        //GradientPaint brickGradient = new GradientPaint()
        brickGraphic.setPaint(Color.WHITE);
        brickGraphic.fillRect((int)_position.x+2, (int)_position.y+2, _pixelWidth-2, _pixelHeight-2);
        //border
        brickGraphic.setStroke(new BasicStroke(1));
        brickGraphic.setPaint(Color.WHITE);
        brickGraphic.drawRect((int)_position.x, (int)_position.y, _pixelWidth, _pixelHeight);
        
        updateCollisionRectangle(_position.x, _position.y, _pixelWidth, _pixelHeight);
    }
    
    private void updateCollisionRectangle(double x, double y, int width, int height)
    {
        _collisionRectangle.x = x-2;
        _collisionRectangle.y = y-2;
        _collisionRectangle.width = width+2;
        _collisionRectangle.height = height+2;
    }
     

}
