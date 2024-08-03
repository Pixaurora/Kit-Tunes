package net.pixaurora.kit_tunes.build_logic.mod_resources_gen;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.pixaurora.kit_tunes.build_logic.Util;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.ModDependency;
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModInfoExtension;

public class ModJsonSerializer implements JsonSerializer<ModInfoExtension> {
    private static final String DEFAULT_INTERMEDIARY_MAPPINGS = "net.fabricmc:intermediary";

    @Override
    public JsonElement serialize(ModInfoExtension modInfo, Type typeOfSrc, JsonSerializationContext context) {
        var qmjRoot = new JsonObject();

        qmjRoot.add("quilt_loader", this.createQuiltLoaderBlock(modInfo));

        var mixin = modInfo.getMixinFile();
        if (mixin.isPresent()) {
            qmjRoot.addProperty("mixin", mixin.get());
        }

        var modmenuBlock = this.createModmenuBlock(modInfo);
        if (modmenuBlock.size() > 0) {
            qmjRoot.add("modmenu", modmenuBlock);
        }

        return qmjRoot;
    }

    private JsonElement createQuiltLoaderBlock(ModInfoExtension modInfo) {
        var quiltLoaderBlock = new JsonObject();

        quiltLoaderBlock.addProperty("id", modInfo.getId().get());
        quiltLoaderBlock.addProperty("version", modInfo.getVersion().get());

        quiltLoaderBlock.add("metadata", this.createMetadataBlock(modInfo));

        quiltLoaderBlock.addProperty("intermediate_mappings",
                modInfo.getIntermediaryMappings().getOrElse(DEFAULT_INTERMEDIARY_MAPPINGS));

        var entrypoints = this.createEntrypoints(modInfo);
        if (entrypoints.size() > 0) {
            quiltLoaderBlock.add("entrypoints", entrypoints);
        }

        var dependsBlock = this.createDependsBlock(modInfo);
        if (dependsBlock.size() > 0) {
            quiltLoaderBlock.add("depends", dependsBlock);
        }

        return quiltLoaderBlock;
    }

    private JsonElement createMetadataBlock(ModInfoExtension modInfo) {
        var metadata = modInfo.getMetadata();

        var metadataBlock = new JsonObject();

        metadataBlock.addProperty("name", metadata.getName().get());
        metadataBlock.addProperty("description", metadata.getDescription().get());
        var iconPath = metadata.getModIcon().get().relativeDestination();
        metadataBlock.addProperty("icon", Util.toString(iconPath));

        return metadataBlock;
    }

    private JsonObject createEntrypoints(ModInfoExtension modInfo) {
        var entrypoints = new JsonObject();

        for (var entrypoint : modInfo.getEntrypoints().get().entrySet()) {
            var classes = new JsonArray();

            for (var classPath : entrypoint.getValue()) {
                classes.add(classPath);
            }

            entrypoints.add(entrypoint.getKey(), classes.size() == 1 ? classes.get(0) : classes);
        }

        return entrypoints;
    }

    private JsonArray createDependsBlock(ModInfoExtension modInfo) {
        var dependsBlock = new JsonArray();

        for (ModDependency dependency : modInfo.getDependencies().get()) {
            dependsBlock.add(dependency.toJson());
        }

        return dependsBlock;
    }

    private JsonObject createModmenuBlock(ModInfoExtension modInfo) {
        var metadata = modInfo.getMetadata();

        var modmenuBlock = new JsonObject();

        if (metadata.isLibrary()) {
            var badges = new JsonArray();
            badges.add("library");

            modmenuBlock.add("badges", badges);
        }

        var parentModId = metadata.getParentModId();
        if (parentModId.isPresent()) {
            modmenuBlock.addProperty("parent", parentModId.get());
        }

        return modmenuBlock;
    }
}
