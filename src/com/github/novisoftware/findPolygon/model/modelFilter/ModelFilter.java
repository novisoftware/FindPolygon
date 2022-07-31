package com.github.novisoftware.findPolygon.model.modelFilter;

import java.util.ArrayList;
import java.util.List;

import com.github.novisoftware.findPolygon.model.Model;

abstract public class ModelFilter {
	abstract boolean check(Model model);

	public ArrayList<Model> apply(List<Model> modelList) {
		ArrayList<Model> result = new ArrayList<Model>();

		for (Model model: modelList) {
			if (check(model)) {
				result.add(model);
			}
		}

		return result;
	}
}