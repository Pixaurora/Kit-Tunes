package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.List;

import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.text.Component;
import net.pixaurora.kit_tunes.impl.ui.texture.Texture;

public interface KitTunesToastData {
    public Texture icon();

    public Size iconSize();

    public Component title();

    public Color titleColor();

    public List<Component> messageLines();

    public Color messageColor();

    public ToastBackground background();
}
