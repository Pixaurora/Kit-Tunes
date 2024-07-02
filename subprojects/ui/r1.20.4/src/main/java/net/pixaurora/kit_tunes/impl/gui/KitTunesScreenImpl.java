package net.pixaurora.kit_tunes.impl.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;
import net.pixaurora.kit_tunes.impl.ui.screen.ScreenHandle;
import net.pixaurora.kit_tunes.impl.ui.widget.Widget;

public class KitTunesScreenImpl extends net.minecraft.client.gui.screens.Screen implements ScreenHandle {
    private final Screen screen;
    private final List<WidgetImpl> widgets;

    private final net.minecraft.client.gui.screens.Screen parent;

    private final ConversionCacheImpl conversions;

    public KitTunesScreenImpl(net.minecraft.client.gui.screens.Screen parent, Screen screen) {
        super(Component.empty());

        this.screen = screen;
        this.widgets = new ArrayList<>();

        this.conversions = new ConversionCacheImpl();

        this.parent = parent;
    }

    // "Core bridge" functions

    @Override
    public void addWidget(Widget widget) {
        WidgetImpl impl = new WidgetImpl(widget, this);

        this.widgets.add(impl);
        this.addWidget(impl);
    }

    // "Minecraft Screen" functions

    @Override
    public void init() {
        this.widgets.clear();
        this.screen.init(this, Size.of(this.width, this.height));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        GuiDisplay display = new GuiDisplayImpl(graphics, this.conversions);
        Point mousePos = Point.of(mouseX, mouseY);

        this.screen.draw(display, mousePos);
        for (WidgetImpl widget : this.widgets) {
            widget.coreVersion().draw(display, mousePos);
        }
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
