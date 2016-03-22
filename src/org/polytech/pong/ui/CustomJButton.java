package org.polytech.pong.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Custom button
 * @author Clément
 *
 */
public class CustomJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public CustomJButton(String label) {
		super(label);
		
		setContentAreaFilled(false); 
        setFocusPainted(false);
	    setBackground(Color.BLACK);
	    setForeground(Color.WHITE);
	    setCursor(new Cursor(Cursor.HAND_CURSOR));
	    setFont(new Font("SansSerif", Font.BOLD, 20));
	    setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.WHITE, 5),
	               BorderFactory.createLineBorder(Color.BLACK, 20)));
	}   
}
