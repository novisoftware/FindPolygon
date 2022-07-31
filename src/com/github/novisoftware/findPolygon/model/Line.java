package com.github.novisoftware.findPolygon.model;

/**
 * 線分を定義する。
 *
 */
public class Line {
	Pos a;
	Pos b;
	int kind_tan;

	Line(Pos a, Pos b) {
		if (a.compareTo(b) >= 0) {
			this.a = a;
			this.b = b;
		}
		else {
			this.a = b;
			this.b = a;
		}

		kind_tan = this.calcTanKind();
	}

	/**
	 * 線分と線分が接しているか。
	 * 比較対象の線分と、始点、終点のどちらかが同じであれば、接していると定義する。
	 *
	 * @return 比較対象の線分と接していたらtrue、接していなければ false。
	 */
	boolean isConnected(Line other) {
		return this.a.equals(other.a)
				|| this.b.equals(other.b)
				|| this.a.equals(other.b)
				|| this.b.equals(other.a);
	}

	/**
	 * 線分と線分を接続する。 接続できない場合は null。
	 *
	 * @return 接続した線分。
	 */
	Line merge(Line other) {
		if (this.kind_tan == other.kind_tan) {
			if (this.a.equals(other.a)) {
				return new Line(this.b, other.b);
			}
			if (this.b.equals(other.a)) {
				return new Line(this.a, other.b);
			}
			if (this.a.equals(other.b)) {
				return new Line(this.b, other.a);
			}
			if (this.b.equals(other.a)) {
				return new Line(this.b, other.a);
			}
		}

		return null;
	}



	@Override
	public int hashCode() {
		return this.a.hashCode() * Model.ARRAY_HEIGHT*Model.ARRAY_WIDTH
				+ this.b.hashCode();
	}

	/**
	 * 線分と線分が等価であるか。
	 * 比較対象の線分と、始点、終点の両方が同じであれば等価であると定義する。
	 *
	 * @param other 比較対象の線分
	 * @return 比較対象の線分と等価であればtrue、等価でなければfalse。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Line)) {
			return false;
		}
		Line other = (Line)obj;

		return this.a.equals(other.a) && this.b.equals(other.b);
		/*
		return (this.a == other.a && this.b == other.b)
				|| (this.a == other.b && this.b == other.a);
		*/
	}

	/**
	 * 傾きを種類分けする。
	 * 以下の4種類。
	 * 0: 水平、
	 * 1: 垂直、
	 * 2: 右下がり、
	 * 3: 右上がり
	 *
	 * @return 傾きの種類
	 */
	private int calcTanKind() {
		if (a.x == b.x) {
			return 0;
		}
		if (a.y == b.y) {
			return 1;
		}
		if ((a.x > b.x) == (a.y > b.y)) {
			return 2;
		}
		else {
			return 3;
		}
	}
}
