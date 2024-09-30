package net.pixaurora.kitten_cube.impl.ui.texture;

import java.util.ArrayList;
import java.util.List;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;

public class AnimatedGuiTexture implements GuiTexture {
    private final int millisPerFrame;
    private final List<GuiTexture> frames;

    private AnimatedGuiTexture(int millisPerFrame, List<GuiTexture> frames) {
        this.millisPerFrame = millisPerFrame;
        this.frames = frames;
    }

    public static AnimatedGuiTexture create(int millisPerFrame, List<GuiTexture> frames) {
        return new AnimatedGuiTexture(millisPerFrame, frames);
    }

    public static AnimatedGuiTexture create(int millisPerFrame, String namespace, String basePath, int frameCount,
            Size size) {
        List<GuiTexture> frames = new ArrayList<>();

        for (int i = 0; i < frameCount; i++) {
            int frame = i + 1; // Aseprite exports animations starting from 1

            frames.add(GuiTexture.of(new ResourcePathImpl(namespace, basePath + frame + ".png"), size));
        }

        return create(millisPerFrame, frames);
    }

    private GuiTexture texture() {
        long frame = System.currentTimeMillis() / millisPerFrame % frames.size();
        return frames.get((int) frame);
    }

    @Override
    public ResourcePath path() {
        return this.texture().path();
    }

    @Override
    public Size size() {
        return this.texture().size();
    }
}
