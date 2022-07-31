package com.github.novisoftware.findPolygon;

import java.util.ArrayList;
import java.util.HashSet;

import com.github.novisoftware.findPolygon.display.SettingWindow;
import com.github.novisoftware.findPolygon.display.misc.Preference;
import com.github.novisoftware.findPolygon.model.Model;
import com.github.novisoftware.findPolygon.model.modelFilter.EdgeCountFilter2;
import com.github.novisoftware.findPolygon.model.modelFilter.HasDuplicatedPointFilter;
import com.github.novisoftware.findPolygon.model.modelFilter.IsSimplyConnectedFilter;

public class Main {
	/**
	 * フィルターされていないモデルのリスト
	 */
	ArrayList<Model> modelList0 = null;

	/**
	 * Mainのコンストラクタ。
	 */
	Main() {
		modelList0 = new ArrayList<Model>();

		for (int i = 1; i < 65536; i++) {
			Model model = new Model(i);
			modelList0.add(model);
		}

		// フィルタの副作用でmodelオブジェクトにデータを詰める
		ArrayList<Model> modelList1 = new IsSimplyConnectedFilter().apply(modelList0);
		new HasDuplicatedPointFilter().apply(modelList1);
	}

	// edgeCondition
	/**
	 * 指定された条件に合致する図形のリストを取得する。
	 *
	 * @param edgeCondition 辺の数の条件
	 * @return
	 */
	public ArrayList<Model> getFilteredModelList(boolean isSimplyConnected, HashSet<Integer> edgeCondition) {
		if (! isSimplyConnected) {
			return modelList0;
		}
		ArrayList<Model> modelList1 = new IsSimplyConnectedFilter().apply(modelList0);
		if (edgeCondition.size() == 0) {
			return modelList1;
		}
		ArrayList<Model> modelList2 = new HasDuplicatedPointFilter().apply(modelList1);
		ArrayList<Model> modelList3 = new EdgeCountFilter2(edgeCondition).apply(modelList2);

		return modelList3;
	}

	static public void main(String arg[]) {
		Main main = new Main();

		SettingWindow settingWindow = new SettingWindow(main);
		Preference.setLookAndFeel(settingWindow);

		settingWindow.setVisible(true);

		/*
		ArrayList<Model> modelList0 = new ArrayList<Model>();

		for (int i = 1; i < 65536; i++) {
			Model model = new Model(i);
			if (!model.isSimplyConnected()) {
				continue;
			}
			modelList0.add(model);
		}

		ArrayList<Model> modelList1 = new IsSimplyConnectedFilter().apply(modelList0);
		ArrayList<Model> modelList2 = new HasDuplicatedPointFilter().apply(modelList1);
		ArrayList<Model> modelList3 = new EdgeCountFilter(4).apply(modelList2);

		Collections.sort(modelList3, new ModelPartsCountComparator());

		DisplayWindow display = new DisplayWindow();
		display.setVisible(true);

		ThumbnailWindow thPanel = new ThumbnailWindow(display);
		thPanel.setModelList(modelList3);

		SettingWindow settingWindow = new SettingWindow(display);
		settingWindow.setVisible(true);
		*/

		/*
		ThumsPanel display = new ThumsPanel();
		while (true) {
			int sequenceNumber = 1;
			for (Model m: modelList3) {
				display.setModel(m, true, sequenceNumber);
				sequenceNumber ++;
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
				}
			}
		}
		*/
	}
}
