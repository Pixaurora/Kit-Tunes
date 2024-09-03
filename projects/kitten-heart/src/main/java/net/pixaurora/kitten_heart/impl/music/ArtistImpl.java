package net.pixaurora.kitten_heart.impl.music;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;
import net.pixaurora.kitten_heart.impl.config.DualSerializerFromString;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.resource.TransformsTo;

public class ArtistImpl implements Artist {
    private final ResourcePath path;

    private final String name;
    private final Optional<ResourcePath> iconPath;

    public ArtistImpl(ResourcePath path, String name, Optional<ResourcePath> iconPath) {
        this.path = path;
        this.name = name;
        this.iconPath = iconPath;
    }

    @Override
    public ResourcePath path() {
        return this.path;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Optional<ResourcePath> iconPath() {
        return this.iconPath;
    }

    public static class FromData implements TransformsTo<Artist> {
        private String name;
        @SerializedName("icon_path")
        @Nullable
        private ResourcePath iconPath;

        public Artist transform(ResourcePath path) {
            return new ArtistImpl(path, this.name, Optional.ofNullable(this.iconPath));
        }
    }

    public static class FromPath implements TransformsTo<Artist> {
        public static final DualSerializer<FromPath> SERIALIZER = new DualSerializerFromString<>(
                artist -> artist.artistPath.representation(),
                representation -> new FromPath(ResourcePathImpl.fromString(representation)));

        private final ResourcePath artistPath;

        public FromPath(ResourcePath artistPath) {
            this.artistPath = artistPath;
        }

        @Override
        public Artist transform(ResourcePath path) {
            return this.transform();
        }

        public Artist transform() {
            Optional<Artist> artist = MusicMetadata.getArtist(this.artistPath);

            if (artist.isPresent()) {
                return artist.get();
            } else {
                throw new RuntimeException("No Track found with path `" + artistPath.representation() + "`!");
            }
        }
    }
}
