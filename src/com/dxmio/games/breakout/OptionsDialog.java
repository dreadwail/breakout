package com.dxmio.games.breakout;

import edu.cmu.relativelayout.*;

import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 * @author byte
 *
 * Options dialog for game settings.
 */
@SuppressWarnings("serial")
public class OptionsDialog extends JDialog implements ActionListener, FocusListener
{
    
    private JLabel _lblBrickCount;
    private JLabel _lblBrickCountRange;
    
    private JLabel _lblHitsToRemoveBrick;
    private JLabel _lblHitsToRemoveBrickRange;
    
    private JLabel _lblBallsPerLevel;
    private JLabel _lblBallsPerLevelRange;
    
    private JLabel _lblLevelCompleteSpeedup;
    
    protected JFormattedTextField _txtBrickCount;
    protected JFormattedTextField _txtHitsToRemoveBrick;
    protected JFormattedTextField _txtBallsPerLevel;

    protected JRadioButton _radLevelSpeedup25;
    protected JRadioButton _radLevelSpeedup50;
    
    private JButton _btnOK;
    private JButton _btnClose;
    
    private BasicFrame _parentFrame;

    /**
     * Instantiates a new options dialog.
     * 
     * @param theFrame The frame to use.
     */
    public OptionsDialog(BasicFrame theFrame)
    {
        super(theFrame, "Game Options", true);
        _parentFrame = theFrame;
        
        

        setSize(300, 200);
        setLayout(new RelativeLayout());

        RelativeConstraints constr = null;
        
        /* BEGIN INITIAL BRICK COUNT */

        constr = new RelativeConstraints();
        _txtBrickCount = new JFormattedTextField();
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.TOP, this),
                new Binding(Edge.RIGHT, 70, Direction.LEFT, Edge.RIGHT, this));
        _txtBrickCount.setEnabled(true);
        getContentPane().add(_txtBrickCount, constr);
        
        constr = new RelativeConstraints();
        _lblBrickCountRange = new JLabel("(1-100)");
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.TOP, _txtBrickCount),
                new Binding(Edge.LEFT, 3, Direction.RIGHT, Edge.RIGHT, _txtBrickCount));
        _lblBrickCountRange.setEnabled(true);
        getContentPane().add(_lblBrickCountRange, constr);
        

        constr = new RelativeConstraints();
        _lblBrickCount = new JLabel("Initial brick count:");
        constr.addBindings(
                new Binding(Edge.TOP, 1, Direction.BELOW, Edge.TOP, _txtBrickCount),
                new Binding(Edge.LEFT, 6, Direction.RIGHT, Edge.LEFT, this));
        _lblBrickCount.setEnabled(true);
        getContentPane().add(_lblBrickCount, constr);
        
        
        /* END INITIAL BRICK COUNT */
        
        /* BEGIN HITS TO REMOVE BRICK */

        
        constr = new RelativeConstraints();
        _txtHitsToRemoveBrick = new JFormattedTextField();
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.BOTTOM, _txtBrickCount),
                new Binding(Edge.RIGHT, 70, Direction.LEFT, Edge.RIGHT, this));
        _txtHitsToRemoveBrick.setEnabled(true);
        getContentPane().add(_txtHitsToRemoveBrick, constr);
        
        
        
        constr = new RelativeConstraints();
        _lblHitsToRemoveBrickRange = new JLabel("(1-4)");
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.TOP, _txtHitsToRemoveBrick),
                new Binding(Edge.LEFT, 3, Direction.RIGHT, Edge.RIGHT, _txtHitsToRemoveBrick));
        _lblHitsToRemoveBrickRange.setEnabled(true);
        getContentPane().add(_lblHitsToRemoveBrickRange, constr);
        

        constr = new RelativeConstraints();
        _lblHitsToRemoveBrick = new JLabel("Hits to remove brick:");
        constr.addBindings(
                new Binding(Edge.TOP, 1, Direction.BELOW, Edge.TOP, _txtHitsToRemoveBrick),
                new Binding(Edge.LEFT, 6, Direction.RIGHT, Edge.LEFT, this));
        _lblHitsToRemoveBrick.setEnabled(true);
        getContentPane().add(_lblHitsToRemoveBrick, constr);

        /* END HITS TO REMOVE BRICK */
        
        /* BEGIN BALLS PER LEVEL */

        constr = new RelativeConstraints();
        _txtBallsPerLevel = new JFormattedTextField();
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.BOTTOM, _txtHitsToRemoveBrick),
                new Binding(Edge.RIGHT, 70, Direction.LEFT, Edge.RIGHT, this));
        _txtBallsPerLevel.setEnabled(true);
        getContentPane().add(_txtBallsPerLevel, constr);
        
        
        
        constr = new RelativeConstraints();
        _lblBallsPerLevelRange = new JLabel("(1-4)");
        constr.addBindings(
                new Binding(Edge.TOP, 3, Direction.BELOW, Edge.TOP, _txtBallsPerLevel),
                new Binding(Edge.LEFT, 3, Direction.RIGHT, Edge.RIGHT, _txtBallsPerLevel));
        _lblBallsPerLevelRange.setEnabled(true);
        getContentPane().add(_lblBallsPerLevelRange, constr);
        
        constr = new RelativeConstraints();
        _lblBallsPerLevel = new JLabel("Balls per level:");
        constr.addBindings(
                new Binding(Edge.TOP, 1, Direction.BELOW, Edge.TOP, _txtBallsPerLevel),
                new Binding(Edge.LEFT, 6, Direction.RIGHT, Edge.LEFT, this));
        _lblBallsPerLevel.setEnabled(true);
        getContentPane().add(_lblBallsPerLevel, constr);

        /* END BALLS PER LEVEL */

        
        /* BEGIN BUTTONS */
        
        constr = new RelativeConstraints();
        _btnClose = new JButton("Close");
        constr.addBindings(
                new Binding(Edge.BOTTOM, 6, Direction.ABOVE, Edge.BOTTOM, this),
                new Binding(Edge.RIGHT, 6, Direction.LEFT, Edge.RIGHT, this));
        _btnClose.setEnabled(true);
        _btnClose.addActionListener(_parentFrame);
        getContentPane().add(_btnClose, constr);
        
        constr = new RelativeConstraints();
        _btnOK = new JButton("OK");
        constr.addBindings(
                new Binding(Edge.BOTTOM, 6, Direction.ABOVE, Edge.BOTTOM, this),
                new Binding(Edge.RIGHT, 6, Direction.LEFT, Edge.LEFT, _btnClose));
        _btnOK.setEnabled(true);
        _btnOK.addActionListener(_parentFrame);
        getContentPane().add(_btnOK, constr);
        
        /* END BUTTONS */
        
        
        /* BEGIN LEVEL COMPLETE SPEEDUP */
        
        constr = new RelativeConstraints();
        _lblLevelCompleteSpeedup = new JLabel("Level complete speedup:");
        constr.addBindings(
                new Binding(Edge.BOTTOM, 6, Direction.ABOVE, Edge.TOP, _btnOK),
                new Binding(Edge.LEFT, 6, Direction.RIGHT, Edge.LEFT, this));
        _lblLevelCompleteSpeedup.setEnabled(true);
        getContentPane().add(_lblLevelCompleteSpeedup, constr);

        constr = new RelativeConstraints();
        _radLevelSpeedup50 = new JRadioButton("50%", true);
        constr.addBindings(
                new Binding(Edge.BOTTOM, 3, Direction.ABOVE, Edge.TOP, _btnOK),
                new Binding(Edge.RIGHT, 6, Direction.LEFT, Edge.RIGHT,this));
        _radLevelSpeedup50.setEnabled(true);
        getContentPane().add(_radLevelSpeedup50, constr);

        constr = new RelativeConstraints();
        _radLevelSpeedup25 = new JRadioButton("25%", true);
        constr.addBindings(
                new Binding(Edge.BOTTOM, 3, Direction.ABOVE, Edge.TOP, _btnOK),
                new Binding(Edge.RIGHT, 6, Direction.LEFT, Edge.LEFT, _radLevelSpeedup50));
        _radLevelSpeedup25.setEnabled(true);
        getContentPane().add(_radLevelSpeedup25, constr);
        
        /* END LEVEL COMPLETE SPEEDUP */
        


        ButtonGroup speedGroup = new ButtonGroup();
        
        speedGroup.add(_radLevelSpeedup25);
        speedGroup.add(_radLevelSpeedup50);
        speedGroup.setSelected(_radLevelSpeedup25.getModel(), true);
        
        getRootPane().setDefaultButton(_btnOK);
        
        setWidgets();
        
    }
    
    /**
     * Sets the widgets on the dialog.
     */
    public void setWidgets()
    {
        _txtBrickCount.setText(getGameOptions().get("brickCount").toString());
        _txtHitsToRemoveBrick.setText(getGameOptions().get("hitsToRemoveBrick").toString());
        _txtBallsPerLevel.setText(getGameOptions().get("ballsPerLevel").toString());
        if(getGameOptions().get("nextLevelSpeedJump") == 25)
        {
            _radLevelSpeedup25.setSelected(true);
            _radLevelSpeedup50.setSelected(false);
        }
        else
        {
            _radLevelSpeedup25.setSelected(false);
            _radLevelSpeedup50.setSelected(true);
        }
    }
    
    public void focusLost(FocusEvent e)
    {
        try
        {
            _txtBrickCount.commitEdit();
            _txtHitsToRemoveBrick.commitEdit();
            _txtBallsPerLevel.commitEdit();
        }
        catch(ParseException ex) { }
        
        int brickCountVal = ((Number)_txtBrickCount.getValue()).intValue();
        if(brickCountVal > 100)
            _txtBrickCount.setValue(100);
        else if(brickCountVal < 1)
            _txtBrickCount.setValue(1);
        
        int hitsToRemoveBrickVal = ((Number)_txtHitsToRemoveBrick.getValue()).intValue();
        if(hitsToRemoveBrickVal > 4)
            _txtHitsToRemoveBrick.setValue(4);
        else if(hitsToRemoveBrickVal < 1)
            _txtHitsToRemoveBrick.setValue(1);
        
        int ballsPerLevelVal = ((Number)_txtBallsPerLevel.getValue()).intValue();
        if(ballsPerLevelVal > 4)
            _txtBallsPerLevel.setValue(4);
        else if(ballsPerLevelVal < 1)
            _txtBallsPerLevel.setValue(1);
    }    


    protected Hashtable<String, Integer> getGameOptions()
    {
        return _parentFrame.getGameOptions();
    }
    


    public void actionPerformed(ActionEvent event)
    {
        //String action = event.getActionCommand();
        setVisible(false);
    }

    public void focusGained(FocusEvent e)
    {
    }
    
    protected int getBrickCount()
    {
        return Integer.parseInt(_txtBrickCount.getText());
    }
    
    protected int getHitsToRemoveBrick()
    {
        return Integer.parseInt(_txtHitsToRemoveBrick.getText());
    }
    
    protected int getBallsPerLevel()
    {
        return Integer.parseInt(_txtBallsPerLevel.getText());
    }

    protected int getLevelSpeedup()
    {
        if(_radLevelSpeedup25.isSelected() == true)
        {
            return 25;
        }
        else
        {
            return 50;
        }
            
    }
    
}
