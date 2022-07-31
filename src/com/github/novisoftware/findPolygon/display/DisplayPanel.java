package com.github.novisoftware.findPolygon.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.github.novisoftware.findPolygon.display.misc.Preference;
import com.github.novisoftware.findPolygon.model.Model;

class DisplayPanel extends JPanel {
	int marginX;
	int marginY;
	int scaleX;
	int scaleY;
	Model m;
	boolean flag = false;

	int sequenceNumber = 0;

	DisplayPanel(int scaleX, int scaleY, int MARGIN_X, int MARGIN_Y) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.marginX = MARGIN_X;
		this.marginY = MARGIN_Y;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Preference.BG_COLOR);
		Dimension size = this.getSize();
		int width = (int) size.getWidth();
		if (width < this.scaleX*4 + this.marginX*2+2) {
			width = this.scaleX*4 + this.marginX*2+2;
		}
		int height = (int) size.getHeight();
		if (height < this.scaleY*4 + this.marginX*2+2) {
			height = this.scaleY*4 + this.marginX*2+2;
		}
		g2.fillRect(0, 0, width, height);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.translate(marginX, marginY);

		g2.setColor(Preference.thinFgColor);
		g2.setStroke(Preference.thinStroke);

		for (int x = 0; x <=  Model.ARRAY_WIDTH; x++) {
			g2.drawLine((x * 2) * scaleX, 0, (x * 2) * scaleX, 2 * Model.ARRAY_HEIGHT * scaleY);
		}
		for (int y = 0; y <=  Model.ARRAY_HEIGHT; y++) {
			g2.drawLine(0, (y * 2) * scaleY, 2 * Model.ARRAY_WIDTH * scaleX, (y * 2) * scaleY);
		}
		for (int x = 0; x <  Model.ARRAY_WIDTH; x++) {
			for (int y = 0; y <  Model.ARRAY_HEIGHT; y++) {
				g2.drawLine((x * 2) * scaleX,  (y * 2) * scaleY, (x * 2 + 2) * scaleX,  (y * 2 + 2) * scaleY);
				g2.drawLine((x * 2 + 2) * scaleX,  (y * 2) * scaleY, (x * 2) * scaleX,  (y * 2 + 2) * scaleY);
			}
		}

		if (m != null) {
			g2.setStroke(Preference.boldStroke);
			for (int a : m.present) {
				int x00 = (a / 4) / Model.ARRAY_HEIGHT;
				int y00 = (a / 4) % Model.ARRAY_HEIGHT;

				int th0 = (a % 4);
				double th = Math.PI * 0.5 * th0;
				double x1 = - DisplayWindow.R * Math.sin(th - 0.25 * Math.PI);
				double y1 = - DisplayWindow.R * Math.cos(th - 0.25 * Math.PI);
				double x2 = - DisplayWindow.R * Math.sin(th + 0.25 * Math.PI);
				double y2 = - DisplayWindow.R * Math.cos(th + 0.25 * Math.PI);

				int x0 = (x00 + 0) * 2 + 1;
				int y0 = (y00 + 0) * 2 + 1;

				int[] xPoints  = {
						scaleX * x0,
						(int) Math.round( scaleX * (x0 + x1) ),
						(int) Math.round( scaleX * (x0 + x2) ),
						scaleX * x0
				};

				int[] yPoints  = {
						scaleY * y0,
						(int) Math.round( scaleY * (y0 + y1) ),
						(int) Math.round( scaleY * (y0 + y2) ),
						scaleY * y0
				};
				g2.setColor(Preference.boldFgFillColor_dumb);
				if (m.edges != null) {
					if (m.edges.size() == 3) {
						g2.setColor(Preference.boldFgFillColor_for3);
					}
					else if (m.edges.size() == 4) {
						g2.setColor(Preference.boldFgFillColor_for4);
					}
					else if (m.edges.size() == 5) {
						g2.setColor(Preference.boldFgFillColor_for5);
					}
				}
				g2.fillPolygon(xPoints, yPoints, 4);
				g2.setColor(Preference.boldFgStrokeColor);
				g2.drawPolyline(xPoints, yPoints, 4);
			}
		}

		/*
		if (this.sequenceNumber > 0) {
			Font f = new Font("UD デジタル 教科書体 N-B", Font.BOLD, 100);
			g2.setFont(f);
			g2.drawString("" + this.sequenceNumber, scaleX * 4.2f, scaleY * 2.5f);
		}
		*/
	}

}