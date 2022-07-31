package com.github.novisoftware.findPolygon.model.modelFilter;

import java.util.Set;

import com.github.novisoftware.findPolygon.model.Model;

public class EdgeCountFilter2 extends ModelFilter {
	final Set<Integer> nEdges;

	public EdgeCountFilter2(Set<Integer> nEdges) {
		this.nEdges = nEdges;
	}

	@Override
	boolean check(Model model) {
		return nEdges.contains(model.edges.size());
	}
}