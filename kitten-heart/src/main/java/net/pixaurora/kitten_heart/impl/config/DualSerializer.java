package net.pixaurora.kitten_heart.impl.config;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface DualSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {
}
