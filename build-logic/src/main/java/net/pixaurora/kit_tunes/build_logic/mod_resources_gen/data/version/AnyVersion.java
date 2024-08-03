package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version;

public class AnyVersion implements Version.Simple {
    public static AnyVersion INSTANCE = new AnyVersion();

    @Override
    public String versionString() {
        return "*";
    }
}
