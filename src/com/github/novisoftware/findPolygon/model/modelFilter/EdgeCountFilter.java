package com.github.novisoftware.findPolygon.model.modelFilter;

import com.github.novisoftware.findPolygon.model.Model;

public class EdgeCountFilter extends ModelFilter {
	final int nEdges;

	public EdgeCountFilter(int n) {
		nEdges = n;
	}

	@Override
	boolean check(Model model) {
		return model.edges.size() == nEdges;
	}
}