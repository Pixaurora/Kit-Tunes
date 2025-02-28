package net.pixaurora.kitten_cube.impl.ui.widget.button;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;

public class IconButton extends AbstractIconButton {
    private final GuiTexture icon;
    private final ClickEvent onClick;

    private boolean isDisabled;

    public IconButton(GuiTexture icon, ClickEvent onClick) {
        this.icon = icon;
        this.onClick = onClick;
        this.isDisabled = false;
    }

    @Override
    protected GuiTexture icon() {
        return this.icon;
    }

    @Override
    protected void onClick(Point mousePos) {
        this.onClick.onClick(this);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public void setDisabledStatus(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
}
