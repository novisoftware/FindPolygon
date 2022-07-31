package com.github.novisoftware.findPolygon.model;

/**
 * 頂点の位置を表現する
 *
 */
class Pos implements Comparable<Pos> {
	int x;
	int y;

	Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Pos other) {
		if (other.x < this.x) {
			return -1;
		}
		if (other.x > this.x) {
			return  1;
		}
		if (other.y < this.y) {
			return -1;
		}
		if (other.y > this.y) {
			return  1;
		}

		return 0;
	}

	@Override
	public int hashCode() {
		return x * Model.ARRAY_HEIGHT + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pos)) {
			return false;
		}
		Pos other = (Pos)obj;

		return other.x == this.x && other.y == this.y;
	}
}