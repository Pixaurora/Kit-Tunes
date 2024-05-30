package net.pixaurora.kit_tunes.impl.ui.toast;

import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public class KitTunesToastBackground {
	private final KitTunesToastBackground.Textures textures;

	private final Point iconPos;

	private final Point titlePos;

	private final Point linesStartPos;
	private final int maxLineLength;

	public KitTunesToastBackground(KitTunesToastBackground.Textures textures, Point iconPos, Point titlePos, Point linesStartPos, int maxLineLength) {
		this.textures = textures;
		this.iconPos = iconPos;
		this.titlePos = titlePos;
		this.linesStartPos = linesStartPos;
		this.maxLineLength = maxLineLength;
	}

	public Point iconPos() {
		return this.iconPos;
	}

	public Point titlePos() {
		return this.titlePos;
	}

	public Point bodyTextStartPos() {
		return this.linesStartPos;
	}

	public int maxLineLength() {
		return this.maxLineLength;
	}

	public NamespacedResourcePath topTexture() {
		return this.textures.topTexture;
	}

	public NamespacedResourcePath middleTexture() {
		return this.textures.middleTexture;
	}

	public NamespacedResourcePath bottomTexture() {
		return this.textures.bottomTexture;
	}

	public Size topSize() {
		return this.textures.topSize;
	}

	public Size middleSize() {
		return this.textures.middleSize;
	}

	public Size bottomSize() {
		return this.textures.bottomSize;
	}

	public static class Textures {
		private final NamespacedResourcePath topTexture;
		private final NamespacedResourcePath middleTexture;
		private final NamespacedResourcePath bottomTexture;

		private final Size topSize;
		private final Size middleSize;
		private final Size bottomSize;

		public Textures(NamespacedResourcePath topTexture, Size topSize, NamespacedResourcePath middleTexture, Size middleSize, NamespacedResourcePath bottomTexture, Size bottomSize) {
			this.topTexture = topTexture;
			this.middleTexture = middleTexture;
			this.bottomTexture = bottomTexture;
			this.topSize = topSize;
			this.middleSize = middleSize;
			this.bottomSize = bottomSize;
		}
	}
}
