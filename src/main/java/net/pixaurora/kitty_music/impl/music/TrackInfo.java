package net.pixaurora.kitty_music.impl.music;

import net.minecraft.network.chat.Component;

public class TrackInfo {
	private final Component name;

	public TrackInfo(Component trackName) {
		this.name = trackName;
	}

	public Component name() {
		return this.name;
	}
}
