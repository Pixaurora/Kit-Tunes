package net.pixaurora.kitten_square.impl.ui.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.TextRenderer;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextField;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextFieldBackground;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class TextFieldImpl extends TextFieldWidget implements TextField {
    private final TextFieldBackground<String> background;
    private final Size size;

    public TextFieldImpl(Screen screen, TextRenderer font, TextFieldBackground<GuiTexture> background, Component defaultText, int maxLength) {
        super(screen, font, 0, 0, background.normal().size().width(), background.normal().size().height(), "");

        this.background = background
            .map(texture -> UICompatImpl.internalToMinecraftType(texture.path()));
        this.size = background.normal().size();

        this.setMaxLength(maxLength);
    }

    @Override
    public Size size() {
        return this.size;
    }

    @Override
    public String input() {
        return this.getText();
    }

    @Override
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // For the Mixin class associated with EditBox
    public TextFieldBackground<String> background() {
        return this.background;
    }
}
