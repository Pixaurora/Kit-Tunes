package net.pixaurora.kit_tunes.impl.ui.widget.button;

import net.pixaurora.kit_tunes.impl.resource.ResourcePathImpl;
import net.pixaurora.kit_tunes.impl.ui.GuiDisplay;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.sound.Sound;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.text.TextProcessor;
import net.pixaurora.kit_tunes.impl.ui.texture.GuiTexture;
import net.pixaurora.kit_tunes.impl.ui.widget.WidgetHandle;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kit_tunes.impl.ui.widget.surface.WidgetSurface;

public class RectangularButton implements Button {
    private static final GuiTexture DEFAULT_DISABLED_TEXTURE = GuiTexture
            .of(new ResourcePathImpl("minecraft", "textures/gui/sprites/widget/button_disabled.png"), Size.of(200, 20));
    private static final GuiTexture DEFAULT_UNHIGLIGHTED_TEXTURE = GuiTexture
            .of(new ResourcePathImpl("minecraft", "textures/gui/sprites/widget/button.png"), Size.of(200, 20));
    private static final GuiTexture DEFAULT_HIGHLIGHTED_TEXTURE = GuiTexture.of(
            new ResourcePathImpl("minecraft", "textures/gui/sprites/widget/button_highlighted.png"), Size.of(200, 20));

    private final ButtonBackground background;
    private final RectangularSurface surface;

    private final Component text;
    private final Point textPos;

    private final ClickEvent action;

    private boolean isDisabled;

    public RectangularButton(ButtonBackground background, Point pos, Component text, TextProcessor font,
            ClickEvent action) {
        this.background = background;
        this.surface = RectangularSurface.of(pos, background.size());
        this.text = text;
        this.action = action;

        Size textSize = font.textSize(text);

        this.textPos = pos.offset(background.size().centerWithinSelf(textSize));
        this.isDisabled = false;
    }

    public static RectangularButton vanillaButton(Point pos, Component text, TextProcessor font, ClickEvent action) {
        return new RectangularButton(new ButtonBackground(DEFAULT_UNHIGLIGHTED_TEXTURE, DEFAULT_HIGHLIGHTED_TEXTURE,
                DEFAULT_DISABLED_TEXTURE), pos, text, font, action);
    }

    @Override
    public void draw(WidgetHandle handle, GuiDisplay gui, Point mousePos) {
        GuiTexture background = this.background.texture(this.isDisabled, this.surface.isWithinBounds(mousePos));
        gui.drawGuiTexture(background, this.surface.startPos());

        gui.drawText(this.text, Color.WHITE, this.textPos);
    }

    @Override
    public WidgetSurface surface() {
        return this.surface;
    }

    @Override
    public boolean isDisabled() {
        return this.isDisabled;
    }

    @Override
    public void setDisabledStatus(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public void onClick(WidgetHandle handle, Point mousePos) {
        handle.playSound(Sound.BUTTON_CLICK);
        this.action.onClick(this);
    }
}
