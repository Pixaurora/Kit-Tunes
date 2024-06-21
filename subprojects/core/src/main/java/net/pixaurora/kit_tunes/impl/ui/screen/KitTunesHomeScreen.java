package net.pixaurora.kit_tunes.impl.ui.screen;

import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public class KitTunesHomeScreen implements Screen {
	public static final Texture KIT_TUNES_ICON = Texture.of(KitTunes.resource("textures/album_art/default.png"), Size.of(16, 16));

	private Point iconPos;

	@Override
	public void init(ScreenHandle handle, Size window) {
		this.iconPos = window.centerWithinSelf(KIT_TUNES_ICON.size());
	}

	@Override
	public void draw(ScreenHandle handle, GuiDisplay gui) {
		gui.drawTexture(KIT_TUNES_ICON, iconPos);
	}
}
