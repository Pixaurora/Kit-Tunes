package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;

public class KitTunesScreenImpl extends net.minecraft.client.gui.screens.Screen implements ScreenHandle {
	private final Screen screen;

	private final net.minecraft.client.gui.screens.Screen parent;

	private final ConversionCacheImpl conversions;

	public KitTunesScreenImpl(net.minecraft.client.gui.screens.Screen parent, Screen screen) {
		super(Component.empty());

		this.screen = screen;

		this.conversions = new ConversionCacheImpl();

		this.parent = parent;
	}

	@Override
	public void init() {
		this.screen.init(this, Size.of(this.width, this.height));
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);

		this.screen.draw(this, new GuiDisplayImpl(graphics, this.conversions));
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}
