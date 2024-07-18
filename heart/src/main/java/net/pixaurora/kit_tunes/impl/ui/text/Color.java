package net.pixaurora.kit_tunes.impl.ui.text;

/**
 * The colors used for text in Minecraft since ancient time.
 */
public enum Color {
    BLACK('0', 0x000000), DARK_BLUE('1', 0x0000aa), DARK_GREEN('2', 0x00aa00), DARK_AQUA('3', 0x00aaaa),
    DARK_RED('4', 0xaa0000), DARK_PURPLE('5', 0xaa00aa), GOLD('6', 0xffaa00), GRAY('7', 0xaaaaaa),
    DARK_GRAY('8', 0x555555), BLUE('9', 0x5555ff), GREEN('a', 0x55ff55), AQUA('b', 0x55ffff), RED('c', 0xff5555),
    LIGHT_PURPLE('d', 0xff55ff), YELLOW('e', 0xffff55), WHITE('f', 0xffffff);

    private final char chatCode;
    private final int hex;

    Color(char chatCode, int hex) {
        this.chatCode = chatCode;
        this.hex = hex;
    }

    public char chatCode() {
        return this.chatCode;
    }

    public int hex() {
        return this.hex;
    }
}
