package net.pixaurora.kitten_cube.impl.ui.widget.button;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;

public class RectangularButton implements Button {
    public static final Size DEFAULT_SIZE = Size.of(200, 20);

    private static final GuiTexture DEFAULT_DISABLED_TEXTURE = GuiTexture
            .of(new ResourcePathImpl("kit_tunes", "textures/gui/sprites/widget/button_disabled.png"), DEFAULT_SIZE);
    private static final GuiTexture DEFAULT_UNHIGLIGHTED_TEXTURE = GuiTexture
            .of(new ResourcePathImpl("kit_tunes", "textures/gui/sprites/widget/button.png"), DEFAULT_SIZE);
    private static final GuiTexture DEFAULT_HIGHLIGHTED_TEXTURE = GuiTexture
            .of(new ResourcePathImpl("kit_tunes", "textures/gui/sprites/widget/button_highlighted.png"), DEFAULT_SIZE);

    private final ButtonBackground background;
    private final RectangularSurface surface;

    private final Component text;
    private final Point textPos;

    private final ClickEvent action;

    private boolean isDisabled;

    public RectangularButton(ButtonBackground background, Point pos, Component text, ClickEvent action) {
        this.background = background;
        this.surface = RectangularSurface.of(pos, background.size());
        this.text = text;
        this.action = action;

        Size textSize = MinecraftClient.textSize(text);

        this.textPos = pos.offset(background.size().centerWithinSelf(textSize));
        this.isDisabled = false;
    }

    public static RectangularButton vanillaButton(Point pos, Component text, ClickEvent action) {
        return new RectangularButton(new ButtonBackground(DEFAULT_UNHIGLIGHTED_TEXTURE, DEFAULT_HIGHLIGHTED_TEXTURE,
                DEFAULT_DISABLED_TEXTURE), pos, text, action);
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        GuiTexture background = this.background.texture(this.isDisabled, this.surface.isWithinBounds(mousePos));
        gui.drawGui(background, this.surface.startPos());

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
    public void onClick(Point mousePos, MouseButton button) {
        MinecraftClient.playSound(Sound.BUTTON_CLICK);

        if (button == MouseButton.PRIMARY) {
            this.action.onClick(this);
        }
    }
}
