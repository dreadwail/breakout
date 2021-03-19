package com.dxmio.games.breakout;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import sun.audio.*;

import com.dxmio.games.breakout.Constants.*;

/**
 * @author byte
 *
 * The frame for the game.
 */
@SuppressWarnings("serial")
public class BasicFrame extends JFrame implements ActionListener, WindowListener
{

    protected BasicCanvas _canvas;
    protected JLabel _statusBar;
    protected JToolBar _toolBar;
    protected JMenuBar _menuBar;
    protected Constants.appStates _appState;
    protected Hashtable<String, Integer> _gameOptions;
    
    protected int _level;
    protected int _balls;
    protected int _score;
    protected int _bricks;
    
    protected AudioStream m_as;
    
    protected JMenuItem _menuGameGo;
    protected JMenuItem _menuGamePause;
    
    protected OptionsDialog _optionsDialog;


    /**
     * Instantiates a new frame.
     */
    public BasicFrame()
    {
        
        addWindowListener(this);

        generateGameOptions(); //must be called before creation of canvas
        generateMenus();
        generateToolBar();
        generateStatusBar();
        
        setTitle("Breakout");
        
        _canvas = new BasicCanvas(this);
        
        getContentPane().add(_canvas);
        getContentPane().add(_toolBar, BorderLayout.NORTH);
        getContentPane().add(_statusBar, BorderLayout.SOUTH);
        
        pack();
        
        setResizable(false);
        
        resetGame();

        _appState = Constants.appStates.Paused;

    }
    
    protected void collision()
    {
        try
        {

            InputStream in = getClass().getResourceAsStream("/sounds/bleep.wav");
            m_as = new AudioStream(in);
            AudioPlayer.player.start(m_as);
            
        }
        catch(IOException e)
        {}

    }
    
    protected void levelFailed(Graphics2D g)
    {
        try
        {
            InputStream in = getClass().getResourceAsStream("/sounds/fail.wav");
            m_as = new AudioStream(in);
            AudioPlayer.player.start(m_as);
        }
        catch(IOException e)
        {}
        

        _balls--;
        if(_balls == 0)
        {
            resetGame();
            JOptionPane.showMessageDialog(null, "Game Over! Your score was "+_score+" and you reached level "+_level+".");
        }
        else
        {
            _canvas.getBall().resetPosition();
            _canvas.drawField(g);
        }
        repaint();
        
        _appState = Constants.appStates.Paused;
    }
    
    protected void levelWon()
    {
        //increase speed option
        double speedJump = 1 + (getGameOptions().get("nextLevelSpeedJump") / 100.0);
        
        _canvas.setCurrentMovementX(_canvas.getCurrentMovementX() * speedJump);
        _canvas.setCurrentMovementY(_canvas.getCurrentMovementY() * speedJump);
        
        _level++;
        
        resetPlayField();
        setStatus();
        repaint();
        _appState = Constants.appStates.Paused;
    }
    
    protected void resetGame()
    {
        _appState = Constants.appStates.Paused;
        resetPlayField();
        _level = 1;
        _balls = getGameOptions().get("ballsPerLevel");
        _bricks = getPlayField().getBrickCount();
        _score = 0;
    }
    
    protected void resetPlayField()
    {
        _canvas.newPlayField();
    }
    
    protected void scored()
    {
        _score++;
    }
    
    protected Constants.appStates getAppState()
    {
        return _appState;
    }
    
    protected void togglePause()
    {
        if(_appState == Constants.appStates.Paused)
        {
            _appState = Constants.appStates.Initialized;
            _menuGameGo.setEnabled(false);
            _menuGamePause.setEnabled(true);
        }
        else if(_appState == Constants.appStates.Initialized)
        {
            _appState = Constants.appStates.Paused;
            _menuGameGo.setEnabled(true);
            _menuGamePause.setEnabled(false);
        }
    }
    
    protected JLabel getStatusBar()
    {
        return _statusBar;
    }
    
    protected Hashtable<String, Integer> getGameOptions()
    {
        return _gameOptions;
    }
    
    private void generateGameOptions()
    {
        _gameOptions = new Hashtable<String, Integer>();
        _gameOptions.put("brickCount", initialOptions.brickCount);
        _gameOptions.put("hitsToRemoveBrick", initialOptions.hitsToRemoveBrick);
        _gameOptions.put("ballsPerLevel", initialOptions.ballsPerLevel);
        _gameOptions.put("nextLevelSpeedJump", initialOptions.nextLevelSpeedJump);
        _gameOptions.put("fieldPixelWidth", initialOptions.fieldPixelWidth);
        _gameOptions.put("fieldPixelHeight", initialOptions.fieldPixelHeight);
    }

    private void generateToolBar()
    {
        _toolBar = new JToolBar();
    }
    
    private void generateStatusBar()
    {
        _statusBar = new JLabel("Ready.");
    }
    
