package net.pixaurora.kit_tunes.impl.ui.toast;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.MinecraftClient;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kit_tunes.impl.ui.widget.text.PositionedText;
import net.pixaurora.kit_tunes.impl.util.Pair;

public class DrawableToast implements Toast {
    private final Texture icon;
    private final Point iconPos;

    private final List<PositionedInnerTile> tiles;

    private final List<PositionedText> text;

    private final Size size;

    public DrawableToast(KitTunesToastData data) {
        ToastBackground background = data.background();

        this.icon = data.icon();
        this.iconPos = background.iconPos();

        this.text = new ArrayList<>();
        this.text.add(new PositionedText(data.title(), data.titleColor(), background.titlePos()));

        Point textPos = background.bodyTextStartPos();
        for (Component line : data.messageLines()) {
            for (Component splitLine : MinecraftClient.splitText(line, background.maxLineLength())) {
                this.text.add(new PositionedText(splitLine, data.messageColor(), textPos));
                textPos = textPos.offset(0, MinecraftClient.textHeight());
            }
        }

        Pair<List<PositionedInnerTile>, Size> tilesAndSize = background.tilesAndSize(this.text);

        this.tiles = tilesAndSize.first();
        this.size = tilesAndSize.second();
    }

    @Override
    public Duration timeShown() {
        return Duration.ofSeconds(5);
    }

    @Override
    public void draw(GuiDisplay gui) {
        this.drawBackground(gui);

        gui.draw(icon, iconPos);

        for (PositionedText line : this.text) {
            gui.drawText(line.text(), line.color(), line.pos(), false);
        }
    }

    private void drawBackground(GuiDisplay gui) {
        for (PositionedInnerTile tile : this.tiles) {
            tile.draw(gui);
        }
    }

    @Override
    public Size size() {
        return this.size;
    }
}
