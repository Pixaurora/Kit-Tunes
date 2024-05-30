package net.pixaurora.kit_tunes.impl.ui;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastData;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastBackground;

public class MeowPlayingToast implements KitTunesToastData {
	public static final Component TITLE = Component.translatable("kit_tunes.toast.title");

	public static final ResourcePath DEFAULT_ICON = KitTunes.resource("textures/album_art/default.png");

	public static final ResourcePath BG_TOP = KitTunes.resource("textures/gui/toast/loaf_top.png");
	public static final ResourcePath BG_MID = KitTunes.resource("textures/gui/toast/loaf_middle.png");
	public static final ResourcePath BG_BOT = KitTunes.resource("textures/gui/toast/loaf_bottom.png");

	public static KitTunesToastBackground BACKGROUND = new KitTunesToastBackground(
		new KitTunesToastBackground.Textures(BG_TOP, Size.of(160, 24), BG_MID, Size.of(160, 8), BG_BOT, Size.of(160, 8)),
		Point.of(8, 1),
		Point.of(34, 5),
		Point.of(34, 19),
		120
	);

	private final Track track;

	public MeowPlayingToast(Track track) {
		this.track = track;
	}

	@Override
	public ResourcePath icon() {
		return this.track.album().flatMap(Album::albumArtPath).orElse(DEFAULT_ICON);
	}

	@Override
	public Size iconSize() {
		return Size.of(16, 16);
	}

	@Override
	public Component title() {
		return TITLE;
	}

	@Override
	public List<Component> messageLines() {
		List<Component> lines = new ArrayList<>();

		Component songDescription = Component
			.literal(this.track.name())
			.concat(Component.literal(" - "))
			.concat(Component.literal(this.track.artist().name()));

		lines.add(songDescription);

		if (this.track.album().isPresent()) {
			Album album = this.track.album().get();

			lines.add(Component.literal(album.name()));
		}

		return lines;
	}

	@Override
	public KitTunesToastBackground background() {
		return BACKGROUND;
	}
}
