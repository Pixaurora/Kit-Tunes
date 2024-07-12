package net.pixaurora.kit_tunes.impl.gui.widget;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kit_tunes.impl.ui.MinecraftClient;
import net.pixaurora.kit_tunes.impl.ui.math.Point;
import net.pixaurora.kit_tunes.impl.ui.math.Size;
import net.pixaurora.kit_tunes.impl.ui.text.Color;
import net.pixaurora.kit_tunes.impl.ui.widget.text.TextBox;

public class TextBoxImpl implements TextBox {
    public final List<FormattedCharSequence> lines;
    public final Color color;
    public final Point startPos;

    public TextBoxImpl(List<FormattedCharSequence> lines, Color color, Point startPos) {
        this.lines = lines;
        this.color = color;
        this.startPos = startPos;
    }

    @SuppressWarnings("resource")
    @Override
    public Size size() {
        int width = 0;

        for (FormattedCharSequence line : lines) {
            width = Math.max(width, Minecraft.getInstance().font.width(line));
        }

        return Size.of(width, MinecraftClient.textHeight() * lines.size());
    }
}
