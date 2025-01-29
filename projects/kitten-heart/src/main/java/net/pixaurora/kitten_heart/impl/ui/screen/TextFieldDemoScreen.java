package net.pixaurora.kitten_heart.impl.ui.screen;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.ReturnToPreviousScreen;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.widget.button.RectangularButton;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextField;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class TextFieldDemoScreen extends KitTunesScreenTemplate {
    public TextFieldDemoScreen(Screen previous) {
        super(previous);
    }

    @Override
    protected Alignment alignmentMethod() {
        return Alignment.CENTER;
    }

    @Override
    protected void firstInit() {
        WidgetContainer<TextField> textField = this.addWidget(TextField.regular(Component.literal("write in me!!"), 64))
                .anchor(WidgetAnchor.TOP_MIDDLE);
        this.addWidget(RectangularButton.vanillaButton(Component.literal("log text field"),
                button -> KitTunes.LOGGER.info("Text field input: " + textField.get().input())))
                .anchor(WidgetAnchor.TOP_MIDDLE)
                .align(textField.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .at(Point.of(0, 10));
    }
}
