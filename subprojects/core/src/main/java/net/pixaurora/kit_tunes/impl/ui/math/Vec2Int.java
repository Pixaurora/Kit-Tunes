package net.pixaurora.kit_tunes.impl.ui.math;

public interface Vec2Int<T extends Vec2Int<T>> {
	public T constructVec(int x, int y);

	public int x();

	public int y();

	public default T offset(T by) {
		return this.constructVec(this.x() + by.x(), this.y() + by.y());
	}
}
