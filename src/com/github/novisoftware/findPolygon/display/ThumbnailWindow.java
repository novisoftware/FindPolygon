package com.github.novisoftware.findPolygon.display;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.github.novisoftware.findPolygon.display.misc.JFrame2;
import com.github.novisoftware.findPolygon.display.misc.Preference;
import com.github.novisoftware.findPolygon.model.Model;


class Thumbnail {
	static final int THUMB_WIDTH = 65;
	static final int THUMB_HEIGHT = 50;
	static int SCALE_X = 14;
	static int SCALE_Y = 10;
	static int MARGIN_X = 4;
	static int MARGIN_Y = 4;

	final Model model;
	final BufferedImage image;
	final JButton jbutton;
	final JPanel jpanel;

	Thumbnail(final Model model, final DisplayWindow display) {
		this.model = model;
		this.image = createThummbNailImage();
		this.jbutton = new JButton(new ImageIcon(this.image));

		final BufferedImage finalImage = this.image;
		final MouseListener listener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// display.setModel(model, false, 0);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				display.setModel(model, false, 0);
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};

		this.jpanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(finalImage, null, 0, 0);
			}
		};
		this.jpanel.setPreferredSize(new Dimension(THUMB_WIDTH, THUMB_HEIGHT));
		this.jpanel.addMouseListener(listener);
	}

	static DisplayPanel displayPanel;

	private BufferedImage createThummbNailImage() {
		if (displayPanel == null) {
			displayPanel = new DisplayPanel(SCALE_X, SCALE_Y, MARGIN_X, MARGIN_Y);
			// displayPanel.setPreferredSize(new Dimension(SCALE_X + MARGIN_X * 2, SCALE_Y + MARGIN_Y * 2));
			// displayPanel.setPreferredSize(new Dimension(THUMB_WIDTH, THUMB_HEIGHT));
		}

		displayPanel.m = model;
		BufferedImage image = new BufferedImage( THUMB_WIDTH, THUMB_HEIGHT, BufferedImage.TYPE_INT_RGB);
		java.awt.Graphics g = image.getGraphics();
		displayPanel.paint(g);

		return image;
	}
}

public class ThumbnailWindow extends JFrame2 implements ComponentListener {
	static int WINDOW_POS_X = 20;
	static int WINDOW_POS_Y = 20 + SettingWindow.WINDOW_HEIGHT + SettingWindow.WINDOW_POS_Y;
	static int WINDOW_WIDTH = 600;
	static int WINDOW_HEIGHT = 550;

	// 描画用のパラメーター
	static int SCALE_X = 140;
	static int SCALE_Y = 100;
	static int MARGIN_X = 20;
	static int MARGIN_Y = 20;

	DisplayWindow display;

	public ThumbnailWindow(DisplayWindow display) {
		super();

		this.display = display;

		this.setTitleN(null, false);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLocation(WINDOW_POS_X, WINDOW_POS_Y);
		this.setVisible(true);
	}

	private void setTitleN(Integer n, boolean limited) {
		String baseString = "結果の一覧";
		if (n == null) {
			this.setTitle(baseString);
		}
		else {
			if (! limited) {
				this.setTitle(String.format("%s (%d個)", baseString, n));
			}
			else {
				this.setTitle(String.format("%s (あてはまる %d個中、 %d個だけを表示)", baseString, n, Preference.THUMB_LIMIT));
			}
		}
	}

	JPanel panelInScrollPane;

	ArrayList<Model> modelList;
	ArrayList<Thumbnail> thumbnailList;

	public void updatePanelSize() {
		if (panelInScrollPane == null) {
			return;
		}

		int width = (int) this.getSize().getWidth();
		int n_in_line = width / (Thumbnail.THUMB_WIDTH + 4);  // 1行に何個 サムネイルが表示できるか
		if (n_in_line == 0) {
			n_in_line = 1;
		}
		int lines = thumbnailList.size() / n_in_line;
		int height = lines * (Thumbnail.THUMB_HEIGHT + 5);
		if (height < (int) this.getSize().getWidth()) {
			height = (int) this.getSize().getWidth();
		}
		panelInScrollPane.setPreferredSize(new Dimension(width, height));
	}

	public void setModelList(ArrayList<Model> modelList) {
		this.modelList = modelList;
		this.thumbnailList = new  ArrayList<Thumbnail>();

		boolean isLimited = false;
		int n = 0;
		for (Model m: modelList) {
			thumbnailList.add(new Thumbnail(m, this.display));

			n++;
			if (n >= Preference.THUMB_LIMIT) {
				isLimited = true;
				break;
			}
		}

		this.setTitleN(modelList.size(), isLimited);

		this.getContentPane().removeAll();
		JPanel panel = new JPanel();
		this.panelInScrollPane = panel;
		updatePanelSize(); // JPanelのサイズ計算をする

		panel.setBackground(Preference.BG_COLOR);
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));

		for (Thumbnail th: thumbnailList) {
			panel.add(th.jpanel);
		}

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.getContentPane().add(scrollPane);


		this.setVisible(false);
		this.setVisible(true);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		updatePanelSize();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// 処理不要
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// 処理不要
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// 処理不要
	}

}