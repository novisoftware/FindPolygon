package com.github.novisoftware.findPolygon.display.misc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class JLabel2 extends JLabel {
	public JLabel2(String text) {
		super(text);
		this.setFont(Preference.LABEL_FONT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
		super.paintComponent(g2);
	}

	/*
	static public JLabel2 getJLabel2(String text) {
		JLabel2 label = new JLabel2(text);
		return label;
	}
	*/
}
