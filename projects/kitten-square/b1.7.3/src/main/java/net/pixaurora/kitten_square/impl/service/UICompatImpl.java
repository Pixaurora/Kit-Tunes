package net.pixaurora.kitten_square.impl.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.resource.language.I18n;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextField;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextFieldBackground;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;
import net.pixaurora.kitten_heart.impl.service.UICompat;
import net.pixaurora.kitten_square.impl.FakeComponent;
import net.pixaurora.kitten_square.impl.SoundUtil;
import net.pixaurora.kitten_square.impl.ui.screen.ScreenImpl;
import net.pixaurora.kitten_square.impl.ui.toast.ToastManager;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextFieldImpl;

public class UICompatImpl implements UICompat {
    public static boolean openingNewScreen;

    private Minecraft client() {
        return Minecraft.INSTANCE;
    }

    public static String internalToMinecraftType(ResourcePath path) {
        String namespacePart = path.namespace() != "" ? path.namespace() + "/" : "";
        return "/assets/" + namespacePart + path.path();
    }

    public static String internalToMinecraftType(Component component) {
        if (component instanceof FakeComponent) {
            return ((FakeComponent) component).text();
        } else {
            throw new RuntimeException(
                    "Internal component is of an unconvertable type `" + component.getClass().getName() + "`!");
        }
    }

    public static net.minecraft.client.gui.screen.Screen internalToMinecraftType(Screen screen) {
        if (screen.minecraftScreen() != null) {
            return (net.minecraft.client.gui.screen.Screen) screen.minecraftScreen();
        } else {
            ScreenImpl minecraftScreen = new ScreenImpl(screen);
            screen.minecraftScreen(minecraftScreen);

            return minecraftScreen;
        }
    }

    @Override
    public void sendToast(net.pixaurora.kitten_cube.impl.ui.toast.Toast toast) {
        ToastManager.INSTANCE.queueToast(toast);
    }

    @Override
    public ResourcePath convertToRegularAsset(ResourcePath path) {
        return path;
    }

    @Override
    public ResourcePath convertToGuiAsset(ResourcePath path) {
        return path;
    }

    @Override
    public Component translatable(String key) {
        return new FakeComponent(I18n.translate(key));
    }

    @Override
    public Component translatableWithFallback(String key, String defaultText) {
        boolean translationExists = key != I18n.translate(key);

        Component component = translationExists ? this.translatable(key) : this.literal(defaultText);
        return component;
    }

    @Override
    public Component literal(String text) {
        return new FakeComponent(text);
    }

    @Override
    public int textHeight() {
        return 9;
    }

    @Override
    public int textWidth(Component text) {
        return this.client().textRenderer.getWidth(internalToMinecraftType(text));
    }

    @Override
    public void playSound(Sound sound) {
        this.client().soundSystem.play(SoundUtil.soundFromInternalID(sound), 1.0F, 1.0F);
    }

    @Override
    public void setScreen(Screen screen) {
        try {
            openingNewScreen = true;

            setScreen0(screen);
        } finally {
            openingNewScreen = false;
        }
    }

    private void setScreen0(Screen screen) {
        this.client().openScreen(internalToMinecraftType(screen));
    }

    @Override
    public void openURL(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            KitTunes.LOGGER.info("Couldn't convert `" + url + "` to URI!");
            return;
        }

        try {
            Class<?> clazz = Class.forName("java.awt.Desktop");
            Object object = clazz.getMethod("getDesktop").invoke(null);
            clazz.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable throwable) {
            KitTunes.LOGGER.error("Couldn't open link", throwable);
        }
    }

    @Override
    public TextBox createTextbox(List<Component> lines, Color color, int maxLineLength, Point pos) {
        List<String> text = lines.stream().map(UICompatImpl::internalToMinecraftType)
                .flatMap(line -> this.client().textRenderer.split(line,
                        maxLineLength).stream())
                .collect(Collectors.toList());

        return new TextBoxImpl(text, color, pos);
    }

    @Override
    public FileAccess accessResource(ResourcePath path) throws IOException {
        if (path.namespace().equals("")) {
            String name = path.path();
            return FileAccess.create(Paths.get(name));
        } else {
            throw new RuntimeException("Can't access regular files yet!");
        }
    }

    @Override
    public boolean isInWorld() {
        return Minecraft.INSTANCE.world != null;
    }

    @Override
    public TextField newTextField(TextFieldBackground<GuiTexture> background, Component defaultText, int maxLength) {
        return new TextFieldImpl(this.client().screen, this.client().textRenderer, background, defaultText, maxLength);
    }

    @Override
    public void addTextField(Screen screen, TextField field) {
        if (!(field instanceof TextFieldImpl)) {
            throw new RuntimeException(
                    "Internal text field is of an unconvertable type `" + field.getClass().getName() + "`!");
        }

        ((ScreenImpl) internalToMinecraftType(screen)).addTextField((TextFieldImpl) field);
    }
}
