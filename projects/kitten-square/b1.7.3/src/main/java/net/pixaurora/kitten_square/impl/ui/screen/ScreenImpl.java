package net.pixaurora.kitten_square.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.display.GuiDisplayImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextFieldImpl;

public class ScreenImpl extends net.minecraft.client.gui.screen.Screen {
    private final Screen screen;
    private final List<TextFieldImpl> textFields;

    private final ConversionCacheImpl conversions;

    public ScreenImpl(Screen screen) {
        super();

        this.screen = screen;
        this.textFields = new ArrayList<>();
        this.conversions = new ConversionCacheImpl();
    }

    // "Minecraft Screen" functions

    @Override
    public void init() {
        textFields.clear();

        this.screen.init(Size.of(this.width, this.height));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);

        GuiDisplay display = new GuiDisplayImpl(this.conversions);
        Point mousePos = Point.of(mouseX, mouseY);

        this.screen.draw(display, mousePos);

        for (TextFieldImpl textField : textFields) {
            textField.render();
        }
    }

    @Override
    public void removed() {
        if (!UICompatImpl.openingNewScreen) {
            this.screen.onExit();
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        Point mousePos = Point.of(x, y);

        this.screen.handleClick(mousePos, MouseButton.fromGlfwCode(button));

        for (TextFieldImpl textField : textFields) {
            textField.mouseClicked(x, y, button);
        }
    }

    @Override
    protected void keyPressed(char chr, int key) {
        super.keyPressed(chr, key);

        for (TextFieldImpl textField : textFields) {
            textField.keyPressed(chr, key);
        }
    }

    @Override
    public void tick() {
        this.screen.handleTick();
    }

    public void addTextField(TextFieldImpl textField) {
        this.textFields.add(textField);
    }
}
