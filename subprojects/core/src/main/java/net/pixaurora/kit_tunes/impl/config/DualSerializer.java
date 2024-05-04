package net.pixaurora.kit_tunes.impl.config;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface DualSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {
}
