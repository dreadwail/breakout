package com.dxmio.games.breakout;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Hashtable;
import java.util.Random;

import com.dxmio.games.breakout.Constants.*;

/**
 * @author byte
 *
 * Represents the play field.
 */
public class PlayField
{
    private int _minBricksHorizontal;
    private int _minBricksVertical;
    private int _maxBricksHorizontal;
    private int _maxBricksVertical;
    private Brick[][] _playSpots;
    private BasicCanvas _parentCanvas;
    private int _brickCount;
    private Paddle _thePaddle;
    private Ball _theBall;
    
    private Line2D.Double _leftWallCollision;
    private Line2D.Double _rightWallCollision;
    private Line2D.Double _ceilingCollision;
    private Line2D.Double _floorCollision;
    
    /**
     * Instantiates a new play field.
     * 
     * @param parentCanvas The canvas to use.
     * @param brickCount The count of bricks.
     */
    public PlayField(BasicCanvas parentCanvas, int brickCount)
    {
        _parentCanvas = parentCanvas;
        _brickCount = brickCount;
        _minBricksHorizontal = playFieldSettings.minBricksHorizontal;
        _minBricksVertical = playFieldSettings.minBricksVertical;
        _maxBricksHorizontal = playFieldSettings.maxBricksHorizontal;
        _maxBricksVertical = playFieldSettings.maxBricksVertical;
        _playSpots = new Brick[_maxBricksVertical][_maxBricksHorizontal];
        initField();
        
        int fieldWidth = getGameOptions().get("fieldPixelWidth");
        int fieldHeight = getGameOptions().get("fieldPixelHeight");

        _ceilingCollision = new Line2D.Double(0, 0, fieldWidth, 0);
        _floorCollision = new Line2D.Double(0, fieldHeight, fieldWidth, fieldHeight);
        _leftWallCollision = new Line2D.Double(0, 0, 0, fieldHeight);
        _rightWallCollision = new Line2D.Double(fieldWidth, 0, fieldWidth, fieldHeight);
    }
    
    protected Line2D getLeftWallCollision()
    {
        return _leftWallCollision;
    }
    
    protected Line2D getRightWallCollision()
    {
        return _rightWallCollision;
    }
    
    protected Line2D getCeilingCollision()
    {
        return _ceilingCollision;
    }
    
    protected Line2D getFloorCollision()
    {
        return _floorCollision;
    }
    
    protected void drawPaddle(Graphics2D brickDrawer)
    {
        _thePaddle.draw(brickDrawer);
    }
    
    protected void drawBall(Graphics2D ballDrawer)
    {
        _theBall.draw(ballDrawer);
    }
    
    protected void drawBrick(Graphics2D brickDrawer, int x, int y)
    {
        _playSpots[x][y].setPosition(x, y);
        _playSpots[x][y].draw(brickDrawer);
    }
    
    protected void findBricksToKnock(Graphics2D brickDrawer)
    {
        for(int x = 0; x < _maxBricksHorizontal; x++)
        {
            for(int y = 0; y < _maxBricksVertical; y++)
            {
                if((_playSpots[x][y].getStatus() > 0) && (getBall().getCollisionRectangle().intersects(_playSpots[x][y].getCollisionRectangle())))
                {
                    hitBrick(brickDrawer, x, y);
                    _playSpots[x][y].draw(brickDrawer);
                    getCanvas().getFrame().collision();
                    if(_playSpots[x][y].getStatus() == 0)
                    {
                        _brickCount--;
                        getCanvas().getFrame().scored();
                    }
                }
            }
        }

    }
    
    protected void drawAllBricks(Graphics2D brickDrawer)
    {
        
        int fieldWidth = getGameOptions().get("fieldPixelWidth");
        int fieldHeight = getGameOptions().get("fieldPixelHeight");

        _ceilingCollision = new Line2D.Double(0, 0, fieldWidth, 0);
        _floorCollision = new Line2D.Double(0, fieldHeight, fieldWidth, fieldHeight);
        _leftWallCollision = new Line2D.Double(0, 0, 0, fieldHeight);
        _rightWallCollision = new Line2D.Double(fieldWidth, 0, fieldWidth, fieldHeight);
        
        for(int x = 0; x < _maxBricksHorizontal; x++)
        {
            for(int y = 0; y < _maxBricksVertical; y++)
            {
                _playSpots[x][y].setPosition(x, y);
                _playSpots[x][y].draw(brickDrawer);
            }
        }

    }
    
    protected int getBrickCount()
    {
        return _brickCount;
    }
    
    protected Paddle getPaddle()
    {
        return _thePaddle;
    }
    
    protected Ball getBall()
    {
        return _theBall;
    }
    
    protected BasicCanvas getCanvas()
    {
        return _parentCanvas;
    }
    
    private void initField()
    {
        for(int x = 0; x < _maxBricksHorizontal; x++)
        {
            for(int y = 0; y < _maxBricksVertical; y++)
            {
                //_playSpots[x][y] = new Brick(this);
                _playSpots[x][y] = new Brick(this, x, y);
                _playSpots[x][y].setStatus(brickStatus.broken);
            }
        }
        
        _thePaddle = new Paddle(this);
        _theBall = new Ball(this);
        placeBricks();
        
    }
    
    protected Hashtable<String, Integer> getGameOptions()
    {
        return _parentCanvas.getGameOptions();
    }
    
    private void placeBricks()
    {
        Random rand = new Random();
        
        for(int n = 0; n < _brickCount; n++)
        {
            int x = 0, y = 0;
            boolean placed = false;
            while(placed == false)
            {
                x = rand.nextInt(_maxBricksHorizontal);
                y = rand.nextInt(_maxBricksVertical);
                if(_playSpots[x][y].getStatus() == brickStatus.broken)
                {
                    _playSpots[x][y].setStatus(getGameOptions().get("hitsToRemoveBrick"));
                    placed = true;
                }
                else
                {
                    continue;
                }
                    
            }
        }
    }

    protected boolean hitBrick(Graphics2D myGraphics, int x, int y)
    {
        if((isValidPosition(x,y) == true) && (_playSpots[x][y].getStatus() > 0))
            return _playSpots[x][y].hit(myGraphics);
        else
            return false;

    }

    private boolean isValidPosition(int x, int y)
    {
        if((x <= _maxBricksHorizontal) && (y <= _maxBricksVertical) && (x >= _minBricksHorizontal) && (y >= _minBricksVertical))
            return true;
        else
            return false;
    }

}
