package com.github.novisoftware.findPolygon.model;

import java.util.Comparator;

/**
 * 図形に含まれるパーツの数で比較を行う。
 *
 */
public class ModelPartsCountComparator implements Comparator<Model> {
	boolean isAscend = true;

	public ModelPartsCountComparator() {
		this.isAscend = true;
	}

	public ModelPartsCountComparator(boolean isAscend) {
		this.isAscend = isAscend;
	}

	public int compare_internal(Model m1, Model m2) {
		int count1 = m1.present.size();
		int count2 = m2.present.size();

		if (count1 < count2) {
			return -1;
		}
		if (count2 == count1) {
			return 0;
		}
		return 1;
	}

	@Override
	public int compare(Model m1, Model m2) {
		if (isAscend) {
			return compare_internal(m1, m2);
		}
		else {
			return compare_internal(m2, m1);
		}
	}
}