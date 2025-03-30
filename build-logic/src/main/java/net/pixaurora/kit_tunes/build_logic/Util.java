package net.pixaurora.kit_tunes.build_logic;

import java.nio.file.Path;

public class Util {
    public static String titleCase(String text) {
        StringBuilder titleCased = new StringBuilder(text.length());

        char previousChar = ' ';
        for (char character : text.toCharArray()) {
            character = previousChar == ' ' ? Character.toUpperCase(character) : character;

            titleCased.append(character);
            previousChar = character;
        }

        return titleCased.toString();
    }
}
