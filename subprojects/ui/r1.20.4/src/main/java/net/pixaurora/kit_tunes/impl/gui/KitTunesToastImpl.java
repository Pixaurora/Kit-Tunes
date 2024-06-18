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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kit_tunes.impl.KitTunesUIImpl;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.toast.ToastBackground;
import net.pixaurora.kit_tunes.impl.ui.toast.ToastBackgroundAppearance;
import net.pixaurora.kit_tunes.impl.ui.toast.ToastBackgroundTile;
import net.pixaurora.kit_tunes.impl.util.Pair;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastData;

public class KitTunesToastImpl implements Toast {
	private final KitTunesToastData toastData;

	private final ResourceLocation icon;

	private final ResourceLocation background;
	private final List<ToastBackgroundTile> tiles;

	private final Component title;
	private final List<FormattedCharSequence> bodyLines;
	private final Size toastSize;

	private boolean hasRendered = false;
	private long firstRenderedTime;

	public KitTunesToastImpl(Font font, KitTunesToastData toastData) {
		this.toastData = toastData;

		this.icon = KitTunesUIImpl.resourceToMinecraftType(this.toastData.icon());

		ToastBackground background = toastData.background();

		this.background = KitTunesUIImpl.resourceToMinecraftGuiSprite(background.appearance().texture());

		List<MutableComponent> lines = toastData.messageLines().stream()
			.map(KitTunesUIImpl::componentToMinecraftType)
			.toList();

		this.title = KitTunesUIImpl.componentToMinecraftType(toastData.title());
		this.bodyLines = new ArrayList<>();

		for (Component line : lines) {
			this.bodyLines.addAll(font.split(line, background.maxLineLength()));
		}

		int textWidth = font.width(this.title);

		for (FormattedCharSequence line : this.bodyLines) {
			textWidth = Math.max(font.width(line), textWidth);
		}

		Size textBox = Size.of(textWidth, this.bodyLines.size() * font.lineHeight);

		Pair<List<ToastBackgroundTile>, Size> tilesAndSize = background.tilesAndSize(textBox);

		this.tiles = tilesAndSize.first();
		this.toastSize = tilesAndSize.second();
	}

	@Override
	public int width() {
		return this.toastSize.width();
	}

	@Override
	public int height() {
		return this.toastSize.height();
	}

	@Override
	public Toast.Visibility render(GuiGraphics graphics, ToastComponent manager, long frameTime) {
		if (!this.hasRendered) {
			this.hasRendered = true;
			this.firstRenderedTime = frameTime;
		}

		Point offset = Point.ZERO;

		this.drawToastBackground(graphics, offset);

		ToastBackground background = this.toastData.background();

		this.drawTexture(graphics, this.icon, background.iconPos().offset(offset), this.toastData.iconSize());

		Minecraft client = manager.getMinecraft();

		Point titlePos = background.titlePos().offset(offset);
		graphics.drawString(client.font, title, titlePos.x(), titlePos.y(), ChatFormatting.AQUA.getColor(), false);

		Point bodyTextStart = background.bodyTextStartPos().offset(offset);
		int currentLineY = bodyTextStart.y();

		for (FormattedCharSequence line : this.bodyLines) {
			graphics.drawString(client.font, line, bodyTextStart.x(), currentLineY, ChatFormatting.BLACK.getColor(), false);
			currentLineY += client.font.lineHeight;
		}

		return frameTime - this.firstRenderedTime < 5000 ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
	}

	public void drawTexture(GuiGraphics graphics, ResourceLocation texture, Point pos, Size size) {
		int width = size.width();
		int height = size.height();

		graphics.blit(texture, pos.x(), pos.y(), 0, 0.0F, 0.0F, width, height, width, height);
	}

	private void drawToastBackground(GuiGraphics graphics, Point offset) {
		ToastBackgroundAppearance appearance = this.toastData.background().appearance();

		Size texture = appearance.size();

		for (ToastBackgroundTile tile : this.tiles) {
			Size tileSize = tile.size();
			Point textureOffset = tile.textureOffset();

			Point tilePos = tile.pos().offset(offset);

			graphics.blitSprite(
				this.background,
				texture.width(), texture.height(),
				textureOffset.x(), textureOffset.y(),
				tilePos.x(), tilePos.y(),
				tileSize.width(), tileSize.height()
			);
		}
	}
}
