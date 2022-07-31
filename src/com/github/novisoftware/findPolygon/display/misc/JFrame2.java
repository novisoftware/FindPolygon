package com.github.novisoftware.findPolygon.display.misc;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFrame;

public class JFrame2 extends JFrame {
	protected JFrame2() {
		super();

		// ウィンドウタイトルバーのアイコンを設定(Windows向け)
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResource(Preference.ICON_IMAGE_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Container contentPane = getContentPane();
	    contentPane.setBackground(Preference.BG_COLOR);
	    contentPane.setForeground(Preference.TEXT_COLOR);
	}

	protected void addHorizontalRule(Container c, int width) {
		Box hr = Box.createHorizontalBox();
		// hr.setPreferredSize(new Dimension(WINDOW_WIDTH * 2, width));
		hr.setPreferredSize(new Dimension((int)this.getSize().getWidth() * 2, width));
		c.add(hr);
	}
}
