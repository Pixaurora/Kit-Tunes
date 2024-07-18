package net.pixaurora.kitten_cube.impl.ui.toast;

import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;

public interface ToastData {
    public Texture icon();

    public Size iconSize();

    public Component title();

    public Color titleColor();

    public List<Component> messageLines();

    public Color messageColor();

    public ToastBackground background();
}