    private void generateMenus()
    {
        
        _menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenu menuGame = new JMenu("Game");
        JMenu menuHelp = new JMenu("Help");
        
        menuFile.setMnemonic('f');
        menuGame.setMnemonic('g');
        menuHelp.setMnemonic('h');

        JMenuItem menuFileExit = new JMenuItem("Exit", 'x');
        JMenuItem menuFileNew = new JMenuItem("New", 'n');
        
        _menuGameGo = new JMenuItem("Go", 'g');
        _menuGamePause = new JMenuItem("Pause", 'p');
        _menuGamePause.setEnabled(false);
        
        JCheckBoxMenuItem menuGameZoomToggle = new JCheckBoxMenuItem("Zoom 2X", false);
        menuGameZoomToggle.setMnemonic('z');
        
        JMenuItem menuGameOptions = new JMenuItem("Options...", 'o');
        
        JMenuItem menuHelpAbout = new JMenuItem("About", 'a');

        menuFileExit.addActionListener(this);
        menuFileNew.addActionListener(this);
        
        _menuGameGo.addActionListener(this);
        _menuGamePause.addActionListener(this);
        menuGameZoomToggle.addActionListener(this);
        menuGameOptions.addActionListener(this);
        
        menuHelpAbout.addActionListener(this);
        
        menuFile.add(menuFileNew);
        menuFile.add(menuFileExit);
        
        menuGame.add(_menuGameGo);
        menuGame.add(_menuGamePause);
        menuGame.add(menuGameZoomToggle);
        menuGame.add(menuGameOptions);
        
        menuHelp.add(menuHelpAbout);

        _menuBar.add(menuFile);
        _menuBar.add(menuGame);
        _menuBar.add(menuHelp);

        setJMenuBar(_menuBar);
        
    }

    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        
        if(action.equals("Exit"))
            onShutdown();
        else if(action.equals("About"))
            onAbout();
        else if(action.equals("New"))
            onNew();
        else if(action.equals("Go"))
            togglePause();
        else if(action.equals("Pause"))
            togglePause();
        else if(action.equals("Zoom 2X"))
            toggleZoom((Graphics2D)getGraphics());
        else if(action.equals("Options..."))
            onOptions();
        else if(action.equals("OK"))
            setOptions();
        else if(action.equals("Close"))
            closeOptions();
    }
    
    protected void toggleZoom(Graphics2D g)
    {
        _appState = Constants.appStates.Paused;
        
        int width = getGameOptions().get("fieldPixelWidth");
        int height = getGameOptions().get("fieldPixelHeight");
        
        if(width == initialOptions.fieldPixelWidth)
        {
            width = width * 2;
            height = height * 2;
            
        }
        else
        {
            width = width / 2;
            height = height / 2;
        }

        getGameOptions().put("fieldPixelWidth", width);
        getGameOptions().put("fieldPixelHeight", height);

        _canvas.setPreferredSize(new Dimension(getGameOptions().get("fieldPixelWidth"), getGameOptions().get("fieldPixelHeight")));
        _canvas.drawField(g);

        pack();
        repaint();

        _appState = Constants.appStates.Initialized;
    }
    
    protected void onOptions() 
    {
        if (JOptionPane.showConfirmDialog(new JFrame(), "This will reset any current games. Continue?", "Options Change", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            _optionsDialog = new OptionsDialog(this);
            _optionsDialog.setVisible(true);
        }
    } 
    
    /**
     * Sets the options.
     */
    public void setOptions()
    {
        _optionsDialog.setVisible(false);
        _gameOptions.put("brickCount", _optionsDialog.getBrickCount());
        _gameOptions.put("hitsToRemoveBrick", _optionsDialog.getHitsToRemoveBrick());
        _gameOptions.put("ballsPerLevel", _optionsDialog.getBallsPerLevel());
        _gameOptions.put("nextLevelSpeedJump", _optionsDialog.getLevelSpeedup());
        resetGame();
        repaint();
    }
    
    /**
     * Closes the options.
     */
    public void closeOptions()
    {
        _optionsDialog.setVisible(false);
        _optionsDialog.dispose();
    }

    /* Begin Event Handlers */
    
    public void windowClosing(WindowEvent e)
    {
        onShutdown();
    }
    
    public void windowDeactivated(WindowEvent e) { }
    public void windowDeiconified(WindowEvent e) { }
    public void windowOpened(WindowEvent e) { }
    public void windowIconified(WindowEvent e) { }
    public void windowClosed(WindowEvent e) { }
    public void windowActivated(WindowEvent e) { }
    
    protected void onShutdown()
    {
        System.exit(0);
    }

    protected void onAbout()
    {
        JOptionPane.showMessageDialog(null, "Breakout! (c) 2009 Dreadwail");
    }

    protected void onNew()
    {
        resetGame();
        repaint();
    }

    protected Ball getBall()
    {
        return _canvas.getBall();
    }
    
    protected PlayField getPlayField()
    {
        return _canvas.getPlayField();
    }

    
    /**
     * Sets the status.
     */
    public void setStatus()
    {
        String sts = "";
        
        sts += "Level="+_level+" ";
        sts += "Balls="+_balls+" ";
        sts += "Score="+_score+" ";
        sts += "Bricks="+getPlayField().getBrickCount()+" ";
        //sts += "Speed="+(Math.abs(_canvas.getCurrentMovementX()) + Math.abs(_canvas.getCurrentMovementY()))+" ";
        sts += "X="+(int)(getBall().getPosition().x)+" Y="+(int)(getBall().getPosition().y)+" ";
        
        _statusBar.setText(sts);
    }


    
}