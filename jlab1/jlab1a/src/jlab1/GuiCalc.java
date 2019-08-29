/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlab1;

/**
 *
 * @author dylan
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiCalc implements ActionListener {
    
    private JFrame frame;
    
    private JPanel xPanel;
    private JPanel yPanel;
    private JPanel rPanel;
    
    
    private JTextField xField;
    private JTextField yField;
    
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel rslt;
    private JLabel mult;
    
    private JButton computeButton;
    
    public GuiCalc() {
        frame = new JFrame();
        
        frame.setTitle("Multiplier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        
        //declare new panels for neatness
        xPanel = new JPanel();
        yPanel = new JPanel();
        rPanel = new JPanel();
        
        //x int label
        xLabel = new JLabel("x:     ");
        xPanel.add(xLabel);
        //field for x int (default 0)
        xField = new JTextField("", 5);
        xPanel.add(xField);
        
        //y int label
        yLabel = new JLabel("y:     ");
        yPanel.add(yLabel);
        //field for y int (default 0)
        yField = new JTextField("", 5);
        yPanel.add(yField);
        
        //x*y label //needs to be changed
        mult = new JLabel("x*y=     ");
        rPanel.add(mult);
        rslt = new JLabel();
        rPanel.add(rslt);
        
        frame.add(xPanel);
        frame.add(yPanel);
        frame.add(rPanel);
        
        computeButton = new JButton("Compute");
        frame.add(computeButton);
        computeButton.addActionListener(this);
        
        frame.setPreferredSize(new Dimension(150, 170));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        try{
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
        
        rslt.setText(String.valueOf(x*y));  
        }
        catch(NumberFormatException e){
            System.out.println("Warning: You Must You Integers.");
            rslt.setText("0"); 
        }
        catch(Exception e){
            System.out.println("Unknown Error. Pleae try again.");
            rslt.setText("0"); 
        }
    }
}
