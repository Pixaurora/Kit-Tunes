package net.pixaurora.kitten_cube.impl.ui.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;

public class TiledBackground implements Widget {
    private final GuiTexture texture;
    private List<Point> posToDrawAt;

    public TiledBackground(GuiTexture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        for (Point pos : posToDrawAt) {
            gui.drawGui(this.texture, pos);
        }
    }

    @Override
    public void onWindowUpdate(Size window) {
        Size texture = this.texture.size();

        Size tileCounts = Size.of((int) Math.ceil((float) window.width() / texture.width()),
                (int) Math.ceil((float) window.height() / texture.height()));

        this.posToDrawAt = new ArrayList<>();
        for (int tileY = 0; tileY <= tileCounts.height(); tileY++) {
            for (int tileX = 0; tileX <= tileCounts.width(); tileX++) {
                this.posToDrawAt.add(Point.of(tileX * texture.width(), tileY * texture.height()));
            }
        }
    }

    @Override
    public Optional<AlignmentStrategy> alignmentMethod() {
        return Optional.of(Alignment.TOP_LEFT);
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }
}
