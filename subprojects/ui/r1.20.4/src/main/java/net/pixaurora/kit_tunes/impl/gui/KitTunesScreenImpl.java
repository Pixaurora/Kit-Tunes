package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.impl.KitTunesUIImpl;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.toast.MeowPlayingToast;

public class KitTunesScreenImpl extends net.minecraft.client.gui.screens.Screen {
	private final net.minecraft.client.gui.screens.Screen parent;

	public KitTunesScreenImpl(net.minecraft.client.gui.screens.Screen parent) {
		super(Component.empty());

		this.parent = parent;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);

		ResourceLocation sprite = KitTunesUIImpl.resourceToMinecraftType(MeowPlayingToast.DEFAULT_ICON);

		this.drawTexture(graphics, sprite, Point.of(mouseX, mouseY), Size.of(16, 16));
	}

	public void drawTexture(GuiGraphics graphics, ResourceLocation texture, Point pos, Size size) {
		int width = size.width();
		int height = size.height();

		graphics.blit(texture, pos.x(), pos.y(), 0, 0.0F, 0.0F, width, height, width, height);
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
