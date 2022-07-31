package com.github.novisoftware.findPolygon.model.modelFilter;

import java.util.HashSet;

import com.github.novisoftware.findPolygon.model.Line;
import com.github.novisoftware.findPolygon.model.LineUtil;
import com.github.novisoftware.findPolygon.model.Model;
import com.github.novisoftware.findPolygon.model.Triangle;

public class HasDuplicatedPointFilter extends ModelFilter {
	@Override
	boolean check(Model model) {
		HashSet<Line> shape0 = new HashSet<Line>();
		for (int a: model.present) {
			LineUtil.mergeShape(shape0, Triangle.getTriangle(a).getEdges());
		}

		// 重複した点があるか？
		// (注: 三角形のピースが接しているかで見て単連結なものを選んでいるため、▽△が上下につながったものは入力に含まれない。
		//  Cの字型は、ある。)
		boolean hasDuplicated = LineUtil.hasDuplicatedPoint(shape0);

		if (hasDuplicated == false) {
			model.edges = LineUtil.sortShape(shape0);

			return true;
		}

		return hasDuplicated == false;
	}
}