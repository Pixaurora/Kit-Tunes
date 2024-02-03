package net.pixaurora.kit_tunes.impl.scrobble;

import com.mojang.serialization.Codec;

import net.pixaurora.kit_tunes.impl.config.SerialType;

public record ScrobblerType(String name, Codec<? extends Scrobbler> codec) implements SerialType<Scrobbler> {
}
