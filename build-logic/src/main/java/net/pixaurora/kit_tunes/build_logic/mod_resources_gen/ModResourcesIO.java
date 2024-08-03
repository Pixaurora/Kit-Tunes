package net.pixaurora.kit_tunes.build_logic.mod_resources_gen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.pixaurora.kit_tunes.build_logic.Serialization;

public class ModResourcesIO {
    public static JsonElement loadJson(Path location) throws IOException {
        var data = Files.newBufferedReader(location, StandardCharsets.UTF_8);

        return new JsonParser().parse(data);
    }

    public static void writeJson(Path destination, JsonElement data) throws IOException {
        var serializer = Serialization.getSerializer();

        ensureHasDirectories(destination);
        var writer = Files.newBufferedWriter(destination, StandardCharsets.UTF_8);

        serializer.toJson(data, serializer.newJsonWriter(writer));
        writer.close();
    }

    public static void ensureHasDirectories(Path location) throws IOException {
        Files.createDirectories(location.getParent());
    }
}
