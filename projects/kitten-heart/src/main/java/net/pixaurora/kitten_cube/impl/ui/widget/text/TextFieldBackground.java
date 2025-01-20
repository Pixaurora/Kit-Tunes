package net.pixaurora.kitten_cube.impl.ui.widget.text;

import java.util.function.Function;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class TextFieldBackground<T> {
    public static final TextFieldBackground<Texture> REGULAR_BACKGROUND = new TextFieldBackground<>(
            Texture.of(KitTunes.resource("textures/gui/sprites/widget/text_field/normal.png"), Size.of(200, 20)),
            Texture.of(KitTunes.resource("textures/gui/sprites/widget/text_field/highlighted.png"), Size.of(200, 20)));

    private final T normal;
    private final T highlighted;

    public TextFieldBackground(T normal, T highlighted) {
        this.normal = normal;
        this.highlighted = highlighted;
    }

    public <G> TextFieldBackground<G> map(Function<T, G> mapFunction) {
        return new TextFieldBackground<G>(mapFunction.apply(this.normal), mapFunction.apply(this.highlighted));
    }

    public T normal() {
        return normal;
    }

    public T getHighlighted() {
        return highlighted;
    }
}
