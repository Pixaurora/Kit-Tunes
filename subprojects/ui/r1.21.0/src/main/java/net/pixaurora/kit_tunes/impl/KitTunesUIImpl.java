package net.pixaurora.kit_tunes.impl;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.gui.KitTunesScreenImpl;
import net.pixaurora.kit_tunes.impl.gui.KitTunesToastImpl;
import net.pixaurora.kit_tunes.impl.gui.MinecraftScreen;
import net.pixaurora.kit_tunes.impl.resource.ResourcePathUtils;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;
import net.pixaurora.kit_tunes.impl.ui.screen.Screen;
import net.pixaurora.kit_tunes.impl.ui.sound.Sound;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class KitTunesUIImpl implements KitTunesMinecraftUICompat {
    private final Minecraft client = Minecraft.getInstance();

    public static ResourceLocation resourceToMinecraftType(ResourcePath path) {
        return ResourceLocation.fromNamespaceAndPath(path.namespace(), path.path());
    }

    public static ResourceLocation resourceToMinecraftGuiSprite(ResourcePath path) {
        return resourceToMinecraftType(
                ResourcePathUtils.stripSuffixAndPrefix("textures/gui/sprites/", ".png", path).get());
    }

    public static MutableComponent componentToMinecraftType(Component component) {
        if (component instanceof FakeComponent) {
            return ((FakeComponent) component).parent;
        } else {
            throw new RuntimeException(
                    "Internal component is of an unconvertable type `" + component.getClass().getName() + "`!");
        }
    }

    @Override
    public void sendToast(net.pixaurora.kit_tunes.impl.ui.toast.Toast toast) {
        this.client.getToasts().addToast(new KitTunesToastImpl(toast));
    }

    @Override
    public ResourcePath convertToRegularAsset(ResourcePath path) {
        return path;
    }

    @Override
    public ResourcePath convertToGuiAsset(ResourcePath path) {
        return ResourcePathUtils.stripSuffixAndPrefix("textures/gui/sprites/", ".png", path).get();
    }

    @Override
    public Component translatable(String key) {
        return new FakeComponent(net.minecraft.network.chat.Component.translatable(key));
    }

    @Override
    public Component translatableWithFallback(String key, String defaultText) {
        return new FakeComponent(net.minecraft.network.chat.Component.translatableWithFallback(key, defaultText));
    }

    @Override
    public Component literal(String text) {
        return new FakeComponent(net.minecraft.network.chat.Component.literal(text));
    }

    @Override
    public int textHeight() {
        return this.client.font.lineHeight;
    }

    @Override
    public int textWidth(Component text) {
        return this.client.font.width(componentToMinecraftType(text));
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
            mcScreen = new KitTunesScreenImpl(screen);
        }
        this.client.setScreen(mcScreen);
    }

    @Override
    public void confirmURL(String url) {
        ConfirmLinkScreen.confirmLinkNow(this.client.screen, url);
    }

    @Override
    public List<Component> splitText(Component text, int lineWidth) {
        // TODO: Actually split the text.
        return List.of(text);
    }
}
