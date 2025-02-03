package net.pixaurora.kitten_cube.impl.text;

/**
 * Colors used for text in the mod, taken from the mod's color palette
 */
public enum Color {
    BLACK(0x22202e),
    BLUE(0x78a2cf),
    PURPLE(0x372a56),
    RED(0xf0a8db),
    GRAY(0x403445),
    YELLOW(0xffff55),
    WHITE(0xf5e1f3),
    ;

    private final int hex;

    Color(int hex) {
        this.hex = hex;
    }

    public int hex() {
        return this.hex;
    }
}
