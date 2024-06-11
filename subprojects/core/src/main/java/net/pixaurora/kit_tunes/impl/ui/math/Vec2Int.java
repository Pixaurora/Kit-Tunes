package net.pixaurora.kit_tunes.impl.ui.math;

public interface Vec2Int<T extends Vec2Int<T>> {
	public T constructVec(int x, int y);

	public int x();

	public int y();

	public default <T2 extends Vec2Int<T2>> T offset(Vec2Int<T2> by) {
		return this.offset(by.x(), by.y());
	}

	public default T offset(int x, int y) {
		return this.constructVec(this.x() + x, this.y() + y);
	}

	public default T withX(int x) {
		return this.constructVec(x, this.y());
	}

	public default T withY(int y) {
		return this.constructVec(this.x(), y);
	}

	public default <T2 extends Vec2Int<T2>> T withX(Vec2Int<T2> other) {
		return this.withX(other.x());
	}

	public default <T2 extends Vec2Int<T2>> T withYOf(Vec2Int<T2> other) {
		return this.withY(other.y());
	}
}
