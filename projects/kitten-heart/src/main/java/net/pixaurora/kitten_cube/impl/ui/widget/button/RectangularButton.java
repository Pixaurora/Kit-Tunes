package net.pixaurora.kitten_cube.impl.ui.widget.button;

import java.util.Optional;

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

public class RectangularButton implements Button {
    public static final int ICON_PADDING = 1;
    public static final Size DEFAULT_SIZE = Size.of(200, 20);

    private final ButtonBackground background;
    private final RectangularSurface surface;

    private final Component text;
    private final Point textPos;
    private final Optional<Icon> icon;

    private final ClickEvent action;

    private boolean isDisabled;

    public RectangularButton(ButtonBackground background, Component text, Optional<GuiTexture> icon,
            ClickEvent action) {
        this.background = background;
        this.surface = RectangularSurface.of(background.size());
        this.text = text;
        this.action = action;

        Size textSize = MinecraftClient.textSize(text);

        Size totalSize = icon.isPresent() ? textSize.offset(ICON_PADDING, 0).offset(icon.get().size()) : textSize;

        Point startPos = background.size().centerWithinSelf(totalSize.withY(0));

        this.textPos = textSize.centerVertically(startPos);
        this.isDisabled = false;
        this.icon = icon.map(icon0 -> new Icon(icon0,
                icon0.size().centerVertically(startPos.offset(textSize.withY(0).offset(ICON_PADDING, 0)))));
    }

    public static RectangularButton vanillaButton(Component text, ClickEvent action) {
        return new RectangularButton(ButtonBackground.NEUTRAL_RECTANGLE, text, Optional.empty(), action);
    }

    public static RectangularButton vanillaButton(Component text, GuiTexture icon, ClickEvent action) {
        return new RectangularButton(ButtonBackground.NEUTRAL_RECTANGLE, text, Optional.of(icon), action);
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        GuiTexture background = this.background.texture(this.isDisabled, this.surface.isWithinBounds(mousePos));
        gui.drawGui(background, Point.ZERO);

        gui.drawText(this.text, Color.PURPLE, this.textPos, false);
        if (this.icon.isPresent()) {
            Icon icon = this.icon.get();
            gui.drawGui(icon.texture, icon.pos);
        }
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

    private static class Icon {
        private final GuiTexture texture;
        private final Point pos;

        public Icon(GuiTexture texture, Point pos) {
            this.texture = texture;
            this.pos = pos;
        }
    }
}
