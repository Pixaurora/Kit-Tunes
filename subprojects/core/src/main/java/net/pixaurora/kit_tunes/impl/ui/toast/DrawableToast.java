package net.pixaurora.kit_tunes.impl.ui.toast;

import java.time.Duration;
import java.util.List;

import net.pixaurora.kit_tunes.impl.ui.MinecraftClient;
import net.pixaurora.kit_tunes.impl.ui.display.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;
import net.pixaurora.kit_tunes.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kit_tunes.impl.ui.widget.text.PositionedText;
import net.pixaurora.kit_tunes.impl.ui.widget.text.TextBox;
import net.pixaurora.kit_tunes.impl.util.Pair;

public class DrawableToast implements Toast {
    private final Texture icon;
    private final Point iconPos;

    private final List<PositionedInnerTile> tiles;

    private final PositionedText title;
    private final TextBox body;

    private final Size size;

    public DrawableToast(KitTunesToastData data) {
        ToastBackground background = data.background();

        this.icon = data.icon();
        this.iconPos = background.iconPos();

        this.title = new PositionedText(data.title(), data.titleColor(), background.titlePos());
        this.body = TextBox.of(data.messageLines(), data.messageColor(), background.maxLineLength(),
                background.bodyTextStartPos());

        Size textSize = this.body.size();
        textSize.withX(Math.max(textSize.x(), MinecraftClient.textWidth(this.title.text())));

        Pair<List<PositionedInnerTile>, Size> tilesAndSize = background.tilesAndSize(textSize);

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

        gui.drawText(this.title.text(), this.title.color(), this.title.pos());
        gui.drawTextBox(this.body);
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
