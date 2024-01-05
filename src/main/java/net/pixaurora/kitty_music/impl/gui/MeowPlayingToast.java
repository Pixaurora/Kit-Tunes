package net.pixaurora.kitty_music.impl.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kitty_music.impl.KittyMusic;
import net.pixaurora.kitty_music.impl.music.TrackInfo;

public class MeowPlayingToast implements Toast {
	public static final ResourceLocation DEFAULT_ALBUM_SPRITE = new ResourceLocation("kitty_music:textures/album_art/default.png");
	public static final ResourceLocation TOAST_BACKGROUND = new ResourceLocation("toast/tutorial");

	public static final Component TITLE = Component.translatable("kitty_music.toast.title");

	private final ResourceLocation albumSprite;
	private final Component songName;

	private boolean hasRendered;
	private long firstRenderedTime;

	public MeowPlayingToast(TrackInfo track) {
		this.albumSprite = DEFAULT_ALBUM_SPRITE;
		this.songName = track.name();

		this.hasRendered = false;
	}

	private void drawAlbumArt(GuiGraphics graphics, int x, int y) {
		RenderSystem.enableBlend();
		graphics.blitSprite(this.albumSprite, x, y, 16, 16);
	}

	@Override
	public Toast.Visibility render(GuiGraphics graphics, ToastComponent manager, long startTime) {
		if (!this.hasRendered) {
			this.hasRendered = true;
			this.firstRenderedTime = startTime;
		}

		graphics.blitSprite(TOAST_BACKGROUND, 0, 0, this.width(), this.height());

		this.drawAlbumArt(graphics, 6, 6);

		Minecraft client = manager.getMinecraft();

		graphics.drawString(client.font, TITLE, 30, 7, ChatFormatting.DARK_PURPLE.getColor(), false);
		graphics.drawString(client.font, this.songName, 30, 18, ChatFormatting.BLACK.getColor(), false);

		return startTime - this.firstRenderedTime < 5000 ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
	}
}
