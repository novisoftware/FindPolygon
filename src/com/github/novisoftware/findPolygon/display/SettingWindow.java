package com.github.novisoftware.findPolygon.display;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import com.github.novisoftware.findPolygon.Main;
import com.github.novisoftware.findPolygon.display.misc.JFrame2;
import com.github.novisoftware.findPolygon.display.misc.JLabel2;
import com.github.novisoftware.findPolygon.display.misc.Preference;
import com.github.novisoftware.findPolygon.model.Model;
import com.github.novisoftware.findPolygon.model.ModelPartsCountComparator;

public class SettingWindow extends JFrame2 {
	static int WINDOW_POS_X = 20;
	static int WINDOW_POS_Y = 20;
	static int WINDOW_WIDTH = 600;
	static int WINDOW_HEIGHT = 250;

	public SettingWindow(final Main main) {
		super();

		// this.display = display;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLocation(WINDOW_POS_X, WINDOW_POS_Y);

		this.setTitle("条件を指定する");

		// レイアウト
		Container pane = this.getContentPane();
		this.setLayout(new FlowLayout(FlowLayout.LEADING));


		final JCheckBox check_simplyConnected = new JCheckBox();
		check_simplyConnected.setBackground(Preference.BG_COLOR);
		check_simplyConnected.setSelected(true);

		pane.add(new JLabel2("単連結な図形のみに絞り込む: "));
		pane.add(check_simplyConnected);
		pane.add(new JLabel2("絞り込む"));

		// 不可視の水平線を作成する (レイアウトの調整)
		addHorizontalRule(pane, 5);

		pane.add(new JLabel2("図形の種類: "));

		final JCheckBox check_triangle = new JCheckBox();
		final JCheckBox check_sqare = new JCheckBox();
		final JCheckBox check_pentagon = new JCheckBox();

		check_triangle.setBackground(Preference.BG_COLOR);
		check_sqare.setBackground(Preference.BG_COLOR);
		check_pentagon.setBackground(Preference.BG_COLOR);

		pane.add(check_triangle);
		pane.add(new JLabel2("三角形"));

		pane.add(check_sqare);
		pane.add(new JLabel2("四角形"));

		pane.add(check_pentagon);
		pane.add(new JLabel2("五角形"));

		check_sqare.setSelected(true);

		check_simplyConnected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check_simplyConnected.isSelected()) {
					check_triangle.setEnabled(true);
					check_sqare.setEnabled(true);
					check_pentagon.setEnabled(true);
				}
				else {
					check_triangle.setEnabled(false);
					check_sqare.setEnabled(false);
					check_pentagon.setEnabled(false);
				}
			}
		});

		// 不可視の水平線を作成する (レイアウトの調整)
		addHorizontalRule(pane, 10);

		pane.add(new JLabel2("並べ替え: "));

	    final JRadioButton radio0 = new JRadioButton();
	    final JRadioButton radio1 = new JRadioButton();
	    final JRadioButton radio2 = new JRadioButton();

	    radio0.setBackground(Preference.BG_COLOR);
	    radio1.setBackground(Preference.BG_COLOR);
	    radio2.setBackground(Preference.BG_COLOR);

	    radio1.setSelected(true);

		pane.add(radio0);
		pane.add(new JLabel2("しない"));
		pane.add(radio1);
		pane.add(new JLabel2("ピース数昇順"));
		pane.add(radio2);
		pane.add(new JLabel2("ピース数降順"));

		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(radio0);
		bgroup.add(radio1);
		bgroup.add(radio2);

		addHorizontalRule(pane, 5);

		JButton runButton = new JButton("表示する");
		pane.add(runButton);

		runButton.addActionListener(
				new ActionListener() {
					DisplayWindow display = null;
					ThumbnailWindow thPanel = null;

					@Override
					public void actionPerformed(ActionEvent e) {
						HashSet<Integer> nEdges = new HashSet<Integer>();
						if (check_triangle.isSelected()) {
							nEdges.add(3);
						}
						if (check_sqare.isSelected()) {
							nEdges.add(4);
						}
						if (check_pentagon.isSelected()) {
							nEdges.add(5);
						}

						ArrayList<Model> modelList = main.getFilteredModelList(check_simplyConnected.isSelected(), nEdges);

						if (radio1.isSelected()) {
							Collections.sort(modelList, new ModelPartsCountComparator(true));
						}
						else if (radio2.isSelected()) {
							Collections.sort(modelList, new ModelPartsCountComparator(false));
						}

						if (display == null) {
							display = new DisplayWindow();
						}
						if (thPanel == null) {
							thPanel = new ThumbnailWindow(display);
						}
						display.setVisible(true);
						thPanel.setModelList(modelList);
					}
				}
				);

		this.setVisible(true);
	}

}