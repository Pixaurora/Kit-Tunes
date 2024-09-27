package net.pixaurora.kitten_square.impl.service;

import java.io.IOException;
import java.util.List;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;
import net.pixaurora.kitten_heart.impl.service.MinecraftUICompat;
import net.pixaurora.kitten_square.impl.FakeComponent;
import net.pixaurora.kitten_square.impl.SoundUtil;
import net.pixaurora.kitten_square.impl.ui.screen.MinecraftScreen;
import net.pixaurora.kitten_square.impl.ui.screen.ScreenImpl;
import net.pixaurora.kitten_square.impl.ui.toast.ToastImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;

public class UICompatImpl implements MinecraftUICompat {
    private final Minecraft client = Minecraft.getInstance();

    public static ResourceLocation internalToMinecraftType(ResourcePath path) {
        return new ResourceLocation(path.namespace(), path.path());
    }

    public static MutableComponent internalToMinecraftType(Component component) {
        if (component instanceof FakeComponent) {
            return ((FakeComponent) component).gameVer();
        } else {
            throw new RuntimeException(
                    "Internal component is of an unconvertable type `" + component.getClass().getName() + "`!");
        }
    }

    @Override
    public void sendToast(net.pixaurora.kitten_cube.impl.ui.toast.Toast toast) {
        this.client.getToasts().addToast(new ToastImpl(toast));
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
        return new FakeComponent(new TranslatableComponent(key));
    }

    @Override
    public Component translatableWithFallback(String key, String defaultText) {
        Component component = I18n.exists(key) ? this.translatable(key) : this.literal(defaultText);
        return component;
    }

    @Override
    public Component literal(String text) {
        return new FakeComponent(new TextComponent(text));
    }

    @Override
    public int textHeight() {
        return this.client.font.lineHeight;
    }

    @Override
    public int textWidth(Component text) {
        return this.client.font.width(internalToMinecraftType(text));
    }

    @Override
    public void playSound(Sound sound) {
        this.client.getSoundManager().play(SoundUtil.soundFromInternalID(sound));
    }

    @Override
    public void setScreen(Screen screen) {
        net.minecraft.client.gui.screens.Screen mcScreen;
        if (screen instanceof MinecraftScreen) {
            mcScreen = ((MinecraftScreen) screen).parent();
        } else {
            mcScreen = new ScreenImpl(screen);
        }
        this.client.setScreen(mcScreen);
    }

    @Override
    public void openURL(String url) {
        Util.getPlatform().openUri(url);
    }

    @Override
    public TextBox createTextbox(List<Component> lines, Color color, int maxLineLength, Point pos) {
        List<FormattedCharSequence> text = lines.stream().map(UICompatImpl::internalToMinecraftType)
                .flatMap(line -> this.client.font.split(line, maxLineLength).stream()).toList();

        return new TextBoxImpl(text, color, pos);
    }

    @Override
    public FileAccess accessResource(ResourcePath path) throws IOException {
        Resource resource = this.client.getResourceManager().getResource(internalToMinecraftType(path));

        return FileAccess.create(resource.getInputStream());
    }
}
