package net.pixaurora.kitten_sounds.impl.mixin;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.ResourceDownloadThread;

@Mixin(ResourceDownloadThread.class)
public class ResourceDownloadThreadMixin {
    private static final List<String> SKIPPED_DIRECTORIES = Arrays.asList("music", "newmusic", "streaming");

    @Shadow
    private Minecraft client;

    @Inject(method = "m_9093389", at = @At(value = "HEAD"), cancellable = true)
    public void skipDownloadingMusic(URL url, String path, long l, int i, CallbackInfo cInfo) {
        if (SKIPPED_DIRECTORIES.contains(directory(path))) {
            cInfo.cancel();
        }
    }

    @Inject(method = "m_9704077", at = @At(value = "HEAD"), cancellable = true)
    public void skipAddingLocalMusic(File directory, String path, CallbackInfo cInfo) {
        if (SKIPPED_DIRECTORIES.contains(directory(path))) {
            cInfo.cancel();
        }
    }

    private String directory(String path) {
        int firstSlash = path.indexOf("/");

        return firstSlash < 0 ? path : path.substring(0, firstSlash);
    }
}
