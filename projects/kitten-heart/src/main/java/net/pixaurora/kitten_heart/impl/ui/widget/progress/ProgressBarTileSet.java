package net.pixaurora.kitten_heart.impl.ui.widget.progress;

import java.util.EnumMap;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.TilePosition;

public class ProgressBarTileSet {
    private final InnerTile left;
    private final InnerTile middle;
    private final InnerTile right;

    private final EnumMap<TilePosition, InnerTile> tileMap;

    public ProgressBarTileSet(InnerTile left, InnerTile middle, InnerTile right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.tileMap = new EnumMap<>(TilePosition.class);

        this.tileMap.put(TilePosition.LEFT, this.left);
        this.tileMap.put(TilePosition.MIDDLE, this.middle);
        this.tileMap.put(TilePosition.RIGHT, this.right);
    }

    public static ProgressBarTileSet create(GuiTexture texture, Point leftOffset, Size leftSize, Point middleOffset,
            Size middleSize, Point rightOffset, Size rightSize) {
        return new ProgressBarTileSet(new InnerTile(texture, leftOffset, leftSize),
                new InnerTile(texture, middleOffset, middleSize), new InnerTile(texture, rightOffset, rightSize));
    }

    public InnerTile left() {
        return this.left;
    }

    public InnerTile middle() {
        return this.middle;
    }

    public InnerTile right() {
        return this.right;
    }

    public InnerTile get(TilePosition tilePosition) {
        return this.tileMap.get(tilePosition);
    }

    public int height() {
        return this.left.texture().size().height();
    }
}
