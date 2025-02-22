package net.pixaurora.kitten_square.impl;

import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Minecraft.OS;
import net.pixaurora.kitten_heart.impl.KitTunes;

public interface URIOpener {
    public void open(URI uri) throws Throwable;

    public static URIOpener create() {
        if (Minecraft.getOs() == OS.LINUX) {
            return new XDGURIOpener();
        } else {
            return new DefaultURIOpener();
        }
    }

    public static class DefaultURIOpener implements URIOpener {
        @Override
        public void open(URI uri) throws Throwable {
            try {
                Class<?> clazz = Class.forName("java.awt.Desktop");
                Object object = clazz.getMethod("getDesktop").invoke(null);
                clazz.getMethod("browse", URI.class).invoke(object, uri);
            } catch (Throwable throwable) {
                KitTunes.LOGGER.error("Couldn't open link", throwable);
            }
        }
    }

    public static class XDGURIOpener implements URIOpener {
        @Override
        public void open(URI uri) throws Throwable {
            String[] xdgOpen = new String[]{"xdg-open", uri.toString()};

            Runtime.getRuntime().exec(xdgOpen);
        }}
}
