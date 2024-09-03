package net.pixaurora.kitten_square.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;

public class MinecraftScreen implements Screen {
    private final net.minecraft.client.gui.screens.Screen parent;

    public MinecraftScreen(net.minecraft.client.gui.screens.Screen parent) {
        this.parent = parent;
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
    }

    @Override
    public void init(Size window) {
        throw new RuntimeException("This isn't a real Kit Tunes screen!");
    }

    @Override
    public void handleClick(Point mousePos, MouseButton button) {
    }

    public net.minecraft.client.gui.screens.Screen parent() {
        return this.parent;
    }
}
