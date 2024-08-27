package net.pixaurora.kitten_heart.impl.config;

import java.lang.reflect.Type;
import java.util.function.Function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class DualSerializerFromString<T> implements DualSerializer<T> {
    private final Function<T, String> toStringMethod;
    private final Function<String, T> fromStringMethod;

    public DualSerializerFromString(Function<T, String> toStringMethod, Function<String, T> fromStringMethod) {
        this.fromStringMethod = fromStringMethod;
        this.toStringMethod = toStringMethod;
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        String representation = this.toStringMethod.apply(src);
        return new JsonPrimitive(representation);
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String representation = json.getAsJsonPrimitive().getAsString();
        return this.fromStringMethod.apply(representation);
    }
}
