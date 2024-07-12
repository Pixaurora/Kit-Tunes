package net.pixaurora.kit_tunes.impl.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pixaurora.kit_tunes.impl.ui.display.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;

public class KitTunesScreenImpl extends net.minecraft.client.gui.screens.Screen {
    private final Screen screen;

    private final ConversionCacheImpl conversions;

    public KitTunesScreenImpl(Screen screen) {
        super(Component.empty());

        this.screen = screen;

        this.conversions = new ConversionCacheImpl();
    }

    // "Minecraft Screen" functions

    @Override
    public void init() {
        this.screen.init(Size.of(this.width, this.height));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        GuiDisplay display = new GuiDisplayImpl(graphics, this.conversions);
        Point mousePos = Point.of(mouseX, mouseY);

        this.screen.draw(display, mousePos);
    }

    @Override
    public void onClose() {
        this.screen.onExit();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        Point mousePos = Point.of((int) x, (int) y);

        this.screen.handleClick(mousePos, button);

        return false;
    }

    @Override
    public void tick() {
        this.screen.tick();
    }
}
