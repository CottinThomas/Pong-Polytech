package org.polytech.pong.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Custom textfield
 * @author Clément
 *
 */
public class CustomJTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public CustomJTextField(String text) {
		super(text);

		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFont(new Font("SansSerif", Font.BOLD, 20));
		setHorizontalAlignment(JTextField.CENTER);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 5),
				BorderFactory.createLineBorder(Color.BLACK, 20)));
	}

}
