package net.pixaurora.kit_tunes.impl.ui.toast;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
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

	public ResourcePath topTexture() {
		return this.textures.topTexture;
	}

	public ResourcePath middleTexture() {
		return this.textures.middleTexture;
	}

	public ResourcePath bottomTexture() {
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
		private final ResourcePath topTexture;
		private final ResourcePath middleTexture;
		private final ResourcePath bottomTexture;

		private final Size topSize;
		private final Size middleSize;
		private final Size bottomSize;

		public Textures(ResourcePath topTexture, Size topSize, ResourcePath middleTexture, Size middleSize, ResourcePath bottomTexture, Size bottomSize) {
			this.topTexture = topTexture;
			this.middleTexture = middleTexture;
			this.bottomTexture = bottomTexture;
			this.topSize = topSize;
			this.middleSize = middleSize;
			this.bottomSize = bottomSize;
		}
	}
}
