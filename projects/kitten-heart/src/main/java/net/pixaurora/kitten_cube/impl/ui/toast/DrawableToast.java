package net.pixaurora.kitten_cube.impl.ui.toast;

import java.time.Duration;
import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PositionedText;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_heart.impl.util.Pair;

public class DrawableToast implements Toast {
    private final Texture icon;
    private final Point iconPos;

    private final List<PositionedInnerTile> tiles;

    private final PositionedText title;
    private final TextBox body;

    private final Size size;

    public DrawableToast(ToastData data) {
        ToastBackground background = data.background();

        this.icon = data.icon();
        this.iconPos = background.iconPos();

        this.body = TextBox.of(data.messageLines(), data.messageColor(), background.maxLineLength(),
                background.bodyTextStartPos());
        Size totalBodySize = this.body.size().offset(background.bodyTextStartPos());

        Component title = data.title();

        Point titlePos = background.titlePos();
        if (background.isTitleCentered()) {
            Point centerOfText = titlePos.midPointBetween(totalBodySize.withYOf(titlePos));
            titlePos = title.size().centerOnVertical(centerOfText);
        }

        this.title = new PositionedText(title, data.titleColor(), titlePos);
        Size totalTitleSize = title.size().offset(titlePos);

        Size minimumSize = totalBodySize.overlay(totalTitleSize).offset(background.padding());

        Pair<List<PositionedInnerTile>, Size> tilesAndSize = background.tilesAndSize(minimumSize);

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
