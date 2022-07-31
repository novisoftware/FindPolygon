package com.github.novisoftware.findPolygon.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 図形の1パターン分を表現する。
 *
 */
public class Model {
	/**
	 * 三角形ピース の数
	 */
	public static int N_PARTS = 16;

	/**
	 *  区画の数 (横方法)
	 */
	public static int ARRAY_WIDTH = 2;

	/**
	 *  区画の数 (縦方法)
	 */
	public static int ARRAY_HEIGHT = 2;

	/**
	 * 連番
	 */
	public final int keyNumber;

	/**
	 * モデルが含む三角形ピースのリスト
	 */
	public ArrayList<Integer> present;

	/**
	 * 辺
	 */
	public ArrayList<Line> edges = null;

	/**
	 * 「三角ピースは隣り合うか?」を判定する (判定方法1)
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	private static boolean internal_isInContact(int a, int b) {
		// 四角の区画が同じ
		if (a / 4 == b / 4) {
			// 隣り合う
			int d = b % 4 - a % 4;
			if (d == 1 || d == 3) {
				return true;
			}
		}

		// 区画の横方向の位置が同じ
		if (a / 4 / ARRAY_HEIGHT == b / 4 / ARRAY_HEIGHT) {
			// 区画が縦に隣り合う
			if (a / 4 + 1 == b / 4 ) {
				if (a % 4 == 2 && b % 4 == 0) {
					return true;
				}
			}
		}

		// 区画の縦方向の位置が同じ
		if (a / 4 % ARRAY_HEIGHT == b / 4 % ARRAY_HEIGHT) {
			// 区画が横に隣り合う
			if (a / 4 + ARRAY_WIDTH == b / 4 ) {
				if (a % 4 == 3 && b % 4 == 1) {
					return true;
				}
			}
		}


		return false;
	}

	/**
	 * 「三角ピースは隣り合うか?」を判定する (判定方法2)
	 *
	 * @param a ピース1
	 * @param b ピース2
	 * @return 隣り合う場合は true, 離れている場合は false
	 */
	private static boolean internal_isInContact_method_0(int a, int b) {
		// 四角の区画が同じ
		if (a / 4 == b / 4) {
			// 隣り合う
			int d = b % 4 - a % 4;
			if (d == 1 || d == 3) {
				return true;
			}
		}

		// 区画が縦に隣り合う
		if (a == 2 && b == 4) {
			return true;
		}
		if (a == 10 && b == 12) {
			return true;
		}

		// 区画が横に隣り合う
		if (a == 3 && b == 9) {
			return true;
		}
		if (a == 7 && b == 13) {
			return true;
		}

		return false;
	}

	/**
	 * 三角ピースは隣り合うか?
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static boolean isInContact(int a, int b) {
		if (a < b) {
			return internal_isInContact(a, b);
		}
		else {
			return internal_isInContact(b, a);
		}
	}

	public Model(int keyNumber) {
		this.keyNumber = keyNumber;

		present = new ArrayList<Integer>();
		for (int index = 0; index < N_PARTS; index ++) {
			if ((keyNumber & (1 << index)) != 0) {
				present.add(index);
			}
		}
	}

	/**
	 * 三角形のピースが単連結になっているかの判定結果をキャッシュする
	 *
	 * @return
	 */
	Boolean isSimplyConnectedCachedResult = null;

	/**
	 * 三角形のピースが単連結になっているかの判定結果のキャッシュを取得する
	 *
	 * @return
	 */
	public boolean
	isSimplyConnectedCached() {
		if (isSimplyConnectedCachedResult == null) {
			isSimplyConnectedCachedResult = isSimplyConnected();
		}
		return isSimplyConnectedCachedResult;
	}

	/**
	 * 三角形のピースが単連結になっているか判定する
	 *
	 * @return
	 */
	public boolean
	isSimplyConnected() {
		if (present.size() == 0) {
			return false;
		}

		// 発見済の集合
		HashSet<Integer> found = new HashSet<Integer>();
		found.add(present.get(0));

		while(true) {
			// 集合全体から、発見済の集合の要素と、つながっているものを探す
			ArrayList<Integer> newFound = new ArrayList<Integer>();
			for (int b : found) {
				for (int a : present) {
					if (found.contains(a)) {
						continue;
					}

					if (Model.isInContact(a, b)) {
						newFound.add(a);
					}
				}
			}

			// 新しく見つからなければ打ち切り
			if (newFound.isEmpty()) {
				break;
			}
			found.addAll(newFound);
		}

		// 個数が一致していた場合は単連結
		return found.size() == present.size();
	}

	static public void check() {
		// 2種類作った連結の判定処理で同じ結果になるかを確かめる。
		for (int i = 0 ; i < 15 ; i++) {
			for (int j = i + 1; j < 16 ; j++) {
				if (internal_isInContact(i, j) != internal_isInContact_method_0(i, j )) {
					System.out.println("bug in " + i + " , " + j);
					System.exit(1);
				}
			}
		}

		// 三角形のピースを確認する。
		System.out.println("三角形ピースの座標一覧");
		for (int i = 0 ; i < 16 ; i++) {
			System.out.printf("%d: %s\n", i, (new Triangle(i)).debugPrint());
		}
	}

	/*
	static public void main(String arg[]) {
		ArrayList<Model> modelList0 = new ArrayList<Model>();

		for (int i = 1; i < 65536; i++) {
			Model model = new Model(i);
			if (!model.isSimplyConnected()) {
				continue;
			}
			modelList0.add(model);
		}

		ArrayList<Model> modelList1 = new IsSimplyConnectedFilter().apply(modelList0);
		ArrayList<Model> modelList2 = new IsSimplyConnectedFilter().apply(modelList1);
		ArrayList<Model> modelList3 = new EdgeCountFilter(4).apply(modelList2);

		Display display = new Display();
		*/


		/*

		// for (int i = 10; i < 1000 ; i++) {
		for (int i = 1 // start // start
				; i < 65536 // 512
				; i++) {
		// for (int i = 1; i < 65536 ; i++) {
			Model m = new Model(i);
			boolean isSc =  m.isSimplyConnected();
			System.out.println();
			System.out.println( i + " 単連結 " + isSc);

			if (!isSc) {
				continue;
			}

			HashSet<Line> shape0 = new HashSet<Line>();
			for (int a: m.present) {
				mergeShape(shape0, Triangle.getTriangle(a).getEdges());
			}

			// 重複した点があるか？
			// 三角形のピースが接しているかで見て単連結なので、▽△が上下につながったものは出てこない。
			// Cの字型がある。
			boolean hasDuplicated = hasDuplicatedPoint(shape0);
			boolean foundFlag = false;

			if (hasDuplicated == false) {
				System.out.println(m);
				ArrayList<Line> shape1 = sortShape(shape0);


				System.out.println("DUP: " + hasDuplicated);
				System.out.println("COUNT: " + shape1.size());

				if (hasDuplicated == false && shape1.size() == 3) {
//				if (hasDuplicated == false && shape1.size() == 4) {
					foundFlag = true;
					sequenceNumber ++;
				}
			}
			display.setModel(m, foundFlag, sequenceNumber);


			System.out.println(String.format("%04d", printCounter));
			printCounter ++;

			if (foundFlag) {
				String filename = String.format( "image%04d.png", sequenceNumber);
				display.writeImageFile(filename);

				try {
					Thread.sleep(150);
//					Thread.sleep(1500);
//					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}



			try {
				Thread.sleep(20);
//				Thread.sleep(5000);
//				Thread.sleep(50000);
			} catch (InterruptedException e) {
			}

//			break;
	}
 */
}

