package net.pixaurora.kitten_heart.impl.ui.widget;

import java.util.function.Supplier;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.button.AbstractIconButton;
import net.pixaurora.kitten_cube.impl.ui.widget.button.Button;
import net.pixaurora.kitten_cube.impl.ui.widget.button.ButtonBackground;
import net.pixaurora.kitten_cube.impl.ui.widget.button.ClickEvent;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;

public class PauseButton extends AbstractIconButton {
    private static final ButtonBackground BACKGROUND = ButtonBackground.NEUTRAL_SQUARE;
    private static final Size SIZE = Size.of(20, 20);

    private final Supplier<PlaybackState> playbackState;
    private final ClickEvent onClick;

    public PauseButton(Supplier<PlaybackState> stateSupplier, ClickEvent onClick) {
        this.playbackState = stateSupplier;
        this.onClick = onClick;
    }

    @Override
    protected GuiTexture icon() {
        return this.playbackState.get().icon();
    }

    @Override
    protected void onClick(Point mousePos) {
        this.onClick.onClick(this);
    }

    @Override
    public boolean isDisabled() {
        return this.playbackState.get() == PlaybackState.STOPPED;
    }

    @Override
    public void setDisabledStatus(boolean isDisabled) {
        throw new UnsupportedOperationException("Unimplemented method 'setDisabledStatus'");
    }
}
