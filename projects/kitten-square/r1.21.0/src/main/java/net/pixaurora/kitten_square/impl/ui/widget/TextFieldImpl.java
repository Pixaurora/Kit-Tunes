package net.pixaurora.kitten_square.impl.ui.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextField;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextFieldBackground;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class TextFieldImpl extends EditBox implements TextField {
    private final WidgetSprites background;
    private final Style style;
    private final Size size;

    public TextFieldImpl(Font font, TextFieldBackground<GuiTexture> background, Component defaultText, int maxLength) {
        super(font, background.normal().size().width(), background.normal().size().height(),
                UICompatImpl.internalToMinecraftType(defaultText));
        this.setHint(UICompatImpl.internalToMinecraftType(defaultText));

        TextFieldBackground<ResourceLocation> background0 = background
                .map(texture -> UICompatImpl.internalToMinecraftType(texture.path()));

        this.background = new WidgetSprites(background0.normal(), background0.highlighted());
        this.size = background.normal().size();
        this.style = Style.EMPTY.withColor(background.colors().typed().hex());

        // Hint text
        this.setTextColor(background.colors().hint().hex());
        // Player text
        this.setFormatter((string, color) -> FormattedCharSequence.forward(string, this.style));

        this.setMaxLength(maxLength);
    }

    @Override
    public Size size() {
        return this.size;
    }

    @Override
    public String input() {
        return this.getValue();
    }

    @Override
    public void setPos(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    // For the Mixin class associated with EditBox
    public WidgetSprites background() {
        return this.background;
    }
}
