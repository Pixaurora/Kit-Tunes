package net.pixaurora.kit_tunes.build_logic.mod_resources_gen.data.version;

public record ExactVersion(String version) implements Version.Simple {
    @Override
    public String versionString() {
        return "=" + this.version;
    }
}
