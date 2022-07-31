package com.github.novisoftware.findPolygon.display;

import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import com.github.novisoftware.findPolygon.display.misc.JFrame2;
import com.github.novisoftware.findPolygon.display.misc.JLabel2;
import com.github.novisoftware.findPolygon.model.Model;

public class DisplayWindow extends JFrame2 {
	static int WINDOW_POS_X = 20 + SettingWindow.WINDOW_WIDTH + SettingWindow.WINDOW_POS_X;
	static int WINDOW_POS_Y = 20;
	static int WINDOW_WIDTH = 750;
	static int WINDOW_HEIGHT = 550;

	static int SCALE_X = 140;
	static int SCALE_Y = 100;
	static int MARGIN_X = 20;
	static int MARGIN_Y = 20;

	DisplayPanel panel; //  = new DisplayPanel(SCALE_X, SCALE_Y);

	JLabel label0 = new JLabel2("");
	JLabel label1 = new JLabel2("");
	JLabel label2 = new JLabel2("");

	public DisplayWindow() {
		super();
		panel = new DisplayPanel(SCALE_X, SCALE_Y, MARGIN_X, MARGIN_Y);

		Container pane = this.getContentPane();
		this.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));;
		pane.add(label0);
		pane.add(label1);
		pane.add(label2);
		pane.add(panel);

		/*
		JPanel workPanel = new JPanel();
		workPanel.setBackground(Preference.BG_COLOR);
		this.add(workPanel);

		Container pane = this.getContentPane();
		workPanel.setLayout(new BoxLayout(workPanel, BoxLayout.Y_AXIS));;
		workPanel.add(label1);
		workPanel.add(label2);
		workPanel.add(panel);
		*/

		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLocation(WINDOW_POS_X, WINDOW_POS_Y);
		this.setTitleN(null);		this.setVisible(true);
		this.repaint();
	}

	private void setTitleN(Integer n) {
		String baseString = "結果の詳細";
		if (n == null) {
			this.setTitle(baseString);
		}
		else {
			this.setTitle(String.format("%s (%d)", baseString, n));
		}
	}

	void writeImageFile(String f) {
		BufferedImage image = new BufferedImage( WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		java.awt.Graphics g = image.getGraphics();
		panel.paint(g);
        try {
			ImageIO.write(image, "png", new File(f));
		} catch (IOException e) {
			System.err.println( e.toString() );
			System.exit(1);
		}
	}

	public void setModel(Model m, boolean flag, int sequenceNumber) {
		label0.setText(String.format("( 図形の連番: %5d  単連結: %3s )", m.keyNumber, m.isSimplyConnectedCached() ? "Yes" : "No"));
		label1.setText(String.format("ピースの数: %d", m.present.size()));
		String nEdges = "-";
		if (m.edges != null) {
			nEdges = String.format("頂点の数: %d", m.edges.size());
		}
		else {
			nEdges = String.format("頂点の数: -");
		}
		label2.setText(nEdges);

		panel.m = m;
		panel.flag = flag;
		panel.sequenceNumber = sequenceNumber;
		panel.repaint();
	}

	static final double R = Math.sqrt(2);
}