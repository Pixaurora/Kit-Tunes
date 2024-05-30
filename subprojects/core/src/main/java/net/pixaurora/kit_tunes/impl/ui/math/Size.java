package net.pixaurora.kit_tunes.impl.ui.math;

public class Size implements Vec2Int<Size> {
	private final int x;
	private final int y;

	private Size(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Size of(int x, int y) {
		return new Size(x, y);
	}

	@Override
	public Size constructVec(int x, int y) {
		return of(x, y);
	}

	@Override
	public int x() {
		return this.x;
	}

	@Override
	public int y() {
		return this.y;
	}
}
