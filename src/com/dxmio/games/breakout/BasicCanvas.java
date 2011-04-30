package com.dxmio.games.breakout;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.*;

/**
 * @author byte
 *
 * The canvas.
 */
@SuppressWarnings("serial")
public class BasicCanvas extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{

    protected JLabel _statusBar;
    private PlayField _playField;
    private BasicFrame _parentFrame;
    private Timer _ballTimer;
    
    private double _currentMovementX;
    private double _currentMovementY;
    
    /**
     * Instantiates a new canvas.
     * 
     * @param theFrame The frame to use.
     */
    public BasicCanvas(BasicFrame theFrame)
    {
        _parentFrame = theFrame;
        _statusBar = theFrame.getStatusBar();
        _playField = new PlayField(this, getGameOptions().get("brickCount"));

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(getGameOptions().get("fieldPixelWidth"), getGameOptions().get("fieldPixelHeight")));

        addMouseListener(this);
        addMouseMotionListener(this);
        
        _currentMovementX = Constants.ballMovement.initialXmovement;
        _currentMovementY = Constants.ballMovement.initialYmovement;
        
        _ballTimer = new Timer(Constants.ballMovement.ballSpeed, this);
        //_ballTimer.setActionCommand("Timer");  //1.6 only
        _ballTimer.start();
        
        repaint();

    }

    
    protected void newPlayField()
    {
        _playField = new PlayField(this, getGameOptions().get("brickCount"));
    }
    
    protected BasicFrame getParentFrame()
    {
        return _parentFrame;
    }
    
    protected Hashtable<String, Integer> getGameOptions()
    {
        return getParentFrame().getGameOptions();
    }
    
    protected Paddle getPaddle()
    {
        return _playField.getPaddle();
    }
    
    protected Ball getBall()
    {
        return _playField.getBall();
    }
    
    protected PlayField getPlayField()
    {
        return _playField;
    }

    protected void togglePause()
    {
        _parentFrame.togglePause();
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        togglePause();
    }

    public void mouseMoved(MouseEvent e)
    {
        Point paddlePoint = e.getPoint();
        Graphics2D myGraphics = (Graphics2D)getGraphics();

        getPaddle().erase(myGraphics);
        getPaddle().updatePosition(paddlePoint.x);
        getPaddle().draw(myGraphics);
    }

    protected void paintComponent(Graphics myGraphics)
    {
        super.paintComponent(myGraphics);
        Graphics2D g = (Graphics2D)myGraphics;
        
        setPreferredSize(new Dimension(getGameOptions().get("fieldPixelWidth"), getGameOptions().get("fieldPixelHeight")));
        drawField(g); //redraw all the bricks



    }

    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if((action == null) && (getParentFrame().getAppState() == Constants.appStates.Initialized))
        {
            drawBall();
        }
        
    }
    
    protected void drawField(Graphics2D g)
    {
        //Graphics2D g = (Graphics2D)getGraphics();
        getPlayField().drawAllBricks(g);
        getPlayField().drawPaddle(g);
        getPlayField().drawBall(g);
    }
    
    protected void drawBall()
    {
        Ball theBall = getBall();
        Paddle thePaddle = getPaddle();
        
        Graphics2D myGraphics = (Graphics2D)getGraphics();
        //_playField.determineLevelEnded();
        
        if(theBall.getCollisionRectangle().intersects(thePaddle.getCollisionRectangle()))
        {
            getPaddle().paddleContacted();
            getFrame().collision();
        }
        else if(theBall.getCollisionRectangle().intersectsLine(getPlayField().getCeilingCollision()))
        {
            flipMovementY();
            getFrame().collision();
        }
        else if(theBall.getCollisionRectangle().intersectsLine(getPlayField().getRightWallCollision()))
        {
            flipMovementX();
            getFrame().collision();
        }
        else if(theBall.getCollisionRectangle().intersectsLine(getPlayField().getLeftWallCollision()))
        {
            flipMovementX();
            getFrame().collision();
        }
        else if(theBall.getCollisionRectangle().intersectsLine(getPlayField().getFloorCollision()))
        {
            theBall.erase(myGraphics);
            getFrame().levelFailed(myGraphics);
        }
        
        _playField.findBricksToKnock(myGraphics);
        
        theBall.erase(myGraphics);
        theBall.movePosition(_currentMovementX, _currentMovementY);
        theBall.draw(myGraphics);
        
        getFrame().setStatus();
        
        if(getPlayField().getBrickCount() == 0)
            getFrame().levelWon();
        
        
        
    }
    
    protected BasicFrame getFrame()
    {
        return _parentFrame;
    }
    
    protected void flipMovementY()
    {
        _currentMovementY = 0 - _currentMovementY;
    }

    protected void flipMovementX()
    {
        _currentMovementX = 0 - _currentMovementX;
    }
    
    protected void setCurrentMovementX(double x)
    {
        _currentMovementX = x;
    }
    
    protected void setCurrentMovementY(double y)
    {
        _currentMovementY = y;
    }
    
    protected double getCurrentMovementX()
    {
        return _currentMovementX;
    }
    
    protected double getCurrentMovementY()
    {
        return _currentMovementY;
    }
}