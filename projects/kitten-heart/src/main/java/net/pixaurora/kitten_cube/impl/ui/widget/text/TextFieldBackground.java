package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.function.Function;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;

public class TextFieldBackground<T> {
    public static final TextFieldBackground<GuiTexture> REGULAR_BACKGROUND = new TextFieldBackground<>(
            GuiTexture.of("textures/gui/sprites/widget/text_field/normal.png", Size.of(200, 20)),
            GuiTexture.of("textures/gui/sprites/widget/text_field/highlighted.png", Size.of(200, 20)),
            new Colors(Color.GRAY, Color.BLUE, Color.WHITE, false));

    private final T normal;
    private final T highlighted;
    private final Colors colors;

    public TextFieldBackground(T normal, T highlighted, Colors colors) {
        this.normal = normal;
        this.highlighted = highlighted;
        this.colors = colors;
    }

    public <G> TextFieldBackground<G> map(Function<T, G> mapFunction) {
        return new TextFieldBackground<G>(mapFunction.apply(this.normal), mapFunction.apply(this.highlighted),
                this.colors);
    }

    public T normal() {
        return this.normal;
    }

    public T highlighted() {
        return this.highlighted;
    }

    public Colors colors() {
        return this.colors;
    }

    public static class Colors {
        private final Color untyped;
        private final Color normal;
        private final Color highlighted;

        private final boolean shadowed;

        public Colors(Color untyped, Color normal, Color highlighted, boolean shadowed) {
            this.untyped = untyped;
            this.normal = normal;
            this.highlighted = highlighted;
            this.shadowed = shadowed;
        }

        public Color untyped() {
            return this.untyped;
        }

        public Color normal() {
            return this.normal;
        }

        public Color highlighted() {
            return this.highlighted;
        }

        public boolean shadowed() {
            return this.shadowed;
        }
    }
}
