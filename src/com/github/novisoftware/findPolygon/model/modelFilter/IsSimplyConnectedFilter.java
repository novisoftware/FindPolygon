package com.github.novisoftware.findPolygon.model.modelFilter;

import com.github.novisoftware.findPolygon.model.Model;

public class IsSimplyConnectedFilter extends ModelFilter {
	@Override
	boolean check(Model model) {
		return model.isSimplyConnected();
	}
}