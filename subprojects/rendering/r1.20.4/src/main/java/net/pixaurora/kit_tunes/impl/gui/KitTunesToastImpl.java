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
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastBackground;
import net.pixaurora.kit_tunes.impl.ui.toast.KitTunesToastData;

public class KitTunesToastImpl implements Toast {
	private final KitTunesToastData toastData;

	private final ResourceLocation icon;
	private final ResourceLocation backgroundTop;
	private final ResourceLocation backgroundMiddle;
	private final ResourceLocation backgroundBottom;

	private final Component title;
	private final List<FormattedCharSequence> bodyLines;
	private final int middleSectionCount;

	private boolean hasRendered = false;
	private long firstRenderedTime;

	public KitTunesToastImpl(Font font, KitTunesToastData toastData) {
		this.toastData = toastData;

		this.icon = KitTunesUIImpl.resourceToMinecraftType(this.toastData.icon());

		KitTunesToastBackground background = toastData.background();

		this.backgroundTop = KitTunesUIImpl.resourceToMinecraftType(background.topTexture());
		this.backgroundMiddle = KitTunesUIImpl.resourceToMinecraftType(background.middleTexture());
		this.backgroundBottom = KitTunesUIImpl.resourceToMinecraftType(background.bottomTexture());

		List<MutableComponent> lines = toastData.messageLines().stream()
			.map(KitTunesUIImpl::componentToMinecraftType)
			.toList();

		this.title = KitTunesUIImpl.componentToMinecraftType(toastData.title());
		this.bodyLines = new ArrayList<>();

		for (Component line : lines) {
			this.bodyLines.addAll(font.split(line, background.maxLineLength()));
		}

		float textEndY = background.bodyTextStartPos().y() + this.bodyLines.size() * font.lineHeight;

		float topAndBottomHeight = background.topSize().y() + background.bottomSize().y();
		float middleSectionHeight = background.middleSize().y();
		this.middleSectionCount = (int) Math.ceil((textEndY - topAndBottomHeight) / middleSectionHeight);
	}

	@Override
	public int height() {
		KitTunesToastBackground background = toastData.background();
		return background.topSize().y() + this.middleSectionCount * background.middleSize().y() + background.bottomSize().y();
	}

	public void drawTexture(ResourceLocation texture, GuiGraphics graphics, int x, int y, int width, int height) {
		graphics.blit(texture, x, y, 0, 0.0F, 0.0F, width, height, width, height);
	}

	private void drawToastBackground(GuiGraphics graphics, int x, int y) {
		KitTunesToastBackground background = this.toastData.background();

		Size topSize = background.topSize();
		this.drawTexture(backgroundTop, graphics, x, y, topSize.x(), topSize.y());
		y += topSize.y();

		Size middleSize = background.middleSize();
		for (int row = 0; row < this.middleSectionCount; row++) {
			this.drawTexture(backgroundMiddle, graphics, x, y, middleSize.x(), middleSize.y());
			y += middleSize.y();
		}

		Size bottomSize = background.bottomSize();
		this.drawTexture(backgroundBottom, graphics, x, y, bottomSize.x(), bottomSize.y());
	}

	@Override
	public Toast.Visibility render(GuiGraphics graphics, ToastComponent manager, long frameTime) {
		if (!this.hasRendered) {
			this.hasRendered = true;
			this.firstRenderedTime = frameTime;
		}

		this.drawToastBackground(graphics, 0, 0);

		KitTunesToastBackground background = this.toastData.background();

		Size iconSize = this.toastData.iconSize();
		Point iconPos = background.iconPos();
		this.drawTexture(this.icon, graphics, iconPos.x(), iconPos.y(), iconSize.x(), iconSize.y());

		Minecraft client = manager.getMinecraft();

		Point titlePos = background.titlePos();
		graphics.drawString(client.font, title, titlePos.x(), titlePos.y(), ChatFormatting.AQUA.getColor(), false);

		Point bodyTextStart = background.bodyTextStartPos();
		int currentLineY = bodyTextStart.y();

		for (FormattedCharSequence line : this.bodyLines) {
			graphics.drawString(client.font, line, bodyTextStart.x(), currentLineY, ChatFormatting.BLACK.getColor(), false);
			currentLineY += client.font.lineHeight;
		}

		return frameTime - this.firstRenderedTime < 5000 ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
	}
}
