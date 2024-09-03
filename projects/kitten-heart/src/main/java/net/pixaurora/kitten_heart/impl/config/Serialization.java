package net.pixaurora.kitten_heart.impl.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.music.ArtistImpl;
import net.pixaurora.kitten_heart.impl.music.TrackImpl;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import net.pixaurora.kitten_heart.impl.scrobble.Scrobbler;

public class Serialization {
    private static Gson SERIALIZER = createSerializer();

    private static final Gson createSerializer() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .registerTypeAdapter(ScrobblerCache.class, new ScrobblerCache.Serializer())
                .registerTypeAdapter(Scrobbler.class, Scrobbler.TYPES.itemSerialzier())
                .registerTypeAdapter(ResourcePath.class, ResourcePathImpl.SERIALIZER)
                .registerTypeAdapter(ArtistImpl.FromPath.class, ArtistImpl.FromPath.SERIALIZER)
                .registerTypeAdapter(TrackImpl.TransformsToTrack.class, new TrackImpl.TransformsToTrack.Serializer())
                .registerTypeAdapter(TrackImpl.FromData.class, new TrackImpl.FromData.Serializer())
                .registerTypeAdapter(TrackImpl.FromPath.class, TrackImpl.FromPath.SERIALIZER)
                .create();
    }

    public static Gson serializer() {
        return SERIALIZER;
    }
}
