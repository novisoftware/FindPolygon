package com.github.novisoftware.findPolygon.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LineUtil {
	/**
	 * 重複する点を持つか?
	 *
	 * @param a
	 * @return
	 */
	public static boolean hasDuplicatedPoint(Set<Line> a) {
		HashSet<Pos> pointSet = new HashSet<Pos>();
		for (Line e: a) {
			pointSet.add(e.a);
			pointSet.add(e.b);
		}

		return pointSet.size() != a.size();
	}

	/**
	 * 線分の集合をまとめる。
	 * あとで一筆書きできる順にするので、同じ線分は除去する。
	 *
	 * @param a
	 * @param b
	 */
	public static void mergeShape(HashSet<Line> a, ArrayList<Line> b) {
		/*
		System.out.println("ADD:");
		for(Line e: b) {
			System.out.format(" (%d, %d) - (%d, %d)\n", e.a.x, e.a.y, e.b.x, e.b.y );
		}
		*/
		for (Line e: b) {
			if (a.contains(e)) {
				a.remove(e);
			}
			else {
				a.add(e);
			}
		}
	}

	/**
	 * 一筆書きできる順に並べる。
	 *
	 * @param a
	 * @return
	 */
	public static ArrayList<Line> sortShape(HashSet<Line> a) {
		/*
		System.out.println("IN:");
		for(Line e: a) {
			System.out.format(" (%d, %d) - (%d, %d)\n", e.a.x, e.a.y, e.b.x, e.b.y );
		}
		System.out.println("RESULT:");
		*/

		LinkedList<Line> pool = new LinkedList<Line>();
		pool.addAll(a);
		assert(pool.size() > 0);

		int index0 = 0;
		int N0 = pool.size();
		int minimumIndex = -1;
		Pos minimumPos = null;
		for (index0 = 0; index0 < N0; index0 ++) {
			Line e = pool.get(index0);

			if (minimumIndex == -1) {
				minimumPos = e.a;
				minimumIndex = index0;

				continue;
			}

			if (e.a.compareTo(minimumPos) < 0) {
				minimumPos = e.a;
				minimumIndex = index0;

				continue;
			}
		}

		Line head = pool.remove(minimumIndex);
		ArrayList<Line> result = new ArrayList<Line>();

		while (true) {
			Line newHead = null;

			// 接している線分を探す。
			/*
			for(Line e: pool) {
				if (e.isConnected(tail)) {
					System.out.println("1 found");
					newTail = e;
					break;
				}
			}
			*/
			int index = 0;
			int N = pool.size();
			for (index = 0; index < N; index ++) {
				Line e = pool.get(index);
				if (e.isConnected(head)) {
					// System.out.println("1 found");
					break;
				}
			}
			assert(index != N);
			if (index == N) {
				System.out.print("bug (connected line is not found)");

				try {
					Thread.sleep(500);
					Thread.sleep(50000);
				} catch (InterruptedException e) {
				}
				System.exit(1);
			}

			newHead = pool.remove(index);
			if (newHead == null) {
				System.out.print("bug (mal LinkList)");
				System.exit(1);
			}

			// 結合しても良いなら、線分を結合する。
			Line merged = head.merge(newHead);
			if (merged != null) {
				head = merged;
			}
			else {
				result.add(head);
				head = newHead;
			}

			if (pool.isEmpty()) {
				break;
			}
		}
		Line merged = result.get(0).merge(head);
		if (merged != null) {
			result.remove(0);
			result.add(merged);
		}
		else {
			result.add(head);
		}

		/*
		for(Line e: result) {
			System.out.format(" (%d, %d) - (%d, %d)\n", e.a.x, e.a.y, e.b.x, e.b.y );
		}
		*/

		return result;
	}

}