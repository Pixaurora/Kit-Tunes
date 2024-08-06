package net.pixaurora.kitten_cube.impl.ui.controls;

import net.pixaurora.kitten_heart.impl.KitTunes;

public enum MouseButton {
    PRIMARY(0),
    SECONDARY(1),
    MIDDLE(2);

    private final int defaultOpenglCode;

    private MouseButton(int defaultOpenglCode) {
        this.defaultOpenglCode = defaultOpenglCode;
    }

    public static MouseButton fromGlfwCode(int buttonCode) {
        for (MouseButton button : MouseButton.values()) {
            if (button.defaultOpenglCode == buttonCode) {
                return button;
            }
        }

        KitTunes.LOGGER.error("Unrecognized button code on click: `" + buttonCode + "`! Defaulting to PRIMARY");
        return MouseButton.PRIMARY;
    }
}
