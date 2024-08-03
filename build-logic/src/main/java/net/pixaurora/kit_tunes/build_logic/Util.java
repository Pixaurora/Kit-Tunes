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

    public static String toString(Path path) { // Because on Windows, it will default to putting '\' instead of '/',
                                               // which doesn't work with Loom
        var parts = path.iterator();

        if (!parts.hasNext()) {
            return "";
        }

        StringBuilder stringConversion = new StringBuilder();

        stringConversion.append(parts.next());

        while (parts.hasNext()) {
            stringConversion.append('/');
            stringConversion.append(parts.next());
        }

        return stringConversion.toString();
    }
}
