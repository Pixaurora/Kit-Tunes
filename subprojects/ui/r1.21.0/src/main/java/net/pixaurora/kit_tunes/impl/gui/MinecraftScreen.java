package net.pixaurora.kit_tunes.impl.gui;

import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;

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
    public void handleClick(Point mousePos, int button) {
    }

    public net.minecraft.client.gui.screens.Screen parent() {
        return this.parent;
    }
}
