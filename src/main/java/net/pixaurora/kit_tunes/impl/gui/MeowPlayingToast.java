package net.pixaurora.kit_tunes.impl.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.music.Album;
import net.pixaurora.kit_tunes.impl.music.Track;

public class MeowPlayingToast implements Toast {
	public static final ResourceLocation DEFAULT_ALBUM_SPRITE = KitTunes.resource("textures/album_art/default.png");

	public static final ResourceLocation BACKGROUND_TOP = KitTunes.resource("textures/gui/toast/loaf_top.png");
	public static final ResourceLocation BACKGROUND_MIDDLE = KitTunes.resource("textures/gui/toast/loaf_middle.png");
	public static final ResourceLocation BACKGROUND_BOTTOM = KitTunes.resource("textures/gui/toast/loaf_bottom.png");

	public static final Component TITLE = Component.translatable("kit_tunes.toast.title");

	public static final int LINE_LENGTH = 120;

	private final Font font;

	private final ResourceLocation albumSprite;
	private final List<FormattedCharSequence> songInfoLines;

	private boolean hasRendered;
	private long firstRenderedTime;

	public MeowPlayingToast(Font font, Track track) {
		this.font = font;
		this.hasRendered = false;

		this.albumSprite = track.album()
			.flatMap(Album::albumArtPath)
			.map(path -> new ResourceLocation(path.namespace(), path.path()))
			.orElse(DEFAULT_ALBUM_SPRITE);

		List<Component> lines = new ArrayList<>();

		lines.add(Component.literal(track.name()));
		lines.add(Component.literal(track.artist().name()));

		if (track.album().isPresent()) {
			lines.add(Component.literal(track.album().get().name()));
		}

		this.songInfoLines = new ArrayList<>();

		for (Component line : lines) {
			this.songInfoLines.addAll(font.split(line, LINE_LENGTH));
		}
	}

	@Override
	public int height() {
		return 32 + this.font.lineHeight * (this.songInfoLines.size() + 1);
	}

	public void drawTexture(ResourceLocation texture, GuiGraphics graphics, int x, int y, int width, int height) {
		graphics.blit(texture, x, y, 0, 0.0F, 0.0F, width, height, width, height);
	}

	private void drawToastBackground(GuiGraphics graphics, int x, int y) {
		this.drawTexture(BACKGROUND_TOP, graphics, x, y, 160, 24);

		// One row is 8 pixels tall, subtract 4 rows for top and bottom texture
		int middleRowCount = Mth.positiveCeilDiv(this.height() - 32, 8);

		for (int row = 3; row < middleRowCount; row++) {
			this.drawTexture(BACKGROUND_MIDDLE, graphics, x, y + row * 8, 160, 8);
		}

		this.drawTexture(BACKGROUND_BOTTOM, graphics, x, y + middleRowCount * 8, 160, 8);
	}

	@Override
	public Toast.Visibility render(GuiGraphics graphics, ToastComponent manager, long startTime) {
		if (!this.hasRendered) {
			this.hasRendered = true;
			this.firstRenderedTime = startTime;
		}

		this.drawToastBackground(graphics, 0, 0);

		this.drawTexture(this.albumSprite, graphics, 8, 1, 16, 16);

		Minecraft client = manager.getMinecraft();

		graphics.drawString(client.font, TITLE, 34, 5, ChatFormatting.AQUA.getColor(), false);

		int lineNumber = 0;
		for (FormattedCharSequence line : this.songInfoLines) {
			graphics.drawString(client.font, line, 34, 19 + font.lineHeight * lineNumber, ChatFormatting.BLACK.getColor(), false);
			lineNumber++;
		}

		return startTime - this.firstRenderedTime < 5000 ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
	}
}
